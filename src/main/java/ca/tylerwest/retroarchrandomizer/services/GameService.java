package ca.tylerwest.retroarchrandomizer.services;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ca.tylerwest.retroarchrandomizer.configuration.Configuration;
import ca.tylerwest.retroarchrandomizer.events.GameLifecycleEvent;
import ca.tylerwest.retroarchrandomizer.events.GameLifecycleListener;
import ca.tylerwest.retroarchrandomizer.model.Game;

public class GameService {

	private Configuration configuration;
	private PlaylistService playlistService;
	
	private List<Game> games;
	private Random random;
	
	private Process currentProcess;
	private boolean running;
	
	private List<GameLifecycleListener> lifecycleListeners;

	public GameService(Configuration configuration) {
		this.configuration = configuration;
		this.playlistService = new PlaylistService();
		this.lifecycleListeners = new LinkedList<GameLifecycleListener>();
		
		this.random = new Random(System.currentTimeMillis());
		this.games = loadGames();
		this.running = false;
	}

	private List<Game> loadGames() {
		List<Game> masterList = new LinkedList<Game>();
		try (DirectoryStream<Path> paths = Files.newDirectoryStream(configuration.getPlaylistDirectory(), "*.lpl")) {
			for (Path playlist : paths) {
				List<Game> collection = playlistService.parsePlaylist(playlist);

				System.out.println("Added " + collection.size() + " games from " + playlist.getFileName());
				masterList.addAll(collection);
			}

			Collections.shuffle(masterList, new Random(System.currentTimeMillis()));
		} catch (IOException e) {
			System.err.println("Failed to load games from playlist: " + e.getMessage());
		}
		return masterList;
	}
	
	public void start() {
		running = true;
		while (running) {
			Game randomGame = randomGame();
			
			fireGameStartedEvents(randomGame);
			launchAndWaitForGame(randomGame);
			fireGameStoppedEvents(randomGame);
		}
	}
	
	public void stop() {
		running = false;
		currentProcess.destroy();
	}
	
	private Game randomGame() {
		int index = random.nextInt(games.size());
		return games.get(index);
	}
	
	private void launchAndWaitForGame(Game game) {
		try {
			ProcessBuilder pb = new ProcessBuilder(configuration.getRetroarchExecutable().toString(), "-L", game.getCore(), game.getRom());
			currentProcess = pb.start();
			currentProcess.waitFor(configuration.getGameplayTimeout(), configuration.getGameplayTimeoutUnit());
			currentProcess.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addGameLifecycleListener(GameLifecycleListener listener) {
		lifecycleListeners.add(listener);
	}
	
	public void removeGameLifecycleListener(GameLifecycleListener listener) {
		lifecycleListeners.remove(listener);
	}
	
	private void fireGameStartedEvents(Game game) {
		lifecycleListeners.forEach(listener -> listener.onGameStarted(new GameLifecycleEvent(game)));
	}
	
	private void fireGameStoppedEvents(Game game) {
		lifecycleListeners.forEach(listener -> listener.onGameStopped(new GameLifecycleEvent(game)));
	}
}

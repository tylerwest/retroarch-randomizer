package ca.tylerwest.retroarchrandomizer;

import java.awt.AWTException;

import javax.swing.SwingUtilities;

import ca.tylerwest.retroarchrandomizer.configuration.Configuration;
import ca.tylerwest.retroarchrandomizer.services.GameService;
import ca.tylerwest.retroarchrandomizer.ui.RandomizerUI;

public class RetroArchRandomizer {
	
	private GameService gameService;
	private RandomizerUI randomizerUI;

	public RetroArchRandomizer(Configuration configuration) {
		this.gameService = new GameService(configuration);
		this.randomizerUI = new RandomizerUI(gameService, configuration);
		
		this.gameService.addGameLifecycleListener(this.randomizerUI.getEventHandler());
	}
	
	public void start() {
		this.gameService.start();
	}
	
	public void ui() {
		this.randomizerUI.start();
	}
	
	public static void main(String[] args) throws AWTException {
		RetroArchRandomizer randomizer = new RetroArchRandomizer(new Configuration(args));
		
		SwingUtilities.invokeLater(() -> randomizer.ui());
		randomizer.start();
	}

}

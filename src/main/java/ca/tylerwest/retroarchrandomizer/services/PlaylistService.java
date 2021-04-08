package ca.tylerwest.retroarchrandomizer.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import ca.tylerwest.retroarchrandomizer.model.Game;

public class PlaylistService {

	public PlaylistService() {

	}
	
	public List<Game> parsePlaylist(Path playlist) {
		List<Game> games = new LinkedList<Game>();
		
		try {
			String coreSearchCriteria = "\"default_core_path\": \"";
			String romSearchCriteria = "\"path\": \"";
			String labelSearchCriteria = "\"label\": \"";

			String system = playlist.getFileName().toString().replaceAll(".lpl", "");
			String core = null;
			String rom = null;
			String label = null;

			List<String> lines = Files.lines(playlist).map(String::trim).collect(Collectors.toList());

			for (String l : lines) {
				if (l.contains(coreSearchCriteria) && core == null)
					core = l.substring(coreSearchCriteria.length(), l.lastIndexOf("\","));

				if (l.contains(romSearchCriteria) && rom == null)
					rom = l.substring(romSearchCriteria.length(), l.lastIndexOf("\","));

				if (l.contains(labelSearchCriteria) && label == null)
					label = l.substring(labelSearchCriteria.length(), l.lastIndexOf("\","));

				if (core != null && rom != null && label != null) {
					games.add(new Game(system, core, rom, label));

					rom = null;
					label = null;
				}
			}
		} catch (IOException e) {
			System.err.println("Failed to parse playlist file: " + playlist);
		}
		
		return games;
	}
}

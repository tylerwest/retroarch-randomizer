package ca.tylerwest.retroarchrandomizer.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public final class Configuration {

	private Path retroarchDirectory = Defaults.DEFAULT_RETROARCH_DIR;
	private Path playlistDirectory = Defaults.DEFAULT_RETROARCH_PLAYLIST_DIR;
	private Path retroarchExecutable = Defaults.DEFAULT_RETROARCH_EXECUTABLE;

	private int gameplayTimeout = Defaults.DEFAULT_GAMEPLAY_TIMEOUT;
	private TimeUnit gameplayTimeoutUnit = Defaults.DEFAULT_GAMEPLAY_TIMEOUT_UNIT;

	public Configuration(String[] args) {
		parseArguments(args);
	}

	private void parseArguments(String[] args) {
		Options options = null;
		try {
			options = new Options();
			CommandLineParser parser = new DefaultParser();
			
			options.addOption(Option.builder("d")
					.desc(String.format("RetroArch directory (default: %s)", Defaults.DEFAULT_RETROARCH_DIR))
					.hasArg()
					.argName("dir")
					.longOpt("install-directory")
					.type(String.class)
					.build());
			
			options.addOption(Option.builder("p")
					.desc(String.format("RetroArch playlist directory (default: %s)", Defaults.DEFAULT_RETROARCH_PLAYLIST_DIR))
					.hasArg()
					.argName("dir")
					.longOpt("playlist-directory")
					.type(String.class)
					.build());
			
			options.addOption(Option.builder("e")
					.desc(String.format("RetroArch executable path (default: %s)", Defaults.DEFAULT_RETROARCH_EXECUTABLE))
					.hasArg()
					.argName("path")
					.longOpt("executable-path")
					.type(String.class)
					.build());
			
			options.addOption(Option.builder("t")
					.desc(String.format("Interval between games (default: %s %s)", String.valueOf(Defaults.DEFAULT_GAMEPLAY_TIMEOUT), String.valueOf(Defaults.DEFAULT_GAMEPLAY_TIMEOUT_UNIT)))
					.hasArg()
					.argName("interval")
					.longOpt("time-interval")
					.type(Integer.class)
					.build());
			
			options.addOption(Option.builder("u")
					.desc(String.format("Interval time unit (default: %s)", String.valueOf(Defaults.DEFAULT_GAMEPLAY_TIMEOUT_UNIT)))
					.hasArg()
					.argName("unit")
					.longOpt("time-interval-unit")
					.type(String.class)
					.build());
			
			options.addOption(Option.builder("h")
					.longOpt("help")
					.desc("Display this help message.")
					.build());
			
			CommandLine commandLine = parser.parse(options, args);
			
			if (commandLine.hasOption("h")) {
				printHelp(options);
				System.exit(0);
			}
			
			if (commandLine.hasOption("install-directory"))
				retroarchDirectory = Paths.get(commandLine.getOptionValue("install-directory"));
			
			if (commandLine.hasOption("playlist-directory"))
				playlistDirectory = Paths.get(commandLine.getOptionValue("playlist-directory"));
			
			if (commandLine.hasOption("executable-path"))
				retroarchExecutable = Paths.get(commandLine.getOptionValue("executable-path"));
			
			if (commandLine.hasOption("time-interval"))
				gameplayTimeout = Integer.valueOf(commandLine.getOptionValue("time-interval"));
			
			if (commandLine.hasOption("time-interval-unit"))
				gameplayTimeoutUnit = TimeUnit.valueOf(commandLine.getOptionValue("time-interval-unit").toUpperCase());
			
		} catch (ParseException | NumberFormatException e) {
			System.err.println(e.getMessage());
			printHelp(options);
			System.exit(-1);
		}
	}

	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setWidth(150);
		formatter.printHelp("java -jar retroarch-randomizer.jar",
				"RetroArch Randomizer scans your RetroArch library configuration files, "
						+ "selects a random game and plays it for a set period of time. "
						+ "After the timeout has elapsed, it closes the game and selects another.",
				options, "https://github.com/tylerwest", true);
	}

	public Path getRetroarchDirectory() {
		return retroarchDirectory;
	}

	public Path getRetroarchExecutable() {
		return retroarchExecutable;
	}

	public Path getPlaylistDirectory() {
		return playlistDirectory;
	}

	public int getGameplayTimeout() {
		return gameplayTimeout;
	}

	public TimeUnit getGameplayTimeoutUnit() {
		return gameplayTimeoutUnit;
	}

	private static final class Defaults {
		private static final Path DEFAULT_RETROARCH_DIR = Paths.get(System.getenv("appdata"), "RetroArch");
		private static final Path DEFAULT_RETROARCH_PLAYLIST_DIR = Paths.get(System.getenv("appdata"), "RetroArch",
				"playlists");
		private static final Path DEFAULT_RETROARCH_EXECUTABLE = Paths.get(System.getenv("appdata"), "RetroArch",
				"retroarch.exe");

		private static final int DEFAULT_GAMEPLAY_TIMEOUT = 10;
		private static final TimeUnit DEFAULT_GAMEPLAY_TIMEOUT_UNIT = TimeUnit.MINUTES;
	}
}

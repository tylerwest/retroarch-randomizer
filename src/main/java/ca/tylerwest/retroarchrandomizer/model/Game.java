package ca.tylerwest.retroarchrandomizer.model;

public class Game {
	private String system;
	private String core;
	private String rom;
	private String label;

	public Game(String system, String core, String rom, String label) {
		super();
		this.system = system;
		this.core = core;
		this.rom = rom;
		this.label = label;
	}

	public String getSystem() {
		return system;
	}

	public String getCore() {
		return core;
	}

	public String getRom() {
		return rom;
	}

	public String getLabel() {
		return label;
	}
}
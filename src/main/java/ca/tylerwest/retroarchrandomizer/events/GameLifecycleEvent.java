package ca.tylerwest.retroarchrandomizer.events;

import ca.tylerwest.retroarchrandomizer.model.Game;

public class GameLifecycleEvent {
	private Game game;

	public GameLifecycleEvent(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
}

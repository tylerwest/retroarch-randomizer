package ca.tylerwest.retroarchrandomizer.events;

public interface GameLifecycleListener {
	void onGameStarted(GameLifecycleEvent event);
	void onGameStopped(GameLifecycleEvent event);
}

package ca.tylerwest.retroarchrandomizer.ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import ca.tylerwest.retroarchrandomizer.configuration.Configuration;
import ca.tylerwest.retroarchrandomizer.events.GameLifecycleEvent;
import ca.tylerwest.retroarchrandomizer.events.GameLifecycleListener;
import ca.tylerwest.retroarchrandomizer.services.GameService;
import ca.tylerwest.retroarchrandomizer.utils.GraphicsUtilities;

@SuppressWarnings("serial")
public class RandomizerUI extends JFrame {

	private Configuration configuration;
	private GameService gameService;
	
	private RandomizerUIGameLifecycleEventHandler eventHandler;
	private TimerPanel timerPanel;
	
	public RandomizerUI(GameService gameService, Configuration configuration) {
		this.gameService = gameService;
		this.configuration = configuration;
		
		this.eventHandler = new RandomizerUIGameLifecycleEventHandler();
		
		try {
			initializeFrameProperties();
			initializeTimerPanel();
			initializeTrayIcon();
		} catch (AWTException e) {
			System.err.println("Failed to initialize tray icon: " + e.getMessage());
		}
	}
	
	private void initializeFrameProperties() {
		setAlwaysOnTop(true);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initializeTimerPanel() {
		this.timerPanel = new TimerPanel(configuration);
		getContentPane().add(timerPanel);
	}
	
	private void initializeTrayIcon() throws AWTException {
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.green);
		graphics.fillRect(0, 0, 32, 32);
		graphics.dispose();
		TrayIcon trayIcon = new TrayIcon(image);
		trayIcon.addActionListener(a -> {
			gameService.stop();
			System.exit(0);
		});
		SystemTray.getSystemTray().add(trayIcon);
	}
	
	public void start() {
		pack();
		setVisible(true);

		Rectangle monitorBounds = GraphicsUtilities.getMonitorBounds();
		setLocation(monitorBounds.x, monitorBounds.y);
		setSize(monitorBounds.width, 50);
	}
	
	public RandomizerUIGameLifecycleEventHandler getEventHandler() {
		return eventHandler;
	}

	private class RandomizerUIGameLifecycleEventHandler implements GameLifecycleListener {
		@Override
		public void onGameStarted(GameLifecycleEvent event) {
			timerPanel.start(event.getGame());
		}
		
		@Override
		public void onGameStopped(GameLifecycleEvent event) {
			timerPanel.stop();
		}
	}

}

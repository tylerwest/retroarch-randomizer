package ca.tylerwest.retroarchrandomizer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import ca.tylerwest.retroarchrandomizer.configuration.Configuration;
import ca.tylerwest.retroarchrandomizer.model.Game;
import ca.tylerwest.retroarchrandomizer.utils.GraphicsUtilities;

@SuppressWarnings("serial")
public class TimerPanel extends JPanel {

	private Configuration configuration;
	
	private long startTime;
	private long duration;
	private JLabel timerLabel;
	private Timer timer;
	private Game game;

	public TimerPanel(Configuration configuration) {
		this.configuration = configuration;
		
		setLayout(new BorderLayout());
		setBackground(Color.black);

		timerLabel = new JLabel();
		timerLabel.setForeground(Color.green);
		add(timerLabel, BorderLayout.CENTER);

		timer = new Timer(10, a -> {
			if (startTime < 0) {
				startTime = System.currentTimeMillis();
			}
			long now = System.currentTimeMillis();
			long clockTime = now - startTime;
			if (clockTime >= duration) {
				clockTime = duration;
				timer.stop();
			}

			SimpleDateFormat df = new SimpleDateFormat("mm:ss:SSS");
			timerLabel.setText(df.format(duration - clockTime) + " - " + game.getLabel() + " - " + game.getSystem());
		});
	}

	public void start(Game game) {
		this.game = game;
		int fontSize = (GraphicsUtilities.getMonitorBounds().width) / (game.getLabel().length() + game.getSystem().length());
		Font font = Font.decode("Courier BOLD " + fontSize);
		timerLabel.setFont(font);
		startTime = -1;
		duration = TimeUnit.MILLISECONDS.convert(configuration.getGameplayTimeout(), configuration.getGameplayTimeoutUnit());
		timer.setInitialDelay(0);
		timer.start();
	}

	public void stop() {
		timer.stop();
	}
}

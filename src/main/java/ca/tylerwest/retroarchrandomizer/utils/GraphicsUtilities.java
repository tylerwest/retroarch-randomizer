package ca.tylerwest.retroarchrandomizer.utils;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

public final class GraphicsUtilities {

	private GraphicsUtilities() {
		
	}
	
	public static Rectangle getMonitorBounds() {
		Rectangle virtualBounds = new Rectangle();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		for (int j = 0; j < gs.length; j++) {
			GraphicsDevice gd = gs[j];
			GraphicsConfiguration[] gc = gd.getConfigurations();
			for (int i = 0; i < gc.length; i++) {
				Rectangle bounds = gc[i].getBounds();
				virtualBounds = virtualBounds.union(bounds);
			}
		}
		return virtualBounds;
	}
}

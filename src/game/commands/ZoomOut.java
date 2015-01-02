package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the game world to have the world window zoom out.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class ZoomOut extends AbstractAction {

	private static ZoomOut command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private ZoomOut() {
		super("Zoom Out");
	}

	private ZoomOut(String name) {
		super(name);
	}
	
	private ZoomOut(GameWorld gw) {
		super("Zoom Out");
		target = gw;
	}

	private ZoomOut(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static ZoomOut getZoomOut() {
		if (command == null)
			command = new ZoomOut();
		return command;
	}

	public static ZoomOut getZoomOut(String name) {
		if (command == null)
			command = new ZoomOut(name);
		return command;
	}
	
	public static ZoomOut getZoomOut(GameWorld gw) {
		if (command == null)
			command = new ZoomOut(gw);
		return command;
	}

	public static ZoomOut getZoomOut(String name, GameWorld gw) {
		if (command == null)
			command = new ZoomOut(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Will only zoom out if the game is not paused.
		if (target.getPlay())
			target.zoomOut();
	}
}

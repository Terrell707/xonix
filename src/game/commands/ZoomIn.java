package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the game world to have the view zoom in.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class ZoomIn extends AbstractAction {

	private static ZoomIn command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private ZoomIn() {
		super("Zoom In");
	}

	private ZoomIn(String name) {
		super(name);
	}
	
	private ZoomIn(GameWorld gw) {
		super("Zoom In");
		target = gw;
	}

	private ZoomIn(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static ZoomIn getZoomIn() {
		if (command == null)
			command = new ZoomIn();
		return command;
	}

	public static ZoomIn getZoomIn(String name) {
		if (command == null)
			command = new ZoomIn(name);
		return command;
	}
	
	public static ZoomIn getZoomIn(GameWorld gw) {
		if (command == null)
			command = new ZoomIn(gw);
		return command;
	}

	public static ZoomIn getZoomIn(String name, GameWorld gw) {
		if (command == null)
			command = new ZoomIn(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Will only zoom in if the game is not paused.
		if (target.getPlay())
			target.zoomIn();
	}
}

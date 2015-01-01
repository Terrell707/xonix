package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the game world that captures a random number of squares.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class CaptureGroup extends AbstractAction {

	private static CaptureGroup command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private CaptureGroup() {
		super("Capture Squares");
	}

	private CaptureGroup(String name) {
		super(name);
	}
	
	private CaptureGroup(GameWorld gw) {
		super("Capture Squares");
		target = gw;
	}
	
	private CaptureGroup(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static CaptureGroup getCaptureGroup() {
		if (command == null)
			command = new CaptureGroup();
		return command;
	}

	public static CaptureGroup getCaptureGroup(String name) {
		if (command == null)
			command = new CaptureGroup(name);
		return command;
	}
	
	public static CaptureGroup getCaptureGroup(GameWorld gw) {
		if (command == null)
			command = new CaptureGroup(gw);
		return command;
	}
	
	public static CaptureGroup getCaptureGroup(String name, GameWorld gw) {
		if (command == null)
			command = new CaptureGroup(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.captureGroup();
	}

}

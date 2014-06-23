package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the game world to move world window up.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class PanUp extends AbstractAction {

	private static PanUp command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private PanUp() {
		super("Pan Up");
	}

	private PanUp(String name) {
		super(name);
	}
	
	private PanUp(GameWorld gw) {
		super("Pan Up");
		target = gw;
	}

	private PanUp(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static PanUp getPanUp() {
		if (command == null)
			command = new PanUp();
		return command;
	}

	public static PanUp getPanUp(String name) {
		if (command == null)
			command = new PanUp(name);
		return command;
	}
	
	public static PanUp getPanUp(GameWorld gw) {
		if (command == null)
			command = new PanUp(gw);
		return command;
	}

	public static PanUp getPanUp(String name, GameWorld gw) {
		if (command == null)
			command = new PanUp(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.panUp();
	}
}

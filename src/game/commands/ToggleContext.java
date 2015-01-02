package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the Game World that toggles drawing the Sweeper objects' context lines.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class ToggleContext extends AbstractAction {

	private static ToggleContext command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private ToggleContext() {
		super("Sweeper Context Lines");
	}

	private ToggleContext(String name) {
		super(name);
	}
	
	private ToggleContext(GameWorld gw) {
		super("Sweeper Context Lines");
		target = gw;
	}

	private ToggleContext(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static ToggleContext getToggleContext() {
		if (command == null)
			command = new ToggleContext();
		return command;
	}

	public static ToggleContext getToggleContext(String name) {
		if (command == null)
			command = new ToggleContext(name);
		return command;
	}
	
	public static ToggleContext getToggleContext(GameWorld gw) {
		if (command == null)
			command = new ToggleContext(gw);
		return command;
	}

	public static ToggleContext getToggleContext(String name, GameWorld gw) {
		if (command == null)
			command = new ToggleContext(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.toggleContext();
	}
}

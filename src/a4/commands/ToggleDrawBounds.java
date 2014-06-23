package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the Game World that toggles drawing the game objects' bounds.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class ToggleDrawBounds extends AbstractAction {

	private static ToggleDrawBounds command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private ToggleDrawBounds() {
		super("Draw Object Bounds");
	}

	private ToggleDrawBounds(String name) {
		super(name);
	}
	
	private ToggleDrawBounds(GameWorld gw) {
		super("Draw Object Bounds");
		target = gw;
	}

	private ToggleDrawBounds(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static ToggleDrawBounds getToggleDrawBounds() {
		if (command == null)
			command = new ToggleDrawBounds();
		return command;
	}

	public static ToggleDrawBounds getToggleDrawBounds(String name) {
		if (command == null)
			command = new ToggleDrawBounds(name);
		return command;
	}
	
	public static ToggleDrawBounds getToggleDrawBounds(GameWorld gw) {
		if (command == null)
			command = new ToggleDrawBounds(gw);
		return command;
	}

	public static ToggleDrawBounds getToggleDrawBounds(String name, GameWorld gw) {
		if (command == null)
			command = new ToggleDrawBounds(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.toggleDrawBounds();
	}
}

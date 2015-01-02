package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that resets the GameWorld to how it was before the last command.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class Undo extends AbstractAction {
	
	private static Undo command;
	private GameWorld target;		// The object in which the command will be invoke.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private Undo() {
		super("Undo");
	}

	private Undo(String name) {
		super(name);
	}
	
	private Undo(GameWorld gw) {
		super("Undo");
		target = gw;
	}

	private Undo(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static Undo getUndo() {
		if (command == null)
			command = new Undo();
		return command;
	}

	public static Undo getUndo(String name) {
		if (command == null)
			command = new Undo(name);
		return command;
	}
	
	public static Undo getUndo(GameWorld gw) {
		if (command == null)
			command = new Undo(gw);
		return command;
	}
	
	public static Undo getUndo(String name, GameWorld gw) {
		if (command == null)
			command = new Undo(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.undo();
	}
}

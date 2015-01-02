package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that deletes the currently selected item(s).
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class Delete extends AbstractAction {

	private static Delete command;	// Holds the delete command.
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private Delete() {
		super("Delete");
	}

	private Delete(String name) {
		super(name);
	}
	
	private Delete(GameWorld gw) {
		super("Delete");
		target = gw;
	}
	
	private Delete(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static Delete getDelete() {
		if (command == null)
			command = new Delete();
		return command;
	}

	public static Delete getDelete(String name) {
		if (command == null)
			command = new Delete(name);
		return command;
	}
	
	public static Delete getDelete(GameWorld gw) {
		if (command == null)
			command = new Delete(gw);
		return command;
	}
	
	public static Delete getDelete(String name, GameWorld gw) {
		if (command == null)
			command = new Delete(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		target.deleteObject();
	}
}

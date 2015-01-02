package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that changes the strategies of the Smart Bombs currently on the
 * field.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class ChangeBombStrategy extends AbstractAction {

	private static ChangeBombStrategy command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private ChangeBombStrategy() {
		super("Switch Bomb Strategy");
	}

	private ChangeBombStrategy(String name) {
		super(name);
	}
	
	private ChangeBombStrategy(GameWorld gw) {
		super("Switch Bomb Strategy");
		target = gw;
	}

	private ChangeBombStrategy(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static ChangeBombStrategy getChangeBombStrategy() {
		if (command == null)
			command = new ChangeBombStrategy();
		return command;
	}

	public static ChangeBombStrategy getChangeBombStrategy(String name) {
		if (command == null)
			command = new ChangeBombStrategy(name);
		return command;
	}
	
	public static ChangeBombStrategy getChangeBombStrategy(GameWorld gw) {
		if (command == null)
			command = new ChangeBombStrategy(gw);
		return command;
	}

	public static ChangeBombStrategy getChangeBombStrategy(String name, GameWorld gw) {
		if (command == null)
			command = new ChangeBombStrategy(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.changeBombStrategy();
	}

}

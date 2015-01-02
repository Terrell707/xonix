package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that starts a new game.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class NewGame extends AbstractAction {

	private static NewGame command;
	private GameWorld target;		// The object in which the command will be invoke.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private NewGame() {
		super("New");
	}

	private NewGame(String name) {
		super(name);
	}
	
	private NewGame(GameWorld gw) {
		super("New");
		target = gw;
	}

	private NewGame(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static NewGame getNewGame() {
		if (command == null)
			command = new NewGame();
		return command;
	}

	public static NewGame getNewGame(String name) {
		if (command == null)
			command = new NewGame(name);
		return command;
	}
	
	public static NewGame getNewGame(GameWorld gw) {
		if (command == null)
			command = new NewGame(gw);
		return command;
	}
	
	public static NewGame getNewGame(String name, GameWorld gw) {
		if (command == null)
			command = new NewGame(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.newGame();
	}
}

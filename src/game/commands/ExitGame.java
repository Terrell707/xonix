package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that allows the player to exit the game. 
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class ExitGame extends AbstractAction {

	private static ExitGame command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private ExitGame() {
		super("Exit Game");
	}

	private ExitGame(String name) {
		super(name);
	}

	private ExitGame(GameWorld gw) {
		super("Exit Game");
		target = gw;
	}
	
	private ExitGame(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static ExitGame getExitGame() {
		if (command == null)
			command = new ExitGame();
		return command;
	}

	public static ExitGame getExitGame(String name) {
		if (command == null)
			command = new ExitGame(name);
		return command;
	}

	public static ExitGame getExitGame(GameWorld gw) {
		if (command == null)
			command = new ExitGame(gw);
		return command;
	}
	
	public static ExitGame getExitGame(String name, GameWorld gw) {
		if (command == null)
			command = new ExitGame(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.exitGame();
	}

}

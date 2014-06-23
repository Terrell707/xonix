package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the GameWorld that saves the current state of the game.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class SaveGame extends AbstractAction {

	private static SaveGame command;
	private GameWorld target;		// The object in which the command will be invoke.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private SaveGame() {
		super("Save");
	}

	private SaveGame(String name) {
		super(name);
	}
	
	private SaveGame(GameWorld gw) {
		super("Save");
		target = gw;
	}

	private SaveGame(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static SaveGame getSaveGame() {
		if (command == null)
			command = new SaveGame();
		return command;
	}

	public static SaveGame getSaveGame(String name) {
		if (command == null)
			command = new SaveGame(name);
		return command;
	}
	
	public static SaveGame getSaveGame(GameWorld gw) {
		if (command == null)
			command = new SaveGame(gw);
		return command;
	}
	
	public static SaveGame getSaveGame(String name, GameWorld gw) {
		if (command == null)
			command = new SaveGame(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.saveGame();
	}
}

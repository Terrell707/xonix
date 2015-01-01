package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the game world that spawns a Sweeper object onto the game field.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class SpawnSweeper extends AbstractAction {
	
	private static SpawnSweeper command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private SpawnSweeper() {
		super("Spawn Sweeper");
	}

	private SpawnSweeper(String name) {
		super(name);
	}
	
	private SpawnSweeper(GameWorld gw) {
		super("Spawn Sweeper");
		target = gw;
	}

	private SpawnSweeper(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static SpawnSweeper getSpawnSweeper() {
		if (command == null)
			command = new SpawnSweeper();
		return command;
	}

	public static SpawnSweeper getSpawnSweeper(String name) {
		if (command == null)
			command = new SpawnSweeper(name);
		return command;
	}
	
	public static SpawnSweeper getSpawnSweeper(GameWorld gw) {
		if (command == null)
			command = new SpawnSweeper(gw);
		return command;
	}

	public static SpawnSweeper getSpawnSweeper(String name, GameWorld gw) {
		if (command == null)
			command = new SpawnSweeper(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.spawnSweeper();
	}

}

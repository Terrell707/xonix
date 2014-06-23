package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the GameWorld that spawns a new Smart Bomb.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class SpawnSmartBomb extends AbstractAction {

	private static SpawnSmartBomb command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private SpawnSmartBomb() {
		super("Spawn Smart Bomb");
	}

	private SpawnSmartBomb(String name) {
		super(name);
	}
	
	private SpawnSmartBomb(GameWorld gw) {
		super("Spawn Smart Bomb");
		target = gw;
	}

	private SpawnSmartBomb(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static SpawnSmartBomb getSpawnSmartBomb() {
		if (command == null)
			command = new SpawnSmartBomb();
		return command;
	}

	public static SpawnSmartBomb getSpawnSmartBomb(String name) {
		if (command == null)
			command = new SpawnSmartBomb(name);
		return command;
	}
	
	public static SpawnSmartBomb getSpawnSmartBomb(GameWorld gw) {
		if (command == null)
			command = new SpawnSmartBomb(gw);
		return command;
	}

	public static SpawnSmartBomb getSpawnSmartBomb(String name, GameWorld gw) {
		if (command == null)
			command = new SpawnSmartBomb(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.spawnSmartBomb();
	}

}

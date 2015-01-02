package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that spawns a new Monster Ball.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class SpawnMonsterBall extends AbstractAction {

	private static SpawnMonsterBall command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private SpawnMonsterBall() {
		super("Spawn Monster Ball");
	}

	private SpawnMonsterBall(String name) {
		super(name);
	}
	
	private SpawnMonsterBall(GameWorld gw) {
		super("Spawn Monster Ball");
		target = gw;
	}

	private SpawnMonsterBall(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static SpawnMonsterBall getSpawnMonsterBall() {
		if (command == null)
			command = new SpawnMonsterBall();
		return command;
	}

	public static SpawnMonsterBall getSpawnMonsterBall(String name) {
		if (command == null)
			command = new SpawnMonsterBall(name);
		return command;
	}
	
	public static SpawnMonsterBall getSpawnMonsterBall(GameWorld gw) {
		if (command == null)
			command = new SpawnMonsterBall(gw);
		return command;
	}

	public static SpawnMonsterBall getSpawnMonsterBall(String name, GameWorld gw) {
		if (command == null)
			command = new SpawnMonsterBall(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.spawnMonsterBall();
	}

}

package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that spawns a new Time Ticket.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class SpawnTimeTicket extends AbstractAction {

	private static SpawnTimeTicket command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private SpawnTimeTicket() {
		super("Spawn Time Ticket");
	}

	private SpawnTimeTicket(String name) {
		super(name);
	}
	
	private SpawnTimeTicket(GameWorld gw) {
		super("Spawn Time Ticket");
		target = gw;
	}

	private SpawnTimeTicket(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static SpawnTimeTicket getSpawnTimeTicket() {
		if (command == null)
			command = new SpawnTimeTicket();
		return command;
	}

	public static SpawnTimeTicket getSpawnTimeTicket(String name) {
		if (command == null)
			command = new SpawnTimeTicket(name);
		return command;
	}
	
	public static SpawnTimeTicket getSpawnTimeTicket(GameWorld gw) {
		if (command == null)
			command = new SpawnTimeTicket(gw);
		return command;
	}

	public static SpawnTimeTicket getSpawnTimeTicket(String name, GameWorld gw) {
		if (command == null)
			command = new SpawnTimeTicket(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.spawnTimeTicket();
	}

}

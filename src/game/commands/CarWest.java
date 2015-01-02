package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that points the player car towards the west.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class CarWest extends AbstractAction {

	private static CarWest command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private CarWest() {
		super("Point Car West");
	}

	private CarWest(String name) {
		super(name);
	}
	
	private CarWest(GameWorld gw) {
		super("Point Car West");
		target = gw;
	}

	private CarWest(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static CarWest getCarWest() {
		if (command == null)
			command = new CarWest();
		return command;
	}

	public static CarWest getCarWest(String name) {
		if (command == null)
			command = new CarWest(name);
		return command;
	}
	
	public static CarWest getCarWest(GameWorld gw) {
		if (command == null)
			command = new CarWest(gw);
		return command;
	}

	public static CarWest getCarWest(String name, GameWorld gw) {
		if (command == null)
			command = new CarWest(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.carWest();
	}

}

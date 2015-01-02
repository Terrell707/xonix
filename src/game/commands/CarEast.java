package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that points the player car towards the east.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class CarEast extends AbstractAction {

	private static CarEast command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private CarEast() {
		super("Point Car East");
	}

	private CarEast(String name) {
		super(name);
	}
	
	private CarEast(GameWorld gw) {
		super("Point Car East");
		target = gw;
	}

	private CarEast(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static CarEast getCarEast() {
		if (command == null)
			command = new CarEast();
		return command;
	}

	public static CarEast getCarEast(String name) {
		if (command == null)
			command = new CarEast(name);
		return command;
	}
	
	public static CarEast getCarEast(GameWorld gw) {
		if (command == null)
			command = new CarEast(gw);
		return command;
	}

	public static CarEast getCarEast(String name, GameWorld gw) {
		if (command == null)
			command = new CarEast(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.carEast();
	}

}

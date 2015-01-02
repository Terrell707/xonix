package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that points the player car towards the south.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class CarSouth extends AbstractAction {

	private static CarSouth command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private CarSouth() {
		super("Point Car South");
	}

	private CarSouth(String name) {
		super(name);
	}
	
	private CarSouth(GameWorld gw) {
		super("Point Car South");
		target = gw;
	}

	private CarSouth(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static CarSouth getCarSouth() {
		if (command == null)
			command = new CarSouth();
		return command;
	}

	public static CarSouth getCarSouth(String name) {
		if (command == null)
			command = new CarSouth(name);
		return command;
	}
	
	public static CarSouth getCarSouth(GameWorld gw) {
		if (command == null)
			command = new CarSouth(gw);
		return command;
	}

	public static CarSouth getCarSouth(String name, GameWorld gw) {
		if (command == null)
			command = new CarSouth(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.carSouth();
	}

}

package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the GameWorld that points the player car to the north.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class CarNorth extends AbstractAction {

	private static CarNorth command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private CarNorth() {
		super("Point Car North");
	}
	
	private CarNorth(String name) {
		super(name);
	}
	
	private CarNorth(GameWorld gw) {
		super("Point Car North");
		target = gw;
	}

	private CarNorth(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static CarNorth getCarNorth() {
		if (command == null)
			command = new CarNorth();
		return command;
	}
	
	public static CarNorth getCarNorth(String name) {
		if (command == null)
			command = new CarNorth(name);
		return command;
	}
	
	public static CarNorth getCarNorth(GameWorld gw) {
		if (command == null)
			command = new CarNorth(gw);
		return command;
	}

	public static CarNorth getCarNorth(String name, GameWorld gw) {
		if (command == null)
			command = new CarNorth(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		target.carNorth();
	}

}

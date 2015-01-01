package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the GameWorld that increases the speed of the player's car.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class IncreaseCarSpeed extends AbstractAction {

	private static IncreaseCarSpeed command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private IncreaseCarSpeed() {
		super("Increase Car Speed");
	}

	private IncreaseCarSpeed(String name) {
		super(name);
	}
	
	private IncreaseCarSpeed(GameWorld gw) {
		super("Increase Car Speed");
		target = gw;
	}

	private IncreaseCarSpeed(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static IncreaseCarSpeed getIncreaseCarSpeed() {
		if (command == null)
			command = new IncreaseCarSpeed();
		return command;
	}

	public static IncreaseCarSpeed getIncreaseCarSpeed(String name) {
		if (command == null)
			command = new IncreaseCarSpeed(name);
		return command;
	}
	
	public static IncreaseCarSpeed getIncreaseCarSpeed(GameWorld gw) {
		if (command == null)
			command = new IncreaseCarSpeed(gw);
		return command;
	}

	public static IncreaseCarSpeed getIncreaseCarSpeed(String name, GameWorld gw) {
		if (command == null)
			command = new IncreaseCarSpeed(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.increaseCarSpeed();
	}

}

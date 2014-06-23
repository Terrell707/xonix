package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the GameWorld that decrease's the player car's speed.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class DecreaseCarSpeed extends AbstractAction {

	private static DecreaseCarSpeed command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private DecreaseCarSpeed() {
		super("Decrease Car Speed");
	}

	private DecreaseCarSpeed(String name) {
		super(name);
	}
	
	private DecreaseCarSpeed(GameWorld gw) {
		super("Decrease Car Speed");
		target = gw;
	}

	private DecreaseCarSpeed(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static DecreaseCarSpeed getDecreaseCarSpeed() {
		if (command == null)
			command = new DecreaseCarSpeed();
		return command;
	}

	public static DecreaseCarSpeed getDecreaseCarSpeed(String name) {
		if (command == null)
			command = new DecreaseCarSpeed(name);
		return command;
	}
	
	public static DecreaseCarSpeed getDecreaseCarSpeed(GameWorld gw) {
		if (command == null)
			command = new DecreaseCarSpeed(gw);
		return command;
	}

	public static DecreaseCarSpeed getDecreaseCarSpeed(String name, GameWorld gw) {
		if (command == null)
			command = new DecreaseCarSpeed(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.decreaseCarSpeed();
	}
}

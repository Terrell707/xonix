package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the GameWorld to flip the sound ON or OFF.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class ToggleSound extends AbstractAction {

	private static ToggleSound command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private ToggleSound() {
		super("Sound");
	}

	private ToggleSound(String name) {
		super(name);
	}
	
	private ToggleSound(GameWorld gw) {
		super("Sound");
		target = gw;
	}

	private ToggleSound(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static ToggleSound getToggleSound() {
		if (command == null)
			command = new ToggleSound();
		return command;
	}

	public static ToggleSound getToggleSound(String name) {
		if (command == null)
			command = new ToggleSound(name);
		return command;
	}
	
	public static ToggleSound getToggleSound(GameWorld gw) {
		if (command == null)
			command = new ToggleSound(gw);
		return command;
	}

	public static ToggleSound getToggleSound(String name, GameWorld gw) {
		if (command == null)
			command = new ToggleSound(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.toggleSound();
	}
}

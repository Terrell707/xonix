package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the Gameworld that pauses the game.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class Pause extends AbstractAction {

	private static Pause command;	// Holds the pause command.
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private Pause() {
		super("Pause");
	}

	private Pause(String name) {
		super(name);
	}
	
	private Pause(GameWorld gw) {
		super("Pause");
		target = gw;
	}
	
	private Pause(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static Pause getPause() {
		if (command == null)
			command = new Pause();
		return command;
	}

	public static Pause getPause(String name) {
		if (command == null)
			command = new Pause(name);
		return command;
	}
	
	public static Pause getPause(GameWorld gw) {
		if (command == null)
			command = new Pause(gw);
		return command;
	}
	
	public static Pause getPause(String name, GameWorld gw) {
		if (command == null)
			command = new Pause(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// If the game is in a paused state, the button will display "Play".
		if (target.getPlay()) {
			putValue(NAME, "Play");
			Delete.getDelete().setEnabled(true);
		}
		// Otherwise, it will display "Pause".
		else {
			putValue(NAME, "Pause");
			Delete.getDelete().setEnabled(false);
		}
		
		target.changePlay();
	}
}

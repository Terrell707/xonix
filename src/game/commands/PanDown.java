package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the game world to move the world window down.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class PanDown extends AbstractAction {

	private static PanDown command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private PanDown() {
		super("Pan Down");
	}

	private PanDown(String name) {
		super(name);
	}
	
	private PanDown(GameWorld gw) {
		super("Pan Down");
		target = gw;
	}

	private PanDown(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static PanDown getPanDown() {
		if (command == null)
			command = new PanDown();
		return command;
	}

	public static PanDown getPanDown(String name) {
		if (command == null)
			command = new PanDown(name);
		return command;
	}
	
	public static PanDown getPanDown(GameWorld gw) {
		if (command == null)
			command = new PanDown(gw);
		return command;
	}

	public static PanDown getPanDown(String name, GameWorld gw) {
		if (command == null)
			command = new PanDown(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.panDown();
	}
}

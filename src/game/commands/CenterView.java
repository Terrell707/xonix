package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Calls the command in the game world that places the view back on the center of the game world.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class CenterView extends AbstractAction {
	
	private static CenterView command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private CenterView() {
		super("Center View");
	}

	private CenterView(String name) {
		super(name);
	}
	
	private CenterView(GameWorld gw) {
		super("Center View");
		target = gw;
	}

	private CenterView(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static CenterView getCenterView() {
		if (command == null)
			command = new CenterView();
		return command;
	}

	public static CenterView getCenterView(String name) {
		if (command == null)
			command = new CenterView(name);
		return command;
	}
	
	public static CenterView getCenterView(GameWorld gw) {
		if (command == null)
			command = new CenterView(gw);
		return command;
	}

	public static CenterView getCenterView(String name, GameWorld gw) {
		if (command == null)
			command = new CenterView(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.centerWorldWindow();
	}
}

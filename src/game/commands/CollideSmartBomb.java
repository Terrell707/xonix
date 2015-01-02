package game.commands;

import game.GameWorld;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Invokes the command in the GameWorld that collides the player car with the closet Smart Bomb.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class CollideSmartBomb extends AbstractAction {

	private static CollideSmartBomb command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private CollideSmartBomb() {
		super("Car Hits Bomb");
	}

	private CollideSmartBomb(String name) {
		super(name);
	}
	
	private CollideSmartBomb(GameWorld gw) {
		super("Car Hits Bomb");
		target = gw;
	}

	private CollideSmartBomb(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static CollideSmartBomb getCollideSmartBomb() {
		if (command == null)
			command = new CollideSmartBomb();
		return command;
	}

	public static CollideSmartBomb getCollideSmartBomb(String name) {
		if (command == null)
			command = new CollideSmartBomb(name);
		return command;
	}
	
	public static CollideSmartBomb getCollideSmartBomb(GameWorld gw) {
		if (command == null)
			command = new CollideSmartBomb(gw);
		return command;
	}

	public static CollideSmartBomb getCollideSmartBomb(String name, GameWorld gw) {
		if (command == null)
			command = new CollideSmartBomb(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.collideSmartBomb();
	}

}

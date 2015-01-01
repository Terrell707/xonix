package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the GameWorld that collides the player car with the closet Monster Ball.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class CollideMonsterBall extends AbstractAction {

	private static CollideMonsterBall command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private CollideMonsterBall() {
		super("Car Hits Ball");
	}

	private CollideMonsterBall(String name) {
		super(name);
	}
	
	private CollideMonsterBall(GameWorld gw) {
		super("Car Hits Ball");
		target = gw;
	}

	private CollideMonsterBall(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static CollideMonsterBall getCollideMonsterBall() {
		if (command == null)
			command = new CollideMonsterBall();
		return command;
	}

	public static CollideMonsterBall getCollideMonsterBall(String name) {
		if (command == null)
			command = new CollideMonsterBall(name);
		return command;
	}
	
	public static CollideMonsterBall getCollideMonsterBall(GameWorld gw) {
		if (command == null)
			command = new CollideMonsterBall(gw);
		return command;
	}

	public static CollideMonsterBall getCollideMonsterBall(String name, GameWorld gw) {
		if (command == null)
			command = new CollideMonsterBall(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.collideMonsterBall();

	}

}

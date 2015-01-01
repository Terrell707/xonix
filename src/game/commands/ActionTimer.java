package a4.commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import a4.GameWorld;

/**
 * Invokes the command in the GameWorld that moves and draw all the objects of the game.
 * @author Adrian
 *
 */
public class ActionTimer implements ActionListener{

	private final int DELAY_IN_MSEC = 20;
	
	private static ActionTimer command;	// Holds the action timer command.
	private Timer timer;				// The timer that invokes the command.
	private GameWorld target;			// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private ActionTimer() {
		timer = new Timer(DELAY_IN_MSEC, this);
		timer.start();
	}

	private ActionTimer(String name) {
		timer = new Timer(DELAY_IN_MSEC, this);
		timer.start();
	}
	
	private ActionTimer(GameWorld gw) {
		timer = new Timer(DELAY_IN_MSEC, this);
		target = gw;
		timer.start();
	}

	private ActionTimer(String name, GameWorld gw) {
		timer = new Timer(DELAY_IN_MSEC, this);
		target = gw;
		timer.start();
	}
	
	public static ActionTimer getActionTimer() {
		if (command == null)
			command = new ActionTimer();
		return command;
	}

	public static ActionTimer getActionTimer(String name) {
		if (command == null)
			command = new ActionTimer(name);
		return command;
	}
	
	public static ActionTimer getActionTimer(GameWorld gw) {
		if (command == null)
			command = new ActionTimer(gw);
		return command;
	}

	public static ActionTimer getActionTimer(String name, GameWorld gw) {
		if (command == null)
			command = new ActionTimer(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	/**
	 * Starts the timer which has the objects in the game world move and animate.
	 */
	public void start()
	{
		timer.start();
	}
	
	/**
	 * Stops the timer which pauses the game.
	 */
	public void stop()
	{
		timer.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Will only invoke the move command in the game world if the game is not paused.
		if (target.getPlay()) {
			target.timeTick(DELAY_IN_MSEC);
		}
	}
}

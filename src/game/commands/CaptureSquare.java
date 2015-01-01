package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the GameWorld that captures a single square.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class CaptureSquare extends AbstractAction {

	private static CaptureSquare command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private CaptureSquare() {
		super("Capture a Square");
	}

	private CaptureSquare(String name) {
		super(name);
	}
	
	private CaptureSquare(GameWorld gw) {
		super("Capture a Square");
		target = gw;
	}
	
	private CaptureSquare(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static CaptureSquare getCaptureSquare() {
		if (command == null)
			command = new CaptureSquare();
		return command;
	}

	public static CaptureSquare getCaptureSquare(String name) {
		if (command == null)
			command = new CaptureSquare(name);
		return command;
	}
	
	public static CaptureSquare getCaptureSquare(GameWorld gw) {
		if (command == null)
			command = new CaptureSquare(gw);
		return command;
	}
	
	public static CaptureSquare getCaptureSquare(String name, GameWorld gw) {
		if (command == null)
			command = new CaptureSquare(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.captureSquare();
	}

}

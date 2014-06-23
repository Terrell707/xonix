package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

/**
 * Invokes the command in the GameWorld that has the player car obtain the closet Time Ticket.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class ObtainTimeTicket extends AbstractAction {

	private static ObtainTimeTicket command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private ObtainTimeTicket() {
		super("Car Hits Ticket");
	}

	private ObtainTimeTicket(String name) {
		super(name);
	}
	
	private ObtainTimeTicket(GameWorld gw) {
		super("Car Hits Ticket");
		target = gw;
	}

	private ObtainTimeTicket(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static ObtainTimeTicket getObtainTimeTicket() {
		if (command == null)
			command = new ObtainTimeTicket();
		return command;
	}

	public static ObtainTimeTicket getObtainTimeTicket(String name) {
		if (command == null)
			command = new ObtainTimeTicket(name);
		return command;
	}
	
	public static ObtainTimeTicket getObtainTimeTicket(GameWorld gw) {
		if (command == null)
			command = new ObtainTimeTicket(gw);
		return command;
	}

	public static ObtainTimeTicket getObtainTimeTicket(String name, GameWorld gw) {
		if (command == null)
			command = new ObtainTimeTicket(name, gw);
		return command;
	}
	
	public void setTarget(GameWorld target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		target.obtainTimeTicket();
	}

}

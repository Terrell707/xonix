package a4.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a4.GameWorld;

@SuppressWarnings("serial")
public class DisplayAbout extends AbstractAction {

	private static DisplayAbout command;
	private GameWorld target;		// The object in which the command will be invoked on.
	
	/**
	 * No-arg constructor. Create a command with a default command name.
	 */
	private DisplayAbout() {
		super("About");
	}

	private DisplayAbout(String name) {
		super(name);
	}
	
	private DisplayAbout(GameWorld gw) {
		super("About");
		target = gw;
	}

	private DisplayAbout(String name, GameWorld gw) {
		super(name);
		target = gw;
	}
	
	public static DisplayAbout getDisplayAbout() {
		if (command == null)
			command = new DisplayAbout();
		return command;
	}

	public static DisplayAbout getDisplayAbout(String name) {
		if (command == null)
			command = new DisplayAbout(name);
		return command;
	}
	
	public static DisplayAbout getDisplayAbout(GameWorld gw) {
		if (command == null)
			command = new DisplayAbout(gw);
		return command;
	}

	public static DisplayAbout getDisplayAbout(String name, GameWorld gw) {
		if (command == null)
			command = new DisplayAbout(name, gw);
		return command;
	}

	public void setTarget(GameWorld target) {
		this.target = target;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		target.displayAbout();
	}
}

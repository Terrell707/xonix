package a4.view;

import java.awt.GridLayout;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import a4.commands.*;

/**
 * Creates a panel that creates JButtons and adds appropriate commands to them. Also adds key bindings to the
 * panel that will invoke commands when this panel is part of the window that is in focus.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class CommandView extends JPanel {
	
	public CommandView()
	{		
		// Create the buttons and gets the instances of the commands that were created in the Game View.
		JButton pause = new JButton(Pause.getPause());
		JButton delete = new JButton(Delete.getDelete());
		JButton center = new JButton(CenterView.getCenterView());
		JButton quit = new JButton(ExitGame.getExitGame());
		
		// Disables the delete button by default.
		Delete.getDelete().setEnabled(false);
		
		// Creates the keystrokes and gets the instances of the commands that were created in the Game View.
		KeyStroke bKey = KeyStroke.getKeyStroke('b');
		KeyStroke iKey = KeyStroke.getKeyStroke('i');
		KeyStroke lKey = KeyStroke.getKeyStroke('l');
		KeyStroke qKey = KeyStroke.getKeyStroke('q');
		KeyStroke sKey = KeyStroke.getKeyStroke('s');
		KeyStroke tKey = KeyStroke.getKeyStroke('t');
		KeyStroke upKey = KeyStroke.getKeyStroke("UP");
		KeyStroke downKey = KeyStroke.getKeyStroke("DOWN");
		KeyStroke leftKey = KeyStroke.getKeyStroke("LEFT");
		KeyStroke rightKey = KeyStroke.getKeyStroke("RIGHT");
		KeyStroke spaceKey = KeyStroke.getKeyStroke("SPACE");
		KeyStroke delKey = KeyStroke.getKeyStroke("DELETE");
		KeyStroke enterKey = KeyStroke.getKeyStroke("ENTER");
		
		// Grabs the Input and Action Map for this panel.
		int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap = this.getInputMap(mapName);
		ActionMap amap = this.getActionMap();
		
		// Gives this panel a layout that puts everything in a line, and gives it an etched border.
		setLayout(new GridLayout(8,0));
		setBorder(new TitledBorder("Commands:"));
		
		// Removes the space bar keystroke from each of the buttons.
		pause.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		delete.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		quit.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		
		// Adds the buttons to the panel.
		add(pause);
		add(delete);
		add(center);
		add(quit);
		
		// Adds the commands to the keys for when "this panel is part of the focused window" input map.	
		imap.put(bKey, "spawnBall");
		imap.put(sKey, "spawnBomb");
		imap.put(qKey, "spawnSweeper");
		imap.put(tKey, "spawnTicket");
		imap.put(iKey, "increaseSpeed");
		imap.put(lKey, "decreaseSpeed");
		imap.put(upKey, "carNorth");
		imap.put(downKey, "carSouth");
		imap.put(leftKey, "carWest");
		imap.put(rightKey, "carEast");
		imap.put(spaceKey, "changeStrategy");
		imap.put(delKey, "delete");
		imap.put(enterKey, "pause");
		
		// Adds the commands to the action map.
		amap.put("spawnBall", SpawnMonsterBall.getSpawnMonsterBall());
		amap.put("spawnBomb", SpawnSmartBomb.getSpawnSmartBomb());
		amap.put("spawnSweeper", SpawnSweeper.getSpawnSweeper());
		amap.put("spawnTicket", SpawnTimeTicket.getSpawnTimeTicket());
		amap.put("increaseSpeed", IncreaseCarSpeed.getIncreaseCarSpeed());
		amap.put("decreaseSpeed", DecreaseCarSpeed.getDecreaseCarSpeed());
		amap.put("carNorth", CarNorth.getCarNorth());
		amap.put("carSouth", CarSouth.getCarSouth());
		amap.put("carWest", CarWest.getCarWest());
		amap.put("carEast", CarEast.getCarEast());
		amap.put("changeStrategy", ChangeBombStrategy.getChangeBombStrategy());
		amap.put("delete", Delete.getDelete());
		amap.put("pause", Pause.getPause());
	}
}

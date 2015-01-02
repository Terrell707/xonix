package game.view;

import game.commands.*;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuView extends JMenuBar {

	public MenuView() 
	{
		// Create the Menu Items
		JMenu fileMenu = new JMenu("File");
		JMenu commandMenu = new JMenu("Commands");
		
		// Adds the Menu Items to the Menu.
		this.add(fileMenu);
		this.add(commandMenu);
		
		// Creates the items for the File menu.
		JMenuItem fileNew = new JMenuItem("New");
		JMenuItem fileSave = new JMenuItem("Save");
		JMenuItem fileUndo = new JMenuItem("Undo");
		JCheckBoxMenuItem fileSound = new JCheckBoxMenuItem("Sound");
		JMenuItem fileAbout = new JMenuItem("About");
		JMenuItem fileQuit = new JMenuItem("Quit");
		
		// Creates the items for the Command menu.
		JMenuItem commandBall = new JMenuItem("Spawn MonsterBall");
		JMenuItem commandBomb = new JMenuItem("Spawn SmartBomb");
		JMenuItem commandTicket = new JMenuItem("Spawn Ticket");
		JMenuItem commandHitBall = new JMenuItem("Car Hit Ball");
		JMenuItem commandHitBomb = new JMenuItem("Car Hit Bomb");
		JMenuItem commandHitTicket = new JMenuItem("Obtain Ticket");
		JMenuItem commandSquare = new JMenuItem("Capture Square");
		JMenuItem commandGroup = new JMenuItem("Capture Group");
		JMenuItem commandStrategy = new JMenuItem("Change Strategies");
		JCheckBoxMenuItem commandContextLines = new JCheckBoxMenuItem("Sweeper Context Lines");
		JCheckBoxMenuItem commandObjectBounds = new JCheckBoxMenuItem("Draw Object Bounds");
		
		// Adds the items to the File menu.
		fileMenu.add(fileNew);
		fileMenu.add(fileSave);
		fileMenu.add(fileUndo);
		fileMenu.add(fileSound);
		fileMenu.add(fileAbout);
		fileMenu.add(fileQuit);
		
		// Adds the items to the Command menu.
		commandMenu.add(commandBall);
		commandMenu.add(commandBomb);
		commandMenu.add(commandTicket);
		commandMenu.add(commandHitBall);
		commandMenu.add(commandHitBomb);
		commandMenu.add(commandHitTicket);
		commandMenu.add(commandSquare);
		commandMenu.add(commandGroup);
		commandMenu.add(commandStrategy);
		commandMenu.add(commandContextLines);
		commandMenu.add(commandObjectBounds);
		
		// Adds the commands to the File menu items.
		fileNew.setAction(NewGame.getNewGame());
		fileSave.setAction(SaveGame.getSaveGame());
		fileUndo.setAction(Undo.getUndo());
		fileSound.setAction(ToggleSound.getToggleSound());
		fileAbout.setAction(DisplayAbout.getDisplayAbout());
		fileQuit.setAction(ExitGame.getExitGame());
		
		// Adds the commands to the Command menu items.
		commandBall.setAction(SpawnMonsterBall.getSpawnMonsterBall());
		commandBomb.setAction(SpawnSmartBomb.getSpawnSmartBomb());
		commandTicket.setAction(SpawnTimeTicket.getSpawnTimeTicket());
		commandHitBall.setAction(CollideMonsterBall.getCollideMonsterBall());
		commandHitBomb.setAction(CollideSmartBomb.getCollideSmartBomb());
		commandHitTicket.setAction(ObtainTimeTicket.getObtainTimeTicket());
		commandSquare.setAction(CaptureSquare.getCaptureSquare());
		commandGroup.setAction(CaptureGroup.getCaptureGroup());
		commandStrategy.setAction(ChangeBombStrategy.getChangeBombStrategy());
		commandContextLines.setAction(ToggleContext.getToggleContext());
		commandObjectBounds.setAction(ToggleDrawBounds.getToggleDrawBounds());
	}
}

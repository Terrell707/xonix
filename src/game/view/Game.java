package game.view;

import game.GameWorld;
import game.GameWorldProxy;
import game.commands.*;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Creates and initializes the Game World. This class allows the user to send commands to the Game World
 * to manipulate and control the Game Objects.
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class Game extends JFrame implements MouseListener, MouseWheelListener, MouseMotionListener {
	
	private MapView map;			// The panel that holds the map.
	private ScoreView score;		// The panel that holds score information.
	private CommandView commands;	// The panel that holds the buttons.
	private MenuView menu;			// The menu items.
	private GameWorld gw;			// The game world.
	private GameWorldProxy proxy;	// The game world proxy.
	private Point2D clickPoint;		// Holds the point when user clicks.
	private Point2D startPoint;		// Holds the point where the user begins holding the mouse button.
	private Point2D endPoint;		// Holds the point where the user releases the mouse button.
	private Point2D prevDrag;		// Holds the point where ther user began dragging.
	
	/**
	 * Creates the game world and allows the player to play the Game.
	 */
	public Game()
	{
		// Creates the instance of the GameWorld
		gw = new GameWorld();
		proxy = new GameWorldProxy(gw);
		
		// Creates and initializes the commands and then passes the instance of GameWorld into each of them.
		ActionTimer.getActionTimer(gw);
		CaptureGroup.getCaptureGroup(gw);
		CaptureSquare.getCaptureSquare(gw);
		CarEast.getCarEast(gw);
		CarNorth.getCarNorth(gw);
		CarSouth.getCarSouth(gw);
		CarWest.getCarWest(gw);
		CenterView.getCenterView(gw);
		ChangeBombStrategy.getChangeBombStrategy(gw);
		CollideMonsterBall.getCollideMonsterBall(gw);
		CollideSmartBomb.getCollideSmartBomb(gw);
		DecreaseCarSpeed.getDecreaseCarSpeed(gw);
		Delete.getDelete(gw);
		DisplayAbout.getDisplayAbout(gw);
		ExitGame.getExitGame(gw);
		IncreaseCarSpeed.getIncreaseCarSpeed(gw);
		NewGame.getNewGame(gw);
		ObtainTimeTicket.getObtainTimeTicket(gw);
		PanDown.getPanDown(gw);
		PanUp.getPanUp(gw);
		Pause.getPause(gw);
		SaveGame.getSaveGame(gw);
		SpawnMonsterBall.getSpawnMonsterBall(gw);
		SpawnSmartBomb.getSpawnSmartBomb(gw);
		SpawnSweeper.getSpawnSweeper(gw);
		SpawnTimeTicket.getSpawnTimeTicket(gw);
		ToggleContext.getToggleContext(gw);
		ToggleDrawBounds.getToggleDrawBounds(gw);
		ToggleSound.getToggleSound(gw);
		Undo.getUndo(gw);
		ZoomIn.getZoomIn(gw);
		ZoomOut.getZoomOut(gw);
		
		// Sets up the frame for the Game.
		setTitle("Xonix");
		setSize(1000, 800);
		setLocation(150, 50);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Adds the menu
		menu = new MenuView();
		setJMenuBar(menu);
		
		// Adds the panels
		score = new ScoreView(proxy);
		add(score, BorderLayout.SOUTH);
		commands = new CommandView();
		add(commands, BorderLayout.WEST);
		map = new MapView(proxy);
		add(map, BorderLayout.CENTER);
		
		// Makes this frame the mouse listener.
		addMouseListener(this);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		
		// Initializes the mouse clicks.
		clickPoint = new Point2D.Double(0, 0);
		startPoint = new Point2D.Double(0, 0);
		endPoint = new Point2D.Double(0, 0);
		prevDrag = new Point2D.Double(0, 0);
		
		// Makes the Frame visible.
		setVisible(true);
	}
	
	/**
	 * Sends the point that was clicked to the game world.
	 */
	@Override
	public void mouseClicked(MouseEvent e) 
	{	
		// Grabs the point that was clicked and converts it from the JFrame coordinates to the Map JPanel
		//	coordinates.
		clickPoint = SwingUtilities.convertPoint(this, e.getX(), e.getY(), map);
		map.getSize().getWidth();
		
		// Checks to see if an object contains the point. If the control button is held down, all objects will
		//	stay selected until control is let go.
		if (e.isControlDown()){
			gw.selectObject(clickPoint, true);
		}
		else {
			gw.selectObject(clickPoint);
		}
	}

	/**
	 * Saves the point which the user began clicking.
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		// Grabs the first point that was clicked and converts it from the JFrame coordinates to the Map Jpanel
		//	coordinates.
		startPoint = SwingUtilities.convertPoint(this, e.getX(), e.getY(), map);
	}

	/**
	 * Saves the point which the user released the click. Will then send the start point and 
	 * the release point to the game world.
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		// Grabs the first point that was clicked and converts it from the JFrame coordinates to the Map Jpanel
		//	coordinates.
		endPoint = SwingUtilities.convertPoint(this, e.getX(), e.getY(), map);
		
		// Sends the start and end point to the game world if the end point differs from the start point.
		if (!endPoint.equals(startPoint)) {
			gw.selectObject(startPoint, endPoint);
		}
	}
	
	/**
	 * Zooms In or Out depending on which way the user rotates the mouse wheel.
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		int clicks = e.getWheelRotation();
		
		if (gw.getPlay())
		{
			// Will zoom in or out depending on how the user scrolls the mouse wheel. Will only do it
			//	if game is not paused.
			if (clicks > 0) {
				gw.zoomIn();
			}
			else if (clicks < 0) {
				gw.zoomOut();
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		// Minuses where the point was previously from where it is now. This shows which direction the user
		//	dragged the mouse.
		double draggedX = prevDrag.getX() - e.getX();
		double draggedY = prevDrag.getY() - e.getY();
		
		// Saves the current point to be used next time this event is fired.
		prevDrag = e.getPoint();
		
		// Will only pan the view if the game is not paused.
		if (gw.getPlay())
		{
			// If the change in X is greater than 0, then the user dragged right, which means move the view right.
			//	And vice-versa.
			if (draggedX > 0)
				gw.panRight();
			else if (draggedX < 0)
				gw.panLeft();
			
			// If the change in Y is greater than 0, then the user dragged up, which means move the view up.
			//	And vice-versa.
			if (draggedY > 0)
				gw.panUp();
			else if (draggedY < 0)
				gw.panDown();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Does nothing.
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Does nothing.
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Does nothing.
	}


}

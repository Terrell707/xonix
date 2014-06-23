package a4.view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import a4.interfaces.IGameWorld;
import a4.interfaces.IObservable;
import a4.interfaces.IObserver;

/**
 * @author Adrian
 *
 */
@SuppressWarnings("serial")
public class MapView extends JPanel implements IObserver{

	private IGameWorld gw;				// The game world this object is observing.
	private AffineTransform worldToND;	// The transformation matrix that translates an object from its local points to a normalized device.
	private AffineTransform ndToScreen;	// The transformation matrix that translates an object from ND to the screen.
	private AffineTransform theVTM;		// Combination of the two above matrices that maps a point to the screen.
	
	/**
	 * Constructor that registers this view with the Observable.
	 * @param observing - The Observable object in which to observe.
	 */
	public MapView(IObservable observing)
	{
		gw = (IGameWorld) observing;
		observing.addObserver(this);
		
		setLayout(new FlowLayout());
	}
	
	/**
	 * Updates the Map View whenever the data that this view is observing updates.
	 */
	@Override
	public void update(IObservable o, Object arg) 
	{
		String className, message;

		// Grabs the class name of the Observable object that was sent. If the Observable object equals the the class that this
		//	class is observing, then the map will check to see if it should update.
		className = o.getClass().toString();

		if (className.compareToIgnoreCase(gw.getClass().toString()) == 0)
		{
			// Grabs the message that was sent by the class this is observing. If it equals map, then the
			//	map will be updated.
			message = (String) arg;
			
			if (message.compareToIgnoreCase("map") == 0)
			{
				repaint();
				//gw.displayMap();
			}
		}
	}
	
	/**
	 * Overrides the panel's pain component method so that the objects are drawn on screen.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		// Saves the graphics object's current transform.
		AffineTransform saveAT = g2d.getTransform();
		
		// Creates two matrices that map a point to a normalized device, and then to the screen.
		worldToND = gw.buildWorldToNDXform();
		ndToScreen = gw.buildNDToScreenXform(this.getWidth(), this.getHeight());
		
		// Combines the two above matrices into one. (Concatenating two matrices does multiplication from the
		//	right. Then adds it to the graphics object.
		theVTM = (AffineTransform) ndToScreen.clone();
		theVTM.concatenate(worldToND);
		gw.setVTM(theVTM);
		
		g2d.transform(theVTM);
		
		// Passes the graphics object to each of the game objects.
		gw.drawMap(g2d);
		
		// Reverts the graphics object to its default transform.
		g2d.setTransform(saveAT);
	}
}

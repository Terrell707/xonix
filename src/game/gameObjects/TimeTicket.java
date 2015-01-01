package a4.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import a4.interfaces.ICollider;
import a4.interfaces.ISelectable;

/**
 * This class will create and initialize a Time Ticket. Time Tickets are used to grant a player an additional amount.
 * The ticket's location, bonus time given, size, and color are configurable when created. After being created, none of
 * these attributes can be changed.
 * @author Adrian
 *
 */
public class TimeTicket extends FixedObject implements ISelectable{

	private int width;
	private int height;
	private int timeGiven;
	private boolean selected;	// Flag indicating whether the object is selected by mouse or not.
	private boolean expired;	// Flag indicating the object needs to be deleted.

	/**
	 * Creates and places a Time Ticket in the location (X, Y), makes it as wide and tall as the width and height values
	 * passed in, and initializes the amount of bonus time that is given when obtained. Also gives the ticket the color
	 * that is passed in,
	 * @param setX - The x location the car will be placed.
	 * @param setY - The y location the car will be placed.
	 * @param setWidth - How wide the Time Ticket will be.
	 * @param setHeight - How tall the Time Ticket will be.
	 * @param setTimeGiven - The amount of time the time ticket will give when touched.
	 * @param setColor - The color to give the object using Java's predefined colors.
	 */
	public TimeTicket(double setX, double setY, int setWidth, int setHeight, int setTimeGiven, Color setColor) {
		super(setX, setY, setColor);
		width = setWidth;
		height = setHeight;
		timeGiven = setTimeGiven;
		
		// Places the time ticket in the correct area in the game world.
		this.translate((setX - (width/2)), (setY - (height/2)));
	}

	/**
	 * Creates and places a Time Ticket in the location (X, Y), makes it as wide and tall as the width and height values
	 * passed in, and initializes the amount of bonus time that is given when obtained. Also gives the ticket the color
	 * that is passed in,
	 * @param setX - The x location the car will be placed.
	 * @param setY - The y location the car will be placed.
	 * @param setWidth - How wide the Time Ticket will be.
	 * @param setHeight - How tall the Time Ticket will be.
	 * @param setTimeGiven - The amount of time the time ticket will give when touched.
	 * @param red - The amount of red tint (0-255) to give the object being placed.
	 * @param green - The amount of green tint (0-255) to give the object being placed.
	 * @param blue - the amount of blue tint (0-255) to give the object being placed.
	 */
	public TimeTicket(double setX, double setY, int setWidth, int setHeight, int setTimeGiven, int red,
			int green, int blue) {
		super(setX, setY, red, green, blue);
		width = setWidth;
		height = setHeight;
		timeGiven = setTimeGiven;
		
		// Places the time ticket in the correct area in the game world.
		this.translate((setX - (width/2)), (setY - (height/2)));
	}
	
	/**
	 * Grabs the width of the Time Ticket.
	 * @return How wide the Time Ticket is.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Grabs and returns the height of the Time Ticket.
	 * @return How tall the Time Ticket is.
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Grabs and returns the amount of bonus time the Time Ticket gives.
	 * @return How much bonus time the Time Ticket gives.
	 */
	public int getTimeGiven()
	{
		return timeGiven;
	}
	
	/**
	 * Looks to see if the object has expired and needs to be removed.
	 * @return True means the object has expired and is requesting to be removed. False if it has not expired.
	 */
	public boolean isExpired()
	{
		return expired;
	}
	
	@Override
	public String toString()
	{
		String myDesc = String.format("Ticket: loc= (%.2f, %.2f), color= (r=%d, g=%d, b=%d), bonusTime=%d, "
				+ "width=%d, height=%d", getX(), getY(), getColor().getRed(), getColor().getGreen(), getColor().getBlue(),
				getTimeGiven(), getWidth(), getHeight());
		return myDesc;
	}
	
	/**
	 * Creates a square bounds around the ticket and returns it.
	 * @return
	 */
	public Rectangle getBounds()
	{
		// Gets the lower left hand corner point because rectangles are made going in the pos x and y
		//	directions.
		int x = (int) getTranslation().getTranslateX() - (width/2);
		int y = (int) getTranslation().getTranslateY() - (height/2);
		
		Rectangle ticketBounds = new Rectangle(x, y, width, height);
		return ticketBounds;
	}
	
	/**
	 * Changes whether the object is selected or not.
	 * @param yesNo - A boolean value indicating whether the object is selected or not.
	 */
	@Override
	public void setSelected(boolean yesNo) {
		selected = yesNo;
	}

	/**
	 * Returns if the object is selected or not.
	 */
	@Override
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Checks to see if the object contains the point passed in.
	 * @return True if the object contains the point. False if it doesn't.
	 */
	@Override
	public boolean contains(Point2D p) 
	{
		// Checks to see if the point is contained inside the bounds of the object.
		boolean selected = getBounds().contains(p);

		return selected;
	}

	@Override
	public boolean contains(Rectangle mouseArea)
	{
		// Checks to see if the bounds of mouse area intersect with the bounds of the object.
		boolean selected = mouseArea.intersects(getBounds());

		return selected;
	}

	/**
	 * Uses the graphics class to draw the time ticket on screen.
	 * @param g - The graphics object to draw with.
	 */
	@Override
	public void draw(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(getTranslation());
		
		// Constructs the square with the set color.
		// If the object is not selected, it will be drawn filled. Otherwise, only the outline will be
		//	drawn.
		if (!selected)
		{
			g2d.setColor(getColor());
			g2d.fillRect(0, 0, width, height);
		}
		else
		{
			g.setColor(getColor());
			g2d.drawRect(0, 0, width, height);
		}
		
		// Looks whether to draw the bounds of the object or not.
		if (getDrawBounds())
		{
			g2d.setColor(Color.black);
			Rectangle bounds = getBounds();
			g2d.drawRect(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
		}
		
		// Changes the transform back to what it was before.
		g2d.setTransform(saveAT);
	}

	/**
	 * Checks to see if this object has collided with the object passed in.
	 * @param object - The object to check against to see if
	 * @return True if the objects are colliding. False if they are not.
	 */
	@Override
	public boolean collidesWith(ICollider object)
	{
		// If the object is a car, will check to see if this ticket has collided with it.
		if (object instanceof Car)
		{
			Car player = (Car) object;

			// Creates the bounds for the car and this ticket.
			Rectangle carBounds = player.getBounds();
			Rectangle ticketBounds = getBounds();
			
			// Checks to see if the ticket and the car has collided.
			if (ticketBounds.intersects(carBounds)){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * If objects are colliding, then this method will handle the response from the collision.
	 * @param object - The response this object will do depends on the object passed in.
	 */
	@Override
	public void handleCollision(ICollider object)
	{
		if (object instanceof Car) {
			expired = true;
		}
	}
}

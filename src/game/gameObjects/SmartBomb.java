package game.gameObjects;

import game.interfaces.ICollider;
import game.interfaces.ISelectable;
import game.interfaces.IStrategy;
import game.strategies.StandardStrategy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class SmartBomb extends MonsterBall implements ISelectable {

	private IStrategy curStrategy;		// Holds the Smart Bomb's current strategy.
	private SmartBombBody body;
	private SmartBombPointer pointer;
	private boolean selected = false;	// Flag indicating whether this object has been clicked.
	private boolean expired = false;	// Flag indicating the object needs to be deleted.
	
	/**
	 * Creates a new Smart Bomb with its center initialized to the X and Y values passed in. Initializes the size, direction,
	 * speed and also sets the color.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setRadius - The size of the Smart Bomb object.
	 * @param heading - The direction the bomb when begin traveling in when created.
	 * @param speed - The speed the bomb will be moving at when created.
	 * @param setColor - The color of the Smart Bomb.
	 */
	public SmartBomb(double setX, double setY, int setRadius, int heading, int speed, Color setColor)
	{
		super(setX, setY, setRadius, heading, speed, setColor);
		curStrategy = new StandardStrategy();
		
		body = new SmartBombBody(getRadius(), getColor());
		pointer = new SmartBombPointer(getRadius(), Color.WHITE);
		pointer.translate(getRadius(), 1);
		pointer.scale(3, 1);
	}
	
	/**
	 * Creates a new Smart Bomb with its center initialized to the X and Y values passed in. Initializes the size, direction,
	 * speed and also sets the color.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setRadius - The size of the Smart Bomb object.
	 * @param heading - The direction the bomb when begin traveling in when created.
	 * @param speed - The speed the bomb will be moving at when created.
	 * @param setColor - The color of the Smart Bomb.
	 * @param strategy - The strategy the bomb will initially start with.
	 */
	public SmartBomb(double setX, double setY, int setRadius, int heading, int speed, Color setColor,
			IStrategy strategy)
	{
		super(setX, setY, setRadius, heading, speed, setColor);
		curStrategy = strategy;
		
		body = new SmartBombBody(getRadius(), getColor());
		pointer = new SmartBombPointer(getRadius(), Color.WHITE);
		pointer.translate(getRadius(), 1);
		pointer.scale(3, 1);
	}

	/**
	 * Creates a new Smart Bomb with its center initialized to the X and Y values passed in. Initializes the size, direction,
	 * speed and also sets the color.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setRadius - The size of the Smart Bomb object.
	 * @param heading - The direction the bomb when begin traveling in when created.
	 * @param speed - The speed the bomb will be moving at when created.
	 * @param red - The amount of red tint (0-255) to give the object being placed.
	 * @param green - The amount of green tint (0-255) to give the object being placed.
	 * @param blue - the amount of blue tint (0-255) to give the object being placed.
	 */
	public SmartBomb(double setX, double setY, int setRadius, int heading, int speed, int red, int green, int blue) 
	{
		super(setX, setY, setRadius, heading, speed, red, green, blue);
		curStrategy = new StandardStrategy();
		
		body = new SmartBombBody(getRadius(), getColor());
		pointer = new SmartBombPointer(getRadius(), Color.WHITE);
		pointer.translate(getRadius(), 1);
		pointer.scale(3, 1);
	}
	
	/**
	 * Creates a new Smart Bomb with its center initialized to the X and Y values passed in. Initializes the size, direction,
	 * speed and also sets the color.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setRadius - The size of the Smart Bomb object.
	 * @param heading - The direction the bomb when begin traveling in when created.
	 * @param speed - The speed the bomb will be moving at when created.
	 * @param red - The amount of red tint (0-255) to give the object being placed.
	 * @param green - The amount of green tint (0-255) to give the object being placed.
	 * @param blue - the amount of blue tint (0-255) to give the object being placed.
	 */
	public SmartBomb(double setX, double setY, int setRadius, int heading, int speed, int red, int green, int blue,
			IStrategy strategy) 
	{
		super(setX, setY, setRadius, heading, speed, red, green, blue);
		curStrategy = strategy;
		
		body = new SmartBombBody(getRadius(), getColor());
		pointer = new SmartBombPointer(getRadius(), Color.WHITE);
		pointer.translate(getRadius(), 1);
		pointer.scale(3, 1);
	}
	
	/**
	 * Sets the Smart Bomb's method of attack according to the strategy passed in.
	 * @param s - The new strategy the Bomb will follow.
	 */
	public void setStrategy(IStrategy s)
	{
		curStrategy = s;
	}
	
	/**
	 * Returns the Smart Bomb's current strategy.
	 * @return The strategy the Smart Bomb is currently using.
	 */
	public IStrategy getStrategy()
	{
		return curStrategy;
	}
	
	/**
	 * Calls the code for the bomb's current strategy.
	 */
	public void invokeStategy(double elapsed)
	{
		curStrategy.apply(this);
		
		if (curStrategy.toString().compareToIgnoreCase("Chase") == 0)
		{
			setXVelocity(getSpeed());
			setYVelocity(getSpeed());
		}
		move(elapsed);
	}
	
	/**
	 * Looks to see if the object has expired and needs to be removed.
	 * @return True means the object has expired and is requesting to be removed. False if it has not expired.
	 */
	public boolean isExpired()
	{
		return expired;
	}
	
	/**
	 * Creates a square bounds around the bomb and returns it.
	 * @return A rectangle 
	 */
	public Rectangle getBounds()
	{
		int radius = getRadius();		// The radius of the bomb.
		// Gets the lower left hand corner point because rectangles are made going in the pos x and y
		//	directions.
		int x = (int) getTranslation().getTranslateX() - radius;
		int y = (int) getTranslation().getTranslateY() - radius;
		
		// Creates a square around the bomb. This is its bounds.
		Rectangle bombBounds = new Rectangle(x, y, (radius*2), (radius*2));
		return bombBounds;
	}
	
	@Override
	public String toString()
	{
		String myDesc = String.format("Bomb: loc=(%.2f, %.2f), color=(r=%d, g=%d, b=%d), speed=%d, heading=%d, "
				+ "radius=%d, strategy=%s", getX(), getY(), getColor().getRed(), getColor().getGreen(), getColor().getBlue(),
				getSpeed(), getHeading(), getRadius(), getStrategy());
		return myDesc;
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
	
	/**
	 * Checks to see if the bounds of the area, in which the the user pressed and released the mouse button,
	 * intersect with the object.
	 * @param mouseArea - The area in which the user pressed and then released the mouse button. 
	 */
	public boolean contains(Rectangle mouseArea)
	{
		// Checks to see if the bounds of mouse area intersect with the bounds of the object.
		boolean selected = mouseArea.intersects(getBounds());

		return selected;
	}
	
	/**
	 * Uses the graphics class to draw the smart bomb on screen.
	 * @param g - The graphics object to draw with.
	 */
	@Override
	public void draw(Graphics g) 
	{	
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(getTranslation());
		g2d.transform(getRotation());
		
		body.draw(g2d);
		pointer.rotate(20);
		pointer.draw(g2d);
		
		// Looks whether to draw the bounds of the object or not.
		if (getDrawBounds())
		{
			g2d.setColor(Color.black);
			Rectangle bounds = getBounds();
			g2d.drawRect(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
		}
		
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
		if (object instanceof FieldSquare) {
			return super.collidesWith(object);
		}
		
		if (object instanceof Car)
		{
			Car player = (Car) object;
			
			// Grabs the bounds of the player.
			Rectangle playerBounds = player.getBounds();
			
			if (getBounds().intersects(playerBounds)) {
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
		if (object instanceof FieldSquare) {
			super.handleCollision(object);	
		}
		
		if (object instanceof Car) {
			expired = true;
		}
	}
}

package a4.gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import a4.gameObjects.FieldSquare.State;
import a4.interfaces.ICollider;
import a4.interfaces.ISteerable;

/**
 * This class will create and initialize a Monster Ball. Monster Balls are enemies that will destroy the player when touched.
 * The Monster Ball's location, direction, speed, color, and size are configurable when created. After being created, only the
 * ball's color and heading can be changed.
 * @author Adrian
 *
 */
public class MonsterBall extends MoveableObject implements ISteerable {

	private int radius;					// The size of the ball.
	private boolean hitCar;				// Meaning the ball crashed into the car.
	private Vector<ICollider> collided = new Vector<ICollider>();
	
	//TODO: Find a better way to do this
	private FieldSquare lastHitSquare;
	
	/**
	 * Creates a new Monster Ball with its center initialized to the X and Y values passed in. Initializes the size, direction,
	 * speed and also sets the color.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setRadius - The size of the Monster Ball object.
	 * @param heading - The direction the ball when begin traveling in when created.
	 * @param speed - The speed the ball will be moving at when created.
	 * @param setColor - The color of the Monster Ball.
	 */
	public MonsterBall(double setX, double setY, int setRadius, int heading, int speed, Color setColor) {
		super(setX, setY, setColor);
		radius = setRadius;
		super.setHeading(heading);
		super.setSpeed(speed);
		
		this.translate((setX - radius), (setY - radius));
	}

	/**
	 * Creates a new Monster Ball with its center initialized to the X and Y values passed in. Initializes the size, direction,
	 * speed and also sets the color.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setRadius - The size of the Monster Ball object.
	 * @param heading - The direction the ball when begin traveling in when created.
	 * @param speed - The speed the ball will be moving at when created.
	 * @param red - The amount of red tint (0-255) to give the object being placed.
	 * @param green - The amount of green tint (0-255) to give the object being placed.
	 * @param blue - the amount of blue tint (0-255) to give the object being placed.
	 */
	public MonsterBall(double setX, double setY, int setRadius, int heading, int speed, int red, int green, int blue) {
		super(setX, setY, red, green, blue);
		radius = setRadius;
		super.setHeading(heading);
		super.setSpeed(speed);
		
		this.translate((setX - radius), (setY - radius));
	}
	
	/**
	 * Sets the color of the Game Object using Java's predefined colors.
	 * @param setColor - The color to give the Game Object.
	 */
	@Override
	public void setColor(Color newColor)
	{
		super.setColor(newColor);
	}
	
	/**
	 * Sets the color of the Game Object using the RGB color model.
	 * @param red - The amount of red to give the Game Object.
	 * @param green - The amount of green to give the Game Object.
	 * @param blue - The amount of blue to give the Game Object.
	 */
	@Override
	public void setColor(int red, int green, int blue)
	{
		super.setColor(red, green, blue);
	}
	
	/**
	 * Returns the Monster Ball's size.
	 */
	public int getRadius()
	{
		return radius;
	}
	
	/**
	 * Changes the direction of the ball.
	 * @param degrees - The new heading of the ball.
	 */
	@Override
	public void changeHeading(int degrees)
	{		
		// Updates the heading.
		super.setHeading(degrees);
	}
	
	/**
	 * Looks to see if the object has expired and needs to be removed.
	 * @return True means the object has expired and is requesting to be removed. False if it has not expired.
	 */
	public boolean getHitCar()
	{
		return hitCar;
	}
	
	/**
	 * Sets the flag in whether this ball hit the car or not.
	 * @param yesNo
	 */
	public void setHitCar(boolean yesNo)
	{
		hitCar = yesNo;
	}
	
	/**
	 * Creates a square bounds around the ball and returns it.
	 * @return
	 */
	public Rectangle getBounds()
	{
		// Gets the lower left hand corner point because rectangles are made going in the pos x and y
		//	directions.
		int x = (int) getTranslation().getTranslateX() - radius;
		int y = (int) getTranslation().getTranslateY() - radius;
		
		Rectangle ballBounds = new Rectangle(x, y, (radius*2), (radius*2));
		return ballBounds;
	}
	
	@Override
	public String toString()
	{
		String myDesc = String.format("Ball: loc=(%.2f, %.2f), color=(r=%d, g=%d, b=%d), speed=%d, heading=%d, "
				+ "radius=%d", getX(), getY(), getColor().getRed(), getColor().getGreen(), getColor().getBlue(),
				getSpeed(), getHeading(), getRadius());
		return myDesc;
	}

	/**
	 * Uses the graphics class to draw the monster ball on screen.
	 * @param g - The graphics object to draw with.
	 */
	@Override
	public void draw(Graphics g) {
		// Calculates where to draw the circle so that the current location is the center of the ball.
		//int x = (int) (getX() - radius);
		//int y = (int) (getY() - radius);
		
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		
		// Sets the color and moves the object to where it should be drawn. Will then draw it.
		g2d.setColor(getColor());
		g2d.transform(getTranslation());
		g2d.fillOval(0, 0, (radius * 2), (radius * 2));
		
		// Looks whether to draw the bounds of the object or not.
		if (getDrawBounds())
		{
			g2d.setColor(Color.black);
			Rectangle bounds = getBounds();
			g2d.drawRect(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
		}
		
		// Reverts the graphics object's transform to what it was prior to this object.
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
		boolean result = false;				// Used to indicate whether the ball is colliding with the object.
		boolean notCollided = true;			// Used to indicate if ball has already collided with some square.

		// Sets flag to false meaning the ball has already collided with this object and is still colliding.
		if (collided.contains(object)) {
			notCollided = false;
		}
		
		if (object instanceof FieldSquare)
		{
			FieldSquare square = (FieldSquare) object;	// Holds the FieldSquare object.
			
			Rectangle ballBounds = getBounds();
			Rectangle squareBounds = square.getBounds(); 
					
			// Checks to see if the bounds of the square and ball are colliding.
			if (ballBounds.intersects(squareBounds) &&
					((square.isOwned() == State.OWNED) || (square.isOwned() == State.POTENTIAL)))
			{
				result = true;
				// Indicates that the object on the list has already been handled. If the object is not on
				//	the list, then this doesn't matter.
				notCollided = true;
			}
			
			// Checks to see if the bounds of the square and ball are colliding.
			if (ballBounds.intersects(squareBounds) && (square.isOwned() == State.POTENTIAL))
			{
				result = true;
				// Indicates that the object on the list has already been handled. If the object is not on
				//	the list, then this doesn't matter.
				notCollided = true;
			}
		}
		
		if (object instanceof Car)
		{
			Car player = (Car) object;	// Holds the player car.
			
			// Grabs the bounds of the player car.
			Rectangle playerBounds = player.getBounds();
			
			if (playerBounds.intersects(getBounds())) {
				result = true;
			}
		}
		
		// If the object on the list is no longer colliding, we remove it from the array.
		if (!notCollided) {
			collided.remove(object);
			
			if (object == lastHitSquare)
				lastHitSquare = null;
		}
		
		return result;
	}

	/**
	 * If objects are colliding, then this method will handle the response from the collision.
	 * @param object - The response this object will do depends on the object passed in.
	 */
	@Override
	public void handleCollision(ICollider object) 
	{
		// If the object exists on the list, then it was already handled.
		if (collided.contains(object)) {
			return;
		}
		
		// Handles the collision with field squares.
		if (object instanceof FieldSquare)
		{
			FieldSquare square = (FieldSquare) object;
			
			// Will not handle a square that is near the last hit square.
			if (lastHitSquare != null)
			{
				double differenceX = Math.abs(square.getX() - lastHitSquare.getX());
				double differenceY = Math.abs(square.getY() - lastHitSquare.getY());
				
				//TODO: Look into a better way to do this.
				if ((differenceX < 12) || (differenceY < 12))
					return;
			}
			
			int boundsRight = (int) getX() + radius;
			int boundsLeft = (int) getX() - radius;
			int boundsTop = (int) getY() + radius;
			int boundsBottom = (int) getY() - radius;
			
			int squareRight = (int) square.getX() + (square.getSize()/2);
			int squareLeft = (int) square.getX() - (square.getSize()/2);
			int squareTop = (int) square.getY() + (square.getSize()/2);
			int squareBottom = (int) square.getY() - (square.getSize()/2);
			
			if ((boundsRight > squareLeft) || (boundsLeft < squareRight) || (squareTop > boundsBottom) || (boundsTop > squareBottom))
			{
				setHeading(getHeading() + 90);
			}
			
			// Flag indicating that the player loses.
			if (square.isOwned() == State.POTENTIAL) {
				//expired = true;
			}
			
			// Adds the square to the list of objects that have already been handled.
			collided.add(square);
			
			// Keeps a record of the last square it hit.
			lastHitSquare = (FieldSquare) object;
		}
		
		// If the object is a car, then a flag is raised indicating the player loses.
		if (object instanceof Car) {
			hitCar = true;
		}
	}
}

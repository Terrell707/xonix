package a4.gameObjects;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * An abstract class to define the basis of any Movable Game Object.
 * @author Adrian
 *
 */
abstract public class MoveableObject extends GameObject {

	private int heading;	// Goes by degrees. 0 being North. 90 being East, etc.
	private int speed;		// The speed the Movable Object is going.
	private int xVelocity, yVelocity;
	
	/**
	 * Creates a new Movable Game Object with its center initialized to the X and Y values passed in. Initializes its heading to 0
	 * (north) and speed to 1. Also sets the color of the object being created.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setColor - The color to give the object.
	 */
	public MoveableObject(double setX, double setY, Color setColor)
	{
		super(setX, setY, setColor);
		setHeading(0);
		speed = 1;
		xVelocity = 1;
		yVelocity = 1;
	}
	
	/**
	 * Creates a new Movable Game Object with its center initialized to the X and Y values passed in. Initializes the direction
	 * and speed and also sets the color.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setHeading - The direction the object will be facing when created.
	 * @param setSpeed - The initial speed of the object.
	 * @param setColor - The color to give the object.
	 */
	public MoveableObject(double setX, double setY, int setHeading, int setSpeed, Color setColor)
	{
		super(setX, setY, setColor);
		setHeading(setHeading);
		speed = setSpeed;
		xVelocity = setSpeed;
		yVelocity = setSpeed;
	}
	
	/**
	 * Creates a new Movable Game Object with its center initialized to the X and Y values passed in. Also sets the color
	 * of the object being created.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param red - The amount of red tint (0-255) to give the object being placed.
	 * @param green - The amount of green tint (0-255) to give the object being placed.
	 * @param blue - the amount of blue tint (0-255) to give the object being placed.
	 */
	public MoveableObject(double setX, double setY, int red, int green, int blue)
	{
		super(setX, setY, red, green, blue);
		setHeading(0);
		speed = 1;
		xVelocity = 1;
		yVelocity = 1;
	}
	
	/**
	 * Creates a new Movable Game Object with its center initialized to the X and Y values passed in. Initializes the direction
	 * and speed and also sets the color.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setHeading - The direction the object will be facing when created.
	 * @param setSpeed - The initial speed of the object.
	 * @param red - The amount of red tint (0-255) to give the object being placed.
	 * @param green - The amount of green tint (0-255) to give the object being placed.
	 * @param blue - the amount of blue tint (0-255) to give the object being placed.
	 */
	public MoveableObject(double setX, double setY, int setHeading,  int setSpeed, int red, int green, int blue)
	{
		super(setX, setY, red, green, blue);
		setHeading(setHeading);
		speed = setSpeed;
		xVelocity = setSpeed;
		yVelocity = setSpeed;
	}
	
	/**
	 * Returns the heading of the Movable Game Object.
	 * @return The heading of the Game Object.
	 */
	public int getHeading()
	{
		return heading;
	}
	
	/**
	 * Sets the heading of Movable Game Object.
	 * @param newHeading - The new direction the Game Object will move in.
	 */
	public void setHeading(int newHeading)
	{
		if (newHeading > 359){
			newHeading = newHeading - 360;
		}
		else if (newHeading < 0){
			newHeading = 360 + newHeading;
		}
		heading = newHeading;
		resetRotation();
		rotate(Math.toRadians(-heading));
	}
	
	/**
	 * Returns the speed of the Movable Game Object.
	 * @return The speed of the Game Object.
	 */
	public int getSpeed()
	{
		return speed;
	}
	
	/**
	 * Sets the speed of the Movable Game Object.
	 * @param newSpeed - The new speed of the Game Object.
	 */
	public void setSpeed(int newSpeed)
	{
		speed = newSpeed;
		
		if (xVelocity < 0)
			xVelocity = -newSpeed;
		else
			xVelocity = newSpeed;
		
		if (yVelocity < 0)
			yVelocity = -newSpeed;
		else
			yVelocity = newSpeed;
	}
	
	/**
	 * Returns the direction and speed the object is moving on the X plane.
	 * @return The velocity on the X axis.
	 */
	protected int getXVelocity()
	{
		return xVelocity;
	}
	
	/**
	 * Sets the direction and speed the object will be moving on the X axis.
	 * @param newXVelocity - The direction and speed the object will be moving on the X axis. Positive
	 * 	number for forward. Negative number for backward.
	 */
	protected void setXVelocity(int newXVelocity)
	{
		xVelocity = newXVelocity;
	}
	
	/**
	 * Returns the direction and speed the object is moving on the Y axis.
	 * @return The velocity on the Y axis.
	 */
	protected int getYVelocity()
	{
		return yVelocity;
	}
	
	/**
	 * Sets the direction and speed the object will be moving on the Y axis.
	 * @param newXVelocity - The direction and speed the object will be moving on the Y axis. Positive
	 * 	number for forward. Negative number for backward.
	 */
	protected void setYVelocity(int newYVelocity)
	{
		yVelocity = newYVelocity;
	}
	
	/**
	 * Set the location of the center of the Game Object at the same location as the point passed in.
	 * @param point - The point in which to center the Game Object.
	 */
	@Override
	public void setLocation(Point2D point)
	{
		super.setLocation(point);
	}
	
	/**
	 * Sets the location of the center of the Movable Object to the location X and Y that is passed in.
	 * @param newX - The new X coordinate of the Game Object.
	 * @param newY - The new Y coordinate of the Game Object.
	 */
	@Override
	public void setLocation(double setX, double setY)
	{
		super.setLocation(setX, setY);
	}
	
	/**
	 * Moves the Movable Object in the direction of its current heading. The amount of spaces it moves depends
	 * on its current speed.
	 */
	public void move(double elapsed)
	{
		double xDistance = (xVelocity * 30) * elapsed; 			// Dist = rate * time
		double yDistance = (yVelocity * 30) * elapsed;
		double angle = (90 - getHeading()) * (Math.PI/180);	// Converts from degrees to radians.
		
		// Calculates move with the equation: newLocation(x,y) = oldLocation(x,y) + (deltaX, deltaY) where:
		//	deltaX = cos(90-heading) * distance
		//  deltaY = sin(90-heading) * distance
		double deltaX = Math.cos(angle) * xDistance;
		double deltaY = Math.sin(angle) * yDistance;
		
		// Updates the object's location.
		//super.setLocation(newX, newY);
		super.translate(deltaX, deltaY);
	}
	
}

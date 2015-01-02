package game.gameObjects;

import game.interfaces.ICollider;
import game.interfaces.IDrawable;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * The GameObject class inherits from the Point class to give attributes to the point.
 * @author Adrian
 *
 */
abstract public class GameObject implements IDrawable, ICollider {
	
	private double x;				// The x location of the Game Object.
	private double y;				// The y location of the Game Object.
	private boolean drawBounds;		// Flag indicating whether to draw the bounds of the object. 
	private Color color;			// The color of the Game Object.
	private Point2D center;			// The point representing the center of the object.
	private AffineTransform rotation, translation;
	
	/**
	 * Creates a new Game Object with its center initialized to the X and Y values passed in. Also sets the color
	 * of the object being created.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setColor - The color to give the object.
	 */
	public GameObject(double setX, double setY, Color setColor)
	{
		x = setX;
		y = setY;
		color = setColor;
		center = new Point2D.Double(x, y);
		
		rotation = new AffineTransform();
		translation = new AffineTransform();
		
		drawBounds = false;
	}
	
	/**
	 * Creates a new Game Object with its center initialized to the X and Y values passed in. Also sets the color
	 * of the object being created.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param red - The amount of red tint (0-255) to give the object being placed.
	 * @param green - The amount of green tint (0-255) to give the object being placed.
	 * @param blue - the amount of blue tint (0-255) to give the object being placed.
	 */
	public GameObject(double setX, double setY, int red, int green, int blue)
	{
		x = setX;
		y = setY;
		color = new Color(red, green, blue);
		center = new Point2D.Double(x, y);
		
		rotation = new AffineTransform();
		translation = new AffineTransform();
		
		drawBounds = false;
	}
	
	/**
	 * Gets the x location of the Game Object.
	 * @return The x coordinate of the Game Object.
	 */
	public double getX()
	{
		return getTranslation().getTranslateX();
	}
	
	/**
	 * Sets and updates the x location of the Game Object.
	 * @param newX - The new x location of the Game Object.
	 */
	public void setX(double newX)
	{
		x = newX;
		y = getTranslation().getTranslateY();
		
		center.setLocation(x, y);
		
		// Sets the translation to the new x.
		resetTranslation();
		translate(x, y);
	}
	
	/**
	 * Gets the y location of the Game Object.
	 * @return The y coordinate of the Game Object.
	 */
	public double getY()
	{
		return getTranslation().getTranslateY();
	}
	
	/**
	 * Sets and updates the y location of the Game Object.
	 * @param newY - The new y location of the Game Object.
	 */
	public void setY(double newY)
	{
		x = getTranslation().getTranslateX();
		y = newY;
		center.setLocation(x, y);
		
		// Sets the transform to the new y.
		resetTranslation();
		translate(x, y);
	}
	
	/**
	 * Returns the location of the center of the Game Object.
	 * @return A point (x, y) representing the center of the object.
	 */
	public Point2D getLocation()
	{
		x = getTranslation().getTranslateX();
		y = getTranslation().getTranslateY();
		
		return new Point2D.Double(x, y);
	}
	
	/**
	 * Set the location of the center of the Game Object at the same location as the point passed in.
	 * @param point - The point in which to center the Game Object.
	 */
	protected void setLocation(Point2D point)
	{
		center = point;
		x = center.getX();
		y = center.getY();
		
		// Sets the transform to the new y.
		resetTranslation();
		translate(x, y);
	}
	
	/**
	 * Sets the location of the center of the Game Object to the location X and Y that is passed in.
	 * @param newX - The new X coordinate of the Game Object.
	 * @param newY - The new Y coordinate of the Game Object.
	 */
	protected void setLocation(double newX, double newY)
	{
		x = newX;
		y = newY;
		center.setLocation(x, y);
		
		// Sets the transform to the new x and y.
		resetTransforms();
		translate(x, y);
	}
	
	/**
	 * Returns a boolean indicating whether to draw the bounds of the object or not.
	 * @return True, draw the bounds of the object. False, do not draw.
	 */
	public boolean getDrawBounds()
	{
		return drawBounds;
	}
	
	/**
	 * Sets the flag for whether to draw the bounds or not.
	 * @param yesNo - Boolean value to set the flag.
	 */
	public void setDrawBounds(boolean yesNo)
	{
		drawBounds = yesNo;
	}
	
	protected void translate(double dx, double dy)
	{
		translation.translate(dx, dy);
	}
	
	protected AffineTransform getTranslation()
	{
		return translation;
	}
	
	protected void resetTranslation()
	{
		translation.setToIdentity();
	}
	
	protected void rotate(double radians)
	{
		rotation.rotate(radians);
	}
	
	protected AffineTransform getRotation()
	{
		return rotation;
	}
	
	protected void resetRotation()
	{
		rotation.setToIdentity();
	}
	
	protected void resetTransforms()
	{
		translation.setToIdentity();
		rotation.setToIdentity();
	}
	
	/**
	 * Returns the color of the Game Object.
	 * @return The color of the object.
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Sets the color of the Game Object using Java's predefined colors.
	 * @param setColor - The color to give the Game Object.
	 */
	protected void setColor(Color setColor)
	{
		color = setColor;
	}
	
	/**
	 * Sets the color of the Game Object using the RGB color model.
	 * @param red - The amount of red to give the Game Object.
	 * @param green - The amount of green to give the Game Object.
	 * @param blue - The amount of blue to give the Game Object.
	 */
	protected void setColor(int red, int green, int blue)
	{
		color = new Color(red, green, blue);
	}

}

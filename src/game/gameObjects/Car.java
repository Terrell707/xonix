package game.gameObjects;

import game.interfaces.ICollider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;


/**
 * This class will create and initialize one player controlled car. This is the object that the player will control.
 * The car's location, color, and size are configurable when being created. After being created, the none of the attributes
 * of the car can be changed. The location can only be moved by calling the Move method on the car.
 * @author Adrian
 *
 */
public class Car extends MoveableObject {
	
	private static Car playerCar;
	private int width;
	private int height;
	
	/**
	 * Creates and places a Car in the location (X, Y), with the color that is passed in, and with the width and
	 * height that is passed in.
	 * @param setX - The x location the car will be placed.
	 * @param setY - The y location the car will be placed.
	 * @param setColor - The color to give the object using Java's predefined colors.
	 * @param setWidth - How wide the car will be.
	 * @param setHeight - How tall the car will be.
	 */
	private Car(double setX, double setY, Color setColor, int setWidth, int setHeight)
	{
		super(setX, setY, setColor);
		width = setWidth;
		height = setHeight;
		
		// Sets where the object will be translated to in the game world.
		this.translate((setX - (width / 2)), (setY - (height / 2)));
	}
	
	/**
	 * Creates and places a Car in the location (X, Y), with three numbers representing the custom color of the car,
	 * and with the width and height that is passed in.
	 * @param setX - The x location the car will be placed.
	 * @param setY - The y location the car will be placed.
	 * @param red - The amount of red tint (0-255) to give the object being placed.
	 * @param green - The amount of green tint (0-255) to give the object being placed.
	 * @param blue - the amount of blue tint (0-255) to give the object being placed.
	 * @param setWidth - How wide the car will be.
	 * @param setHeight - How tall the car will be.
	 */
	private Car(double setX, double setY, int red, int green, int blue, int setWidth, int setHeight)
	{
		super(setX, setY, red, green, blue);
		width = setWidth;
		height = setHeight;
		
		// Sets where the object will be translated to in the game world.
		this.translate((setX - (width / 2)), (setY - (height / 2)));
	}
	
	
	public static Car getCar()
	{
		return playerCar;
	}
	
	public static Car getCar(double setX, double setY, Color setColor, int setWidth, int setHeight)
	{
		if (playerCar == null)
			playerCar = new Car(setX, setY, setColor, setWidth, setHeight);
		return playerCar;
	}
	
	public static Car getCar(double setX, double setY, int red, int green, int blue, int setWidth, int setHeight)
	{
		if (playerCar == null)
			playerCar = new Car(setX, setY, red, green, blue, setWidth, setHeight);
		return playerCar;
	}
	
	/**
	 * Returns the width of the car object.
	 * @return Returns the width of the car.
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Returns the height of the car object.
	 * @return Returns the height of the car.
	 */
	public int getHeight()
	{
		return height;
	}
	
	/**
	 * Changes the direction of the car.
	 * @param newHeading - Only accepts values of 0 (North), 90 (East), 180 (South), or 270 (West)
	 */
	@Override
	public void setHeading(int newHeading)
	{
		switch (newHeading)
		{
			case 0: 
				super.setHeading(newHeading);
				super.resetRotation();
				super.rotate(Math.toRadians(-newHeading));
				break;
			case 90:
				super.setHeading(newHeading);
				super.resetRotation();
				super.rotate(Math.toRadians(-newHeading));
				break;
			case 180:
				super.setHeading(newHeading);
				super.resetRotation();
				super.rotate(Math.toRadians(-newHeading));
				break;
			case 270:
				super.setHeading(newHeading);
				super.resetRotation();
				super.rotate(Math.toRadians(-newHeading));
				break;
		}
	}
	
	/**
	 * Updates the X coordinate so that the translation moves the car's center to the new X.
	 */
	public void setX(double newX)
	{
		super.setX((newX - (width/2)));
	}
	
	/**
	 * Updates the Y coordinate so that the translation moves the car's center to the new Y.
	 */
	public void setY(double newY)
	{
		super.setY((newY - (height/2)));
	}
	
	/**
	 * Returns the information for Car in a readable format.
	 */
	@Override
	public String toString()
	{
		String myDesc = String.format("Car: loc=(%.2f, %.2f), color=(r=%d, g=%d, b=%d), speed=%d, heading=%d, "
				+ "width=%d, height=%d", getX(), getY(), getColor().getRed(), getColor().getGreen(), getColor().getBlue(),
				getSpeed(), getHeading(), getWidth(), getHeight());
		return myDesc;
	}
	
	/**
	 * Creates a bounds around the player car and returns it.
	 * @return
	 */
	public Rectangle getBounds()
	{
		// Gets the lower left hand corner point because rectangles are made going in the pos x and y
		//	directions.
		int x = (int) getTranslation().getTranslateX() - (width/2);	
		int y = (int) getTranslation().getTranslateY() - (height/2);
		
		Rectangle carBounds = new Rectangle(x, y, width, height);
		return carBounds;
	}

	/**
	 * Uses the graphics class to draw the car on screen.
	 * @param g - The graphics object to draw with.
	 */
	@Override
	public void draw(Graphics g)
	{	
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		
		/*  Calculates where to draw the bomb so that the current location is in the center of the bomb.
		 *	Creates a triangle.
		 *
		 *  (x1,y1)---(x3,y1)
		 * 		\		 /
		 * 		 \		/
		 * 		  \    /
		 * 		  (x2,y2)      			
		 */
		
		int x1 = (int) 0;					// The x coordinate for the upper left point of the triangle.
		int x2 = (int) (0 + (width/2));		// The x coordinate for the bottom point of the triangle.
		int x3 = (int) (0 + width);			// The x coordinate for the upper right point of the triangle.
		int y1 = (int) 0;					// The y coordinates for the upper left and right points of the triangle.
		int y2 = (int) (0 + height);		// The y coordinate for the bottom point of the triangle.
		
		// Creates the array of x and y points to construct a triangle.
		int x[] = {x1, x2, x3};
		int y[] = {y1, y2, y1};
		
		// Constructs a filled triangle with the set color.
		g2d.setColor(getColor());
		g2d.transform(getTranslation()); // Updates the object's location.
		g2d.transform(getRotation());
		g2d.fillPolygon(x, y, 3);
		
		// Looks whether to draw the bounds of the object or not.
		if (getDrawBounds())
		{
			g2d.setColor(Color.black);
			Rectangle bounds = getBounds();
			g2d.drawRect(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
		}
		
		// Reverts the transform for the graphics object back to what it was before this object.
		g2d.setTransform(saveAT);
	}
	
	@Override
	public void handleCollision(ICollider object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean collidesWith(ICollider object) {
		// TODO Auto-generated method stub
		return false;
	}

}

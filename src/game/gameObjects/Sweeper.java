package game.gameObjects;

import game.interfaces.ICollider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Sweeper extends MoveableObject {

	private int size;					// The size of the bezier curve.
	private Point2D[] controlPoints;	// The lower, upper left points and upper, lower right points.
	private boolean context;			// A flag indicating whether the context lines of the curve should be drawn.
	private boolean objectHit;			// A flag indicating whether this object has collided with another object.
	private boolean expired;			// A flag indicating whether to remove this object.
	
	/**
	 * Creates a new Sweeper game object with its center initialized to the X and Y values passed in. Initializes its heading to 0
	 * (north) and speed to 1. Also sets the color of the object being created.
	 * @param setX - The x location on the screen that the Sweeper will be placed.
	 * @param setY - The y location on the screen that the Sweeper will be placed.
	 * @param setSize - The size of the Sweeper object.
	 * @param setColor - The color to give the Sweeper.
	 */
	public Sweeper(double setX, double setY, int setSize, Color setColor) {
		super(setX, setY, setColor);
		size = setSize;
		setHeading(0);
		setSpeed(1);
		
		// Sets initial points for the sweeper object.
		initialPoints();
		
		// Places the object in the correct place in the game world.
		translate((setX - (size/2)), (setY - (size/2)));
	}

	/**
	 * Creates a new Sweeper game object with its center initialized to the X and Y values passed in. Initializes the direction
	 * and speed and also sets the color.
	 * @param setX - The x location on the screen that the Sweeper will be placed.
	 * @param setY - The y location on the screen that the Sweeper will be placed.
	 * @param setHeading - The direction the Sweeper will be facing when created.
	 * @param setSpeed - The initial speed of the Sweeper.
	 * @param setSize - The size of the Sweeper object.
	 * @param setColor - The color to give the object.
	 */
	public Sweeper(double setX, double setY, int setHeading, int setSpeed, int setSize, Color setColor) {
		super(setX, setY, setHeading, setSpeed, setColor);
		size = setSize;
		
		// Sets initial points for the sweeper object.
		initialPoints();
		
		// Places the object in the correct place in the game world.
		translate((setX - (size/2)), (setY - (size/2)));
	}

	/**
	 * Creates a new Sweeper object with its center initialized to the X and Y values passed in. Also sets the color
	 * of the object being created.
	 * @param setX - The x location on the screen that the Sweeper will be placed.
	 * @param setY - The y location on the screen that the Sweeper will be placed.
	 * @param setSize - The size of the Sweeper object.
	 * @param red - The amount of red tint (0-255) to give the Sweeper being placed.
	 * @param green - The amount of green tint (0-255) to give the Sweeper being placed.
	 * @param blue - the amount of blue tint (0-255) to give the Sweeper being placed.
	 */
	public Sweeper(double setX, double setY, int setSize, int red, int green, int blue) {
		super(setX, setY, red, green, blue);
		size = setSize;
		
		// Sets initial points for the sweeper object.
		initialPoints();
		
		// Places the object in the correct place in the game world.
		translate((setX - (size/2)), (setY - (size/2)));
	}

	/**
	 * Creates a new Sweeper object with its center initialized to the X and Y values passed in. Initializes the direction
	 * and speed and also sets the color.
	 * @param setX - The x location on the screen that the Sweeper will be placed.
	 * @param setY - The y location on the screen that the Sweeper will be placed.
	 * @param setHeading - The direction the Sweeper will be facing when created.
	 * @param setSpeed - The initial speed of the Sweeper.
	 * @param setSize - The size of the Sweeper object.
	 * @param red - The amount of red tint (0-255) to give the Sweeper being placed.
	 * @param green - The amount of green tint (0-255) to give the Sweeper being placed.
	 * @param blue - the amount of blue tint (0-255) to give the Sweeper being placed.
	 */
	public Sweeper(double setX, double setY, int setHeading, int setSpeed, int setSize,
			int red, int green, int blue) {
		super(setX, setY, setHeading, setSpeed, red, green, blue);
		size = setSize;
		
		// Sets initial points for the sweeper object.
		initialPoints();
		
		// Places the object in the correct place in the game world.
		translate((setX - (size/2)), (setY - (size/2)));
	}
	
	/**
	 * Defines the initial points that a new Sweeper object will start at.
	 */
	private void initialPoints()
	{
		// Sets initial points for the sweeper object.
		controlPoints = new Point2D[4];
		Point2D q0 = new Point2D.Double((0 - (size/2)), (0 - (size/2)));
		Point2D q1 = new Point2D.Double(q0.getX(), q0.getY() + (size*2));
		Point2D q2 = new Point2D.Double((q1.getX() + (size*2)), q1.getY());
		Point2D q3 = new Point2D.Double(q2.getX(), (q2.getY() - (size*2)));
		
		setPoints(q0, q1, q2, q3);
		
		// The extra lines for the bezier curve will not be shown by default.
		context = false;
		
		// The flag that is set when an object is hit.  
		objectHit = false;
	}

	/**
	 * Sets the points to set the bezier curve.
	 * @param q0 - Sets the lower left point.
	 * @param q1 - Sets the upper left point.
	 * @param q2 - Sets the upper right point.
	 * @param q3 - Sets the lower right point.
	 */
	public void setPoints(Point2D q0, Point2D q1, Point2D q2, Point2D q3)
	{
		controlPoints[0] = q0;
		controlPoints[1] = q1;
		controlPoints[2] = q2;
		controlPoints[3] = q3;
	}
	
	/**
	 * Set whether to draw the context lines of the sweeper object or not.
	 * @param yesNo	- True, will draw the context line. False, it will not.
	 */
	public void setContext(boolean yesNo)
	{
		context = yesNo;
	}
	
	/**
	 * Sets the object hit flag.
	 * @param yesNo - The value to set the object hit.
	 */
	public void setHit(boolean yesNo)
	{
		objectHit = yesNo;
	}
	
	public void setExpired(boolean yesNo)
	{
		expired = yesNo;
	}
	
	/**
	 * Returns whether the flag has been set that an object has been hit or not.
	 * @return True if this object has just collided with another object. False if not.
	 */
	public boolean getObjectHit()
	{
		return objectHit;
	}
	
	public boolean getExpired()
	{
		return expired;
	}
	
	/**
	 * Creates a bounds around the sweeper and returns it.
	 * @return
	 */
	public Rectangle getBounds()
	{
		// Gets the lower left hand corner point because rectangles are made going in the pos x and y
		//	directions.
		int x = (int) getTranslation().getTranslateX() - (size/2);	
		int y = (int) getTranslation().getTranslateY() - (size/2);
		
		Rectangle sweeperBounds = new Rectangle(x, y, size, size);
		return sweeperBounds;
	}
	
	/**
	 * Draws the bezier curve for the sweeper.
	 */
	@Override
	public void draw(Graphics g) 
	{	
		// Grabs the graphics object's transform to re-apply later.
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		
		// Applies the transformations to the object.
		g2d.transform(getTranslation());
		g2d.transform(getRotation());
		
		// Sets the color and then draws the curve.
		g2d.setColor(getColor());
		drawBezierCurve(controlPoints, 0, g2d);
		
		// Looks whether to draw the bounds of the object or not.
		if (getDrawBounds())
		{
			g2d.setColor(Color.black);
			Rectangle bounds = getBounds();
			g2d.drawRect(0, 0, (int)bounds.getWidth(), (int)bounds.getHeight());
		}
		
		// Returns the transform to what it was before.
		g2d.setTransform(saveAT);
	}
	
	/**
	 * Draws the bezier curve.
	 * @param controlVector - The four main points that make up the curve.
	 * @param level - The current level down we are at drawing the sweeper.
	 * @param g2d - The graphics object to draw the shape.
	 */
	private void drawBezierCurve(Point2D[] controlVector, int level, Graphics2D g2d)
	{
		if (level > 6)
		{
			g2d.drawLine((int)controlVector[0].getX(), (int)controlVector[0].getY(), (int)controlVector[1].getX(), (int)controlVector[1].getY());
			g2d.drawLine((int)controlVector[1].getX(), (int)controlVector[1].getY(), (int)controlVector[2].getX(), (int)controlVector[2].getY());
			g2d.drawLine((int)controlVector[2].getX(), (int)controlVector[2].getY(), (int)controlVector[3].getX(), (int)controlVector[3].getY());
		}
		else
		{
			// Will draw out the context lines of the bezier curve if the flag is set to true.
			if (context)
			{
				g2d.drawLine((int)controlVector[0].getX(), (int)controlVector[0].getY(), (int)controlVector[1].getX(), (int)controlVector[1].getY());
				g2d.drawLine((int)controlVector[1].getX(), (int)controlVector[1].getY(), (int)controlVector[2].getX(), (int)controlVector[2].getY());
				g2d.drawLine((int)controlVector[2].getX(), (int)controlVector[2].getY(), (int)controlVector[3].getX(), (int)controlVector[3].getY());
			}
			
			// Creates new arrays to hold the left and right side of the bezier curve. 
			Point2D[] left = new Point2D[4];
			Point2D[] right = new Point2D[4];
			// Breaks the curve into its left and right sides. Will then draw out the twos sides seperately.
			subDivideCurve(controlVector, left, right);
			drawBezierCurve(left, level+1, g2d);
			drawBezierCurve(right, level+1, g2d);
		}
	}
	
	/**
	 * Takes the control vector (controlPoints) and breaks it into its left side (left) and right side (right).
	 * @param controlPoints - A Point2D array containing the points of the bezier curve.
	 * @param left - A Point2D array containing the right side of the bezier curve.
	 * @param right - A Point2D array containing the left side of the bezier curve.
	 */
	private void subDivideCurve(Point2D[] controlPoints, Point2D[] left, Point2D[] right)
	{		
		left[0] = controlPoints[0];
		right[3] = controlPoints[3];
		
		double r1X = (controlPoints[0].getX() + controlPoints[1].getX()) / 2.0;
		double r1Y = (controlPoints[0].getY() + controlPoints[1].getY()) / 2.0;
		left[1] = new Point2D.Double(r1X, r1Y);
		
		double s2X = (controlPoints[2].getX() + controlPoints[3].getX()) / 2.0;
		double s2Y = (controlPoints[2].getY() + controlPoints[3].getY()) / 2.0;
		right[2] = new Point2D.Double(s2X, s2Y);
		
		double r2X = ((left[1].getX() / 2.0) + ((controlPoints[1].getX() + controlPoints[2].getX()) / 4.0));
		double r2Y = ((left[1].getY() / 2.0) + ((controlPoints[1].getY() + controlPoints[2].getY()) / 4.0));
		left[2] = new Point2D.Double(r2X, r2Y);
		
		double s1X = ((right[2].getX() / 2.0) + ((controlPoints[1].getX() + controlPoints[2].getX()) / 4.0));
		double s1Y = ((right[2].getY() / 2.0) + ((controlPoints[1].getY() + controlPoints[2].getY()) / 4.0));
		right[1] = new Point2D.Double(s1X, s1Y);
		
		double r3X = (left[2].getX() + right[1].getX()) / 2.0;
		double r3Y = (left[2].getY() + right[1].getY()) / 2.0;
		left[3] = new Point2D.Double(r3X, r3Y);
		
		right[0] = left[3];
	}

	@Override
	public boolean collidesWith(ICollider object)
	{
		// Checks to see if the sweeper intersects with the object.
		if (!(object instanceof FieldSquare))
		{
			if (getBounds().intersects(object.getBounds()))
			{
				System.out.println("Collided with " + object);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void handleCollision(ICollider object)
	{
		objectHit = true;
	}

}

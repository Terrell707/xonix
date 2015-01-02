package game.gameObjects;

import game.interfaces.ICollider;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 * This class will create and initialize a Field Square. The field squares serve as both the boundry and score of the
 * game. The Field Square's location, color, and size are configurable when being created. After being created, the square's
 * color can be changed.
 * @author Adrian
 *
 */
public class FieldSquare extends FixedObject {

	private int size;
	public enum State {FIELD, POTENTIAL, OWNED}
	private State owner;

	/**
	 * Creates a new Field Square with its center initialized to the X and Y values passed in. Also sets the color
	 * of the square being created using Java's predefined Colors.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setColor - The color to give the object using Java's predefined colors.
	 */
	public FieldSquare(double setX, double setY, Color setColor, int setSize) {
		super(setX, setY, setColor);
		size = setSize;
		owner = State.FIELD;
		
		// Sets where the object will be translated to in the game world.
		this.translate((setX - (size / 2)), (setY - (size / 2)));
	}

	/**
	 * Creates a new Field Square with its center initialized to the X and Y values passed in. Also sets the color
	 * of the square being created using Java's predefined Colors.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setColor - The color to give the object using Java's predefined colors.
	 * @param owner - Who owns the field square when created.
	 */
	public FieldSquare(double setX, double setY, Color setColor, int setSize, State setOwner) {
		super(setX, setY, setColor);
		size = setSize;
		owner = setOwner;
		
		// Sets where the object will be translated to in the game world.
		this.translate((setX - (size / 2)), (setY - (size / 2)));
	}
	
	/**
	 * Creates a new Field Square with its center initialized to the X and Y values passed in. Also sets the color
	 * of the Field Square being created.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param red - The amount of red tint (0-255) to give the object being placed.
	 * @param green - The amount of green tint (0-255) to give the object being placed.
	 * @param blue - the amount of blue tint (0-255) to give the object being placed.
	 */
	public FieldSquare(double setX, double setY, int red, int green, int blue, int setSize) {
		super(setX, setY, red, green, blue);
		size = setSize;
		owner = State.FIELD;
		
		// Sets where the object will be translated to in the game world.
		this.translate((setX - (size / 2)), (setY - (size / 2)));
	}
	
	/**
	 * Sets the color of the Field Square using Java's predefined colors.
	 * @param setColor - The color to give the Game Object.
	 */
	@Override
	public void setColor(Color newColor)
	{
		super.setColor(newColor);
	}
	
	/**
	 * Sets the color of the Field Square using the RGB color model.
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
	 * Returns the size of the field square.
	 * @return Returns the size of the square.
	 */
	public int getSize()
	{
		return size;
	}
	
	/**
	 * Changes if the Field Square is owned by the player or not.
	 */
	public void changeOwnership(State newOwner)
	{
		owner = newOwner;
	}
	
	/**
	 * Returns who owns the Field Square.
	 * @return FIELD if the square belongs to the field. POTENTIAL if the car is in the process of being owned
	 * 			by the player. OWNED if the square is currently owned by the player.
	 */
	public State isOwned()
	{
		return owner;
	}
	
	/**
	 * Creates a bounds around the square and returns it.
	 * @return
	 */
	public Rectangle getBounds()
	{
		int x = (int) getTranslation().getTranslateX() - (size/2);
		int y = (int) getTranslation().getTranslateY() - (size/2);
		
		Rectangle squareBounds = new Rectangle(x, y, size, size);
		return squareBounds;
	}
	
	/**
	 * Returns the information for FieldSquare in a readable format.
	 */
	@Override
	public String toString()
	{
		// Displays the Field Square's: center, color, size, and if it is owned or not.
		String myDesc = String.format("FieldSquare: loc= (%.2f, %.2f), color= (r=%d, g=%d, b=%d), size= %d",
				getX(), getY(), getColor().getRed(), getColor().getGreen(), getColor().getBlue(), getSize());
		return myDesc;
	}

	/**
	 * Uses the graphics class to draw the field square on screen.
	 * @param g - The graphics object to draw with.
	 */
	@Override
	public void draw(Graphics g)
	{
		// Grabs the graphics object and saves its current transform.
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		
		// If owned, the square will have an owned color. Otherwise, it will have a "in progress" color.
		if ((owner == State.OWNED) || (owner == State.POTENTIAL)){
			g2d.setColor(getColor());
			g2d.transform(getTranslation());		// Sets where the field square will be drawn at.
			g2d.fillRect(0, 0, size, size);
		}
		
		// Reverts the transform back to what it was prior.
		g2d.setTransform(saveAT);
	}

	@Override
	public boolean collidesWith(ICollider object)
	{
		return false;
	}

	@Override
	public void handleCollision(ICollider object) 
	{

	}
}

package game.gameObjects;

import game.gameObjects.GameObject;

import java.awt.Color;

/**
 * An abstract class to define the basis of any Game Object that will not be moved.
 * @author Adrian
 *
 */
abstract public class FixedObject extends GameObject {

	/**
	 * Creates a new Fixed Object with its center initialized to the X and Y values passed in. Also sets the color
	 * of the object being created.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param setColor - The color to give the object.
	 */
	public FixedObject(double setX, double setY, Color setColor) {
		super(setX, setY, setColor);
	}

	/**
	 * Creates a new Fixed Object with its center initialized to the X and Y values passed in. Also sets the color
	 * of the object being created.
	 * @param setX - The x location on the screen that the object will be placed.
	 * @param setY - The y location on the screen that the object will be placed.
	 * @param red - The amount of red tint (0-255) to give the object being placed.
	 * @param green - The amount of green tint (0-255) to give the object being placed.
	 * @param blue - the amount of blue tint (0-255) to give the object being placed.
	 */
	public FixedObject(double setX, double setY, int red, int green, int blue) {
		super(setX, setY, red, green, blue);
	}

}

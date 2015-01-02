package game.interfaces;

import java.awt.Graphics;

/**
 * This Drawable Interface provides the calling class with the methods to draw itself on screen.
 * @author Adrian
 *
 */
public interface IDrawable {
	
	/**
	 * Uses the graphics class to draw the object on screen.
	 * @param g - The graphics object to draw with.
	 */
	public void draw(Graphics g);
}

package game.interfaces;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * This Selectable Interface gives the calling class the methods to be able to be selected by mouse click.
 * @author Adrian
 *
 */
public interface ISelectable extends IDrawable {

	/**
	 * Sets whether the current object is selected or not.
	 * @param yesNo - True to indicate the object is selected False if it is not.
	 */
	public void setSelected(boolean yesNo);
	
	/**
	 * Checks to see if the object is currently selected.
	 * @return True if the object is selected. False if it is not.
	 */
	public boolean isSelected();
	
	/**
	 * Compares this object's location to the location being passed in. Will then check to see if the two
	 * objects are colliding.
	 * @param p - The location of the object to compare against.
	 * @return True if the objects are colliding. False if they are not.
	 */
	public boolean contains(Point2D p);
	
	/**
	 * Compares this object's location to the area being passed in. 
	 * @param mouseArea - The rectangle that is made when a user clicks and then releases somewhere else.
	 * @return
	 */
	public boolean contains(Rectangle mouseArea);
	
}

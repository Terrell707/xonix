package game.interfaces;

import java.awt.Rectangle;

/**
 * This Collider Interface gives the calling class the methods to be able to collide with other objects, and
 * then respond to that collision.
 * @author Adrian
 *
 */
public interface ICollider {
	
	/**
	 * Checks to see if this object has collided with the object passed in.
	 * @param object - The object to check against to see if
	 * @return True if the objects are colliding. False if they are not.
	 */
	public boolean collidesWith(ICollider object);
	
	/**
	 * If objects are colliding, then this method will handle the response from the collision.
	 * @param object - The response this object will do depends on the object passed in.
	 */
	public void handleCollision(ICollider object);
	
	/**
	 * Grabs the bounds of the object to determine if the objects are colliding.
	 * @return A rectangle representing the bounds of the object.
	 */
	public Rectangle getBounds();

}

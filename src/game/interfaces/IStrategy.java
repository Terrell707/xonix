package game.interfaces;

import game.gameObjects.SmartBomb;

/**
 * Provides functionality for the object to change how it operates during runtime.
 * @author Adrian
 *
 */
public interface IStrategy {
	
	/**
	 * Invokes the code of the current strategy.
	 */
	public void apply(SmartBomb bomb);
	
}

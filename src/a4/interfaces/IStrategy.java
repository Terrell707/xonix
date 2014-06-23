package a4.interfaces;

import a4.gameObjects.SmartBomb;

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

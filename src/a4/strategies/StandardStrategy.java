package a4.strategies;

import a4.gameObjects.SmartBomb;
import a4.interfaces.IStrategy;

/**
 * Used to change how a Smart Bomb acts during runtime. Will make the Smart Bomb bounce around the game
 * field in the same way as a Monster Ball.
 * @author Adrian
 *
 */
public class StandardStrategy implements IStrategy {
	
	/**
	 * Moves the Smart Bomb in the same way as a MonsterBall.
	 */
	@Override
	public void apply(SmartBomb bomb) {

	}
	
	@Override
	public String toString()
	{
		String myDesc = "Standard";
		return myDesc;
	}

}

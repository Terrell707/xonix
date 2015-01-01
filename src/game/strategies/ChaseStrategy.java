package a4.strategies;

import a4.gameObjects.Car;
import a4.gameObjects.SmartBomb;
import a4.interfaces.IStrategy;

/**
 * Used to change how a Smart Bomb acts during runtime. Will cause the Smart Bomb to chase after the
 * player character.
 * @author Adrian
 *
 */
public class ChaseStrategy implements IStrategy {

	private Car prey;		// The object that is being chased.
	
	/**
	 * Sets the object that is being chased.
	 * @param player - The car object that is being chased.
	 */
	public ChaseStrategy(Car player) {
		prey = player;
	}

	/**
	 * Invokes the commands to make the bomb follow the player car.
	 */
	@Override
	public void apply(SmartBomb bomb) {
		giveChase(bomb);
	}
	
	/**
	 * Updates the attributes of the bomb to point towards the player object. Also updates the
	 * speed to be just a bit slower than the player.
	 */
	private void giveChase(SmartBomb bomb)
	{
		double preyX, preyY;		// Used to hold the player's coordinates.
		double bombX, bombY;		// Used to hold the bomb's coordinates.
		double deltaX, deltaY;		// Used to hold the difference in coordinates between the two.
		int newSpeed = 1;
		int newHeading = 0;
		
		// Gets the location of the player and the bomb.
		preyX = prey.getX();
		preyY = prey.getY();
		bombX = bomb.getX();
		bombY = bomb.getY();
		
		// Calculates the difference in position between the two.
		deltaX = Math.abs(preyX - bombX);
		deltaY = Math.abs(preyY - bombY);
		
		// Changes the heading of the bomb to point it towards the player.
		// Calculates this by: tan(theta) = y/x (SOHCOATOA - Tan = Opposite/Adjacent)
		// Our reference axis is positive X goes right, positive Y goes up.
		
		// If the car and bomb are on the same X-axis, then the new heading is either north or south
		//	depending on if the car is above or below the bomb.
		if (preyX == bombX)
		{
			if (preyY > bombY){
				newHeading = 0;
			}
			else{
				newHeading = 180;
			}
		}
		
		// If the car and bomb are on the same Y-axis, then the new heading is either east or west
		//	depending on if the car is to the left or right of the bomb.
		else if (preyY == bombY)
		{
			if (preyX > bombX){
				newHeading = 90;
			}
			else{
				newHeading = 270;
			}
		}
		
		// If car is higher than the bomb, then we calculate theta.
		else if (preyY > bombY){
			// Calculates theta.
			newHeading = (int) Math.toDegrees(Math.atan(deltaY/deltaX));
			// If car's X is less than the bomb's, then theta is shifted by 90 degrees to the left.
			if (preyX < bombX){
				newHeading = newHeading - 90;
			}
			// Otherwise, theta gives the correct heading.
		}
		
		// If car is lower than the bomb, then we calculate theta.
		else if (preyY < bombY){
			// Calculates theta.
			newHeading = (int) Math.toDegrees(Math.atan(deltaY/deltaX));
			// If car's X is greater than the bomb's, then theta is shifted 90 degrees to the right.
			if (preyX > bombX){
				newHeading = newHeading + 90;
			}
			// If car's X is less than the bomb's, then theta is flipped to point the opposite way.
			else{
				newHeading = newHeading + 180;
			}
		}
		
		// Gives the bomb its new heading.
		bomb.setHeading(newHeading);
		
		// Updates the bomb's speed to be a bit slower than the car.
		newSpeed = prey.getSpeed() - 1;
		if (newSpeed <= 0){
			newSpeed = 1;
		}
		bomb.setSpeed(newSpeed);
	}
	
	@Override
	public String toString()
	{
		String myDesc = "Chase";
		return myDesc;
	}

}

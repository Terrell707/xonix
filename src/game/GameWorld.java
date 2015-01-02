package game;

import game.commands.SpawnMonsterBall;
import game.gameObjects.Car;
import game.gameObjects.FieldSquare;
import game.gameObjects.GameCollection;
import game.gameObjects.GameObject;
import game.gameObjects.MonsterBall;
import game.gameObjects.MoveableObject;
import game.gameObjects.SmartBomb;
import game.gameObjects.Sweeper;
import game.gameObjects.TimeTicket;
import game.gameObjects.FieldSquare.State;
import game.interfaces.ICollider;
import game.interfaces.IGameWorld;
import game.interfaces.IObservable;
import game.interfaces.IObserver;
import game.interfaces.ISelectable;
import game.interfaces.Iterator;
import game.sounds.AddTimeClip;
import game.sounds.BGMusic;
import game.sounds.CarExplosionClip;
import game.sounds.CreateSquaresClip;
import game.sounds.GrenadeClip;
import game.sounds.LevelCompleteClip;
import game.strategies.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * This class will handle all methods to create and handle all the game objects on the field. Will also
 * handling signaling all the observing classes to let them know when there are updates in the game world.
 * @author Adrian
 *
 */
public class GameWorld implements IObservable, IGameWorld {
	
	// Constants
	// Object Colors
	private final Color playerColor = Color.red;							// The color of the player.							
	private final Color potentialColor = Color.gray;						// The color of the field not owned by the player.
	private final Color ownedColor = Color.blue;							// The color of the field owned by the player.
	private final Color ticketColor = Color.green;							// The color of the time tickets.
	// Object Sizes
	private final int numSquares = 100;										// Holds the multiplier of squares.
	private final int squareSize = 10;										// The size of each square on the game field.
	private final int ballRadius = 10;										// The radius of each monster ball on the game field.
	private final int gameSize = squareSize * numSquares;					// The max x and y range of the game field.
	private final int fieldSquares = numSquares * numSquares;				// The total number of field squares that will exist.
	// Time to complete, bonus time, and goal
	private final int initialLives = 3;										// The amount of lives a player starts with.
	private final int initialTime = 60;										// The amount of time given on level 1.
	private final int bonusTime = 5;										// The amount of time given to the player when picking up a time ticket.
	private final double initialGoal = 0.50;								// The initial goal of the player.
	// Player starting location
	private final double startingX = ((gameSize / 2.0) - (squareSize / 2.0));	// The starting x location of the player (places them in the center)
	private final double startingY = (squareSize / 2.0);						// The starting y location of the player (places them in the bottom)
	// Notifying messages
	private final String notifyScoreView = "score";							// The message that is passed so that score view knows to update.
	private final String notifyMapView = "map";								// The message that is passed so that map view knows to update.
	// World Window View
	private final double leftWindow = 0;
	private final double rightWindow = gameSize;
	private final double topWindow = gameSize;
	private final double bottomWindow = 0;
	// Collections
	private GameWorldProxy proxy;											// The proxy class to pass to all views.
	private GameCollection gameObjects;										// The collection that holds all the GameObjects.											
	private Vector<IObserver> observers;									// Holds the objects that are observing this object.
	private Vector<FieldSquare> newSquares;									// Holds squares that are in the process of being owned.
	private Vector<FieldSquare> replace;
	private Vector<Integer> location;
	private Vector<Sweeper> sweepers;										// Holds the sweepers that are on the game field.
	private Vector<Integer> times;
	// Sounds
	private CreateSquaresClip winSquaresClip;								// Holds the sound to be played when squares become owned.
	private LevelCompleteClip levelCompleteClip;							// Holds the sound to be played when the level is completed.
	private AddTimeClip addTimeClip;										// Holds the sound to be played when a time ticket is acquired.
	private CarExplosionClip playerDiedClip;								// Holds the sound to be played when player loses a life.
	private GrenadeClip bombExplodeClip;									// Holds the sound to be played when a bomb explodes.
	private BGMusic bgMusic;												// Holds the music that plays in the background.
	private boolean keepSound;												// Holds whether the music was playing before game was paused.
	
	// Variables for World Window View
	private double windowLeft = 0;											// The initial left bounds of the view into the game world.
	private double windowRight = gameSize;									// The initial right bounds of the view into the game world.
	private double windowTop = gameSize;									// The initial top bounds of the view into the game world.
	private double windowBottom = 0;										// The initial bottom bounds of the view into the game world.
	private double zoomDistance = 0.05;										// The distance the game will zoom in/out.
	private double panDistance = 10.0;										// The distance the game will pan.
	private AffineTransform theVTM;											// Holds the VTM transform matrix.
	// Variables for game state
	private int level;														// The player's current game level.
	private int lives;														// The player's current amount of lives.
	private int timer;														// The amount of time left to complete the stage.
	private int timeElapsed;												// Holds the total amount of time that has elapsed since the action timer started.
	private double score;													// The player's current score.
	private double goal;													// The score needed to complete the level.
	private boolean sound;													// Holds whether the sound is on or not.
	private boolean play;													// Holds whether the game is paused or not.
	private boolean bounds;													// Holds whether to draw the bounds of each of the game objects.
	private boolean context;												// Holds whether to draw the context lines of the Sweeper objects.
	private int playerOwned;												// The amount of squares owned by the player.

	/**
	 * Initializes the game world with the default statistics and objects for the game.
	 */
	public GameWorld()
	{
		// Initializes the game world defaults.
		sound = false;
		keepSound = sound;
		play = true;
		
		// Initializes the stats.
		level = 1;
		lives = initialLives;
		score = 0;
		goal = initialGoal;
		
		// Initializes the GameWorld Proxy.
		proxy = new GameWorldProxy(this);
		
		// Initializes the collection of observers and the collection of potentially owned squares.
		observers = new Vector<IObserver>();
		newSquares = new Vector<FieldSquare>();
		sweepers = new Vector<Sweeper>();
		times = new Vector<Integer>();
		
		// Creates the sounds to be played.
		winSquaresClip = new CreateSquaresClip();
		levelCompleteClip = new LevelCompleteClip();
		addTimeClip = new AddTimeClip();
		bombExplodeClip = new GrenadeClip();
		playerDiedClip = new CarExplosionClip();
		bgMusic = new BGMusic();
		
		// Initializes the game field.
		gameStart();
	}
	
	/**
	 * Creates the arrays that will hold the game field and the game objects. Also goes about creating the initial game field.
	 */
	private void gameStart()
	{	
		// Resets the score and the time elapsed.
		playerOwned = 0;
		timer = initialTime - ((level - 1) * 2);
		timeElapsed = 0;
		
		// Initializes the ArrayList
		gameObjects = new GameCollection();
		
		// Creates the game field.
		createGameField();
		
		// Creates the player's car and places it in the center square at the bottom of the game field. Gives it the same height and width
		//	as a field square and gives it a black color.
		Car player = Car.getCar(startingX, startingY, playerColor, squareSize, squareSize);
		player.setLocation(startingX, startingY); 	// Sets the location of the car if it was already created.
		player.setHeading(0); 						// Player starts out facing north.
		gameObjects.add(player);
		
		// Creates the level.
		createLevel();
		
		// Updates the views.
		notifyObservers(notifyScoreView);
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Will lay out the squares and set up the outer region as owned by the player.
	 */
	private void createGameField()
	{
		// Creates the boundary for the game. If the square exists on the outside of the field, then it is 
		//	owned from the beginning.
		for(double xCoordinate = (squareSize/2.0); xCoordinate < gameSize; xCoordinate += squareSize)
		{
			for(double yCoordinate = (squareSize/2.0); yCoordinate < gameSize; yCoordinate += squareSize)
			{
				// If x or y equals the bottom or the left side of the grid, or it equals the top or the
				//	right side of the grid, then those squares are owned by the player initially.
				if (xCoordinate == (squareSize/2.0) || xCoordinate == (gameSize - (squareSize/2.0)) 
						|| yCoordinate == (squareSize/2.0) || yCoordinate == (gameSize - (squareSize/2.0)))
				{
					// Adds these squares to the collection and increments the amount of squares that are on the field and
					//	the amount that the player owns.
					gameObjects.add(new FieldSquare(xCoordinate, yCoordinate, ownedColor, squareSize, State.OWNED));
					playerOwned++;
				}
				else
				{
					gameObjects.add(new FieldSquare(xCoordinate, yCoordinate, potentialColor, squareSize, State.FIELD));
				}
			}
		}
		// Updates the score with the newly obtained squares.
		updateScore();
	}
	
	private void createLevel()
	{
		for (int balls = 1; balls < (level * 2); balls++)
			spawnMonsterBall();
		
		for (int bombs = 1; bombs < (level - 1); bombs++)
			spawnSmartBomb();
	}
	
	/**
	 * Will increment the car's speed by one.
	 */
	@Override
	public void increaseCarSpeed()
	{
		Iterator collection;	// Iterator used to go through the collection.
		GameObject object;		// Holds the object in the collecton.
		Car player;				// The player's Car.
		int newSpeed;			// The new speed that will be given to the car.
		
		collection = gameObjects.iterator();	// Grabs an iterator.
		
		// Looks for the Car gameObject in the Array. Will then increase its speed by one.
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// If the object is a car...
			if (object instanceof Car)
			{
				// ...its speed will be changed.
				player = (Car) object;
				newSpeed = player.getSpeed() + 1;
				player.setSpeed(newSpeed);
			}
		}
		
		// Updates the Map View
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Will decrease the car's speed by one.
	 */
	@Override
	public void decreaseCarSpeed()
	{
		Iterator collection;	// Iterator used to go through the collection.
		GameObject object;		// Holds the next item in the collection.
		Car player;				// The player's Car.
		int newSpeed;			// The new speed that will be given to the car.
		
		collection = gameObjects.iterator();	// Grabs an iterator.
		
		// Looks for the Car gameObject in the Array. Will then decrease its speed by one.
		while (collection.hasNext())
		{
			// Holds the next item in the collection.
			object = (GameObject) collection.next();
			
			// If the object is a car...
			if (object instanceof Car)
			{
				//... its speed will be decreased.
				player = (Car) object;
				
				// If the Car's speed is 0, then the speed will not be changed.
				if (player.getSpeed() > 0)
				{
					newSpeed = player.getSpeed() - 1;
					player.setSpeed(newSpeed);
				}
			}
		}
		
		// Updates the Map View
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Will change the car's direction to make it face east.
	 */
	@Override
	public void carEast()
	{
		Iterator collection;	// Iterator used to go through the collection.
		GameObject object;		// Holds the next object in the collection.
		Car player;				// The players' Car.

		collection = gameObjects.iterator();	// Grabs an iterator.
		
		// Looks for the Car gameObject in the Array. Will then change its heading.
		while (collection.hasNext())
		{
			// Holds the next object in the collection.
			object = (GameObject) collection.next();
			
			// If the object is a car...
			if (object instanceof Car)
			{
				// ... its heading will be changed.
				player = (Car) object;
				player.setHeading(90);
			}
		}
		
		// Updates the Map View
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Will change the car's direction to make it face north.
	 */
	@Override
	public void carNorth()
	{
		Iterator collection;	// Iterator used to go through the collection.
		GameObject object;		// Holds the next object in the collection.
		Car player;				// The players' Car.
		
		collection = gameObjects.iterator();	// Grabs an iterator.
		
		// Looks for the Car gameObject in the Array. Will then change its heading.
		while (collection.hasNext())
		{
			// Holds the next object in the collection.
			object = (GameObject) collection.next();
			
			// If the object is a car...
			if (object instanceof Car)
			{
				// ... its heading will be changed.
				player = (Car) object;
				player.setHeading(0);
			}
		}
		
		// Updates the Map View
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Will change the car's direction to make it face south.
	 */
	@Override
	public void carSouth()
	{
		Iterator collection;	// Iterator used to go through the collection.
		GameObject object;		// Holds the next object in the collection.
		Car player;				// The players' Car.
		
		collection = gameObjects.iterator();	// Grabs an iterator.
		
		// Looks for the Car gameObject in the Array. Will then change its heading.
		while (collection.hasNext())
		{
			// Holds the next object in the collection.
			object = (GameObject) collection.next();
			
			// If the object is a car...
			if (object instanceof Car)
			{
				// ... its heading will be changed.
				player = (Car) object;
				player.setHeading(180);
			}
		}
		
		// Updates the Map View
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Will change the car's direction to make it face west.
	 */
	@Override
	public void carWest()
	{
		Iterator collection;	// Iterator used to go through the collection.
		GameObject object;		// Holds the next object in the collection.
		Car player;				// The players' Car.
		
		collection = gameObjects.iterator();	// Grabs an iterator.
		
		// Looks for the Car gameObject in the Array. Will then change its heading.
		while (collection.hasNext())
		{
			// Holds the next object in the collection.
			object = (GameObject) collection.next();
			
			// If the object is a car...
			if (object instanceof Car)
			{
				// ... its heading will be changed.
				player = (Car) object;
				player.setHeading(270);
			}
		}
		
		// Updates the Map View.
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Spawns a monster ball in a random area on the game field and with a random color.
	 */
	@Override
	public void spawnMonsterBall()
	{
		Random randomizer = new Random();		// Used to supply random numbers to all attributes of the monster ball.
		int red = randomizer.nextInt(256);		// A random amount of red added to the color of the ball. (Max being 255)
		int green = randomizer.nextInt(256);	// A random amount of green added to the color of the ball. (Max being 255)
		int blue = randomizer.nextInt(256);		// A random amount of blue added to the color of the ball. (Max being 255)
		int heading = randomizer.nextInt(359);	// A random heading from 0-359 (0 is equivalent to 360).
		int speed = randomizer.nextInt(10);		// A random speed no faster than 10.
		// Outer area is owned by player. So monster ball can not spawn on outer edge. This means range is from
		//	[squareSize thru (gameSize - squareSize)]. Example: If gameSize is 500 on x & y sides and the size of a square is 5,
		//	then this range is restricted to [5-495] for both x and y. nextDouble return range [0,1), so have to multiply it by our
		//	max and add by 1 to get the range we want.
		double x = squareSize + (randomizer.nextDouble() * ((gameSize - squareSize) + 1));
		double y = squareSize + (randomizer.nextDouble() * ((gameSize - squareSize) + 1));
		
		// Adds the new monster ball to the game objects array.
		gameObjects.add(new MonsterBall(x, y, ballRadius, heading, speed, red, green, blue));
		
		System.out.println("Spawned a Monster Ball!");
		
		// Looks to see if the bounds of the object should be drawn.
		drawBounds();
		
		// Updates map view.
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Spawns a smart bomb in random area on the game field with a default strategy.
	 */
	@Override
	public void spawnSmartBomb()
	{
		Random randomizer = new Random();		// Used to supply random numbers to some attributes of Smart Bomb.
		int heading = randomizer.nextInt(359);	// A random heading from 0-359 (0 is equivalent to 360).
		int speed = randomizer.nextInt(10);		// A random speed no faster than 10.
		// Outer area is owned by player. So smart bomb can not spawn on outer edge. This means range is from
		//	[squareSize thru (gameSize - squareSize)]. Example: If gameSize is 500 on x & y sides and the size of a square is 5,
		//	then this range is restricted to [5-495] for both x and y. nextDouble return range [0,1), so have to multiply it by our
		//	max and add by 1 to get the range we want.
		double x = squareSize + (randomizer.nextDouble() * ((gameSize - squareSize) + 1));
		double y = squareSize + (randomizer.nextDouble() * ((gameSize - squareSize) + 1));
		
		// Adds a smart bomb to the collection with the default strategy.
		gameObjects.add(new SmartBomb(x, y, ballRadius, heading, speed, Color.BLACK));
		
		System.out.println("Spawned a Smart Bomb!");
		
		// Looks to see if the bounds of the object should be drawn.
		drawBounds();
		
		// Updates map view.
		notifyObservers(notifyMapView);
	}
	
	public void spawnSweeper()
	{
		Sweeper sweep;							// Used to hold the new Sweeper object.
		Random randomizer = new Random();		// Used to supply random numbers to some attributes of Smart Bomb.
		Point2D[] points = new Point2D[4];
		int heading = randomizer.nextInt(359);	// A random heading from 0-359 (0 is equivalent to 360).
		int speed = randomizer.nextInt(10);		// A random speed no faster than 10.
		int red = randomizer.nextInt(256);		// A random amount of red added to the color of the ball. (Max being 255)
		int green = randomizer.nextInt(256);	// A random amount of green added to the color of the ball. (Max being 255)
		int blue = randomizer.nextInt(256);		// A random amount of blue added to the color of the ball. (Max being 255)
		// Outer area is owned by player. So sweeper can not spawn on outer edge. This means range is from
		//	[squareSize thru (gameSize - squareSize)]. Example: If gameSize is 500 on x & y sides and the size of a square is 5,
		//	then this range is restricted to [5-495] for both x and y. nextDouble return range [0,1), so have to multiply it by our
		//	max and add by 1 to get the range we want.
		double x = squareSize + (randomizer.nextDouble() * ((gameSize - squareSize) + 1));
		double y = squareSize + (randomizer.nextDouble() * ((gameSize - squareSize) + 1));
		int size = squareSize + randomizer.nextInt(10*squareSize);
		
		// Picks 4 random points that are within the size of the object.
		for (int index = 0; index < 4; index++)
		{
			double xPoint = 0 + (randomizer.nextDouble() * (size + 1));
			double yPoint = 0 + (randomizer.nextDouble() * (size + 1));
			points[index] = new Point2D.Double(xPoint, yPoint);
		}
		
		// Creates the sweeper object with the random points.
		sweep = new Sweeper(x, y, heading, speed, size, red, green, blue);
		sweep.setPoints(points[0], points[1], points[2], points[3]);
		
		// Adds a sweeper to the collection.
		gameObjects.add(sweep);
		
		sweepers.add(sweep);
		System.out.println("Spawned a Sweeper!");
		
		// Looks to see if the bounds of the object and the context lines of the curve should be drawn.
		drawBounds();
		drawContext();
		
		// Updates map view.
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Spawns a time ticket in a random area on the game field.
	 */
	@Override
	public void spawnTimeTicket()
	{
		Random randomizer = new Random();
		// Time tickets can spawn anywhere on the game field so the range goes from 0 to the max size of the game field.
		double x = (randomizer.nextDouble() * ((gameSize - squareSize) + 1));
		double y = (randomizer.nextDouble() * ((gameSize - squareSize) + 1));
		
		// Spawns a time ticket in a random location and the bonus time it gives is decremented by one each level.
		gameObjects.add(new TimeTicket(x, y, (squareSize*2), (squareSize*2), (bonusTime - (level-1)), ticketColor));
		
		System.out.println("Spawned a Time Ticket!");
		
		// Looks to see if the bounds of the object should be drawn.
		drawBounds();
		
		// Updates map view.
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Changes the bomb's current strategy.
	 */
	@Override
	public void changeBombStrategy()
	{
		Iterator collection;		// Iterator used to go through the collection.
		String strategy;			// Holds the bomb's current strategy.
		GameObject object = null;	// Holds the current object in the collection.
		Car player = null;			// Holds the player car.
		SmartBomb bomb;				// Holds the Smart Bomb.
		boolean found = false;		// Flag that holds if a bomb was found.
		
		// Grabs an iterator to look for the car.
		collection = gameObjects.iterator();
		
		while (collection.hasNext())
		{
			object = (GameObject) collection.next();
			
			if (object instanceof Car){
				player = (Car) object;
			}
		}
		
		// Grabs another iterator to look for the smartbombs.
		collection = gameObjects.iterator();
		
		while (collection.hasNext())
		{
			// Grabs the next object in the collection
			object = (GameObject) collection.next();
			
			// Checks to see if the object is a SmartBomb
			if (object instanceof SmartBomb)
			{
				// Sets a flag indicating that a bomb was found.
				found = true;
				
				bomb = (SmartBomb) object;
				strategy = bomb.getStrategy().toString();
				
				// If the bomb's strategy is currently chase, will switch to standard. And vice-versa.
				if (strategy.equalsIgnoreCase("Chase")){
					bomb.setStrategy(new StandardStrategy());
				}
				else{
					bomb.setStrategy(new ChaseStrategy(player));
				}		
			}
		}
		
		if (!found){
			System.out.println("No bomb exists on the field!");
		}
		else{
			// Updates the Map View.
			notifyObservers(notifyMapView);
		}
	}
	
	/**
	 * Sets all game object's draw bounds flag to the current bounds boolean.
	 */
	public void drawBounds()
	{
		Iterator collection;		// Iterator used to go through the collection.
		GameObject object = null;	// Holds the current object in the collection.
		
		// Grabs an iterator
		collection = gameObjects.iterator();
		
		// Will go through all game objects, except Field Squares, and change their draw bounds flag
		//	to true.
		while (collection.hasNext())
		{
			object = (GameObject) collection.next();
			
			if (!(object instanceof FieldSquare))
			{
				object.setDrawBounds(bounds);
			}
		}
	}
	
	/**
	 * Sets all Sweeper object's draw context lines to the current context boolean.
	 */
	public void drawContext()
	{
		Iterator collection;		// Iterator used to go through the collection.
		GameObject object = null;	// Holds the current object in the collection.
		Sweeper sweep;				// Holds the current Sweeper object.
		
		// Grabs an iterator
		collection = gameObjects.iterator();

		// Will go through all Sweeper objects and set their draw context flags to true.
		while (collection.hasNext())
		{
			object = (GameObject) collection.next();
			
			if (object instanceof Sweeper)
			{
				sweep = (Sweeper) object;
				sweep.setContext(context);
			}
		}
	}
	
	/**
	 * Changes the bomb's current strategy.
	 */
	public void changeBombStrategy(String strategy)
	{
		Iterator collection;		// Iterator used to go through the collection.
		GameObject object = null;	// Holds the current object in the collection.
		Car player = null;			// Holds the player car.
		SmartBomb bomb;				// Holds the Smart Bomb.
		
		// Grabs an iterator to look for the car.
		collection = gameObjects.iterator();
		
		while (collection.hasNext())
		{
			object = (GameObject) collection.next();
			
			if (object instanceof Car){
				player = (Car) object;
			}
		}
		
		// Grabs another iterator to look for the smartbombs.
		collection = gameObjects.iterator();
		
		while (collection.hasNext())
		{
			// Grabs the next object in the collection
			object = (GameObject) collection.next();
			
			// Checks to see if the object is a SmartBomb
			if (object instanceof SmartBomb)
			{	
				bomb = (SmartBomb) object;
				
				// Will change to whichever strategy was passed in.
				if (strategy.equalsIgnoreCase("Chase")){
					bomb.setStrategy(new ChaseStrategy(player));
					
				}
				else{
					bomb.setStrategy(new StandardStrategy());
				}		
			}
		}
		// Updates the Map View.
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Increments the number of squares owned by the player by one.
	 */
	@Override
	public void captureSquare()
	{
		Iterator collection;		// Iterator used to go through the collection.
		GameObject object;			// Used to hold current object in collection.
		FieldSquare gameSquare;		// Used to hold the current field square.
		int captured = 1;			// Will only capture one square.
		
		collection = gameObjects.iterator();	// Grabs an iterator.
		
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// Checks to make sure that the game object is a field square before doing anything with it.
			if (object instanceof FieldSquare)
			{
				gameSquare = (FieldSquare) object;
				
				// If the game field is not owned (returns false) and captured is greater than 0,
				//	then will change the ownership to true and decrement the number captured by one.
				if ((gameSquare.isOwned() == State.FIELD) && captured > 0)
				{
					gameSquare.changeOwnership(State.OWNED);
					gameSquare.setColor(ownedColor);
					playerOwned++;
					captured--;
				}
			}
		}
		
		System.out.println("Captured a square!");
		
		// Will update the Map View.
		notifyObservers(notifyMapView);
		// Will update the score with the newly owned square.
		updateScore();
	}
	
	/**
	 * Will make a random number of field squares that are not owned by the player now owned. Will also update the score
	 * accordingly.
	 */
	@Override
	public void captureGroup()
	{
		Iterator collection;				// Iterator used to go through the collection.
		Random randomizer = new Random();
		GameObject object;					// Holds the next object in the collection.
		FieldSquare gameSquare;				
		int noFieldSquares = 0;				// Holds the total number of field squares.
		int owned = 0;						// Holds the number of squares owned by the player.
		int notOwned;						// Holds the number of squares that are still part of the field.
		int captured;						// Holds the number of squares that the player just won.
		
		collection = gameObjects.iterator();	// Grabs an iterator.
		
		// Counts how many field squares are currently owned by the player
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// Checks to see if the game object is a field square before doing anything with it.
			if (object instanceof FieldSquare)
			{
				// Increments number of Field Squares by one.
				noFieldSquares++;
				
				// Will check to see if its owned. If so, will update the owned count.
				gameSquare = (FieldSquare) object;
				if (gameSquare.isOwned() == State.OWNED){
					owned++;
				}
			}
		}
		
		// Minuses the game field size from owned to see how many squares are not owned.
		notOwned = noFieldSquares - owned;
		// Gets a random number with the max number being the total amount of squares not currently owned.
		captured = randomizer.nextInt(notOwned);
		System.out.println("Captured: " + captured + " squares!");
		// Updates the global variable with how many are currently owned now.
		playerOwned += captured;
		
		// Will go through the array and turn field squares that are not owned to owned.
		while (captured > 0)
		{
			collection = gameObjects.iterator();	// Grabs an iterator.
			
			while (collection.hasNext())
			{
				// Grabs the next object in the collection.
				object = (GameObject) collection.next();
				
				// Will check to see if game object is a square before doing anything with it.
				if (object instanceof FieldSquare)
				{
					gameSquare = (FieldSquare) object;
					// If the field square is not owned (returns false) and captured is greater than 0,
					//	then will change the ownership to true and decrement the number captured by one.
					if ((gameSquare.isOwned() == State.FIELD) && captured > 0)
					{
						gameSquare.changeOwnership(State.OWNED);
						gameSquare.setColor(ownedColor);
						captured--;
					}
				}
			}
		}
		
		// Update the Map View.
		notifyObservers(notifyMapView);
		// Updates the score with the newly obtained squares.
		updateScore();
	}
	
	public void flood()
	{
		Iterator collection;
		FieldSquare square;
		FieldSquare[] squares = new FieldSquare [gameSize*gameSize];
		int index = 0;
		GameObject obj;
		
		replace.clear();
		collection = gameObjects.iterator();
		
		while (collection.hasNext())
		{
			obj = (GameObject) collection.next();
			
			if (obj instanceof FieldSquare)
			{
				squares[index++] = (FieldSquare) obj;
				//square = (FieldSquare) obj;
				//floodFill(square);
			}
		}
		
		floodFill(squares);
	}
	
	public void floodFill(FieldSquare[] squares)
	{
		FieldSquare square;
		int loc;
		
		for (int index = 0; index < squares.length; index++)
		{
			if (squares[index].isOwned() != State.OWNED)
			{
				replace.add(squares[index]);
				location.add(index);
			}
		}
		
		while (!replace.isEmpty())
		{
			square = replace.firstElement();
			loc = location.firstElement();
			
			
		}
	}
	
	/**
	 * Collides the player with the closet monster ball. Will then decrement a life, change the ball's color, and return
	 * the player to start.
	 */
	@Override
	public void collideMonsterBall()
	{
		Iterator collection;							// Iterator used to go through the collection.
		Random randomizer = new Random();
		MonsterBall closet = null;						// Holds the monster ball closet to the car.
		GameObject object;								// Holds the next object in the collection.
		
		int red = randomizer.nextInt(256);				// The random red value for the collided ball.
		int green = randomizer.nextInt(256);			// The random green value for the collided ball.
		int blue = randomizer.nextInt(256);				// The random blue value for the collided ball.
		double playerX = 0, playerY = 0;				// The player's old location.
		double enemyX = 0, enemyY = 0;					// The Monster Ball's location.
		double distanceX = 0, distanceY = 0;			// The distance player and ball are from each other.
		double distance = 0;							// The actual distance of the player and the ball.
		double nearestDist = gameSize;					// Holds the closet distance value.
		boolean found = false;							// Holds whether there is a monster ball on the field.
		
		collection = gameObjects.iterator();			// Grabs an iterator.
		
		// Looks for the Car gameObject in the Array.
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// Will grab its position so we can find the ball closet to it.
			if (object instanceof Car)
			{
				Car player = (Car) object;
				playerX = player.getX();
				playerY = player.getY();
			}
		}
		
		collection = gameObjects.iterator();			// Grabs an iterator.
		
		// Looks for the Monster Ball gameObject in the Array.
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// Looks for the MonsterBall that was closet to the player.
			if (object instanceof MonsterBall && !(object instanceof SmartBomb))
			{
				if (!found){
					closet = (MonsterBall) object;		// Marks the first ball found the closet ball starting out.
				}
				
				// Marks that there was a monster ball in the collection.
				found = true;
				
				// Grabs the ball's coordinates.
				MonsterBall enemy = (MonsterBall) object;	
				enemyX = enemy.getX();
				enemyY = enemy.getY();
				
				// Calculates the distance of the ball from the player car. Does this by using 
				//	Pythagorean Theorem.
				distanceX = playerX - enemyX;
				distanceY = playerY - enemyY;
				distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
				
				// If the ball is closer that the previously recorded ball, then we will save this
				//	index.
				if (distance < nearestDist){
					nearestDist = distance;
					closet = (MonsterBall) object;
				}
			}
		}
		
		// If a ball was found on the field, then we will have the closet one collide with the player.
		//	Otherwise, nothing will happen.
		if (found)
		{
			// The ball is given a random color since it collided with player.
			closet.setColor(red, green, blue);
			
			System.out.println("Collided with Monster Ball!");
			
			// Player loses a life.
			loseLife();
		}
		else{
			System.out.println("No Monster Balls exist on the field!");
		}
	}
	
	@Override
	public void collideSmartBomb()
	{
		Iterator collection;							// Iterator used to go through the collection.
		SmartBomb closet = null;						// Holds the smart bomb closet to the car.
		GameObject object;								// Holds the next object in the collection.
		
		double playerX = 0, playerY = 0;				// The player's old location.
		double enemyX = 0, enemyY = 0;					// The smart bomb's location.
		double distanceX = 0, distanceY = 0;			// The distance player and bomb are from each other.
		double distance = 0;							// The actual distance of the player and the bomb.
		double nearestDist = gameSize;					// Holds the closet distance value.
		boolean found = false;							// Holds whether there is a smart bomb on the field.
		
		collection = gameObjects.iterator();			// Grabs an iterator.
		
		// Looks for the Car gameObject in the Array.
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// Will grab its position so we can find the ball closet to it.
			if (object instanceof Car)
			{
				Car player = (Car) object;
				playerX = player.getX();
				playerY = player.getY();
			}
		}
		
		collection = gameObjects.iterator();			// Grabs an iterator.
		
		// Looks for the Smart Bomb gameObject in the Array.
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// Looks for the MonsterBall that was closet to the player.
			if (object instanceof SmartBomb)
			{
				if (!found){
					closet = (SmartBomb) object;		// Marks the first bomb found the closet bomb starting out.
				}
				
				// Marks that there was a smart bomb in the collection.
				found = true;
				
				// Grabs the bombs's coordinates.
				SmartBomb enemy = (SmartBomb) object;	
				enemyX = enemy.getX();
				enemyY = enemy.getY();
				
				// Calculates the distance of the bomb from the player car. Does this by using 
				//	Pythagorean Theorem.
				distanceX = playerX - enemyX;
				distanceY = playerY - enemyY;
				distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
				
				// If the bomb is closer that the previously recorded bomb, then we will save this
				//	index.
				if (distance < nearestDist){
					nearestDist = distance;
					closet = (SmartBomb) object;
				}
			}
		}
		
		// If a ball was found on the field, then we will have the closet one collide with the player.
		//	Otherwise, nothing will happen.
		if (found)
		{			
			System.out.println("Collided with Smart Bomb!");
			
			// Removes the closet bomb to the player.
			gameObjects.remove(closet);

			// Player loses a life.
			loseLife();
		}
		else{
			System.out.println("No Smart Bombs exist on the field!");
		}
	}
	
	/**
	 * Removes a time ticket from the game world that is closet to the player and adds the time
	 * it gives to the timer.
	 */
	@Override
	public void obtainTimeTicket()
	{
		Iterator collection;					// Iterator used to go through the collection.
		GameObject object;						// Holds the next object in the collection.
		TimeTicket closet = null;				// Holds the time ticket closet to the player.
		
		double playerX = 0, playerY = 0;		// Holds the player car's current position.
		double ticketX = 0, ticketY = 0;		// Holds the time tickets' current postion.
		double distanceX = 0, distanceY = 0;	// The distance player and ball are from each other.
		double distance = 0;					// The actual distance of the player and the ball.
		double nearestDist = gameSize;			// Holds the closet distance value.
		boolean found = false;					// Indicates if a ticket was found in the array.
		
		collection = gameObjects.iterator();	// Grabs an iterator.
		
		// Looks for the Car gameObject in the Array.
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// Will then reset the car to the starting location and make it face north.
			if (object instanceof Car)
			{
				Car player = (Car) object;
				playerX = player.getX();
				playerY = player.getY();
			}
		}
		
		// Will look for the closet time ticket in the Game Objects array to the player car. If one
		//	is found, the timer will gain the amount of time that the ticket gives and then be removed 
		//	from the world.
		collection = gameObjects.iterator();			// Grabs an iterator.
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// Looks for a Time Ticket that is closet to the player.
			if (object instanceof TimeTicket)
			{
				if (!found){
					closet = (TimeTicket) object;		// Marks the first ticket found the closet ticket starting out.
				}
				
				found = true;
				TimeTicket ticket = (TimeTicket) object;
				
				// Grabs the ticket's coordinates.
				ticketX = ticket.getX();
				ticketY = ticket.getY();
				
				// Calculates the distance of the ticket from the player car. Does this by using 
				//	Pythagorean Theorem.
				distanceX = playerX - ticketX;
				distanceY = playerY - ticketY;
				distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
				
				// If the ticket is closer that the previously recorded ticket, then we will save this
				//	index.
				if (distance < nearestDist){
					nearestDist = distance;
					closet = (TimeTicket) object;
				}
			}
		}
		
		// If a time ticket was found on the field, the closet one will update the timer with the amount
		//	of bonus time it gives, and then it will be removed. Otherwise nothing will happen.
		if (found)
		{
			System.out.println("Obtained Time Ticket! +" + closet.getTimeGiven() + " seconds!");
			timer += closet.getTimeGiven();
			gameObjects.remove(closet);
			
			// Updates the views.
			notifyObservers(notifyMapView);
			notifyObservers(notifyScoreView);
		}
		else
		{
			System.out.println("No Time Ticket exists on the field!");
		}
	}
	
	/**
	 * Checks to see if any of the ISelectable objects contains the point that was passed in.
	 * @param clickedPoint - The point that was clicked on the Map Panel.
	 */
	public void selectObject(Point2D clickedPoint)
	{
		AffineTransform invertVTM;	// Used to convert mouse click from screen coords to world coords.
		Iterator collection;		// Holds the iterator.
		GameObject object;			// Holds the next object in the iterator.
		ISelectable shape;			// Holds the next ISelectable object.
		boolean selected;			// Boolean that indicates object was selected.
		
		// Grabs an iterator.
		collection = gameObjects.iterator();
		
		// Inverts the current VTM
		invertVTM = invertVTM();
		
		// Runs the mouse click through the inverted VTM matrix.
		invertVTM.transform(clickedPoint, clickedPoint);
		
		System.out.println(clickedPoint);
		
		// Will only check to see if an object was selected if the game is paused.
		if (!getPlay())
		{
			while(collection.hasNext())
			{
				// Grabs the next object in the iterator.
				object = (GameObject) collection.next();
				
				if (object instanceof ISelectable)
				{	
					// Sends the point that was clicked to the shape to see if the object was selected.
					shape = (ISelectable) object;
					selected = shape.contains(clickedPoint);
					
					if (selected) {
						shape.setSelected(true);
					}
					else {
						shape.setSelected(false);
						System.out.println(shape);
					}
				}
			}
		}
		
		// Updates the map view.
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Checks to see if the object lays in the rectangle that is laid out by the start and end point.
	 * @param startPoint - The point that is initially clicked.
	 * @param endPoint - The point at which the mouse was released.
	 */
	public void selectObject(Point2D startPoint, Point2D endPoint)
	{
		AffineTransform invertVTM;		// Used to convert mouse clicks from screen coords to world coords.
		Iterator collection;			// Holds the iterator.
		GameObject object;				// Holds the next object in the iterator.
		ISelectable shape;				// Holds the next ISelectable object.
		Rectangle mouseBounds;			// Rectangle that represent the area pressed and released the mouse.
		int x;							// Holds the x starting point of the rectangle.
		int y;							// Holds the y starting point of the rectangle.
		int width;						// Holds the opposite corner's x coord of the rectangle.
		int height;						// Holds the opposite corner's y coord of the rectangle.
		boolean selected;
		
		// Inverts the current VTM
		invertVTM = invertVTM();
		
		// Runs the mouse clicks through the inverted VTM.
		invertVTM.transform(startPoint, startPoint);
		invertVTM.transform(endPoint, endPoint);
		
		//TODO: This isn't working like its supposed to.
		// Creates the bounds made by clicking and then releasing the mouse.
		x = (int) startPoint.getX();
		y = (int) startPoint.getY();
		width = (int) endPoint.getX() - x;
		height = (int) endPoint.getY() - y;
		mouseBounds = new Rectangle(x, y, width, height);
		
		collection = gameObjects.iterator();
		
		// Will only check to see if an object was selected if the game is paused.
		if (!getPlay())
		{
			while(collection.hasNext())
			{
				// Grabs the next object in the iterator.
				object = (GameObject) collection.next();
				
				if (object instanceof ISelectable)
				{	
					// Sends the point that was clicked to the shape to see if the object was selected.
					shape = (ISelectable) object;
					selected = shape.contains(mouseBounds);
					
					if (selected) {
						shape.setSelected(true);
					}
					else {
						shape.setSelected(false);
					}
				}
			}
		}
		
		// Updates the map view.
		notifyObservers(notifyMapView);
	}
	
	public void selectObject(Point2D clickedPoint, boolean ctrlDown)
	{
		AffineTransform invertVTM;	// Used to convert mouse click from screen coords to world coords.
		Iterator collection;		// Holds the iterator.
		GameObject object;			// Holds the next object in the iterator.
		ISelectable shape;			// Holds the next ISelectable object.
		boolean selected;			// Boolean that indicates object was selected.
		boolean oneSelected = false;	// Boolean that indicates at least one object was selected.
		
		// Inverts the current VTM
		invertVTM = invertVTM();
		
		// Runs the mouse click through the inverted VTM matrix.
		invertVTM.transform(clickedPoint, clickedPoint);
		
		// Grabs an iterator.
		collection = gameObjects.iterator();
		
		// Will only check to see if an object was selected if the game is paused.
		if (!getPlay())
		{
			if (ctrlDown)
			{
				while(collection.hasNext())
				{
					// Grabs the next object in the iterator.
					object = (GameObject) collection.next();
					
					if (object instanceof ISelectable)
					{	
						// Sends the point that was clicked to the shape to see if the object was selected.
						shape = (ISelectable) object;
						selected = shape.contains(clickedPoint);
						
						// If an object was selected, then all objects stay selected and current object
						//	
						if (selected) {
							shape.setSelected(true);
							oneSelected = true;
						}
					}
				}
			}
			
			// If no object was selected, then all the objects become unselected.
			if (!oneSelected) {
				unSelectObjects();
			}
		}
		
		// Updates the map view.
		notifyObservers(notifyMapView);
		
	}
	
	/**
	 * Will go through and unselect all objects that are selectable.
	 */
	public void unSelectObjects()
	{
		Iterator collection = gameObjects.iterator();
		GameObject object;	// Holds the next object in the iterator.
		
		// Goes through the collection and unselects all objects that can be selected.
		while (collection.hasNext())
		{
			object = (GameObject) collection.next();
			
			if (object instanceof ISelectable)
			{
				ISelectable shape = (ISelectable) object;
				shape.setSelected(false);
			}
		}
	}
	
	/**
	 * Will delete objects from the Game World that are selected.
	 */
	public void deleteObject()
	{
		Iterator collection;	// Iterator used to go through the collection.
		GameObject object;		// Holds the next object in the collection.
		ISelectable shape;		// Holds the next ISelectable object.
		boolean selected;		// Flag indicating the shape is selected.
		
		Vector<ISelectable> toDelete = new Vector<>();
		
		// Grabs an iterator.
		collection = gameObjects.iterator();

		// Will only check to see if an object was selected if the game is paused.
		if (!getPlay())
		{
			while(collection.hasNext())
			{
				// Grabs the next object in the iterator.
				object = (GameObject) collection.next();
				
				if (object instanceof ISelectable)
				{	
					// Sends the point that was clicked to the shape to see if the object was selected.
					shape = (ISelectable) object;
					selected = shape.isSelected();
					
					if (selected) {
						toDelete.add(shape);
					}
				}
			}
			
			// Will go through and delete the objects that need to be deleted.
			while (!toDelete.isEmpty()){
				gameObjects.remove(toDelete.get(0));
				toDelete.remove(0);
			}
		}
		
		// Notify the map view to redraw itself.
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Decrements the game timer by one. Also updates all movable object's positions.
	 */
	public void timeTick(int delay)
	{
		Iterator collection;			// Iterator used to go through the collection.
		GameObject object;				// Holds the next object in the collection.
		double elapsed;					// Gets how many parts of a second the delay from timer is.
		
		// Stores the amount of time that has been passed by the timer in the Play command.
		timeElapsed += delay;
		
		// If the total time is equal to one second, the game timer will be decremented by one and
		//	the total will reset.
		if ((timeElapsed % 1000) == 0)
			timer--;
		
		// Spawns Sweepers every 10 seconds.
		if ((timeElapsed % 10000) == 0)
		{
			spawnSweeper();
			times.add(timer/2);
		}
		
		// Spawns Time Tickets every 12 seconds.
		if ((timeElapsed % 12000) == 0)
			spawnTimeTicket();
		
		// Removes the sweeper after it has existed for half the remaining time in which it was spawned.
		if (times.contains(timer))
		{	
			int time = timer;
			Sweeper sweep = sweepers.firstElement();
			
			System.out.println("Despawning Sweeper!");
			
			// Removes the time to look for from the vector.
			times.remove((Object)time);
			gameObjects.remove(sweep);
			
			// Removes sweeper from the sweeper from the array.
			sweepers.remove(sweepers.firstElement());
		}
		
		// Will reset the time elapsed after so many seconds.
		if (timeElapsed == (initialTime * 1000))
			timeElapsed = 0;
		
		// Grabs an iterator.
		collection = gameObjects.iterator();
		
		// Finds out how much of a second has passed since this method was last called.
		elapsed = (double)delay / (double)1000;
		
		// Updates all movable object's positions according to their heading and speed.
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			// Moves the object.
			move(object, elapsed);
		}
		
		// Will go through all objects and see if any collisions have happened.
		collisionDetection();

		// Updates the views.
		notifyObservers(notifyScoreView);
		notifyObservers(notifyMapView);
		
		// If the timer is equal to or less than 0, then the player loses a life, is reset to the starting
		//	position, and the timer is reset.
		if (timer <= 0)
		{
			loseLife();
		}
	}
	
	/**
	 * Moves each movable object that is on the game field.
	 * @param object - The object to be moved.
	 * @param elapsed - The amount of time since the object last moved.
	 */
	private void move(GameObject object, double elapsed)
	{
		// Will move any object that is a movable object.
		if (object instanceof MoveableObject)
		{
			MoveableObject mObj = (MoveableObject) object;
			
			// If the object is a SmartBomb, it's strategy will be invoked (same as moving the bomb).
			//	All other object's move command will be called.
			if (mObj instanceof SmartBomb){
				((SmartBomb) mObj).invokeStategy(elapsed);
			}
			else{
				mObj.move(elapsed);
			}
			
			if (mObj instanceof Car)
			{
				Iterator collection;
				GameObject gObject;									// Holds the next object in the iterator.
				Car mCar = (Car) mObj;								// Holds the car that was in the iterator.
				double minLoc = (squareSize / 2.0);					// The min coordinate the car can be at.
				double maxLoc = (gameSize - (squareSize / 2.0));	// The max coordinate the car can be at.
				
				FieldSquare contactSquare = null;
				
				// Checks to make sure that the player Car is within bounds of the game field. If not, it will
				//	place it back on the field.
				if (mCar.getX() < 0){
					mCar.setX(minLoc);
				}
				if (mCar.getX() > maxLoc){
					mCar.setX(maxLoc);
				}
				if (mCar.getY() < 0){
					mCar.setY(minLoc);
				}
				if (mCar.getY() > maxLoc){
					mCar.setY(maxLoc);
				}
				
				// Grabs an iterator.
				collection = gameObjects.iterator();
				
				// Creates the car's bounds.
				Rectangle carBounds = mCar.getBounds();
				Rectangle squareBounds;
				
				// Will leave a trail of potentially owned squares behind the car. (If it moves to an area
				//	with no squares on the game field).
				while(collection.hasNext())
				{
					// Grabs the next object in the iterator.
					gObject = (GameObject) collection.next();
					
					// If the object is a field square, then we will see if the car's bounds and the square's
					//	bounds intersect.
					if (gObject instanceof FieldSquare)
					{
						FieldSquare square = (FieldSquare) gObject;
						
						// Creates a bounds around the square. If the bounds of the square and car intersect,
						//	then we save the square for future use.
						squareBounds = square.getBounds();
						if (carBounds.intersects(squareBounds)){
							contactSquare = square;
						}
					}
				}
				
				// If the square the car is in contact with is part of the field, then it becomes potentially
				//	owned. This square then gets added to an array.
				if (contactSquare!= null && contactSquare.isOwned() == State.FIELD)
				{
					contactSquare.changeOwnership(State.POTENTIAL);
					contactSquare.setColor(potentialColor);
					newSquares.add(contactSquare);
					
					// Bombs will chase after the player while player is on the field.
					changeBombStrategy("Chase");
				}
				// If the car is in contact with a owned square, then any squares that are potentially owned
				//	are now owned. The array then gets cleared out.
				else if (contactSquare != null && contactSquare.isOwned() == State.OWNED)
				{
					// Only plays the sound if the sound is on.
					if (getSound()){
						winSquaresClip.stop();  // Stops the sound if it was currently playing.
						winSquaresClip.play();	// Plays a sound to let player know they acquired squares.
					}
					
					for (FieldSquare square : newSquares) {
						square.changeOwnership(State.OWNED);	// Changes square to owned.
						square.setColor(ownedColor);			// Changes the color to a owned color.
						playerOwned++;							// Increases the number of squares the player owns.
					}

					// Bombs will stop chasing if player is not on the field.
					changeBombStrategy("Standard");
					
					// Clears the list of potentially owned squares and updates the score.
					newSquares.clear();
					updateScore();
				}
			}
		}
	}
	
	/**
	 * Goes through the game objects and checks if any are colliding with each other.
	 */
	private void collisionDetection()
	{
		Iterator collection;		// Holds an iterator.
		Iterator compareAgainst;	// Holds an iterator to compare against.
		ICollider curObj;			// Will hold the next ICollider object in the first iterator.
		ICollider otherObj;			// Will hold the next ICollider object in the compare against iterator.
		Vector<GameObject> toRemove;	// An arraylist holding all objects that need to be removed from field.
		
		toRemove = new Vector<>();
		
		// Grabs an iterator.
		collection = gameObjects.iterator();
		
		// Will check to see if moving caused any collisions.
		while (collection.hasNext())
		{
			// Grabs the next object in the first iterator.
			curObj = (ICollider) collection.next();
			
			// Will not check the FieldSquares.
			if (!(curObj instanceof FieldSquare))
			{
				// Grabs a new iterator to see if any collisions happened.
				compareAgainst = gameObjects.iterator();
				
				// Compares the current object against all the other objects in the collection to see if 
				//	any collisions happened.
				while (compareAgainst.hasNext())
				{
					 otherObj = (ICollider) compareAgainst.next();
					
					// If the current object doesn't equal the second object, check to see if they have collided.
					if (curObj != otherObj)
					{
						if (curObj.collidesWith(otherObj))
						{
							// If there was a collision, this will handle it.
							curObj.handleCollision(otherObj);
							
							// If the object is a time ticket, then time will be added to the clock.
							if (curObj instanceof TimeTicket)
							{
								if (getSound())
								{
									addTimeClip.stop();	// Will stop the clip if its currently playing.
									addTimeClip.play();	// Will play the clip if the sound is on.
								}
								
								// Increments the timer based on how much time the ticket gives. Will then queue
								//	the ticket to be removed.
								TimeTicket ticket = (TimeTicket) curObj;
								timer += ticket.getTimeGiven();
								toRemove.add(ticket);
							}
							
							if (curObj instanceof SmartBomb && ((SmartBomb) curObj).isExpired())
							{
								if (getSound())
								{
									bombExplodeClip.stop(); // Will stop the clip if its currently playing.
									bombExplodeClip.play(); // Will play the clip if the sound is on.
								}
								
								SmartBomb bomb = (SmartBomb) curObj;
								toRemove.add(bomb);
								loseLife();
							}
							
							if (curObj instanceof MonsterBall && !(curObj instanceof SmartBomb))
							{
								MonsterBall ball = (MonsterBall) curObj;
								
								if (ball.getHitCar())
								{
									loseLife();
									ball.setHitCar(false);
								}
							}
							
							if (curObj instanceof Sweeper)
							{
								Sweeper curve = (Sweeper) curObj;
								
								if (curve.getObjectHit())
								{
									if (otherObj instanceof Car)
										loseLife();
									else
										toRemove.add((GameObject) otherObj);
								}
							}
						}
					}
				}
			}
		}
		
		// Will go through and delete the objects that need to be deleted.
		while (!toRemove.isEmpty()){
			gameObjects.remove(toRemove.get(0));
			toRemove.remove(0);
		}
		
	}
	
	/**
	 * Updates the player's current score.
	 */
	private void updateScore()
	{
		// Updates the score by looking at the number of squares owned by player and dividing by what is left.
		score = (double) playerOwned / fieldSquares;
		if (score >= goal){
			nextLevel();
		}
		// Notifies the score view that the score has been updated.
		notifyObservers(notifyScoreView);
	}
	
	/**
	 * Increment's the game's current level by one, decreases the time allowed to complete the level,
	 * increases the goal, and resets the score.
	 */
	private void nextLevel()
	{
		// Increments the level by one.
		level++;
		
		// Only plays the sound if it is currently on.
		if (getSound()){
			levelCompleteClip.play(); // Plays the sound clip.
		}
		
		// If the level is greater than 5, then the player has completed the game and it resets.
		if (level > 5){
			System.out.println("You have completed the Game! Congrats!!");
			System.out.println("Game Reset");
			
			// Starts the game over.
			level = 1;
			lives = 3;
			timer = initialTime;
			score = 0;
			goal = initialGoal;
			
			// Resets the field. Also updates the Score View.
			gameStart();
		}
		else
		{
			// Decrements time to complete the level by 2 for each level.
			timer = (initialTime - ((level - 1) * 2));
			// Increases the goal by 10% for each level.
			goal = initialGoal + ((level - 1) * 0.10);
			
			// Resets the field. Also updates the Score View.
			System.out.println("Completed the level! Now at Level: " + level);
			gameStart();
		}
	}
	
	/**
	 * Decreases the player's lives by one. If lives are less than 0, then it is game over. Otherwise,
	 * the car resets its location, and the ball that hit it changes color.
	 */
	private void loseLife()
	{
		Iterator collection;	// Iterator used to go through the collection.
		GameObject object;		// Holds the next object in the collection.
		int result;				// Holds if the player wants to continue.
		
		lives--;				// Decreases number of lives by one.
		
		System.out.println("Lost a Life!");
		
		// The array of potentially owned squares gets returned to the field and the array is cleared.
		for (FieldSquare square : newSquares) {
			square.changeOwnership(State.FIELD);
		}
		newSquares.clear();
		
		// If sound is on, the player died sound will play.
		if (getSound()){
			playerDiedClip.stop();	// If the sound is already playing, this will stop it.
			playerDiedClip.play();  // Will play the sound if sound is on.
		}
		// If lives are less than 0, then it is game over.
		if (lives <= 0)
		{			
			// Resets the field. Also updates the Score View.
			System.out.println("Out of Lives! Game Over!");
			
			// Displays a modal YES or NO dialog box which confirms with the user whether they want to quit or
			//	not.
			result = JOptionPane.showConfirmDialog(null, "Game Over!\nDo you want to play again?", "Game Over",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			
			// Will confirm quit if the user clicks no.
			if (result == JOptionPane.NO_OPTION) {
				exitGame();
			}
			
			System.out.println("Game Reset");
			
			lives = initialLives;
			gameStart();
		}
		// Otherwise, a life is decremented, the player reset, and the game continues.
		else
		{
			// The amount of time given back depends on the current level.
			timer = initialTime - ((level - 1) * 2);
			
			collection = gameObjects.iterator();	// Grabs an iterator.
			
			// Looks for the Car gameObject in the Array.
			while (collection.hasNext())
			{
				// Grabs the next object in the collection.
				object = (GameObject) collection.next();
				
				// Will then reset the car to the starting location and make it face north.
				if (object instanceof Car)
				{
					Car player = (Car) object;
					player.setLocation(startingX, startingY);
					player.setHeading(0);
				}
			}
			
			// Updates the views.
			notifyObservers(notifyMapView);
			notifyObservers(notifyScoreView);
		}
	}
	
	/**
	 * Shows the game's about screen.
	 */
	@Override
	public void displayAbout()
	{
		String message;
		boolean wasPaused = true;	// Flag that indicates whether the game was already paused prior.
		
		// If the game is not currently paused, this will pause the game.
		if (getPlay())
		{
			changePlay(); 		// Will pause the game.
			wasPaused = false;	// Indicates the game will be switched back to play if user declines quitting.
		}
		
		message = "Created By: Adrian Chambers\n";
		message += "CSC 133 - Object-Oriented Programming\n";
		message += "Version: 0.75\n\n";
		message += "Music By: DST - http://www.nosoapradio.us\n";
		message += "Sounds By: Mike Koenig and Yannick Lemieux - http://soundbible.com";
		JOptionPane.showMessageDialog(null, message, "About Xonix", JOptionPane.INFORMATION_MESSAGE);
		
		if (!wasPaused) {
			changePlay();	// Will unpause the game if it wasn't paused prior.
		}
	}
	
	/**
	 * Shows all object's and their current location.
	 */
	@Override
	public void displayMap()
	{
		Iterator collection;					// Iterator used to go through the collection.
		GameObject object;						// Holds the next object in the collection.
		collection = gameObjects.iterator();	// Grabs an iterator.
		
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// Prints the objects' in the collection.
			if (!(object instanceof FieldSquare)){
				System.out.println(object.toString());
			}
		}
	}
	
	
	/**
	 * Draws all the objects on the screen.
	 */
	public void drawMap(Graphics g)
	{
		Iterator collection;					// Iterator used to go through the collection.
		GameObject object;						// Holds the next object in the collection.
		collection = gameObjects.iterator();	// Grabs an iterator.
		
		while (collection.hasNext())
		{
			// Grabs the next object in the collection.
			object = (GameObject) collection.next();
			
			// Calls the object's draw method.
			object.draw(g);
		}
	}
	
	/**
	 * Shows the game's current state.
	 */
	@Override
	public void displayStats()
	{
		String stats = String.format("Level: %d; Lives Remaining: %d; Time Left: %d; Score: %.2f; Goal: %.2f, Owned: %d, Field: %d",
				level, lives, timer, (score * 100), (goal * 100), playerOwned, fieldSquares);
		System.out.println(stats);
	}
	
	/**
	 * Sets the new size of the window looking into the game world.
	 * @param windowLeft - The left bounds of the window.
	 * @param windowRight - The new right bounds of the window.
	 * @param windowTop - The new top bounds of the window.
	 * @param windowBottom - The new bottom bounds of the window.
	 */
	public void setWorldWindow(double windowLeft, double windowRight, double windowTop, double windowBottom)
	{
		this.windowLeft = windowLeft;
		this.windowRight = windowRight;
		this.windowTop = windowTop;
		this.windowBottom = windowBottom;
	}
	
	/**
	 * Centers the world window with the default world window values.
	 */
	public void centerWorldWindow()
	{
		setWorldWindow(leftWindow, rightWindow, topWindow, bottomWindow);
	}
	
	/**
	 * Returns the window bounds of the view into the game world.
	 * @return - A rectangle representing the view into the game world.
	 */
	public Rectangle getWorldWindow()
	{
		int width = (int) (windowRight - windowLeft);
		int height = (int) (windowTop - windowBottom);
		
		Rectangle worldWindow = new Rectangle((int) windowLeft, (int) windowBottom, width, height);
		
		return worldWindow;
	}
	
	/**
	 * Makes the world window smaller. This makes the game zoom in.
	 */
	public void zoomIn()
	{
		double width = windowRight - windowLeft;
		double height = windowTop - windowBottom;
		
		windowLeft += width * zoomDistance;
		windowRight -= width * zoomDistance;
		windowTop -= height * zoomDistance;
		windowBottom += width * zoomDistance;
		
		notifyObservers(notifyMapView);
	}
	
	/**
	 * Makes the world window bigger. This makes the game zoom out.
	 */
	public void zoomOut()
	{
		double width = windowRight - windowLeft;
		double height = windowTop - windowBottom;
		
		windowLeft -= width * zoomDistance;
		windowRight += width * zoomDistance;
		windowTop += height * zoomDistance;
		windowBottom -= height * zoomDistance;
	}
	
	/**
	 * Moves the world window up in the game world. This moves the view in the game world up.
	 */
	public void panUp()
	{
		windowTop += panDistance;
		windowBottom += panDistance;
	}
	
	/**
	 * Moves the world window down in the game world. This moves the view in the game world down.
	 */
	public void panDown()
	{
		windowTop -= panDistance;
		windowBottom -= panDistance;
	}
	
	/**
	 * Moves the world window left in the game world. This moves the view in the game world left.
	 */
	public void panLeft()
	{
		windowLeft -= panDistance;
		windowRight -= panDistance;
	}
	
	/**
	 * Moves the world window right in the game world. This moves the view in the game world right.
	 */
	public void panRight()
	{
		windowLeft += panDistance;
		windowRight += panDistance;
	}
	
	/**
	 * Gets the current level of the game.
	 * @return - The current level.
	 */
	@Override
	public int getLevel()
	{
		return level;
	}
	
	/**
	 * Gets the current number of lives the player has.
	 * @return - The current number of lives.
	 */
	@Override
	public int getLives()
	{
		return lives;
	}
	
	/**
	 * Gets the current time left in the game.
	 * @return - The current time left.
	 */
	@Override
	public int getTime()
	{
		return timer;
	}
	
	/**
	 * Gets the flag indicating whether to draw the bounds of each of the game objects or not.
	 * @return
	 */
	public boolean getDrawBounds()
	{
		return bounds;
	}
	
	/**
	 * Gets the flag indicating whether to draw the context lines of each of the Sweeper objects.
	 * @return
	 */
	public boolean getDrawContext()
	{
		return context;
	}
	
	
	/**
	 * Switch the playing state of the game.
	 */
	public void changePlay()
	{	
		// Will pause the game if currently playing.
		if (play) 
		{
			play = false;
			
			// Saves whether the sound was previously on or not.
			keepSound = getSound();
			
			if (getSound()) {
				toggleSound();	// Turns the sound off.
			}
		}
		// Will unpause the game and unselect all objects that were currently selected.
		else 
		{
			play = true;
			
			// If sound was previously on...
			if (keepSound)
			{
				if(!getSound()) {
					toggleSound();	// Turns the sound back on.
				}
			}
			// Unselects all objects that were previously selected.
			unSelectObjects();
		}
	}
	
	/**
	 * Gets the current playing state of the game.
	 * @return - The current play state of the game.
	 */
	public boolean getPlay()
	{
		return play;
	}
	
	/**
	 * Gets the current score of the player.
	 * @return - The player's current score.
	 */
	@Override
	public double getScore()
	{
		return score;
	}
	
	/**
	 * Gets the current goal of the level.
	 * @return - The goal required to complete the level.
	 */
	@Override
	public double getGoal()
	{
		return goal;
	}
	
	/**
	 * Gets the width and height of the game world.
	 * @return - The size of the game world.
	 */
	public int getWorldSize()
	{
		//TODO: May not need this.
		return gameSize;
	}
	
	/**
	 * Toggles the sound on or off.
	 */
	@Override
	public void toggleSound()
	{
		// If the sound is on, turns it off. If its off, turns it on.
		if (sound)
		{
			sound = false;
			bgMusic.stop();
		}
		else
		{
			if (play)
			{
				sound = true;
				bgMusic.loop();
			}
		}

		// Updates the score view with the sound value.
		notifyObservers(notifyScoreView);
	}
	
	/**
	 * Toggles drawing the context lines of the Sweeper objects on or off.
	 */
	public void toggleContext()
	{
		if (context)
			context = false;
		else
			context = true;
		
		// Sets all the Sweeper objects' flags to the current context value.
		drawContext();
	}
	
	/**
	 * Toggles drawing the bounds of the game objects on or off.
	 */
	public void toggleDrawBounds()
	{
		if (bounds)
			bounds = false;
		else
			bounds = true;
		
		// Sets all the Game Object's flags to the current bounds value.
		drawBounds();
	}
	
	/**
	 * Returns whether the sound is on or not.
	 * @return - A flag showing if the sound is on or not.
	 */
	@Override
	public boolean getSound()
	{
		return sound;
	}
	
	/**
	 * Starts a new game.
	 */
	public void newGame()
	{
		System.out.println("New was selected!");
	}
	
	/**
	 * Saves the current state of the game.
	 */
	public void saveGame()
	{
		System.out.println("Save was selected!");
	}
	
	/**
	 * Undoes the last action invoked.
	 */
	public void undo()
	{
		System.out.println("Undo was selected!");
	}
	
	/**
	 * Allows user to quit out of the game.
	 */
	@Override
	public void exitGame()
	{
		int result;					// Used to hold whether the user wants to exit the game or not.
		boolean wasPaused = true;	// Flag that indicates whether the game was already paused prior.
		
		// If the game is not currently paused, this will pause the game.
		if (getPlay())
		{
			changePlay(); 		// Will pause the game.
			wasPaused = false;	// Indicates the game will be switched back to play if user declines quitting.
		}
		
		// Displays a modal YES or NO dialog box which confirms with the user whether they want to quit or
		//	not.
		result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		
		// Will quit if the user clicks yes.
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
		
		if (!wasPaused){
			changePlay();	// Unpauses the game if the game wasn't paused prior to this method being called.
		}
	}
	
	/**
	 * Returns an iterator.
	 * @return
	 */
	public Iterator getIterator()
	{
		return gameObjects.iterator();
	}
	
	/**
	 * Creates a transform matrix that maps a point to a normalized device. A normalized device being a
	 * view container that has a width and height of 1. This is used to make transforms to different screen
	 * sizes easier.
	 * @param worldWidth - The width of the world view window.
	 * @param worldHeight - The height of the world view window.
	 * @param worldLeft - The left bounds of the world view window.
	 * @param worldBottom - The bottom bounds of the world view window.
	 * @return Returns a transform matrix that maps a point to normalized device depending on the size of the
	 * 			game world window view.
	 */
	public AffineTransform buildWorldToNDXform()
	{
		AffineTransform worldToND = new AffineTransform();
		
		// Grabs the world's window view.
		Rectangle worldWindow = getWorldWindow();
		double width = worldWindow.getWidth();
		double height = worldWindow.getHeight();
		double left = worldWindow.getMinX();
		double bottom = worldWindow.getMinY();
		
		// Mapping a point from the world to a normalized device is as follows:
		// ndX = (wX * Translate(-wLeft)) * Scale(1/wWidth)
		// ndY = (wY * Translate(-wBottom)) * Scale(1/wHeight)
		
		worldToND.scale(1/width, 1/height);
		worldToND.translate(-left, -bottom);
		
		return worldToND;
	}
	
	/**
	 * Creates a transform matrix that maps a point from a normalized device to a screen.
	 * @param screenWidth - The width of the screen that the points will be viewed on.
	 * @param screenHeight - The height of the screen that the points will be viewed on.
	 * @return Returns a transform matrix that maps a point to the screen.
	 */
	public AffineTransform buildNDToScreenXform(double screenWidth, double screenHeight)
	{
		AffineTransform ndToScreen = new AffineTransform();
		
		// Mapping a point from normalized device to the screen is as follows:
		// scrX = ndX * Scale(scrWidth) * Translate(0)
		// scrY = ndY * Scale(-scrHeight) * Translate(scrHeight)
		
		ndToScreen.translate(0, screenHeight);
		ndToScreen.scale(screenWidth, -screenHeight);
		
		return ndToScreen;
	}
	
	/**
	 * Sets the VTM that is being used to draw points on the screen.
	 * @param newVTM - The new VTM that is being used.
	 */
	public void setVTM(AffineTransform newVTM)
	{
		theVTM = newVTM;
	}
	
	/**
	 * Inverts the currently set VTM.
	 * @return The inverse of the current VTM.
	 */
	public AffineTransform invertVTM()
	{
		try {
			return theVTM.createInverse();
		} catch (NoninvertibleTransformException e) {
			System.out.println(e.getMessage());
			return theVTM;
		}
	}

	/**
	 * Adds an object that wants to monitor this class to a list so that they can be signaled later.
	 * @param obs - The object to add to the list of objects that are currently monitoring.
	 */
	@Override
	public void addObserver(IObserver obs) 
	{
		observers.add(obs);
	}
	
	/**
	 * Signals all objects in the list to let them know that this object has been updated.
	 * @param notifier - The message to send the observing objects so they know what is being updated.
	 */
	@Override
	public void notifyObservers(Object message) 
	{
		IObserver obs;
		
		for (int index = 0; index < observers.size(); index++){
			obs = observers.elementAt(index);
			obs.update(proxy, message);
		}
	}
}

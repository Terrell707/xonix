package a4.interfaces;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * This GameWorld interface gives the calling class the methods to interact with and control the GameWorld.
 * @author Adrian
 *
 */
public interface IGameWorld {
	
	public AffineTransform buildNDToScreenXform(double screenWidth, double screenHeight);
	public AffineTransform buildWorldToNDXform();
	public void setVTM(AffineTransform VTM);
	public void captureGroup();
	public void captureSquare();
	public void carEast();
	public void carNorth();
	public void carSouth();
	public void carWest();
	public void changeBombStrategy();
	public void changePlay();
	public void collideMonsterBall();
	public void collideSmartBomb();
	public void decreaseCarSpeed();
	public void displayAbout();
	public void displayMap();
	public void displayStats();
	public void drawMap(Graphics g);
	public void exitGame();
	public double getGoal();
	public int getLevel();
	public int getLives();
	public double getScore();
	public boolean getSound();
	public int getTime();
	public boolean getPlay();
	public int getWorldSize();
	public Rectangle getWorldWindow();
	public void increaseCarSpeed();
	public void obtainTimeTicket();
	public void selectObject(Point2D click);
	public void setWorldWindow(double left, double right, double top, double bottom);
	public void spawnMonsterBall();
	public void spawnSmartBomb();
	public void spawnTimeTicket();
	public void timeTick(int elapsed);
	public void toggleSound();
	
	public void addObserver(IObserver obs);
	public void notifyObservers(Object message);
}

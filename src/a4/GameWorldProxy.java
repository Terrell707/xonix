package a4;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import a4.interfaces.IGameWorld;
import a4.interfaces.IObservable;
import a4.interfaces.IObserver;

public class GameWorldProxy implements IObservable, IGameWorld {

	private GameWorld realGameWorld;	// Holds the instance of the real Game World.
	
	/**
	 * Takes the instance of GameWorld so that this class can act on behalf of the real GameWorld.
	 * @param realGameWorld
	 */
	public GameWorldProxy(GameWorld gw)
	{
		realGameWorld = gw;
	}
	
	@Override
	public AffineTransform buildWorldToNDXform() {
		return realGameWorld.buildWorldToNDXform();
	}
	
	@Override
	public AffineTransform buildNDToScreenXform(double screenWidth, double screenHeight) {
		return realGameWorld.buildNDToScreenXform(screenWidth, screenHeight);
	}
	
	@Override
	public void setVTM(AffineTransform VTM) {
		realGameWorld.setVTM(VTM);
	}
	
	@Override
	public void captureGroup() {
		
	}

	@Override
	public void captureSquare() {

	}

	public void changeCarDirection(int heading) {

	}
	
	@Override
	public void changePlay() {
		
	}
	
	@Override
	public void carEast() {
		
	}
	
	@Override
	public void carNorth() {
		
	}
	
	@Override
	public void carSouth() {
		
	}
	
	@Override
	public void carWest() {
		
	}
	
	@Override
	public void changeBombStrategy() {
		
	}

	@Override
	public void collideMonsterBall() {

	}
	
	@Override
	public void collideSmartBomb() {
		
	}
	
	public void createGameField() {

	}

	@Override
	public void decreaseCarSpeed() {

	}
	
	@Override
	public void displayAbout() {
		realGameWorld.displayAbout();
	}

	@Override
	public void displayMap() {
		realGameWorld.displayMap();
	}

	@Override
	public void displayStats() {
		realGameWorld.displayStats();
	}
	
	@Override
	public void drawMap(Graphics g){
		realGameWorld.drawMap(g);
	}

	@Override
	public void exitGame() {

	}

	public void gameStart() {

	}

	@Override
	public void increaseCarSpeed() {

	}

	public void loseLife() {

	}

	public void nextLevel() {

	}

	@Override
	public void obtainTimeTicket() {

	}
	
	@Override
	public void selectObject(Point2D clickedPoint)
	{
		realGameWorld.selectObject(clickedPoint);
	}
	
	@Override
	public void setWorldWindow(double left, double right, double top, double bottom) {
		
	}

	@Override
	public void spawnMonsterBall() {

	}
	
	@Override
	public void spawnSmartBomb() {
		
	}

	@Override
	public void spawnTimeTicket() {

	}

	@Override
	public void timeTick(int elapsed) {

	}
	
	@Override
	public void toggleSound() {
		
	}

	public void updateScore() {

	}

	public void notifyObservers() {

	}

	@Override
	public double getGoal() {
		return realGameWorld.getGoal();	
	}

	@Override
	public int getLevel() {
		return realGameWorld.getLevel();
	}

	@Override
	public int getLives()
	{
		return realGameWorld.getLives();
	}

	@Override
	public double getScore() {
		return realGameWorld.getScore();
	}
	
	@Override
	public boolean getSound() {
		return realGameWorld.getSound();
	}

	@Override
	public int getTime() {
		return realGameWorld.getTime();
	}
	
	@Override
	public boolean getPlay() {
		return realGameWorld.getPlay();
	}
	
	@Override
	public int getWorldSize() {
		return realGameWorld.getWorldSize();
	}
	
	@Override
	public Rectangle getWorldWindow() {
		return realGameWorld.getWorldWindow();
	}

	@Override
	public void addObserver(IObserver obs) {
		realGameWorld.addObserver(obs);
	}

	@Override
	public void notifyObservers(Object message) {
		
	}

}

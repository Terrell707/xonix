package game.gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class SmartBombBody {
	
	private int radius;
	private Color color;
	private AffineTransform translation, rotation, scale;

	/**
	 * Creates the SmartBomb body with the size and color passed in.
	 * @param setRadius - The size of the SmartBomb's body.
	 * @param setColor - The color of the SmartBomb's body.
	 */
	public SmartBombBody(int setRadius, Color setColor)
	{
		radius = setRadius;
		color = setColor;
		
		translation = new AffineTransform();
		rotation = new AffineTransform();
		scale = new AffineTransform();
	}
	
	/**
	 * Creates the SmartBomb body with the size and color passed in.
	 * @param setRadius - The size of the SmartBomb's body.
	 * @param red - The amount of red added to the SmartBomb's color.
	 * @param green - The amount of green added to the SmartBomb's color.
	 * @param blue - The amount of blue added to the SmartBomb's color.
	 */
	public SmartBombBody(int setRadius, int red, int green, int blue)
	{
		radius = setRadius;
		color = new Color(red, green, blue);
		
		translation = new AffineTransform();
		rotation = new AffineTransform();
		scale = new AffineTransform();
	}
	
	public void translate(double dx, double dy)
	{
		translation.translate(dx, dy);
	}
	
	public void rotate(double degrees)
	{
		rotation.rotate(Math.toRadians(degrees));
	}
	
	public void scale(double sx, double sy)
	{
		scale.scale(sx, sy);
	}
	
	public void draw(Graphics2D g2d)
	{
		AffineTransform saveAT = g2d.getTransform();
		
		g2d.transform(translation);
		g2d.transform(rotation);
		g2d.transform(scale);
		
		g2d.setColor(color);
		g2d.fillOval(0, 0, (radius*2), (radius*2));
		
		g2d.setTransform(saveAT);
	}
}

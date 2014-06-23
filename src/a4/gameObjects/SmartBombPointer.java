package a4.gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class SmartBombPointer {

	private Point2D top, bottomLeft, bottomRight;
	private Color color;
	private AffineTransform translation, rotation, scale;
	
	public SmartBombPointer(int radius, Color setColor)
	{
		top = new Point2D.Double(0, radius*2);
		bottomLeft = new Point2D.Double(-1, 0);
		bottomRight = new Point2D.Double(1, 0);
		
		color = setColor;
		
		translation = new AffineTransform();
		rotation = new AffineTransform();
		scale = new AffineTransform();
	}
	
	public SmartBombPointer(int radius, int red, int green, int blue)
	{
		top = new Point2D.Double(0, radius);
		bottomLeft = new Point2D.Double(-radius, 0);
		bottomRight = new Point2D.Double(radius, 0);
		
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
		g2d.translate(-g2d.getTransform().getTranslateX(), -g2d.getTransform().getTranslateY());
//		g2d.transform(rotation);
//		g2d.transform(translation);
//		g2d.transform(scale);
		
		g2d.setColor(color);
		
		int[] xPts = { (int)top.getX(), (int)bottomLeft.getX(), (int)bottomRight.getX() };
		int[] yPts = { (int)top.getY(), (int)bottomLeft.getY(), (int)bottomRight.getY() };
		
		g2d.fillPolygon(xPts, yPts, 3);
		
		g2d.setTransform(saveAT);
	}
}

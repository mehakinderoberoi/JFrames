package Data_structure;

import java.awt.Color;

import Utility.Constants;
import Utility.Formula;

public class Rectangle extends Shape{
	
	/* Upper left coordinate */
	private int x1;
	private int y1;
	
	private int x2;
	private int y2;
	
	private int strokeColor;
	private int fillColor;
	private int thickness;
	
	/* Lower right coordinate */
	
	public Rectangle(int x1, int y1, int x2, int y2)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.strokeColor = Constants.COLOR_BLACK;
		this.fillColor = Constants.COLOR_BLACK;
		this.thickness = 1;
	}
	public Rectangle(int x1, int y1, int x2, int y2, int thickness)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.strokeColor = Constants.COLOR_BLACK;
		this.fillColor = Constants.COLOR_BLACK;
		this.thickness = thickness;
	}

	public void setThickness(int thick)
	{
		this.thickness = thick;
	}
	public int getThickness()
	{
		return this.thickness;
	}
	public int getStrokeColor()
	{
		return this.strokeColor;
	}
	public void setStrokeColor(int color)
	{
		this.strokeColor = color;
	}
	public void setFillColor(int color)
	{
		this.fillColor = color;
	}
	public int getUpperLeftX()
	{
		return this.x1;
	}
	public int getUpperLeftY()
	{
		return this.y1;
	}
	public int getLowerRightX()
	{
		return this.x2;
	}
	public int getLowerRightY()
	{
		return this.y2;
	}
	public int getWidth()
	{
		return Math.abs(this.x1 - this.x2); 
	}
	
	public int getHeight()
	{
		return Math.abs(this.y1 - this.y2);
	}
	
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append("x1: " + this.x1 + " y1 : " + this.y1 + " x2: " + this.x2 + " y2: " + this.y2);
		return s.toString();
	}
	
	@Override
	public double calcArea() {
		return this.getWidth() * this.getHeight();
	}
	
}

package tk.lindholm.limn.model;

import javax.xml.bind.annotation.XmlAttribute;



/**
 * A small model to easily remember x, y, width and height of a window.
 * 
 * @author Wiggy boy
 */
public class WindowBounds {



	double x, y, width, height;
	boolean maximized;



	@XmlAttribute
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}



	@XmlAttribute
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}



	@XmlAttribute
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}



	@XmlAttribute
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}



	@XmlAttribute
	public boolean isMaximized() {
		return maximized;
	}
	public void setMaximized(boolean fullscreen) {
		this.maximized = fullscreen;
	}



	@Override
	public String toString() {
		return "[x: " + x + ", y: " + y + ", width: " + width + ", height: " + height + ", maximized: " + maximized + "]";
	}



}

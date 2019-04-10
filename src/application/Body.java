package application;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * 
 * 
 * 
 */

public class Body extends StackPane implements Sprite {
	
	private Rectangle _body;		// Body of snake
	private int _width, _height;		// width / height of Body
	private String _name;				// name of Body
	static int count;
	Label lcount;
	private double _xpos, _ypos;
	
	Body(String name, int bodyCount) {
		
		_width = 25;
		_height = 25;
		_name = name;
		count = bodyCount;
		draw();
		
	}
	
	@Override
	public void draw() {
		_body = new Rectangle(_width, _height);
		_body.setFill(Color.TRANSPARENT);
		_body.setStroke(Color.CRIMSON);
		
		lcount = new Label(Integer.toString(count));
		lcount.setTextFill(Color.BLACK);
		lcount.setFont(Font.font(15));
	}
	
	@Override
	public void show() {
		getChildren().addAll(_body, lcount);
	}
	
	
	// doesn't actually set positions. it keeps track of the position this body is in
	public void setPosition(double x, double y) {
		_xpos = x;
		_ypos = y;
	}
	
	public double getXPosition() {
		return _xpos;
	}
	public double getYPosition() {
		return _ypos;
	}
	
	
	@Override
	public String toString() {
		return String.format("(%.2f, %.2f)", _xpos, _ypos);
	}
	
}

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
	private Label _lcount;
	
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
		
		_lcount = new Label(Integer.toString(count));
		_lcount.setTextFill(Color.BLACK);
		_lcount.setFont(Font.font(15));
	}
	
	@Override
	public void show() {
		getChildren().addAll(_body, _lcount);
	}
	
}

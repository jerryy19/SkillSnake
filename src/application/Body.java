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
	
	private Rectangle body;				// Body of snake
	private int width, height;			// width / height of Body
	private String name;				// name of Body
	static int count;
	private Label lcount;
	
	Body(String name, int bodyCount) {
		
		width = 25;
		height = 25;
		name = name;
		count = bodyCount;
		draw();
		
	}
	
	@Override
	public void draw() {
		body = new Rectangle(width, height);
		body.setFill(Color.TRANSPARENT);
		body.setStroke(Color.CRIMSON);
		
		lcount = new Label(Integer.toString(count));
		lcount.setTextFill(Color.BLACK);
		lcount.setFont(Font.font(15));
	}
	
	@Override
	public void show() {
		getChildren().addAll(body, lcount);
	}
	
}

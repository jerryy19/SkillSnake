package application;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * @author Jerry Yu
 * Due Date: 4 / 17 / 19
 */

public class Body extends StackPane implements Sprite {
	
	private Rectangle body;				// body of snake
	private int width = 25;				// width of body
	private int height = 25;			// height of Body
	private String name;				// name of Body
	private static int count;			// body count
	private Label lcount;
	
	Body(String name, int bodyCount) {
		
		this.name = name;
		count = bodyCount;
		draw();
		
	}
	
	@Override
	public void draw() {
		
		body = new Rectangle(width, height);
		if(count == 0) {
			body.setFill(Color.YELLOW);
		} else {
			body.setFill(Color.TRANSPARENT);			
		}
		body.setStroke(Color.CRIMSON);
		
		lcount = new Label(Integer.toString(count));
		lcount.setTextFill(Color.BLACK);
		lcount.setFont(Font.font(15));
		
	}
	
	@Override
	public void show() {
		getChildren().addAll(body, lcount);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}

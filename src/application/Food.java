package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Jerry Yu
 * Due Date: 4 / 17 / 19
 */

public class Food extends Pane implements Sprite {
	
	private Rectangle r;				// outline box
	private int width = 25;				// width of Food
	private int height = 25;			// height of Food
	private String name;				// name of Food
	private Image apple;
	private ImageView imgApple;
	
	Food(String name) {
		
		this.name = name;
		draw();
		
	}
	
	@Override
	public void draw() {
		
		apple = new Image(getClass().getResourceAsStream("\\assets\\apple.png"));
		imgApple = new ImageView(apple);
		imgApple.setFitWidth(width);
		imgApple.setFitHeight(height);
		
		r = new Rectangle(width, height);
		r.setStroke(Color.LIGHTGRAY);
		r.setFill(Color.TRANSPARENT);
		
	}
	
	@Override
	public void show() {
		getChildren().addAll(imgApple, r);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}

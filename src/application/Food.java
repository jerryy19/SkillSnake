package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * 
 *
 */

public class Food extends Pane implements Sprite {
	
	private Rectangle _rContainer;		// outline box(unnecessary)
	private int _width, _height;		// width / height of Wall
	private String _name;				// name of Wall
	Image apple;
	ImageView imgApple;
	
	Food(String name) {
		
		_width = 25;
		_height = 25;
		_name = name;
		draw();
		
	}
	
	@Override
	public void draw() {
		apple = new Image(getClass().getResourceAsStream("\\assets\\apple.png"));
		imgApple = new ImageView(apple);
		imgApple.setFitWidth(25);
		imgApple.setFitHeight(25);
	}
	
	@Override
	public void show() {
//		_r = new Rectangle(_width, _height);
//		_r.setFill(Color.RED);
		getChildren().add(imgApple);
	}
	
}

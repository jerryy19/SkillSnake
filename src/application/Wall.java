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

public class Wall extends Pane implements Sprite {
	
	private Rectangle rContainer;		// outline box(unnecessary)
	private int width, height;			// width / height of Wall
	private String name;				// name of Wall
	Image wall;
	ImageView imgWall;
	
	Wall(String name) {
		
		width = 25;
		height = 25;
		name = name;
		draw();
		
	}
	
	@Override
	public void draw() {
		wall = new Image(getClass().getResourceAsStream("\\assets\\wall.png"));
		imgWall = new ImageView(wall);
		imgWall.setFitWidth(25);
		imgWall.setFitHeight(25);
	}
	
	@Override
	public void show() {
//		r = new Rectangle(width, height);
//		r.setFill(Color.RED);
		getChildren().add(imgWall);
	}
	
}

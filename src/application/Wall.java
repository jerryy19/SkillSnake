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

public class Wall extends Pane implements Sprite {
	
	private Rectangle r;				// outline box
	private int width = 25;				// width of Wall
	private int height = 25;			// height of Wall
	private String name;				// name of Wall
	private Image wall;
	private ImageView imgWall;
	
	Wall(String name) {
		
		this.name = name;
		draw();
		
	}
	
	@Override
	public void draw() {
		
		wall = new Image(getClass().getResourceAsStream("\\assets\\wall.png"));
		imgWall = new ImageView(wall);
		imgWall.setFitWidth(width);
		imgWall.setFitHeight(height);
		
		r = new Rectangle(width, height);
		r.setStroke(Color.LIGHTGRAY);
		r.setFill(Color.TRANSPARENT);
		
	}
	
	@Override
	public void show() {
		getChildren().addAll(imgWall, r);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}

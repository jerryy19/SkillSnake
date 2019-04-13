package application;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * 
 * 
 * 
 */

public class Board extends VBox implements Sprite, EventHandler<Event> {
	
	private Rectangle rContainer;		// outline box(unnecessary)
	private int width, height;		// width / height of Board
	private String name;				// name of Board
	private Rectangle[][] grid;
	Pane p;
	Snake s;
	Food f;
	Wall w;
	String mode =  "snake20Mode";
	static Timeline timeline = new Timeline();

	
	Random r = new Random();
	
	Board(int w, int h, String name) {
		width = w;
		height = h;
		this.name = name;
		draw();
		
//		W A S D
		setOnKeyPressed(this);
	}
	public void draw() {
		rContainer = new Rectangle(width, height);
//		rContainer.setStroke(Color.BLACK);
		rContainer.setFill(Color.WHITE);
		
		grid = new Rectangle[width / 25][height / 25];
		
		for (int i = 0; i < width / 25; i++) {
			for (int j = 0; j < height / 25; j++) {
				grid[j][i] = new Rectangle(j * 25, i * 25, 25, 25);
				if((j + i)% 2 == 0) {
					grid[j][i].setFill(Color.LIGHTBLUE);
				} else {
					grid[j][i].setFill(Color.GHOSTWHITE);
				}
			}
		}
		
		s = new Snake(width, height);
		s.show();
		
		w = new Wall("w1");
		w.show();
		
		f = new Food("f1");
		f.show();
		
		f.setTranslateX(r.nextInt(width / 25) * 25);
		f.setTranslateY(r.nextInt(height / 25) * 25);
		w.setTranslateX(r.nextInt(width / 25) * 25);
		w.setTranslateY(r.nextInt(height / 25) * 25);
	}

	@Override
	public void show() {
		p = new Pane();
		setMaxSize(width, height);
		
		for (int i = 0; i < width / 25; i++) {
			for (int j = 0; j < height / 25; j++) {
				p.getChildren().add(grid[i][j]);
			}
		}
		
		p.getChildren().add(s);
		p.getChildren().add(w);
		
		p.getChildren().add(f);
		getChildren().add(p);
	
//		addEventFilter(MouseEvent.MOUSECLICKED, event -> System.out.println("Clicked!"));
		
	}
	
	public String toString() {
		return String.format("%s", name);
	}

	@Override
	public void handle(Event event) {
		
		if(event instanceof KeyEvent) {
			KeyEvent ke = (KeyEvent) event;
			try {
				if(ke.getCode() == KeyCode.W || ke.getCode() == KeyCode.UP) {
					move(0, -25);
				} else if(ke.getCode() == KeyCode.A || ke.getCode() == KeyCode.LEFT) {
					move(-25, 0);
				} else if(ke.getCode() == KeyCode.S || ke.getCode() == KeyCode.DOWN) {
					move(0, 25);
				} else if(ke.getCode() == KeyCode.D || ke.getCode() == KeyCode.RIGHT) {
					move(25, 0);
				}
			} catch(Exception e) {System.out.println("err in keyevent");}
			
//			if(((KeyEvent) event).getCode() == KeyCode.I) {
//				s.setTranslateX(s.getTranslateX() + 25);
//			}
//			System.out.println(((KeyEvent) event).getCode());
		}
	}
	
	public void move(double x, double y) {
		timeline.stop();
		timeline = new Timeline(new KeyFrame(
				Duration.millis(160), ae -> {
					s.setDirection(x, y);
					checkFoodPos();
				}
		));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
	public void checkMode() {
		if(mode.equals("snake20Mode")) {
			System.out.println("snake20 on");
			try {
				p.getChildren().addAll(w);
			} catch(Exception e) {
				System.out.println("wall is currently visible");
			}
				
			checkWallPos();
		} else {
			System.out.println("snake20 off");
			p.getChildren().removeAll(w);
		}
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void checkFoodPos() {
		if((int)s.getHeadxPos() == (int)f.getTranslateX() && (int)s.getHeadyPos() == (int)f.getTranslateY()) {
			System.out.println("food true");
			f.setTranslateX(r.nextInt(width / 25) * 25);
			f.setTranslateY(r.nextInt(height / 25) * 25);
			s.grow();
			Main.updateScore();
		}
	}
	
	public void checkWallPos() {
		if((int)s.getHeadxPos() == (int)w.getTranslateX() && (int)s.getHeadyPos() == (int)w.getTranslateY()) {
			System.out.println("wall true");
			w.setTranslateX(r.nextInt(width / 25) * 25);
			w.setTranslateY(r.nextInt(height / 25) * 25);
			Main.updateScore();
		}
	}
	
}

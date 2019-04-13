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
	Snake snake;
	Food f;
	Wall w[];
	static String mode =  "snake20Mode";
	static Timeline timeline = new Timeline();
	
	int highscore;
	
	int scaleWidth, scaleHeight;

	
	Random r = new Random();
	
	Board(int w, int h, String name) {
		width = w;
		height = h;
		this.name = name;
		
		scaleWidth = width / 25;
		scaleHeight = height / 25;
		
		draw();
		


//		W A S D
		setOnKeyPressed(this);
	}
	public void draw() {
		rContainer = new Rectangle(width, height);
//		rContainer.setStroke(Color.BLACK);
		rContainer.setFill(Color.WHITE);
		
		grid = new Rectangle[scaleWidth][scaleHeight];
		
		for (int i = 0; i < scaleWidth; i++) {
			for (int j = 0; j < scaleHeight; j++) {
				grid[j][i] = new Rectangle(j * 25, i * 25, 25, 25);
				if((j + i)% 2 == 0) {
					grid[j][i].setFill(Color.LIGHTBLUE);
				} else {
					grid[j][i].setFill(Color.GHOSTWHITE);
				}
			}
		}
		
		snake = new Snake(width, height);
		snake.show();
		
		w = new Wall[(int) ((scaleWidth) * (scaleHeight) * 0.04)];
		for(int i = 0; i < w.length; i++) {
			w[i] = new Wall("w" + i);
			w[i].show();
			w[i].setTranslateX(r.nextInt(scaleWidth) * 25);
			w[i].setTranslateY(r.nextInt(scaleHeight) * 25);
		}
		
		f = new Food("f1");
		f.show();
		
		int x = r.nextInt(scaleWidth) * 25;
		int y = r.nextInt(scaleHeight) * 25;
		f.setTranslateX(x);
		f.setTranslateY(y);
		checkFoodWall(x, y);
	}

	@Override
	public void show() {
		p = new Pane();
		setMaxSize(width, height);
		
		for (int i = 0; i < scaleWidth; i++) {
			for (int j = 0; j < scaleHeight; j++) {
				p.getChildren().add(grid[i][j]);
			}
		}
		
		p.getChildren().add(snake);
		p.getChildren().addAll(w);
		
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
			
			if(ke.getCode() == KeyCode.R) {
				
			}
			
			
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
					snake.setDirection(x, y);
					checkFoodPos();
					checkWallPos();
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
			
		} else {
			System.out.println("snake20 off");
			p.getChildren().removeAll(w);
			snake.restart();
			Main.reset();
		}
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void checkFoodWall(int x, int y) {
		for(int i = 0; i < w.length; i++) {
			if((int)f.getTranslateX() == (int)w[i].getTranslateX() && 
					(int)f.getTranslateY() == (int)w[i].getTranslateY()) {
				System.out.println("food wall true");
				x = r.nextInt(scaleWidth) * 25;
				y = r.nextInt(scaleHeight) * 25;
				f.setTranslateX(x);
				f.setTranslateY(y);
				checkFoodWall(x, y);
			} else {
				
			}
		}
	}
	
	public void checkFoodPos() {
		if((int)snake.getHeadxPos() == (int)f.getTranslateX() && (int)snake.getHeadyPos() == (int)f.getTranslateY()) {
			System.out.println("food true");
			int x = r.nextInt(scaleWidth) * 25;
			int y = r.nextInt(scaleHeight) * 25;
			f.setTranslateX(x);
			f.setTranslateY(y);
			checkFoodWall(x, y);
			
			snake.grow();
			Main.updateScore();
		}
	}
	
	public void checkWallPos() {
		for(int i = 0; i < w.length; i++) {
			if((int)snake.getHeadxPos() == (int)w[i].getTranslateX() && 
					(int)snake.getHeadyPos() == (int)w[i].getTranslateY()) {
				System.out.println("wall true");
				snake.shrink();
//				w[i].setTranslateX(r.nextInt(scaleWidth) * 25);
//				w[i].setTranslateY(r.nextInt(scaleHeight) * 25);
			}
		}
	}
	
	public void endGame() {
		snake.endGame();
	}
	
}

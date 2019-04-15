package application;

import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * @author Jerry Yu
 * Due Date: 4 / 17 / 19
 */

public class Board extends VBox implements Sprite, EventHandler<Event> {
	
	private Rectangle rect;				// outline box
	private int width, height;			// width / height of Board
	private String name;				// name of Board
	private Rectangle[][] grid;
	private Pane p;
	private Snake snake;
	private Food f;
	private Wall w[];
	private int fill;					// boxes in grid
	
	static String mode =  "snake20Mode";
	static Timeline timeline = new Timeline();
	
	private int scaleWidth, scaleHeight;
	
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
		rect = new Rectangle(width, height);
//		rContainer.setStroke(Color.BLACK);
		rect.setFill(Color.WHITE);
		
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
		
//		new snake
		snake = new Snake(width, height);
		snake.show();
		
//		new wall
		w = new Wall[(int) ((scaleWidth) * (scaleHeight) * 0.04)];
		for(int i = 0; i < w.length; i++) {
			w[i] = new Wall("w" + i);
			w[i].show();
		}
		newWall();
		
		
//		new food
		f = new Food("f1");
		f.show();
		
//		food position
		int x = r.nextInt(scaleWidth) * 25;
		int y = r.nextInt(scaleHeight) * 25;
		f.setTranslateX(x);
		f.setTranslateY(y);
		checkFoodWall(x, y);
		
//		number of boxes in the grid to fill
//		fill = 4;
		fill = scaleWidth * scaleHeight - w.length;
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
		
	}
	
//	create wall positions
	public void newWall() {
		for(int i = 0; i < w.length; i++) {
			w[i].setTranslateX(r.nextInt(scaleWidth) * 25);
			w[i].setTranslateY(r.nextInt(scaleHeight) * 25);
			while(w[i].getTranslateX() == 0 && w[i].getTranslateY() == 0) {
				w[i].setTranslateX(r.nextInt(scaleWidth) * 25);
				w[i].setTranslateY(r.nextInt(scaleHeight) * 25);
			}
		}
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
				} else if(ke.getCode() == KeyCode.DIGIT1) {
//					System.out.println("true");
					newWall();
					p.getChildren().removeAll(w);
					p.getChildren().addAll(w);
				}
				
			} catch(Exception e) {System.out.println("err in keyevent");}
			
//			if(((KeyEvent) event).getCode() == KeyCode.I) {
//				s.setTranslateX(s.getTranslateX() + 25);
//			}
//			System.out.println(((KeyEvent) event).getCode());
		}
	}
	
	public void move(double x, double y) {
		if(snake.isVisible()) {
			
		Label winner = new Label("Winner");
		timeline.stop();
		timeline = new Timeline(new KeyFrame(
				Duration.millis(160), ae -> {
					snake.setDirection(x, y);
					checkFoodPos();
					checkWallPos();
					if(snake.getSize() == fill) {
						timeline.stop();
						System.out.println("winner");
						Main.updateHighscore();
						winner.relocate(50, 50);
						winner.setTextFill(Color.GREEN);
						winner.setFont(Font.font(20));
						getChildren().add(winner);
						endGame();
					}
				}
		));
		if(winner.isVisible()) {
			getChildren().remove(winner);
		}
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		}
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
		Board.mode = mode;
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
			Main.updateScore(1);
		}
	}
	
	public void checkWallPos() {
		for(int i = 0; i < w.length; i++) {
			if((int)snake.getHeadxPos() == (int)w[i].getTranslateX() && 
					(int)snake.getHeadyPos() == (int)w[i].getTranslateY()) {
				System.out.println("wall true");
				if(mode.equals("snake20Mode")) {
					snake.shrink();					
				}
//				w[i].setTranslateX(r.nextInt(scaleWidth) * 25);
//				w[i].setTranslateY(r.nextInt(scaleHeight) * 25);
			}
		}
	}
	
	public void endGame() {
		snake.endGame();
	}
	
	public String toString() {
		return String.format("%s", name);
	}
	
}

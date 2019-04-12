package application;

import java.util.Random;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
				if(ke.getCode() == KeyCode.W) {
					s.setDirection(0, -25);
					checkFoodPos();
				} else if(ke.getCode() == KeyCode.A) {
					s.setDirection(-25, 0);
					checkFoodPos();
				} else if(ke.getCode() == KeyCode.S) {
					s.setDirection(0, 25);
					checkFoodPos();
				} else if(ke.getCode() == KeyCode.D) {
					s.setDirection(25, 0);
					checkFoodPos();
				}
			} catch(Exception e) {System.out.println("err");}
			
//			if(((KeyEvent) event).getCode() == KeyCode.I) {
//				s.setTranslateX(s.getTranslateX() + 25);
//			}
//			System.out.println(((KeyEvent) event).getCode());
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
//			Main_v2.setScore();
		}
	}
	
}

package application;

import java.util.Random;

import javafx.event.Event;
import javafx.event.EventHandler;
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
	
	private Rectangle _rContainer;		// outline box(unnecessary)
	private int _width, _height;		// width / height of Board
	private String _name;				// name of Board
	private Rectangle[][] grid;
	Pane p;
	Snake s;
	Food f;
	Wall w;
	
	Random r = new Random();
	
	Board(int w, int h, String name) {
		_width = w;
		_height = h;
		
		draw();
		
//		W A S D
		setOnKeyPressed(this);
	}
	public void draw() {
		_rContainer = new Rectangle(_width, _height);
//		_rContainer.setStroke(Color.BLACK);
		_rContainer.setFill(Color.WHITE);
		
		grid = new Rectangle[_width / 25][_height / 25];
		
		for (int i = 0; i < _width / 25; i++) {
			for (int j = 0; j < _height / 25; j++) {
				grid[j][i] = new Rectangle(j * 25, i * 25, 25, 25);
				if((j + i)% 2 == 0) {
					grid[j][i].setFill(Color.LIGHTBLUE);
				} else {
					grid[j][i].setFill(Color.GHOSTWHITE);
				}
			}
		}
		
		s = new Snake(_width, _height);
		s.show();
		w = new Wall("w1");
		w.show();
		f = new Food("f1");
		f.show();
		
		f.setTranslateX(r.nextInt(_width / 25) * 25);
		f.setTranslateY(r.nextInt(_height / 25) * 25);
		w.setTranslateX(r.nextInt(_width / 25) * 25);
		w.setTranslateY(r.nextInt(_height / 25) * 25);
	}

	@Override
	public void show() {
		p = new Pane();
		setMaxSize(_width, _height);
		
		for (int i = 0; i < _width / 25; i++) {
			for (int j = 0; j < _height / 25; j++) {
				p.getChildren().add(grid[i][j]);
			}
		}
		p.getChildren().add(s);
		p.getChildren().add(w);
		p.getChildren().add(f);
		getChildren().add(p);
	
//		addEventFilter(MouseEvent.MOUSE_CLICKED, event -> System.out.println("Clicked!"));
		
	}
	
	public String toString() {
		return String.format("%s", _name);
	}

	@Override
	public void handle(Event event) {
		
		if(event instanceof KeyEvent) {
			KeyEvent ke = (KeyEvent) event;
			if(ke.getCode() == KeyCode.W) {
				s.setDirection(0, -25);
				checkPos();
			} else if(ke.getCode() == KeyCode.A) {
				s.setDirection(-25, 0);
				checkPos();
			} else if(ke.getCode() == KeyCode.S) {
				s.setDirection(0, 25);
				checkPos();
			} else if(ke.getCode() == KeyCode.D) {
				s.setDirection(25, 0);
				checkPos();
			}
			
//			if(((KeyEvent) event).getCode() == KeyCode.I) {
//				s.setTranslateX(s.getTranslateX() + 25);
//			}
//			System.out.println(((KeyEvent) event).getCode());
		}
	}
	
	public void checkPos() {
		if((int)s.getHeadxPos() == (int)f.getTranslateX() && (int)s.getHeadyPos() == (int)f.getTranslateY()) {
//			System.out.println("true");
			f.setTranslateX(r.nextInt(_width / 25) * 25);
			f.setTranslateY(r.nextInt(_height / 25) * 25);
			s.grow();
			Main.setScore();
		}
	}
	
}

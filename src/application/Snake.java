package application;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * 
 * 
 */

public class Snake extends Pane implements Sprite {
	
	private ArrayList<Body> snake = new ArrayList<>();
	private ArrayList<Double> _xpos = new ArrayList<>();
	private ArrayList<Double> _ypos = new ArrayList<>();
	
	private static double _xdir, _ydir;
	private int _bodyCount;
	
	private double _bwidth, _bheight;
	private double headx, heady;
	
	Button b;
	
	
	Snake(double x, double y) {
		
		_bwidth = x;
		_bheight = y;

		draw();
	}
	
	@Override
	public void draw() {
//		snake.get(i).setFill(Color.MEDIUMVIOLETRED);
//		snake.get(i).setStroke(Color.DARKMAGENTA);
		
		snake.add(new Body("Body#" + _bodyCount, _bodyCount));
		snake.get(0).show();
		_xpos.add(snake.get(0).getTranslateX());
		_ypos.add(snake.get(0).getTranslateY());
		
		
		
//		System.out.println("headx " + snake.get(0).getTranslateX());
//		System.out.println("heady " + snake.get(0).getTranslateY());
		
//		System.out.println(snake.get(i)._name);
	}
	
	@Override
	public void show() {
		getChildren().addAll(snake);
	}
	
	public void checkBounds() {
		if(snake.get(0).getTranslateX() < 0 || snake.get(0).getTranslateX() > _bwidth - 25||
				snake.get(0).getTranslateY() < 0 || snake.get(0).getTranslateY() > _bheight - 25) {
//			endGame();
			if(Main.getLives() > 0) {
				retry();				
			} else {
				endGame();
			}
		}
	}
	
	
	public void retry() {
		Main.setLives();
		
//		headx = 0;
//		heady = 0;
		
//		snake.get(0).setTranslateX(0);
//		snake.get(0).setTranslateY(0);
		
		shift(0, 0);
		
	}
	public void setDirection(double xdir, double ydir) {
		_xdir = xdir;
		_ydir = ydir;
		update();
	}
	
	public void update() {
//		get positions before updating them
		_xpos.set(0, snake.get(0).getTranslateX());
		_ypos.set(0, snake.get(0).getTranslateY());
		
		shift(snake.get(0).getTranslateX() + _xdir, snake.get(0).getTranslateY() + _ydir);
//		set new head position
//		snake.get(0).setTranslateX(snake.get(0).getTranslateX() + _xdir);
//		snake.get(0).setTranslateY(snake.get(0).getTranslateY() + _ydir);
		
//		new head position
//		headx = snake.get(0).getTranslateX();
//		heady = snake.get(0).getTranslateY();
		
		
		System.out.println("headx " + headx);
		System.out.println("heady " + heady);
		System.out.println();
		
		
		checkBounds();
		
	}
	public void shift(double translateX, double translateY) {
		
		snake.get(0).setTranslateX(translateX);
		snake.get(0).setTranslateY(translateY);
		
		headx = snake.get(0).getTranslateX();
		heady = snake.get(0).getTranslateY();
		
//		shifting body positions
		for(int i = 1; i < snake.size(); i++) {
			snake.get(i).setTranslateX(_xpos.get(i - 1));
			snake.get(i).setTranslateY(_ypos.get(i - 1));
		}
		
//		get new translation
		for(int i = 0; i < snake.size(); i++) {
			_xpos.set(i, snake.get(i).getTranslateX());
			_ypos.set(i, snake.get(i).getTranslateY());
		}
	}
	
//	public void shift() {
////		shifting body positions
//		for(int i = 1; i < snake.size(); i++) {
//			snake.get(i).setTranslateX(_xpos.get(i - 1));
//			snake.get(i).setTranslateY(_ypos.get(i - 1));
//		}
//		
////		get new translation
//		for(int i = 0; i < snake.size(); i++) {
//			_xpos.set(i, snake.get(i).getTranslateX());
//			_ypos.set(i, snake.get(i).getTranslateY());
//		}
//	}
	
	public void grow() {
		_bodyCount++;
		snake.add(new Body("Body#" + _bodyCount, _bodyCount));
		double lastxpos = 0;
		double lastypos = 0;
		
		int last = snake.size() - 1;
		
		if(_xdir > 0) {
			lastxpos = snake.get(last - 1).getTranslateX() - 25;
		} else if(_xdir < 0){
			lastxpos = snake.get(last - 1).getTranslateX() + 25;
		} else {
			lastxpos = snake.get(last - 1).getTranslateX();
		}
		
		if(_ydir > 0) {
			lastypos = snake.get(last - 1).getTranslateY() - 25;
		} else if(_ydir < 0){
			lastypos = snake.get(last - 1).getTranslateY() - 25;
		} else {
			lastypos = snake.get(last - 1).getTranslateY();
		}
		
		snake.get(last).setTranslateX(lastxpos);
		snake.get(last).setTranslateY(lastypos);
		snake.get(last).show();
		
//		System.out.println(snake.get(last).getTranslateX());
//		System.out.println(snake.get(last).getTranslateY());
		
		_xpos.add(snake.get(last).getTranslateX());
		_ypos.add(snake.get(last).getTranslateY());
		
		getChildren().removeAll(snake);
		getChildren().addAll(snake);
//		System.out.println(_lastxpos);
//		System.out.println(_lastypos);
		
		
	}
	public double getHeadxPos() {
		return headx;
	}
	public double getHeadyPos() {
		return heady;
	}
	
	public void restart() {
		getChildren().addAll(snake);
	}
	public void endGame() {
		getChildren().removeAll(snake);
		Main.setHighscore();
		Main.reset();
		headx = 0;
		heady = 0;
		_bodyCount = 0;
		snake.clear();
		_xpos.clear();
		_ypos.clear();
		
		b = new Button("retry");
		b.setMinSize(100, 100);
		b.relocate(150, 150);
		b.setOnAction(e -> {
			getChildren().remove(b);
			
			snake.add(new Body("Body#" + _bodyCount, _bodyCount));
			snake.get(0).show();
			_xpos.add(snake.get(0).getTranslateX());
			_ypos.add(snake.get(0).getTranslateY());
			
			restart();
		});
		getChildren().add(b);
	}
	
	
	@Override
	public String toString() {
		return "Snake";
	}
	
}

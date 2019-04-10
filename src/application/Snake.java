package application;

import java.util.ArrayList;

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
	private static int _lives = 3;
	private static double _xdir, _ydir;
	private int _bodyCount;
//	private double _lastxpos = -75, _lastypos;
	double _bwidth, _bheight;
	double headx, heady;
	
	ArrayList<Double> xpos = new ArrayList<>();
	ArrayList<Double> ypos = new ArrayList<>();
	
	
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
		xpos.add(snake.get(0).getTranslateX());
		ypos.add(snake.get(0).getTranslateY());
		
		
		
		System.out.println("headx " + snake.get(0).getTranslateX());
		System.out.println("heady " + snake.get(0).getTranslateY());
		
//		System.out.println(snake.get(i)._name);
	}
	
	@Override
	public void show() {
		getChildren().addAll(snake);
	}
	
	public void checkBounds() {
		if(snake.get(0).getTranslateX() < 0 || snake.get(0).getTranslateX() > _bwidth ||
				snake.get(0).getTranslateY() < 0 || snake.get(0).getTranslateY() > _bheight) {
			endGame();
		}
	}
	
	public void setDirection(double xdir, double ydir) {
		_xdir = xdir;
		_ydir = ydir;
		update();
	}
	
	public void update() {
		xpos.set(0, snake.get(0).getTranslateX());
		ypos.set(0, snake.get(0).getTranslateY());
		
		snake.get(0).setTranslateX(snake.get(0).getTranslateX() + _xdir);
		snake.get(0).setTranslateY(snake.get(0).getTranslateY() + _ydir);
		
		headx = snake.get(0).getTranslateX() + _xdir;
		heady = snake.get(0).getTranslateY() + _ydir;
		
		System.out.println("headx " + headx);
		System.out.println("heady " + heady);
		System.out.println();
		
		for(int i = 1; i < snake.size(); i++) {
			snake.get(i).setTranslateX(xpos.get(i - 1));
			snake.get(i).setTranslateY(ypos.get(i - 1));
				
		}
		for(int i = 0; i < snake.size(); i++) {
			xpos.set(i, snake.get(i).getTranslateX());
			ypos.set(i, snake.get(i).getTranslateY());
		}
		
		checkBounds();
		
		
		
		
	}
	
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
		snake.get(last).setPosition(lastxpos, lastypos);
		snake.get(last).show();
		
//		System.out.println(snake.get(last).getTranslateX());
//		System.out.println(snake.get(last).getTranslateY());
		
		xpos.add(snake.get(last).getTranslateX());
		ypos.add(snake.get(last).getTranslateY());
		
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
		_lives = 3;
		getChildren().addAll(snake);
	}
	public void endGame() {
		getChildren().removeAll(snake);
	}
	public void setLives(int lives) {
		_lives = lives;
	}
	public int getLives() {
		return _lives;
	}
	
	@Override
	public String toString() {
		return "Snake";
	}
	
}

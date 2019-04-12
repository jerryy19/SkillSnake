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
	private ArrayList<Double> xpos = new ArrayList<>();
	private ArrayList<Double> ypos = new ArrayList<>();
	
	private static double xdir, ydir;
	private int bodyCount;
	
	private double bwidth, bheight;
	private double headx, heady;
	
	Button b;
	
	
	Snake(double x, double y) {
		
		bwidth = x;
		bheight = y;

		draw();
	}
	
	@Override
	public void draw() {
//		snake.get(i).setFill(Color.MEDIUMVIOLETRED);
//		snake.get(i).setStroke(Color.DARKMAGENTA);
		
		snake.add(new Body("Body#" + bodyCount, bodyCount));
		snake.get(0).show();
		xpos.add(snake.get(0).getTranslateX());
		ypos.add(snake.get(0).getTranslateY());
		
//		System.out.println(snake.get(i).name);
	}
	
	@Override
	public void show() {
		getChildren().addAll(snake);
	}
	
	public void checkBounds() {
		if(snake.get(0).getTranslateX() < 0 || snake.get(0).getTranslateX() > bwidth - 25||
				snake.get(0).getTranslateY() < 0 || snake.get(0).getTranslateY() > bheight - 25) {
//			endGame();
			if(Main.getLives() > 0) {
				retry();				
			} else {
				endGame();
			}
		}
	}
	
	public void retry() {
		Main.updateLives();
		
		shift(0, 0);
	}
	
	public void setDirection(double xdir, double ydir) {
		this.xdir = xdir;
		this.ydir = ydir;
		update();
	}
	
	public void update() {
//		get positions before updating them
		xpos.set(0, snake.get(0).getTranslateX());
		ypos.set(0, snake.get(0).getTranslateY());
		
		shift(snake.get(0).getTranslateX() + xdir, snake.get(0).getTranslateY() + ydir);
		
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
			snake.get(i).setTranslateX(xpos.get(i - 1));
			snake.get(i).setTranslateY(ypos.get(i - 1));
		}
		
//		get new translation
		for(int i = 0; i < snake.size(); i++) {
			xpos.set(i, snake.get(i).getTranslateX());
			ypos.set(i, snake.get(i).getTranslateY());
		}
	}
	
	public void grow() {
		bodyCount++;
		snake.add(new Body("Body#" + bodyCount, bodyCount));
		double lastxpos = 0;
		double lastypos = 0;
		
		int last = snake.size() - 1;
		
		if(xdir > 0) {
			lastxpos = snake.get(last - 1).getTranslateX() - 25;
		} else if(xdir < 0){
			lastxpos = snake.get(last - 1).getTranslateX() + 25;
		} else {
			lastxpos = snake.get(last - 1).getTranslateX();
		}
		
		if(ydir > 0) {
			lastypos = snake.get(last - 1).getTranslateY() - 25;
		} else if(ydir < 0){
			lastypos = snake.get(last - 1).getTranslateY() - 25;
		} else {
			lastypos = snake.get(last - 1).getTranslateY();
		}
		
		snake.get(last).setTranslateX(lastxpos);
		snake.get(last).setTranslateY(lastypos);
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
		getChildren().addAll(snake);
	}
	public void endGame() {
		getChildren().removeAll(snake);
		Main.updateHighscore();
		Main.reset();
		headx = 0;
		heady = 0;
		bodyCount = 0;
		snake.clear();
		xpos.clear();
		ypos.clear();
		
		b = new Button("retry");
		b.setMinSize(100, 100);
		b.relocate(150, 150);
		b.setOnAction(e -> {
			getChildren().remove(b);
			
			snake.add(new Body("Body#" + bodyCount, bodyCount));
			snake.get(0).show();
			xpos.add(snake.get(0).getTranslateX());
			ypos.add(snake.get(0).getTranslateY());
			
			restart();
		});
		getChildren().add(b);
	}
	
	
	@Override
	public String toString() {
		return "Snake";
	}
	
}

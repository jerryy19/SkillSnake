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
	
	private ArrayList<Body> snakeBody = new ArrayList<>();
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
		
		snakeBody.add(new Body("Body#" + bodyCount, bodyCount));
		snakeBody.get(0).show();
		xpos.add(snakeBody.get(0).getTranslateX());
		ypos.add(snakeBody.get(0).getTranslateY());
		
//		System.out.println(snake.get(i).name);
	}
	
	@Override
	public void show() {
		getChildren().addAll(snakeBody);
	}
	
	public void checkBounds() {
//		System.out.println(snakeBody.get(0).getTranslateX() < 0 || snakeBody.get(0).getTranslateX() > bwidth - 25||
//				snakeBody.get(0).getTranslateY() < 0 || snakeBody.get(0).getTranslateY() > bheight - 25);
		if(snakeBody.get(0).getTranslateX() < 0 || snakeBody.get(0).getTranslateX() > bwidth - 25||
				snakeBody.get(0).getTranslateY() < 0 || snakeBody.get(0).getTranslateY() > bheight - 25) {
			if(Board.mode.equals("snake20Mode")) {
				if(Main_v1.getLives() > 1) {
					restart();
					Main_v1.updateLives();
				} else {
					endGame();
				}
			} else {
				endGame();
			}
		}
	}
	
	
	public void setDirection(double xdir, double ydir) {
		Snake.xdir = xdir;
		Snake.ydir = ydir;
		update();
	}
	
	public void update() {
		
//		get positions before updating them
		xpos.set(0, snakeBody.get(0).getTranslateX());
		ypos.set(0, snakeBody.get(0).getTranslateY());
		
		shift(snakeBody.get(0).getTranslateX() + xdir, snakeBody.get(0).getTranslateY() + ydir);
		
		System.out.println("headx " + headx);
		System.out.println("heady " + heady);
		System.out.println();
		
		checkBounds();
	}
	
	public void shift(double translateX, double translateY) {
		
		snakeBody.get(0).setTranslateX(translateX);
		snakeBody.get(0).setTranslateY(translateY);
		
		headx = snakeBody.get(0).getTranslateX();
		heady = snakeBody.get(0).getTranslateY();
		
//		shifting body positions
		for(int i = 1; i < snakeBody.size(); i++) {
			snakeBody.get(i).setTranslateX(xpos.get(i - 1));
			snakeBody.get(i).setTranslateY(ypos.get(i - 1));
		}
		
//		get new translation
		for(int i = 0; i < snakeBody.size(); i++) {
			xpos.set(i, snakeBody.get(i).getTranslateX());
			ypos.set(i, snakeBody.get(i).getTranslateY());
		}
	}
	
	public void grow() {
		bodyCount++;
		snakeBody.add(new Body("Body#" + bodyCount, bodyCount));
		double lastxpos = 0;
		double lastypos = 0;
		
		int last = snakeBody.size() - 1;
		
		if(xdir > 0) {
			lastxpos = snakeBody.get(last - 1).getTranslateX() - 25;
		} else if(xdir < 0){
			lastxpos = snakeBody.get(last - 1).getTranslateX() + 25;
		} else {
			lastxpos = snakeBody.get(last - 1).getTranslateX();
		}
		
		if(ydir > 0) {
			lastypos = snakeBody.get(last - 1).getTranslateY() - 25;
		} else if(ydir < 0){
			lastypos = snakeBody.get(last - 1).getTranslateY() - 25;
		} else {
			lastypos = snakeBody.get(last - 1).getTranslateY();
		}
		
		snakeBody.get(last).setTranslateX(lastxpos);
		snakeBody.get(last).setTranslateY(lastypos);
		snakeBody.get(last).show();
		
//		System.out.println(snake.get(last).getTranslateX());
//		System.out.println(snake.get(last).getTranslateY());
		
		xpos.add(snakeBody.get(last).getTranslateX());
		ypos.add(snakeBody.get(last).getTranslateY());
		
		getChildren().removeAll(snakeBody);
		getChildren().addAll(snakeBody);
//		System.out.println(_lastxpos);
//		System.out.println(_lastypos);
		
		
	}
	
	public void shrink() {
		if(snakeBody.size() < 2) {
			Main_v1.updateLives();
			restart();
		}
//		} else {
//			for(int i = 1; i <= 2; i++) {
//				getChildren().removeAll(snakeBody);
//				snakeBody.remove(snakeBody.size() - i);
//				xpos.remove(snakeBody.size() - i);
//				ypos.remove(snakeBody.size() - i);
//				getChildren().addAll(snakeBody);
//			}
//		}
	}
	
	public double getHeadxPos() {
		return headx;
	}
	public double getHeadyPos() {
		return heady;
	}
	
	public void restart() {
		Board.timeline.stop();
		getChildren().removeAll(snakeBody);
		
		headx = 0;
		heady = 0;
		bodyCount = 0;
		snakeBody.clear();
		xpos.clear();
		ypos.clear();
		
		snakeBody.add(new Body("Body#" + bodyCount, bodyCount));
		snakeBody.get(0).show();
		xpos.add(snakeBody.get(0).getTranslateX());
		ypos.add(snakeBody.get(0).getTranslateY());
		
		getChildren().addAll(snakeBody);
	}
	
	public void endGame() {
		getChildren().removeAll(snakeBody);
		Main_v1.updateHighscore();
		Main_v1.reset();
		Board.timeline.stop();
		headx = 0;
		heady = 0;
		bodyCount = 0;
		snakeBody.clear();
		xpos.clear();
		ypos.clear();
		
		b = new Button("retry");
		b.setMinSize(100, 100);
		b.relocate(150, 150);
		b.setOnAction(e -> {
			getChildren().remove(b);
			
			snakeBody.add(new Body("Body#" + bodyCount, bodyCount));
			snakeBody.get(0).show();
			xpos.add(snakeBody.get(0).getTranslateX());
			ypos.add(snakeBody.get(0).getTranslateY());
			
			getChildren().addAll(snakeBody);
		});
		getChildren().add(b);
	}
	
	
	@Override
	public String toString() {
		return "Snake";
	}
	
}

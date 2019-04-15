package application;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

/**
 * @author Jerry Yu
 * Due Date: 4 / 17 / 19
 */

public class Snake extends Pane implements Sprite {
	
	private ArrayList<Body> snakeBody = new ArrayList<>();			// body of snake
	private ArrayList<Double> xpos = new ArrayList<>();				// x position of each snake body
	private ArrayList<Double> ypos = new ArrayList<>();				// y position of each snake body
	
	private static double xdir, ydir;								// direction of snake
	private int bodyCount;											// labeling each body of snake
	
	private double bwidth, bheight;									// board width and height
	private double headx, heady;									// x and y position of the head
	private Button b;												// end game button
	
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
				checkLives();
			} else {
				endGame();
			}
		}
	}
	
	public void checkLives() {
		if(Main.getLives() > 1) {
			Main.updateLives();

			shift(0, 0);
			Board.timeline.stop();
		} else {
			endGame();
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
		
//		appending snake body depending on direction the snake is going
		lastxpos = (xdir > 0) ? snakeBody.get(last - 1).getTranslateX() - 25 : 
			((xdir < 0) ? snakeBody.get(last - 1).getTranslateX() + 25 : snakeBody.get(last - 1).getTranslateX());
		
		lastypos = (ydir > 0) ? snakeBody.get(last - 1).getTranslateY() - 25 : 
			((ydir < 0) ? snakeBody.get(last - 1).getTranslateY() + 25 : snakeBody.get(last - 1).getTranslateY());
		
		
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
		if(snakeBody.size() <= 2) {
			checkLives();
		} else  {
			getChildren().removeAll(snakeBody);
			for(int i = 0; i < 2; i++) {
				snakeBody.remove(snakeBody.size() - 1);
				xpos.remove(snakeBody.size() - 1);
				ypos.remove(snakeBody.size() - 1);
			}
			getChildren().addAll(snakeBody);
		}
		Main.updateScore(-2);
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
		Main.updateHighscore();
		Main.reset();

		restart();
		getChildren().removeAll(snakeBody);
		
		b = new Button("retry");
		b.setMinSize(100, 100);
		b.relocate(bwidth / 2, bheight / 2);
		b.setOnAction(e -> {
			getChildren().remove(b);
			getChildren().addAll(snakeBody);
		});
		getChildren().add(b);
	}
	
	@Override
	public String toString() {
		return "Snake";
	}
	
	public int getSize() {
		return snakeBody.size();
	}
	
}

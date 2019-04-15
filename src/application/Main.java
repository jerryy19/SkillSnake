package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author Jerry Yu
 * Date Due : 4 / 17 / 19
 */


public class Main extends Application implements EventHandler<ActionEvent> {
	
	private Scene scene;
	private HBox container;										// Main Container
	private VBox settingsPanel, gamePanel, switchPanel;			// embedded to Container
	private VBox boardContainer;								// in GamePanel
	private VBox instructionsPanel;								// in GamePanel
	private StackPane scorePanel;								// in GamePanel
	private static int scoreCounter = 0;
	private static int highscoreCounter = 0;
	private static int livesCounter = 3;
	
	private static Label score, highscore, lives;
	private Button easyMode, mediumMode, hardMode, classicMode, snake20Mode, instructions, back;
	private Board boarde, boardm, boardh, current;
	private Image snake;
	private ImageView imgSnake;
	private TextArea ins;
	
	private Button restart;
	
	private boolean instructTF = false;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		initUICtrl();				// initialize ui controls
		initUICtrlEvents();			// initialize ui control events
		initGame();					// initialize game
		
		
		initGUI();					// initialize gui
		
		restart = new Button("restart");
		restart.setOnAction(e -> {
//			primaryStage.close();
			try {
				start(primaryStage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		settingsPanel.getChildren().add(restart);
		
		scene = new Scene(container);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		scene.setOnKeyPressed(current);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Snaaaaake");
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.show();
		
		System.out.printf("Scene Width : %s%n",scene.getWidth());
		System.out.printf("Scene Height : %s%n", scene.getHeight());
		
	}
	
	public void initUICtrl() {
		score = new Label("Score: 0");
		highscore = new Label("Highscore: 0");
		lives = new Label("Lives: 3");
		
		easyMode = new Button("Easy");
		mediumMode = new Button("Medium");
		hardMode = new Button("Hard");
		classicMode = new Button("Classic");
		snake20Mode = new Button("Snake 2.0");
		instructions = new Button("Instructions");
		back = new Button("Back");
		
		snake = new Image(getClass().getResourceAsStream("\\assets\\snake.jpg"));
		imgSnake = new ImageView(snake);
		imgSnake.setFitWidth(100);
		imgSnake.setFitHeight(100);
	}
	
	public void initUICtrlEvents() {
		classicMode.setOnAction(e -> {							// switch from classic to remake(illusion new scene)
			System.out.println("Classic Mode Event pressed");
			settingsPanel.getChildren().remove(instructions);
			switchPanel.getChildren().removeAll(classicMode, imgSnake);
			switchPanel.getChildren().addAll(snake20Mode, imgSnake);
			scorePanel.getChildren().remove(lives);
			
			boarde.setMode("classicMode");
			boardm.setMode("classicMode");
			boardh.setMode("classicMode");
			current.checkMode();
			
		});
		
		snake20Mode.setOnAction(e -> {							// switch from remake to classic(illusion new scene)
			System.out.println("Remake Mode Event pressed");
			settingsPanel.getChildren().add(instructions);
			switchPanel.getChildren().removeAll(snake20Mode, imgSnake);
			switchPanel.getChildren().addAll(classicMode, imgSnake);
			scorePanel.getChildren().add(lives);
			
			boarde.setMode("snake20Mode");
			boardm.setMode("snake20Mode");
			boardh.setMode("snake20Mode");
			current.checkMode();
		});
		
		easyMode.setOnAction(e -> {
			System.out.println("Easy Mode Event pressed");
			if(instructTF) {
				container.getChildren().removeAll(instructionsPanel, switchPanel);
				container.getChildren().addAll(gamePanel, switchPanel);
				instructTF = false;
			}
			boardContainer.getChildren().remove(current);
			boardContainer.getChildren().add(boarde);
			current = boarde;
			scene.setOnKeyPressed(current);
			current.checkMode();
		});
		
		mediumMode.setOnAction(e -> {
			System.out.println("Medium Mode Event pressed");
			if(instructTF) {
				container.getChildren().removeAll(instructionsPanel, switchPanel);
				container.getChildren().addAll(gamePanel, switchPanel);
				instructTF = false;
			}
			boardContainer.getChildren().remove(current);
			boardContainer.getChildren().add(boardm);
			current = boardm;
			scene.setOnKeyPressed(current);
			current.checkMode();
		});
		
		hardMode.setOnAction(e -> {
			System.out.println("Hard Mode Event pressed");
			if(instructTF) {
				container.getChildren().removeAll(instructionsPanel, switchPanel);
				container.getChildren().addAll(gamePanel, switchPanel);
				instructTF = false;
			}
			boardContainer.getChildren().remove(current);
			boardContainer.getChildren().add(boardh);
			current = boardh;
			scene.setOnKeyPressed(current);
			current.checkMode();
		});
		
		instructions.setOnAction(e -> {
			System.out.println("Instructions pressed");
			if(instructTF) {
				container.getChildren().removeAll(instructionsPanel, switchPanel);
				container.getChildren().addAll(gamePanel, switchPanel);
				instructTF = false;
			} else {
				container.getChildren().removeAll(gamePanel, switchPanel);
				container.getChildren().addAll(instructionsPanel, switchPanel);
				instructTF = true;
			}
		});
		
		back.setOnAction(e -> {
			System.out.println("back pressed");
			container.getChildren().removeAll(instructionsPanel, switchPanel);
			container.getChildren().addAll(gamePanel, switchPanel);
			instructTF = false;
		});
	}

	public void initGUI() {
		
		settingsPanel = new VBox();
		setPanes(settingsPanel, 100, 0, new Insets(10, 0, 10, 10), "cyan");
		settingsPanel.setSpacing(15);
		settingsPanel.getChildren().addAll(easyMode, mediumMode, hardMode, instructions);
		
		gamePanel = new VBox();
		setPanes(gamePanel, 420, 440, new Insets(10, 10, 10, 10), "red");
		gamePanel.setAlignment(Pos.CENTER);
		gamePanel.setSpacing(15);
		gamePanel.getChildren().addAll(scorePanel, boardContainer);
		
		switchPanel = new VBox();
		setPanes(switchPanel, 100, 0, new Insets(10, 10, 10, 10), "cyan");
		switchPanel.setSpacing(15);
		switchPanel.getChildren().addAll(classicMode, imgSnake);
		
		container = new HBox();
		setPanes(container, 0, 0, new Insets(10, 10, 10, 10), "darkgray");
		container.setSpacing(15);
		container.getChildren().addAll(settingsPanel, gamePanel, switchPanel);
		
	}

	public void setPanes(Pane p, double width, double height, Insets i, String color) {
		p.setMinSize(width, height);
		p.setPadding(i);
		p.setStyle(" -fx-border-color: " + color);
	}
	
	public void initGame() {
		boarde = new Board(400, 400, "boarde");
		boardm = new Board(300, 300, "boardm");
		boardh = new Board(200, 200, "boardh");
		
		boarde.show();
		boardm.show();
		boardh.show();
		
		current = boarde;
		
		scorePanel = new StackPane();
		setPanes(scorePanel, 400, 25, new Insets(0, 10, 0, 10), "green");
		scorePanel.setMaxSize(400, 25);
		StackPane.setAlignment(score, Pos.CENTER_LEFT);
		StackPane.setAlignment(highscore, Pos.CENTER);
		StackPane.setAlignment(lives, Pos.CENTER_RIGHT);
		scorePanel.getChildren().addAll(score, highscore, lives);
		
		
		boardContainer= new VBox();
		setPanes(boardContainer, 402, 402, new Insets(0, 0, 0, 0), "blue");	// transparent
		boardContainer.setAlignment(Pos.CENTER);
		boardContainer.getChildren().addAll(current);
		
		
		instructionsPanel = new VBox();
		setPanes(instructionsPanel, 420, 440, new Insets(10, 10, 10, 10), "purple");
		instructionsPanel.setAlignment(Pos.CENTER);
		instructionsPanel.setSpacing(15);
		
		
		
		ins = new TextArea();
					//"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
		ins.setText  ("The game is pretty simple. Use the arrow keys or WASD\n" + 
					"to move the snake get as many points as possible.\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n" + 
					"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n");
		
		ins.setPrefSize(402, 400);
		ins.setEditable(false);
		ins.setStyle("-fx-font: 15 times;");
		
//		Label ins = new Label("  aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa\n");
//		ins.setTextFill(Color.PURPLE);
//		ins.setFont(Font.font(15));
		instructionsPanel.getChildren().addAll(back, ins);
		
	}
	
	public static void updateScore(int inc) {
		score.setText("Score: " + (scoreCounter += inc));
	}
	
	public static void updateHighscore() {
		highscoreCounter = (highscoreCounter > scoreCounter) ? highscoreCounter : scoreCounter;
		highscore.setText("High Score: " + highscoreCounter);
	}
	
	public static void updateLives() {
		lives.setText("Lives : " + --livesCounter);
	}
	
	public static void reset() {
		scoreCounter = 0;
		livesCounter = 3;
		lives.setText("Lives : " + livesCounter);
		score.setText("Score: " + scoreCounter);
	}
	
	public static int getLives() {
		return livesCounter;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

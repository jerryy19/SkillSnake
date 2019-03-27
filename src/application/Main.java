package application;

import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Main extends Application implements EventHandler<ActionEvent> {
	Scene s1;
	HBox hb_1;
	VBox left_1, main_1, right_1;			// embedded to hb_1
	StackPane scorePanelContainer;			// in main_1
	int scoreCounter, highscoreCounter, livesCounter = 3;
	
	Label score, highscore, lives;
	Button easyMode, mediumMode, hardMode, classicMode, reSnakeMode, instructions;
	Rectangle boarde, boardm, boardh, current, scorePanel;
	
	Random r = new Random();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		hb_1 = new HBox();					// container for whole code
		left_1 = new VBox();				// left side
		left_1.setMinWidth(100);
		main_1 = new VBox();				// middle
		main_1.setMinSize(420, 420);
		right_1 = new VBox();				// right side
		right_1.setMinWidth(100);
		scorePanelContainer = new StackPane();	// score panel
		

//		---------------------------------------------------------------------
		
		score = new Label("Score: " + scoreCounter);
		highscore = new Label("Highscore: " + highscoreCounter);
		lives = new Label("Lives: " + livesCounter);
		
		easyMode = new Button("easy");
		mediumMode = new Button("medium");
		hardMode = new Button("hard");
		classicMode = new Button("classic");
		reSnakeMode = new Button("re : Snake");
		instructions = new Button("Instructions");
		
		classicMode.setOnAction(e -> {							// switch from classic to remake
			left_1.getChildren().remove(instructions);
			right_1.getChildren().remove(classicMode);
			right_1.getChildren().add(0, reSnakeMode);
		});
		
		reSnakeMode.setOnAction(e -> {							// switch from remake to classic
			left_1.getChildren().add(instructions);
			right_1.getChildren().remove(reSnakeMode);
			right_1.getChildren().add(0, classicMode);
		});
		
//		BOARD
//		---------------------------------------------------------------------	
		
		boarde = new Rectangle(400, 400);		// easy board
		boarde.setFill(Color.WHITE);
		boarde.setStroke(Color.BLACK);
		
		boardm = new Rectangle(300, 300);		// medium bard
		boardm.setFill(Color.WHITE);
		boardm.setStroke(Color.BLACK);
		
		boardh = new Rectangle(200, 200);
		boardh.setFill(Color.WHITE);
		boardh.setStroke(Color.BLACK);
		
		current = boarde;
		
		scorePanel = new Rectangle(400, 25);	// hard board
		scorePanel.setFill(Color.WHITE);
		scorePanel.setStroke(Color.BLACK);
		
//		BOARD SETTINGS(Change boards)
//		---------------------------------------------------------------------
		
		easyMode.setOnAction(e -> {
			main_1.getChildren().remove(current);
			main_1.getChildren().add(boarde);
			current = boarde;
		});
		
		mediumMode.setOnAction(e -> {
			main_1.getChildren().removeAll(current);
			main_1.getChildren().add(boardm);
			current = boardm;
		});
		
		hardMode.setOnAction(e -> {
			main_1.getChildren().removeAll(current);
			main_1.getChildren().add(boardh);
			current = boardh;
		});
		
		
//		NODE SETTINGS
//		---------------------------------------------------------------------
		
		left_1.setPadding(new Insets(10, 10, 10, 10));
		left_1.setSpacing(15);
		left_1.setStyle(" -fx-border-color: cyan;");
		left_1.getChildren().addAll(easyMode, mediumMode, hardMode, instructions);
		
		main_1.setPadding(new Insets(10, 10, 10, 10));
		main_1.setSpacing(10);
		main_1.setAlignment(Pos.BASELINE_CENTER);
		main_1.setStyle(" -fx-border-color: red;");
		main_1.getChildren().addAll(scorePanelContainer, current);
		
		right_1.setPadding(new Insets(10, 10, 10, 10));
		right_1.setSpacing(15);
		right_1.setStyle(" -fx-border-color: cyan;");
		right_1.getChildren().addAll(classicMode);
		
		score.setPadding(new Insets(0, 0, 0, 10));
		lives.setPadding(new Insets(0, 20, 0, 0));
		lives.setAlignment(Pos.BASELINE_RIGHT);
		StackPane.setAlignment(score, Pos.CENTER_LEFT);
//		StackPane.setAlignment(highscore, Pos.CENTER);
		StackPane.setAlignment(lives, Pos.CENTER_RIGHT);
		scorePanelContainer.getChildren().addAll(scorePanel, score, highscore, lives);
		
		hb_1.setPadding(new Insets(10, 0, 10, 10));
		hb_1.setSpacing(10);
//		hb_1.setStyle("-fx-background-color: darkgray");
		hb_1.getChildren().addAll(left_1, main_1, right_1);
		

//		test score panel(temporary)
//		---------------------------------------------------------------------
//		scorePanel.setStyle(" -fx-border-color: green;");
		Button temps = new Button("score");
		Button temphs = new Button("highscore");
		Button templ = new Button("lives");
		
		temps.setOnAction(e -> {
			scoreCounter++;
			score.setText("Score: " + scoreCounter);
		});
		temphs.setOnAction(e -> {
			highscoreCounter++;
			highscore.setText("Highscore: " + highscoreCounter);
		});
		templ.setOnAction(e -> {
			if(livesCounter != 0) {
				livesCounter--;
			}
			
			if(livesCounter == 0) {
				System.out.println("game over");
			}
			
			lives.setText("Lives: " + livesCounter);
		});
		right_1.getChildren().addAll(temps, temphs, templ);
//		---------------------------------------------------------------------


		s1 = new Scene(hb_1);
		primaryStage.setScene(s1);
		
//		disable full screen
		primaryStage.setResizable(false);
		primaryStage.setTitle("snaaaake");
		primaryStage.show();
		System.out.println(s1.getWidth());
		System.out.println(s1.getHeight());

	}
	

	@Override
	public void handle(ActionEvent arg0) {
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}

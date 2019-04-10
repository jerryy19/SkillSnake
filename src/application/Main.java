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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 
 * 
 * 
 */


public class Main extends Application implements EventHandler<ActionEvent> {
	
	private Scene _scene;
	private HBox _container;										// Main Container
	private VBox _settingsPanel, _gamePanel, _switchPanel;			// embedded to Container
	private VBox _boardContainer;									// in GamePanel
	private VBox _instructionsPanel;								// in GamePanel
	private StackPane _scorePanel;							// in GamePanel
	private static int _scoreCounter = 0;
	private static int _highscoreCounter = 0;
	private static int _livesCounter = 3;
	
	private static Label _score, _highscore, _lives;
	private Button _easyMode, _mediumMode, _hardMode, _classicMode, _reSnakeMode, _instructions, _back;
	private Board _boarde, _boardm, _boardh, _current;
	
	private boolean instructTF = false;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		initUICtrl();				// initialize ui controls
		initUICtrlEvents();			// initialize ui control events
		initGame();					// initialize game
		
		
		initGUI();					// initialize gui
		
		_scene = new Scene(_container);
		_scene.setOnKeyPressed(_current);
		
		primaryStage.setScene(_scene);
		primaryStage.setTitle("Snaaaaake");
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.show();
		
		System.out.printf("Scene Width : %s%n",_scene.getWidth());
		System.out.printf("Scene Height : %s%n", _scene.getHeight());
		
	}
	
	public void initUICtrl() {
		_score = new Label("Score: " + _scoreCounter);
		_highscore = new Label("Highscore: " + _highscoreCounter);
		_lives = new Label("Lives: " + _livesCounter);
		
		_easyMode = new Button("Easy");
		_mediumMode = new Button("Medium");
		_hardMode = new Button("Hard");
		_classicMode = new Button("Classic");
		_reSnakeMode = new Button("Snake 2.0");
		_instructions = new Button("Instructions");
		_back = new Button("Back");
	}
	
	public void initUICtrlEvents() {
		_classicMode.setOnAction(e -> {							// switch from classic to remake(illusion new scene)
			System.out.println("Classic Mode Event pressed");
			_settingsPanel.getChildren().remove(_instructions);
			_switchPanel.getChildren().remove(_classicMode);
			_switchPanel.getChildren().add(_reSnakeMode);
		});
		
		_reSnakeMode.setOnAction(e -> {							// switch from remake to classic(illusion new scene)
			System.out.println("Remake Mode Event pressed");
			_settingsPanel.getChildren().add(_instructions);
			_switchPanel.getChildren().remove(_reSnakeMode);
			_switchPanel.getChildren().add(_classicMode);
		});
		
		_easyMode.setOnAction(e -> {
			System.out.println("Easy Mode Event pressed");
			if(instructTF) {
				instructTF = false;
				_gamePanel.getChildren().addAll(_scorePanel, _boardContainer);
				_gamePanel.getChildren().remove(_instructionsPanel);
			}
			_boardContainer.getChildren().remove(_current);
			_boardContainer.getChildren().add(_boarde);
			_current = _boarde;
			_scene.setOnKeyPressed(_current);
		});
		
		_mediumMode.setOnAction(e -> {
			System.out.println("Medium Mode Event pressed");
			if(instructTF) {
				_gamePanel.getChildren().addAll(_scorePanel, _boardContainer);
				_gamePanel.getChildren().remove(_instructionsPanel);
				instructTF = false;
			}
			_boardContainer.getChildren().remove(_current);
			_boardContainer.getChildren().add(_boardm);
			_current = _boardm;
			_scene.setOnKeyPressed(_current);
		});
		
		_hardMode.setOnAction(e -> {
			System.out.println("Hard Mode Event pressed");
			if(instructTF) {
				_gamePanel.getChildren().addAll(_scorePanel, _boardContainer);
				_gamePanel.getChildren().remove(_instructionsPanel);
				instructTF = false;
			}
			_boardContainer.getChildren().remove(_current);
			_boardContainer.getChildren().add(_boardh);
			_current = _boardh;
			_scene.setOnKeyPressed(_current);
		});
		
		_instructions.setOnAction(e -> {
			System.out.println("Instructions pressed");
			if(instructTF) {
				_gamePanel.getChildren().addAll(_scorePanel, _boardContainer);
				_gamePanel.getChildren().remove(_instructionsPanel);
				instructTF = false;
			} else {
				_gamePanel.getChildren().removeAll(_scorePanel, _boardContainer);
				_gamePanel.getChildren().add(_instructionsPanel);
				instructTF = true;
			}
			
		});
		
		_back.setOnAction(e -> {
			System.out.println("back pressed");
			instructTF = false;
			_gamePanel.getChildren().addAll(_scorePanel, _boardContainer);
			_gamePanel.getChildren().remove(_instructionsPanel);
		});
	}

	public void initGUI() {
		
		_settingsPanel = new VBox();
		setPanes(_settingsPanel, 100, 0, new Insets(10, 0, 10, 10), "cyan");
		_settingsPanel.setSpacing(15);
		_settingsPanel.getChildren().addAll(_easyMode, _mediumMode, _hardMode, _instructions);
		
		_gamePanel = new VBox();
		setPanes(_gamePanel, 420, 440, new Insets(10, 10, 10, 10), "red");
		_gamePanel.setAlignment(Pos.CENTER);
		_gamePanel.setSpacing(15);
		_gamePanel.getChildren().addAll(_scorePanel, _boardContainer);
//		_gamePanel.getChildren().add(_boardContainer);
		
		_switchPanel = new VBox();
		setPanes(_switchPanel, 100, 0, new Insets(10, 10, 10, 10), "cyan");
		_switchPanel.setSpacing(15);
		_switchPanel.getChildren().addAll(_classicMode);
		
		_container = new HBox();
		setPanes(_container, 0, 0, new Insets(10, 10, 10, 10), "darkgray");
		_container.setSpacing(15);
		_container.getChildren().addAll(_settingsPanel, _gamePanel, _switchPanel);
		
	}

	public void setPanes(Pane p, double width, double height, Insets i, String color) {
		p.setMinSize(width, height);
		p.setPadding(i);
		p.setStyle(" -fx-border-color: " + color);
	}
	
	public void initGame() {
		_boarde = new Board(400, 400, "boarde");
		_boardm = new Board(300, 300, "boardm");
		_boardh = new Board(200, 200, "boardh");
		
		_boarde.show();
		_boardm.show();
		_boardh.show();
		
		_current = _boarde;
		
		_scorePanel = new StackPane();
		setPanes(_scorePanel, 400, 25, new Insets(0, 10, 0, 10), "green");
		_scorePanel.setMaxSize(400, 25);
		StackPane.setAlignment(_score, Pos.CENTER_LEFT);
		StackPane.setAlignment(_highscore, Pos.CENTER);
		StackPane.setAlignment(_lives, Pos.CENTER_RIGHT);
		_scorePanel.getChildren().addAll(_score, _highscore, _lives);
		
		
		_boardContainer= new VBox();
		setPanes(_boardContainer, 402, 402, new Insets(0, 0, 0, 0), "blue");	// transparent
		_boardContainer.setAlignment(Pos.CENTER);
		_boardContainer.getChildren().addAll(_current);
		
		_instructionsPanel = new VBox();
		_instructionsPanel.setSpacing(15);
		setPanes(_instructionsPanel, 402, 442, new Insets(10, 10, 10, 10), "purple");
		_instructionsPanel.setAlignment(Pos.BASELINE_CENTER);
		
		//"  aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa  "
		TextArea ins = new TextArea();
		ins.setText("  aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa");
		ins.setPrefSize(200, 300);
//		ins.setStyle("-fx-background-color: transparent");
//		ins.setStyle("-fx-text-fill: #000; -fx-opacity: 1.0; ");
//		ins.setEditable(false);
		ins.setStyle("-fx-font: 15 times;");
		
//		Label ins = new Label("  aaaaaaaaaa aaaaaaaaaa aaaaaaaaaa\n");
//		ins.setTextFill(Color.PURPLE);
//		ins.setFont(Font.font(15));
		_instructionsPanel.getChildren().addAll(_back, ins);
		
	}
	public static void setScore() {
		_score.setText("Score: " + ++_scoreCounter);
	}
	
	@Override
	public void handle(ActionEvent event) {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}

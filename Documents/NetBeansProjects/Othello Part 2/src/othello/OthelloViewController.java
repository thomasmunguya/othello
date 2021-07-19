package othello;

import javafx.scene.layout.*;
import java.util.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * This contains the GUI
 */
public class OthelloViewController extends FlowPane {
    
    private OthelloModel othelloModel;
    public static final int BOARD_SIZE = 8;
    
    private BorderPane[][] board = new BorderPane[BOARD_SIZE][BOARD_SIZE];
    private GridPane boardGridPane;
    
    //An array to keep track of the current position of the cursor
    private final int[] cursorPosition = {0, 0};
    
    //Buttons
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private Button moveButton;
    private Button submitButton;
    
    //Text fields and text areas
    private TextField submitTextField;
    private TextArea gameChatTextArea;
    
    //show valid moves checkbox
    private CheckBox showValidMovesCheckBox;
    
    //Score designation labels
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    
    //color variables to keep track of current board colors
    private String boardColor1 = "BLACK";
    private String boardColor2 = "WHITE";
    
    //variable to keep track of the current player
    private int currentPlayer = 1;
    
    //variable to keep track of the current mode
    private int mode = 0;
    
    //Menu items
    private MenuItem newGameMenuItem;
    private MenuItem loadMenuItem;
    private MenuItem saveMenuItem;
    private MenuItem exitMenuItem;
    private MenuItem aboutMenuItem;
    private MenuItem canadianMenuItem;
    private MenuItem redBlueMenuItem;
    private MenuItem greyGreenMenuItem;
    private MenuItem purpleYellowMenuItem;
    private Menu boardColoursMenu;
    private RadioMenuItem normalGameMenuItem;
    private RadioMenuItem cornerTestMenuItem;
    private RadioMenuItem sideTestsMenuItem;
    private RadioMenuItem timesOneCaptureTestMenuItem;
    private RadioMenuItem timesTwoCaptureTestMenuItem;
    private RadioMenuItem emptyBoardMenuItem;
    private RadioMenuItem innerSquareTestMenuItem;
    private RadioMenuItem upArrowOrientationTestMenuItem;
    private ToggleGroup gameModesToggleGroup;
    
    /**
     * Instantiates an OthelloViewController
     */
    public OthelloViewController() {
        
        submitTextField = new TextField();
        submitTextField.setPrefHeight(23.0);
        submitTextField.setPrefWidth(975.0);
        submitTextField.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        
        submitButton = new Button("Submit");
        submitButton.setId("submitButton");
        submitButton.setTextFill(Paint.valueOf("RED"));
        submitButton.setStyle("-fx-background-color: black;");
        submitButton.setPrefSize(96.0, 23.0);
        
        FlowPane consoleFlowPane = new FlowPane();
        consoleFlowPane.setPrefHeight(83.0);
        consoleFlowPane.setPrefWidth(975.0 + 96.0);
        consoleFlowPane.setOrientation(Orientation.HORIZONTAL);
        consoleFlowPane.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0, 0.0, 0.0, 0.0))));
        consoleFlowPane.getChildren().addAll(submitTextField, submitButton);
        
        FlowPane mainFlowPane = new FlowPane();
        mainFlowPane.setPrefHeight(753);
        mainFlowPane.setPrefWidth(1075.0);
        mainFlowPane.setStyle("-fx-background-color: rgb(220, 220, 220);");
        mainFlowPane.setOrientation(Orientation.HORIZONTAL);
        mainFlowPane.setRowValignment(VPos.CENTER);
        mainFlowPane.setAlignment(Pos.TOP_LEFT);
        mainFlowPane.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0, 5.0, 5.0, 5.0))));
        mainFlowPane.getChildren().addAll(setUpMainGridPane(), consoleFlowPane);
        
        this.setPrefHeight(783);
        this.setPrefWidth(1075.0);
        this.getChildren().addAll(setUpMenu(), mainFlowPane);
        
        othelloModel = new OthelloModel();
        
        new Controller();
        
        
    }
    
    /**
     * Sets up the main grid pane
     * @return the main grid pane
     */
    private GridPane setUpMainGridPane() {
        
        BorderPane boardAreaBorderPane = new BorderPane();
        boardAreaBorderPane.setPrefHeight(564.0);
        boardAreaBorderPane.setPrefWidth(640);
        
        List<FlowPane> boardLabelsFlowPanes = setUpBoardLabels();
        boardAreaBorderPane.setTop(boardLabelsFlowPanes.get(0));
        boardAreaBorderPane.setBottom(boardLabelsFlowPanes.get(1));
        boardAreaBorderPane.setCenter(setUpBoardGridPane());
        boardAreaBorderPane.setLeft(boardLabelsFlowPanes.get(2));
        boardAreaBorderPane.setRight(boardLabelsFlowPanes.get(3));
        
        showValidMovesCheckBox = new CheckBox("Show Valid Moves");
        showValidMovesCheckBox.setId("showValidMovesCheckbox");
        
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.SOMETIMES);
        columnConstraints1.setMaxWidth(640.0);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(617.0);
        
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setHgrow(Priority.SOMETIMES);
        columnConstraints2.setMaxWidth(525.0);
        columnConstraints2.setMinWidth(10.0);
        columnConstraints2.setPrefWidth(450.0);
        
        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setMaxHeight(218.0);
        rowConstraints1.setMinHeight(0.0);
        rowConstraints1.setPrefHeight(23.0);
        rowConstraints1.setVgrow(Priority.SOMETIMES);
        
        RowConstraints rowConstraints2 = new RowConstraints();
        rowConstraints2.setMaxHeight(624.0);
        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(612.0);
        rowConstraints2.setVgrow(Priority.SOMETIMES);
        
        GridPane mainGridPane = new GridPane();
        mainGridPane.setMaxHeight(Double.NEGATIVE_INFINITY);
        mainGridPane.setMaxWidth(Double.NEGATIVE_INFINITY);
        mainGridPane.setMinHeight(Double.NEGATIVE_INFINITY);
        mainGridPane.setMinWidth(Double.NEGATIVE_INFINITY);
        mainGridPane.setPrefHeight(670.0);
        mainGridPane.setPrefWidth(1067.0);
        mainGridPane.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        mainGridPane.setAlignment(Pos.CENTER);
        mainGridPane.getRowConstraints().addAll(rowConstraints1, rowConstraints2);
        mainGridPane.add(boardAreaBorderPane, 0, 1);
        mainGridPane.add(setUpGameChatAndDetailsFlowPane(), 1, 1);
        mainGridPane.add(showValidMovesCheckBox, 1, 0);
        
        return mainGridPane;
        
    }
    
    /**
     * Sets up the grid pane to contain the board squares
     * @return  the GridPane containing the board squares
     */
    private GridPane setUpBoardGridPane() {
       
        boardGridPane = new GridPane();
        boardGridPane.setPrefHeight(460.0); 
        boardGridPane.setPrefWidth(483.0);
        boardGridPane.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        for(int i = 0; i < 8; i++) {
            boardGridPane.getColumnConstraints().add(new ColumnConstraints(60.0));
            boardGridPane.getRowConstraints().add(new RowConstraints(60.0));
        }
        
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++) {
                
                BorderPane boardSquare = new BorderPane();
                boardSquare.setPrefSize(60.0, 60.0);
                boardSquare.setId(i + "" + j);
                
                
                if((i % 2 == 0) == (j % 2 == 0)) {
                    boardSquare.setStyle("-fx-background-color: " + boardColor2 + ";");
                }
                else {
                    boardSquare.setStyle("-fx-background-color: " + boardColor1 + ";");
                }
                   
                boardGridPane.add(boardSquare, j, i);
                board[i][j] = boardSquare;
                
                
            }
        }
        return boardGridPane;
    }
    
    /**
     * Sets up the labels for the board
     * This method assembles the labels around the board.
     * @return the list of flow panes containing the top, right, bottom and left board labels
     */
    private List<FlowPane> setUpBoardLabels() {
        
        //set up the top board labels
        Label top1Label = new Label("1");
        top1Label.setFont(new Font(20.0));
        
        Label top2Label = new Label("2");
        top2Label.setFont(new Font(20.0));
        
        Label top3Label = new Label("3");
        top3Label.setFont(new Font(20.0));
        
        Label top4Label = new Label("4");
        top4Label.setFont(new Font(20.0));
        
        Label top5Label = new Label("5");
        top5Label.setFont(new Font(20.0));
        
        Label top6Label = new Label("6");
        top6Label.setFont(new Font(20.0));
        
        Label top7Label = new Label("7");
        top7Label.setFont(new Font(20.0));
        
        Label top8Label = new Label("8");
        top8Label.setFont(new Font(20.0));
        
        //set up the flow pane to contain the top board labels
        FlowPane topBoardLabelsFlowPane = new FlowPane();
        topBoardLabelsFlowPane.setAlignment(Pos.CENTER_LEFT);
        topBoardLabelsFlowPane.setHgap(49.0);
        topBoardLabelsFlowPane.setPrefHeight(70.0);
        topBoardLabelsFlowPane.setPrefWidth(640.0);
        topBoardLabelsFlowPane.setPadding(new Insets(0, 65.0, 0, 92.0));
        topBoardLabelsFlowPane.getChildren().addAll(top1Label, top2Label, top3Label,
                top4Label, top5Label, top6Label, top7Label, top8Label);
        
        //set up bottom board labels
        Label bottom1Label = new Label("1");
        bottom1Label.setFont(new Font(20.0));
        
        Label bottom2Label = new Label("2");
        bottom2Label.setFont(new Font(20.0));
        
        Label bottom3Label = new Label("3");
        bottom3Label.setFont(new Font(20.0));
        
        Label bottom4Label = new Label("4");
        bottom4Label.setFont(new Font(20.0));
        
        Label bottom5Label = new Label("5");
        bottom5Label.setFont(new Font(20.0));
        
        Label bottom6Label = new Label("6");
        bottom6Label.setFont(new Font(20.0));
        
        Label bottom7Label = new Label("7");
        bottom7Label.setFont(new Font(20.0));
        
        Label bottom8Label = new Label("8");
        bottom8Label.setFont(new Font(20.0));
        
        //set up the flow pane to contain the bottom board labels
        FlowPane bottomBoardLabelsFlowPane = new FlowPane();
        bottomBoardLabelsFlowPane.setAlignment(Pos.CENTER_LEFT);
        bottomBoardLabelsFlowPane.setHgap(49.0);
        bottomBoardLabelsFlowPane.setPrefHeight(70.0);
        bottomBoardLabelsFlowPane.setPrefWidth(640.0);
        bottomBoardLabelsFlowPane.setPadding(new Insets(0, 65.0, 0, 92.0));
        bottomBoardLabelsFlowPane.getChildren().addAll(bottom1Label, bottom2Label, bottom3Label,
                bottom4Label, bottom5Label, bottom6Label, bottom7Label, bottom8Label);
        
        //set up left board labels
        Label left1Label = new Label("A");
        left1Label.setFont(new Font(20.0));
        
        Label left2Label = new Label("B");
        left2Label.setFont(new Font(20.0));
        
        Label left3Label = new Label("C");
        left3Label.setFont(new Font(20.0));
        
        Label left4Label = new Label("D");
        left4Label.setFont(new Font(20.0));
        
        Label left5Label = new Label("E");
        left5Label.setFont(new Font(20.0));
        
        Label left6Label = new Label("F");
        left6Label.setFont(new Font(20.0));
        
        Label left7Label = new Label("G");
        left7Label.setFont(new Font(20.0));
        
        Label left8Label = new Label("H");
        left8Label.setFont(new Font(20.0));
        
        //set up the flow pane to contain the left board labels
        FlowPane leftBoardLabelsFlowPane = new FlowPane();
        leftBoardLabelsFlowPane.setAlignment(Pos.TOP_CENTER);
        leftBoardLabelsFlowPane.setVgap(30.0);
        leftBoardLabelsFlowPane.setPrefHeight(640.0);
        leftBoardLabelsFlowPane.setPrefWidth(70.0);
        leftBoardLabelsFlowPane.setColumnHalignment(HPos.CENTER);
        leftBoardLabelsFlowPane.setOrientation(Orientation.VERTICAL);
        leftBoardLabelsFlowPane.setPadding(new Insets(25.0, 0.0, 0.0, 0.0));
        leftBoardLabelsFlowPane.getChildren().addAll(left1Label, left2Label, left3Label,
                left4Label, left5Label, left6Label, left7Label, left8Label);
        
        //set up right board labels
        Label right1Label = new Label("A");
        right1Label.setFont(new Font(20.0));
        
        Label right2Label = new Label("B");
        right2Label.setFont(new Font(20.0));
        
        Label right3Label = new Label("C");
        right3Label.setFont(new Font(20.0));
        
        Label right4Label = new Label("D");
        right4Label.setFont(new Font(20.0));
        
        Label right5Label = new Label("E");
        right5Label.setFont(new Font(20.0));
        
        Label right6Label = new Label("F");
        right6Label.setFont(new Font(20.0));
        
        Label right7Label = new Label("G");
        right7Label.setFont(new Font(20.0));
        
        Label right8Label = new Label("H");
        right8Label.setFont(new Font(20.0));
        
        //set up the flow pane to contain the right board labels
        FlowPane rightBoardLabelsFlowPane = new FlowPane();
        rightBoardLabelsFlowPane.setAlignment(Pos.TOP_CENTER);
        rightBoardLabelsFlowPane.setVgap(30.0);
        rightBoardLabelsFlowPane.setPrefHeight(564.0);
        rightBoardLabelsFlowPane.setPrefWidth(70.0);
        rightBoardLabelsFlowPane.setColumnHalignment(HPos.CENTER);
        rightBoardLabelsFlowPane.setOrientation(Orientation.VERTICAL);
        rightBoardLabelsFlowPane.setPadding(new Insets(25.0, 0.0, 0.0, 0.0));
        rightBoardLabelsFlowPane.getChildren().addAll(right1Label, right2Label, right3Label,
                right4Label, right5Label, right6Label, right7Label, right8Label);
        
        //put all the panes containing the board labels together
        List<FlowPane> boardLabelsFlowPanes = new ArrayList<>();
        boardLabelsFlowPanes.add(topBoardLabelsFlowPane);
        boardLabelsFlowPanes.add(bottomBoardLabelsFlowPane);
        boardLabelsFlowPanes.add(leftBoardLabelsFlowPane);
        boardLabelsFlowPanes.add(rightBoardLabelsFlowPane);
        
        return boardLabelsFlowPanes;
    }
    
    /**
     * Assembles the border pane containing the game control buttons (move button and directional buttons)
     * @return the border pane containing the game controls
     */
    private BorderPane setUpGameControlsBorderPane() {
        
        //Assemble control buttons
        Background upButtonBackground = new Background(new BackgroundImage(new Image("images/uparrow.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
        
        upButton = new Button();
        upButton.setId("upButton");
        upButton.setPrefSize(40.0, 40.0);
        upButton.setBackground(upButtonBackground);
        upButton.setMaxSize(40.0, 40.0);
        upButton.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        Background downButtonBackground = new Background(new BackgroundImage(new Image("images/downarrow.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
        
        downButton = new Button();
        downButton.setId("downButton");
        downButton.setPrefSize(40, 40);
        downButton.setBackground(downButtonBackground);
        downButton.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        Background leftButtonBackground = new Background(new BackgroundImage(new Image("images/leftarrow.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
        
        leftButton = new Button();
        leftButton.setId("leftButton");
        leftButton.setPrefSize(40.0, 40.0);
        leftButton.setBackground(leftButtonBackground);
        leftButton.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        Background rightButtonBackground = new Background(new BackgroundImage(new Image("images/rightarrow.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
        
        rightButton = new Button();
        rightButton.setId("rightButton");
        rightButton.setPrefSize(40.0, 40.0);
        rightButton.setBackground(rightButtonBackground);
        rightButton.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        moveButton = new Button("Move");
        moveButton.setId("moveButton");
        moveButton.setFont(new Font(10));
        moveButton.setPrefSize(40.0, 40.0);
        moveButton.setStyle("-fx-background-color: WHITE;");
        moveButton.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, 1.0, 1.0, 1.0))));
        
        //Assemble the border pane that holds the control buttons
        BorderPane gameControlsBorderPane = new BorderPane();
        gameControlsBorderPane.setPrefHeight(145.0);
        gameControlsBorderPane.setPrefWidth(157.0);
        gameControlsBorderPane.setTop(upButton);
        gameControlsBorderPane.setBottom(downButton);
        gameControlsBorderPane.setCenter(moveButton);
        gameControlsBorderPane.setLeft(leftButton);
        gameControlsBorderPane.setRight(rightButton);
        BorderPane.setAlignment(upButton, Pos.CENTER);
        BorderPane.setAlignment(downButton, Pos.CENTER);
        BorderPane.setAlignment(leftButton, Pos.CENTER);
        BorderPane.setAlignment(rightButton, Pos.CENTER);
 
        return gameControlsBorderPane;
    }
    
    /**
     * Assembles the flow pane containing the game details
     * @return the game details flow pane
     */
    private FlowPane setUpGameDetailsFlowPane() {
        
        //set up the labels showing the player pieces
        Label player1Label = new Label("Player 1 Pieces:");
        player1Label.setFont(new Font(15.0));
        
        Label player2Label = new Label("Player 2 Pieces:");
        player2Label.setFont(new Font(15.0));
        
        //Assemble a border pane and put the labels in a border pane
        BorderPane playerLabelsBorderPane = new BorderPane();
        playerLabelsBorderPane.setTop(player1Label);
        playerLabelsBorderPane.setBottom(player2Label);
        playerLabelsBorderPane.setPrefHeight(146);
        playerLabelsBorderPane.setPrefWidth(145);
        BorderPane.setAlignment(player1Label, Pos.CENTER);
        BorderPane.setAlignment(player2Label, Pos.CENTER);
        playerLabelsBorderPane.setPadding(new Insets(25.0, 0.0, 25.0, 0.0));
        
        //set up the player icons
        ImageView player1IconImageView = new ImageView("images/black.png");
        player1IconImageView.setFitHeight(36);
        player1IconImageView.setFitWidth(36);
        
        ImageView player2IconImageView = new ImageView("images/white.png");
        player2IconImageView.setFitHeight(36);
        player2IconImageView.setFitWidth(36);
        
        //set up the score labels
        player1ScoreLabel = new Label("2");
        player1ScoreLabel.setFont(new Font(15.0));
        
        player2ScoreLabel = new Label("2");
        player2ScoreLabel.setFont(new Font(15.0));
        
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setMaxWidth(142.0);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(45.0);
        
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setMaxWidth(101.0);
        columnConstraints2.setMinWidth(0.0);
        columnConstraints2.setPrefWidth(99.0);
        
        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setMaxHeight(75.0);
        rowConstraints1.setMinHeight(0.0);
        rowConstraints1.setPrefHeight(67.0);
        
        RowConstraints rowConstraints2 = new RowConstraints();
        rowConstraints2.setMaxHeight(81.0);
        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(63.0);
        
        GridPane playerIconAndScoreGridPane = new GridPane();
        playerIconAndScoreGridPane.setPrefHeight(145.0);
        playerIconAndScoreGridPane.setPrefWidth(144.0);
        playerIconAndScoreGridPane.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        playerIconAndScoreGridPane.getRowConstraints().addAll(rowConstraints1, rowConstraints2);
        
        playerIconAndScoreGridPane.add(player1IconImageView, 0, 0);
        playerIconAndScoreGridPane.add(player2IconImageView, 0, 1);
        playerIconAndScoreGridPane.add(player1ScoreLabel, 1, 0);
        playerIconAndScoreGridPane.add(player2ScoreLabel, 1, 1);
        playerIconAndScoreGridPane.setAlignment(Pos.CENTER);
        
        FlowPane gameDetailsFlowPane = new FlowPane();
        gameDetailsFlowPane.setPrefHeight(199);
        gameDetailsFlowPane.setPrefWidth(451);
        gameDetailsFlowPane.setPadding(new Insets(20.0, 0.0, 0.0, 5.0));
        gameDetailsFlowPane.getChildren().addAll(setUpGameControlsBorderPane(), playerLabelsBorderPane, playerIconAndScoreGridPane);
        
        return gameDetailsFlowPane;
    }
    
    /**
     * Assembles the flow pane containing the game chat and game details
     * @return the game chat and game details flow pane
     */
    private FlowPane setUpGameChatAndDetailsFlowPane() {
        
        gameChatTextArea = new TextArea();
        gameChatTextArea.setPrefHeight(430.0);
        gameChatTextArea.setPrefWidth(450.0);
        gameChatTextArea.setPadding(new Insets(5.0, 5.0, 5.0, 5.0));
        gameChatTextArea.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0, 0, 5.0, 0))));
        gameChatTextArea.setStyle("-fx-control-inner-background: rgb(175, 175, 255)");
        gameChatTextArea.setEditable(false);
        gameChatTextArea.setWrapText(true);
        
       
        
        FlowPane gameChatAndDetailsFlowPane = new FlowPane();
        gameChatAndDetailsFlowPane.setPrefHeight(621.0);
        gameChatAndDetailsFlowPane.setPrefWidth(455.0);
        gameChatAndDetailsFlowPane.getChildren().addAll(setUpGameDetailsFlowPane(), gameChatTextArea);
        gameChatAndDetailsFlowPane.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0, 5.0, 5.0, 5.0))));
        
        return gameChatAndDetailsFlowPane;
    }
    
    /**
     * Initialises the game cursor to board[0][0]
     */
    public void initializeCursor() {
        board[0][0].setBorder(
                        new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
                                CornerRadii.EMPTY,new BorderWidths(5.0, 5.0, 5.0, 5.0))));
        ((BorderPane)boardGridPane.getChildren().get((0 * BOARD_SIZE) + 0)).setBorder(
                        new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
                                CornerRadii.EMPTY,new BorderWidths(5.0, 5.0, 5.0, 5.0))));
    }
    
    /**
     * Assembles the game menu
     * @return a menu bar with menu items
     */
    private MenuBar setUpMenu() {
        
        //set up the menu items
        newGameMenuItem = new MenuItem("New Game");
        newGameMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl + N"));
        
        loadMenuItem = new MenuItem("Load");
        loadMenuItem.setDisable(true);
        loadMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl + L"));
        
        saveMenuItem = new MenuItem("Save");
        saveMenuItem.setDisable(true);
        saveMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl + S"));
        
        exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl + E"));
        
        Menu fileMenu = new Menu("_File");
        fileMenu.setMnemonicParsing(true);
        fileMenu.getItems().addAll(newGameMenuItem, loadMenuItem, saveMenuItem, exitMenuItem);
        
        canadianMenuItem = new MenuItem("Canadian");
        redBlueMenuItem = new MenuItem("Red & Blue");
        purpleYellowMenuItem = new MenuItem("Purple & Yellow");
        greyGreenMenuItem = new MenuItem("Grey & Green");
        
        boardColoursMenu = new Menu("Board Colours");
        boardColoursMenu.getItems().addAll(canadianMenuItem, redBlueMenuItem, purpleYellowMenuItem, greyGreenMenuItem);
        
        normalGameMenuItem = new RadioMenuItem("Normal Game");
        cornerTestMenuItem = new RadioMenuItem("Corner Test");
        sideTestsMenuItem = new RadioMenuItem("Side Tests");
        timesOneCaptureTestMenuItem = new RadioMenuItem("1x Capture Test");
        timesTwoCaptureTestMenuItem = new RadioMenuItem("2x Capture Test");
        emptyBoardMenuItem = new RadioMenuItem("Empty Board");
        innerSquareTestMenuItem = new RadioMenuItem("Inner Square Test");
        upArrowOrientationTestMenuItem = new RadioMenuItem("Up Arrow Orientation Test");
        
        gameModesToggleGroup = new ToggleGroup();
        normalGameMenuItem.setToggleGroup(gameModesToggleGroup);
        cornerTestMenuItem.setToggleGroup(gameModesToggleGroup);
        sideTestsMenuItem.setToggleGroup(gameModesToggleGroup);
        timesOneCaptureTestMenuItem.setToggleGroup(gameModesToggleGroup);
        timesTwoCaptureTestMenuItem.setToggleGroup(gameModesToggleGroup);
        emptyBoardMenuItem.setToggleGroup(gameModesToggleGroup);
        innerSquareTestMenuItem.setToggleGroup(gameModesToggleGroup);
        upArrowOrientationTestMenuItem.setToggleGroup(gameModesToggleGroup);
        
        Menu debugScenariosMenu = new Menu("Debug Scenarios");
        debugScenariosMenu.getItems().addAll(normalGameMenuItem, cornerTestMenuItem, sideTestsMenuItem,
                timesOneCaptureTestMenuItem, timesTwoCaptureTestMenuItem, emptyBoardMenuItem,
                innerSquareTestMenuItem, upArrowOrientationTestMenuItem);
        
        Menu gameMenu = new Menu("_Game");
        gameMenu.setMnemonicParsing(true);
        gameMenu.getItems().addAll(boardColoursMenu, debugScenariosMenu);
        
        aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl + H"));
        
        Menu helpMenu = new Menu("_Help");
        helpMenu.setMnemonicParsing(true);
        helpMenu.getItems().add(aboutMenuItem);
        
        //Add all the menus to the menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu);
        menuBar.setVisible(true);
        
        return menuBar;
    }
    
    /**
     * Changes the colours of the squares on the board
     * @param firstColor the colour of the first set of squares 
     * @param secondColor the colour of the second set of squares
     */
    public void changeBoardColours(String firstColor, String secondColor) {
        boardGridPane.getChildren().forEach(square -> {
            if(square.getStyle().equals("-fx-background-color: " + boardColor1 + ";")) {
                square.setStyle("-fx-background-color: " + firstColor + ";");
            }
            if(square.getStyle().equals("-fx-background-color: " + boardColor2 + ";")) {
                square.setStyle("-fx-background-color: " + secondColor + ";");
            }
        });
        
    }
    
    /**
     * Shows the "About" dialog box
     */
    public void showAboutDialogBox() {
        Alert aboutDialogBox = new Alert(Alert.AlertType.INFORMATION);
        aboutDialogBox.setTitle("About");
        aboutDialogBox.setHeaderText("About");
        aboutDialogBox.setContentText("Othello Game\nby Arzaan Irani\n\nJuly 2021"); //TODO: Change this to client's name
        aboutDialogBox.show();
    }
    
    /**
     * Shows the valid moves for the player for the piece at board[row][col]
     * @param validMoves valid moves to show
     */
    public void showValidMoves(int[][] validMoves) {
        //if an empty array is passed it means the valid moves should not be shown
        //Remove the checkmarks from the valid moves squares if so
        for(int row = 0; row < BOARD_SIZE; row++) {
            for(int col = 0; col < BOARD_SIZE; col++) {
                if(othelloModel.getBoard()[row][col] == 0) {
                    board[row][col].setCenter(null);
                    ((BorderPane)boardGridPane.getChildren().get((row * BOARD_SIZE) + col)).setCenter(null);
                }
            }
        }
        
        //otherwise, show the valid movies by adding a checkmark to the squares
        //which are valid moves
        for (int[] validMove : validMoves) {
            int validMoveRow = validMove[0];
            int validMoveCol = validMove[1];
            if(board[validMoveRow][validMoveCol].equals(
                    ((BorderPane)boardGridPane.getChildren().get((validMoveRow * BOARD_SIZE) + validMoveCol)))) {
                board[validMoveRow][validMoveCol].setCenter(new ImageView("images/checkmark.png"));
                ((BorderPane)boardGridPane.getChildren().get((validMoveRow * BOARD_SIZE) + validMoveCol))
                        .setCenter(new ImageView("images/checkmark.png"));
            }
        }      
         
    }
    
    /**
     * Updates the state of the board
     * @param newBoardState the new state of the board
     */
    public void updateBoardState(int[][] newBoardState) {
        
        //update the board state with the new state
        for(int row = 0; row < BOARD_SIZE; row++) {
            for(int col = 0; col < BOARD_SIZE; col++) {
                if(newBoardState[row][col] == OthelloModel.BLACK) {
                    board[row][col].setCenter(new ImageView("images/black.png"));
                    ((BorderPane)boardGridPane.getChildren().get((row * BOARD_SIZE) + col))
                            .setCenter(new ImageView("images/black.png"));
                }
                else if(newBoardState[row][col] == OthelloModel.WHITE) {
                    board[row][col].setCenter(new ImageView("images/white.png"));
                    ((BorderPane)boardGridPane.getChildren().get((row * BOARD_SIZE) + col))
                            .setCenter(new ImageView("images/white.png"));
                }
                else {
                    board[row][col].setCenter(null);
                    ((BorderPane)boardGridPane.getChildren().get((row * BOARD_SIZE) + col))
                            .setCenter(null);
                }
            }
        }
  
    }
   
    /**
     * Updates the square selecting cursor to a new position
     * @param row the row index of the square to move the cursor to
     * @param col the col index of the square to move the cursor to
     */
    public void updateCursorPosition(int row, int col) {
        //if the square is not on the board don't move the cursor, do nothing
        if(!isSquareOnBoard(row, col)) {
            return;
        }
        
        //otherwise
        //remove the border from the current square
        board[cursorPosition[0]][cursorPosition[1]].setBorder(Border.EMPTY);
        board[cursorPosition[0]][cursorPosition[1]].setBorder(
                        new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
                                CornerRadii.EMPTY,new BorderWidths(5.0, 5.0, 5.0, 5.0))));
        ((BorderPane)boardGridPane.getChildren().get((cursorPosition[0] * BOARD_SIZE) + cursorPosition[1])).setBorder(Border.EMPTY);
        ((BorderPane)boardGridPane.getChildren().get((row * BOARD_SIZE) + col)).setBorder(
                        new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
                                CornerRadii.EMPTY,new BorderWidths(5.0, 5.0, 5.0, 5.0))));
    }
    
    /**
     * Resets the the cursor to board[0][0]
     */
    public void resetCursor() {
        board[cursorPosition[0]][cursorPosition[1]].setBorder(Border.EMPTY);
        ((BorderPane)boardGridPane.getChildren().get((cursorPosition[0] * BOARD_SIZE) + cursorPosition[1])).setBorder(Border.EMPTY);
        cursorPosition[0] = 0;
        cursorPosition[1] = 0;
        
    }
    
    /**
     * Updates the score for a given player
     * @param chipCount the updated number of chips the player has on the board
     * @param player the player whose score to update
     */
    public void updateScore(int chipCount, int player){
        if(player == 1) {
            player1ScoreLabel.setText(chipCount + "");
        }
        if(player == 2) {
            player2ScoreLabel.setText(chipCount + "");
        }
    }
    
    /**
    * Logs a message to the game chat
    * @param message the message to log
    */
    public void logToGameChat(String message) {
        gameChatTextArea.setText(gameChatTextArea.getText() + message + "\n");
    }
    
    /**
     * Clears the game chat
     */
    public void clearGameChat() {
        gameChatTextArea.clear();
    }
    
    /**
    * Skips a player's turn if they do not have any valid moves
    */
    private void skipTurn() {
        switchMoveButton();
        currentPlayer = (currentPlayer == OthelloModel.BLACK) ? 
                        OthelloModel.WHITE : OthelloModel.BLACK; 
    }
    
    /**
     * Switches the functionality of the move button between skip and move
     * 
     */
    public void switchMoveButton() {
        String buttonText = (moveButton.getText().equals("Move")) ? "Skip" :
                "Move";
        moveButton.setText(buttonText);
    }
    
 
    /**
     * A convenience method for checking if a square is on the board
     * @param row the row index of the square
     * @param col the col index of the square
     */
    private boolean isSquareOnBoard(int row, int col) {
        return (row < 8 && row > -1) && (col < 8 && col > -1);
    }
    
    /**
     * An inner Controller class for controlling the UI
     */
    class Controller {
        public Controller() {
            logActionsPerformed();
            controlCursorPosition();
            startNewGame();
            controlMenus();
        }
        
        /**
         * Logs information when a button is pressed or when the tick box is checked/ unchecked
         */
        public void logActionsPerformed() {
            
            showValidMovesCheckBox.setOnAction((e) -> {
                if(showValidMovesCheckBox.isSelected()) {
                    showValidMoves(getValidMoves());
                }
                else {
                    showValidMoves(new int[0][0]);
                }
                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId());
            });
            
            submitButton.setOnAction((e) -> {
                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId());  
            });
            
            moveButton.setOnAction((e) -> {
                if(moveButton.getText().equals("Skip")) {
                    skipTurn();
                }
                else {
                    attemptMove();
                }
                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId());  
            });
            
        }
        
        /**
         * Gets the list of valid moves for the current player
         * @return the list of valid of moves
         */
        private int[][] getValidMoves() {
            List<int[]> validMoves = new ArrayList<>();
            for(int row = 0; row < BOARD_SIZE; row++) {
                for(int col = 0; col < BOARD_SIZE; col++) {
                    if(othelloModel.canMove(row, col, currentPlayer)) {
                        validMoves.add(new int[]{row, col});
                    }
                }
            }
            int[][] validMovesArray =  new int [validMoves.size()][2];
            for(int i = 0; i < validMoves.size(); i++) {
                validMovesArray[i] = validMoves.get(i);
            }
            
            for(int row = 0; row < validMoves.size(); row++) {
                System.out.println(Arrays.toString(validMoves.get(row)));
            }
            return validMovesArray;
        }
        
        
        
        
        /**
         * Controls the game menus
         */
        public void controlMenus() {
            //control menu items for board colours
            canadianMenuItem.setOnAction((e) -> {
                changeBoardColours("RED", "WHITE");
                boardColor1 = "RED";
                boardColor2 = "WHITE";
            });
            redBlueMenuItem.setOnAction((e) -> {
                changeBoardColours("#2C4FD8", "#E62E2D");
                boardColor1 = "#2C4FD8";
                boardColor2 = "#E62E2D";
            });
            purpleYellowMenuItem.setOnAction((e) -> {
                changeBoardColours("#6C119C", "#F8FC03");
                boardColor1 = "#6C119C";
                boardColor2 = "#F8FC03";
            });
            greyGreenMenuItem.setOnAction((e) -> {
                changeBoardColours("#5A5A5A", "#CCED00");
                boardColor1 = "#5A5A5A";
                boardColor2 = "#CCED00";
            });
            newGameMenuItem.setOnAction((e) -> {
                clearGameChat();
                startNewGame();
            });
            
            //Control the menu items for game modes
            gameModesToggleGroup.selectedToggleProperty().addListener((ov) -> {
                if(gameModesToggleGroup.getSelectedToggle().equals(normalGameMenuItem)) {
                    mode = OthelloModel.NORMAL;
                }
                if(gameModesToggleGroup.getSelectedToggle().equals(cornerTestMenuItem)) {
                    mode = OthelloModel.CORNER_TEST;
                }
                if(gameModesToggleGroup.getSelectedToggle().equals(sideTestsMenuItem)) {
                    mode = OthelloModel.OUTER_TEST;
                }
                if(gameModesToggleGroup.getSelectedToggle().equals(timesOneCaptureTestMenuItem)) {
                    mode = OthelloModel.TEST_CAPTURE;
                }
                if(gameModesToggleGroup.getSelectedToggle().equals(timesTwoCaptureTestMenuItem)) {
                    mode = OthelloModel.TEST_CAPTURE2;
                }
                if(gameModesToggleGroup.getSelectedToggle().equals(emptyBoardMenuItem)) {
                    mode = OthelloModel.UNWINNABLE;
                }
                if(gameModesToggleGroup.getSelectedToggle().equals(innerSquareTestMenuItem)) {
                    mode = OthelloModel.INNER_TEST;
                }
                if(gameModesToggleGroup.getSelectedToggle().equals(upArrowOrientationTestMenuItem)) {
                    mode = OthelloModel.ARROW;
                }
            });
            
            //control the exit menu item
            exitMenuItem.setOnAction((e) -> System.exit(0));
            
            //control the about menu item
            aboutMenuItem.setOnAction((e) -> showAboutDialogBox());
            
        }
        
       /**
        * Starts a new game. The layout will be different according to the mode
        * selected
        */
        public void startNewGame() {
            
            resetCursor(); //reset the position of the cursor
            initializeCursor(); //initialize the cursor
            currentPlayer = OthelloModel.BLACK; //set current player to black
            moveButton.setText("Move");
            
            //set up the board layout according to the game mode selected
            switch(mode) {
                case 0: {
                    othelloModel.prepareBoard(OthelloModel.NORMAL);
                    updateBoardState(othelloModel.getBoard());
                    
                    int player1ChipCount = othelloModel.chipCount(OthelloModel.BLACK);
                    int player2ChipCount = othelloModel.chipCount(OthelloModel.WHITE);
                    
                    updateScore(player1ChipCount, OthelloModel.BLACK);
                    updateScore(player2ChipCount, OthelloModel.WHITE);
                    
                    logToGameChat("Player " + OthelloModel.BLACK + " initialized with " + 
                             player1ChipCount + " piece(s)");
                    logToGameChat("Player " + OthelloModel.WHITE + " initialized with " +
                            player2ChipCount + " piece(s)");
                    
                    if(showValidMovesCheckBox.isSelected()) {
                        showValidMoves(getValidMoves());
                    }
                    
                    break;
                }
                case 1: {
                    othelloModel.prepareBoard(OthelloModel.CORNER_TEST);
                    updateBoardState(othelloModel.getBoard());
                    
                    int player1ChipCount = othelloModel.chipCount(OthelloModel.BLACK);
                    int player2ChipCount = othelloModel.chipCount(OthelloModel.WHITE);
                    
                    updateScore(player1ChipCount, OthelloModel.BLACK);
                    updateScore(player2ChipCount, OthelloModel.WHITE);
                    
                    logToGameChat("Player " + OthelloModel.BLACK + " initialized with " + 
                             player1ChipCount + " piece(s)");
                    logToGameChat("Player " + OthelloModel.WHITE + " initialized with " +
                            player2ChipCount + " piece(s)");
                    
                    if(showValidMovesCheckBox.isSelected()) {
                        showValidMoves(getValidMoves());
                    }
                    
                    declareWinner();
                    
                    break;
                }
                case 2: {
                    othelloModel.prepareBoard(OthelloModel.OUTER_TEST);
                    updateBoardState(othelloModel.getBoard());
                    
                    int player1ChipCount = othelloModel.chipCount(OthelloModel.BLACK);
                    int player2ChipCount = othelloModel.chipCount(OthelloModel.WHITE);
                    
                    updateScore(player1ChipCount, OthelloModel.BLACK);
                    updateScore(player2ChipCount, OthelloModel.WHITE);
                    
                    logToGameChat("Player " + OthelloModel.BLACK + " initialized with " + 
                             player1ChipCount + " piece(s)");
                    logToGameChat("Player " + OthelloModel.WHITE + " initialized with " +
                            player2ChipCount + " piece(s)");
                    
                    if(showValidMovesCheckBox.isSelected()) {
                        showValidMoves(getValidMoves());
                    }
                    
                    declareWinner();
                    break;
                }
                case 3: {
                    othelloModel.prepareBoard(OthelloModel.TEST_CAPTURE);
                    updateBoardState(othelloModel.getBoard());
                    
                    int player1ChipCount = othelloModel.chipCount(OthelloModel.BLACK);
                    int player2ChipCount = othelloModel.chipCount(OthelloModel.WHITE);
                    
                    updateScore(player1ChipCount, OthelloModel.BLACK);
                    updateScore(player2ChipCount, OthelloModel.WHITE);
                    
                    logToGameChat("Player " + OthelloModel.BLACK + " initialized with " + 
                             player1ChipCount + " piece(s)");
                    logToGameChat("Player " + OthelloModel.WHITE + " initialized with " +
                            player2ChipCount + " piece(s)");
                    
                    if(showValidMovesCheckBox.isSelected()) {
                        showValidMoves(getValidMoves());
                    }
                    
                    declareWinner();
                    
                    break;
                }
                case 4: {
                    othelloModel.prepareBoard(OthelloModel.TEST_CAPTURE2);
                    updateBoardState(othelloModel.getBoard());
                    
                    int player1ChipCount = othelloModel.chipCount(OthelloModel.BLACK);
                    int player2ChipCount = othelloModel.chipCount(OthelloModel.WHITE);
                    
                    updateScore(player1ChipCount, OthelloModel.BLACK);
                    updateScore(player2ChipCount, OthelloModel.WHITE);
                    
                    logToGameChat("Player " + OthelloModel.BLACK + " initialized with " + 
                             player1ChipCount + " piece(s)");
                    logToGameChat("Player " + OthelloModel.WHITE + " initialized with " +
                            player2ChipCount + " piece(s)");
                    
                    if(showValidMovesCheckBox.isSelected()) {
                        showValidMoves(getValidMoves());
                    }
                    
                    declareWinner();
                    
                    break;
                }
                case 5: {
                    othelloModel.prepareBoard(OthelloModel.UNWINNABLE);
                    updateBoardState(othelloModel.getBoard());
                    
                    int player1ChipCount = othelloModel.chipCount(OthelloModel.BLACK);
                    int player2ChipCount = othelloModel.chipCount(OthelloModel.WHITE);
                    
                    updateScore(player1ChipCount, OthelloModel.BLACK);
                    updateScore(player2ChipCount, OthelloModel.WHITE);
                    
                    logToGameChat("Player " + OthelloModel.BLACK + " initialized with " + 
                             player1ChipCount + " piece(s)");
                    logToGameChat("Player " + OthelloModel.WHITE + " initialized with " +
                            player2ChipCount + " piece(s)");
                    
                    if(showValidMovesCheckBox.isSelected()) {
                        showValidMoves(getValidMoves());
                    }
                    
                    declareWinner();
                    
                    break;
                }
                case 6: {
                    othelloModel.prepareBoard(OthelloModel.INNER_TEST);
                    updateBoardState(othelloModel.getBoard());
                    
                    int player1ChipCount = othelloModel.chipCount(OthelloModel.BLACK);
                    int player2ChipCount = othelloModel.chipCount(OthelloModel.WHITE);
                    
                    updateScore(player1ChipCount, OthelloModel.BLACK);
                    updateScore(player2ChipCount, OthelloModel.WHITE);
                    
                    logToGameChat("Player " + OthelloModel.BLACK + " initialized with " + 
                             player1ChipCount + " piece(s)");
                    logToGameChat("Player " + OthelloModel.WHITE + " initialized with " +
                            player2ChipCount + " piece(s)");
                    
                    if(showValidMovesCheckBox.isSelected()) {
                        showValidMoves(getValidMoves());
                    }
                    
                    declareWinner();
                    
                    break;
                }
                case 7: {
                    othelloModel.prepareBoard(OthelloModel.ARROW);
                    updateBoardState(othelloModel.getBoard());
                    
                    int player1ChipCount = othelloModel.chipCount(OthelloModel.BLACK);
                    int player2ChipCount = othelloModel.chipCount(OthelloModel.WHITE);
                    
                    updateScore(player1ChipCount, OthelloModel.BLACK);
                    updateScore(player2ChipCount, OthelloModel.WHITE);
                    
                    logToGameChat("Player " + OthelloModel.BLACK + " initialized with " + 
                             player1ChipCount + " piece(s)");
                    logToGameChat("Player " + OthelloModel.WHITE + " initialized with " +
                            player2ChipCount + " piece(s)");
                    
                    if(showValidMovesCheckBox.isSelected()) {
                        showValidMoves(getValidMoves());
                    }
                    
                    declareWinner();
                    
                    break;
                }
                
            }
        }
        
        
        /**
         * Controls the position of the move cursor
         */
        private void controlCursorPosition() {
            upButton.setOnAction((e) -> {
                if(cursorPosition[0] > 0) {
                    updateCursorPosition(cursorPosition[0] - 1, cursorPosition[1]);
                    cursorPosition[0] -= 1;
                }
                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId()); 
            });
            
            downButton.setOnAction((e) -> {
                if(cursorPosition[0] < 7) {
                    updateCursorPosition(cursorPosition[0] + 1, cursorPosition[1]);
                    cursorPosition[0] += 1;
                }
                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId()); 
            });
            
            leftButton.setOnAction((e) -> {
                if(cursorPosition[1] > 0) {
                    updateCursorPosition(cursorPosition[0], cursorPosition[1] - 1);
                    cursorPosition[1] -= 1;
                }
                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId()); 
            });
            
            rightButton.setOnAction((e) -> {
                if(cursorPosition[1] < 7) {
                    updateCursorPosition(cursorPosition[0], cursorPosition[1] + 1);
                    cursorPosition[1] += 1;
                }
                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId()); 
            });
        }
        
        /**
         * Attempts a move at the position of the cursor. If the move is valid, the player's piece will move 
         * to that particular position. If the move is not valid. Nothing happens
         */
        private void attemptMove() {
            
            if(othelloModel.canMove(cursorPosition[0], cursorPosition[1], currentPlayer)) {
                int chipsCaptured = othelloModel.tryMove(cursorPosition[0], cursorPosition[1], currentPlayer);
                updateBoardState(othelloModel.getBoard());
                updateScore(othelloModel.chipCount(OthelloModel.BLACK), OthelloModel.BLACK);
                updateScore(othelloModel.chipCount(OthelloModel.WHITE), OthelloModel.WHITE);
                logToGameChat("Player " + currentPlayer + " has captured " + chipsCaptured + " piece(s)");
                currentPlayer = (currentPlayer == OthelloModel.BLACK) ? OthelloModel.WHITE :
                        OthelloModel.BLACK;
                if(showValidMovesCheckBox.isSelected()) {
                    showValidMoves(new int[0][0]);
                    showValidMoves(getValidMoves());
                }
                
            }
         
            
            //if the game is over, declare the winner
            if(isGameOver()) {
                logToGameChat("GAME OVER.");
                declareWinner();
                logToGameChat("");
                logToGameChat("Select New Game to Play Again.");
                return;
            }
            
            //if the current player has no valid moves, skip their turn
            if(!othelloModel.moveTest(currentPlayer)) {
                logToGameChat("Player " + currentPlayer + " has no valid moves. "
                        + "Press skip.");
                switchMoveButton();
            }
            
        }
        
        /**
         * Checks if the game is over. The game is over when both players have
         * no valid moves left
         * @return {@code true} if the game is over and {@code false} otherwise
         */
        private boolean isGameOver() {
            return !othelloModel.moveTest(OthelloModel.BLACK) &&
                    !othelloModel.moveTest(OthelloModel.WHITE);
        }
        
        /**
        * Declares a winner
        */
        public void declareWinner() {
            int player1Score = othelloModel.chipCount(OthelloModel.BLACK);
            int player2Score = othelloModel.chipCount(OthelloModel.WHITE);
            if(player1Score > player2Score) {
                logToGameChat("Player " + OthelloModel.BLACK + " wins!");
            }
            else if (player1Score < player2Score) {
                logToGameChat("Player " + OthelloModel.WHITE + " wins!");
            }
            else if (player1Score == 0 && player2Score == 0) {
                logToGameChat("GAME OVER");
                logToGameChat("Its a draw!");
            }
        }
        
    }
}
    

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * This class controls the UI 
 */
public class OthelloViewController extends FlowPane {
    
    private final int BOARD_SIZE = 8;
    private final BorderPane[][] BOARD = new BorderPane[BOARD_SIZE][BOARD_SIZE];
    private final int[] cursorPosition = {0, 0};
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private Button moveButton;
    private Button submitButton;
    private TextField submitTextField;
    private TextArea gameChatTextArea;
    private CheckBox showValidMovesCheckBox;
    private Label player1ScoreLabel;
    private Label player2ScoreLabel;
    private GridPane boardGridPane;
    private String boardColor1 = "BLACK";
    private String boardColor2 = "WHITE";
    private MenuItem newGameMenuItem;
    private MenuItem loadMenuItem;
    private MenuItem saveMenuItem;
    private MenuItem exitMenuItem;
    private MenuItem aboutMenuItem;
    private MenuItem canadianMenuItem;
    private MenuItem blueMenuItem;
    private MenuItem yellowMenuItem;
    private MenuItem purpleMenuItem;
    private Menu boardColoursMenu;
    private int currentPlayer = 1;
    
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
        consoleFlowPane.setPrefHeight(23.0);
        consoleFlowPane.setPrefWidth(975.0 + 96.0);
        consoleFlowPane.setOrientation(Orientation.HORIZONTAL);
        consoleFlowPane.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0, 0.0, 0.0, 0.0))));
        consoleFlowPane.getChildren().addAll(submitTextField, submitButton);
        
        
        this.setPrefHeight(750);
        this.setPrefWidth(1075.0);
        this.setStyle("-fx-background-color: rgb(220, 220, 220);");
        this.setOrientation(Orientation.HORIZONTAL);
        this.setRowValignment(VPos.CENTER);
        this.setAlignment(Pos.TOP_LEFT);
        this.setBorder(new Border(new BorderStroke(Color.GREY, 
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5.0, 5.0, 5.0, 5.0))));
        this.getChildren().addAll(setUpMenu(), setUpMainGridPane(), consoleFlowPane);
        new Controller();
        InitializeGame();
        
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
                BOARD[i][j] = boardSquare;
                
                
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
        
        //Create the top board labels
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
        
        //Create bottom board labels
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
        
        //set up the flow pane to contain the left board labels
        FlowPane bottomBoardLabelsFlowPane = new FlowPane();
        bottomBoardLabelsFlowPane.setAlignment(Pos.CENTER_LEFT);
        bottomBoardLabelsFlowPane.setHgap(49.0);
        bottomBoardLabelsFlowPane.setPrefHeight(70.0);
        bottomBoardLabelsFlowPane.setPrefWidth(640.0);
        bottomBoardLabelsFlowPane.setPadding(new Insets(0, 65.0, 0, 92.0));
        bottomBoardLabelsFlowPane.getChildren().addAll(bottom1Label, bottom2Label, bottom3Label,
                bottom4Label, bottom5Label, bottom6Label, bottom7Label, bottom8Label);
        
        //Create left board labels
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
        
        //Create right board labels
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
     * Sets up the border pane for the game control buttons (move button and directional buttons)
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
        player1ScoreLabel = new Label("0");
        player1ScoreLabel.setFont(new Font(15.0));
        
        player2ScoreLabel = new Label("0");
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
     * Initialises the game
     */
    private void InitializeGame() {
        BOARD[0][0].setBorder(
                        new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
                                CornerRadii.EMPTY,new BorderWidths(5.0, 5.0, 5.0, 5.0))));
        ((BorderPane)boardGridPane.getChildren().get((0 * BOARD_SIZE) + 0)).setBorder(
                        new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
                                CornerRadii.EMPTY,new BorderWidths(5.0, 5.0, 5.0, 5.0))));
        
//        //remove all the pieces from the board
//        boardGridPane.getChildren().forEach(square -> ((BorderPane)square).getChildren().clear());
//        
//        //add pieces to the center of the board to initialize the game
//        for(int i = 0; i < BOARD_SIZE; i++) {
//            for(int j = 0; j < BOARD_SIZE; j++) {
//                if((i == 4 - 1 && j == 4 - 1) | (i == 5 - 1 && j == 5 - 1) ) {
//                    boardGridPane.add(new BorderPane(new ImageView("images/white.png"), null, null, null, null), i, j);
//                }
//                if((i == 4 - 1 && j == 5 - 1) | (i == 5 - 1 && j == 4 - 1) ) {
//                    boardGridPane.add(new BorderPane(new ImageView("images/black.png"), null, null, null, null), i, j);
//                }
//            }
//        }
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
        blueMenuItem = new MenuItem("Blue");
        purpleMenuItem = new MenuItem("Purple");
        yellowMenuItem = new MenuItem("Yellow");
        
        boardColoursMenu = new Menu("Board Colours");
        boardColoursMenu.getItems().addAll(canadianMenuItem, blueMenuItem, purpleMenuItem, yellowMenuItem);
        
        MenuItem normalGameMenuItem = new MenuItem("Normal Game");
        MenuItem cornerTestMenuItem = new MenuItem("Corner Test");
        MenuItem sideTestsMenuItem = new MenuItem("Side Tests");
        MenuItem timesOneCaptureTestMenuItem = new MenuItem("1x Capture Test");
        MenuItem timesTwoCaptureTestMenuItem = new MenuItem("2x Capture Test");
        MenuItem emptyBoardMenuItem = new MenuItem("Empty Board");
        MenuItem innerSquareTestMenuItem = new MenuItem("Inner Square Test");
        MenuItem upArrowOrientationTestMenuItem = new MenuItem("Up Arrow Orientation Test");
        
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
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu);
        
        return menuBar;
    }
    
    public void changeBoardColors(String firstColor, String secondColor) {
        boardGridPane.getChildren().forEach(square -> {
            if(square.getStyle().equals("-fx-background-color: BLACK;")) {
                square.setStyle("-fx-background-color: " + firstColor + ";");
            }
            if(square.getStyle().equals("-fx-background-color: WHITE;")) {
                square.setStyle("-fx-background-color: " + secondColor + ";");
            }
        });
        
    }
    
    /**
     * Shows the "about" dialog box
     */
    public void showAboutDialogBox() {
        Alert aboutDialogBox = new Alert(Alert.AlertType.INFORMATION);
        aboutDialogBox.setTitle("About");
        aboutDialogBox.setHeaderText("About");
        aboutDialogBox.setContentText("Othello Game\nby Thomas Munguya\n\nJuly 2021");
        aboutDialogBox.show();
    }
    
    /**
     * Shows the valid moves for the player for the piece at (row, col)
     * @param validMoves valid moves to show
     */
    public void showValidMoves(int[][] validMoves) {
        //if an empty array is passed it means the valid moves should not be shown
        //Remove the borders from the squares if so
        if(validMoves.length == 0) {
            boardGridPane.getChildren().forEach(square -> {
                ((BorderPane) square).setBorder(Border.EMPTY);
            });
        }
        
        //otherwise, show the valid movies by adding a green border to the squares
        //which are valid moves
        for (int[] validMove : validMoves) {
            int validMoveRow = validMove[0];
            int validMoveCol = validMove[1];
            if(BOARD[validMoveRow][validMoveCol].equals(
                    ((BorderPane)boardGridPane.getChildren().get((validMoveRow * BOARD_SIZE) + validMoveCol)))) {
                ((BorderPane)boardGridPane.getChildren().get((validMoveRow * BOARD_SIZE) + validMoveCol)).setBorder(
                        new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                                new BorderWidths(5.0, 5.0, 5.0, 5.0))));
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
                BorderPane square = new BorderPane();
                if(newBoardState[row][col] == OthelloModel.PLAYER_1) {
                    BOARD[row][col].setCenter(new ImageView("images/black.png"));
                    ((BorderPane)boardGridPane.getChildren().get((row * BOARD_SIZE) + col))
                            .setCenter(new ImageView("images/black.png"));
                }
                else if(newBoardState[row][col] == OthelloModel.PLAYER_2) {
                    BOARD[row][col].setCenter(new ImageView("images/white.png"));
                    ((BorderPane)boardGridPane.getChildren().get((row * BOARD_SIZE) + col))
                            .setCenter(new ImageView("images/white.png"));
                }
                else {
                    BOARD[row][col].setCenter(null);
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
        System.out.println(row + ", " + col);
        //if the square is not on the board don't move the cursor, do nothing
        if(!isSquareOnBoard(row, col)) {
            System.out.println("The square is not on the board");
            return;
        }
        //otherwise
        //remove the border from the current square
        BOARD[cursorPosition[0]][cursorPosition[1]].setBorder(Border.EMPTY);
        BOARD[cursorPosition[0]][cursorPosition[1]].setBorder(
                        new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
                                CornerRadii.EMPTY,new BorderWidths(5.0, 5.0, 5.0, 5.0))));
        
        //Update the squares on the grid pane holding the squares
        ((BorderPane)boardGridPane.getChildren().get((cursorPosition[0] * BOARD_SIZE) + cursorPosition[1])).setBorder(Border.EMPTY);
        ((BorderPane)boardGridPane.getChildren().get((row * BOARD_SIZE) + col)).setBorder(
                        new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID,
                                CornerRadii.EMPTY,new BorderWidths(5.0, 5.0, 5.0, 5.0))));
    }
    
    /**
     * Update a score for a given player
     * @param chipsCaptured the chips captured and therefore the scored to add
     * to the current score
     * @param player the player whom to update the score for
     */
    public void updateScore(int chipsCaptured, int player){
        if(player == 1) {
            int currentScore = Integer.parseInt(player2ScoreLabel.getText());
            player1ScoreLabel.setText(currentScore + chipsCaptured + "");
        }
        if(player == 2) {
            int currentScore = Integer.parseInt(player2ScoreLabel.getText());
            player1ScoreLabel.setText(currentScore + chipsCaptured + "");
        }
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
        private final OthelloModel othelloModel = OthelloModel.getInstance();
        public Controller() {
            logActionsPerformed();
            controlMenus();
            controlshowingOfValidMoves();
            controlCursorPosition();
            updateBoardState(othelloModel.getBoard());
        }
        
        /**
         * Logs information when a button is pressed or when the tick box is checked/ unchecked
         */
        public void logActionsPerformed() {
//           upButton.setOnAction((e) -> {
//                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId()); 
//            });
//            
//            downButton.setOnAction((e) -> {
//                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId());  
//            });
//            
//            leftButton.setOnAction((e) -> {
//                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId()); 
//            });
//            
//            rightButton.setOnAction((e) -> {
//                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId());
//            });
            
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
                attemptMove();
                System.out.println("An event was triggered on " + ((Node)e.getSource()).getId());  
            });
            
        }
        
        private int[][] getValidMoves() {
            List<int[]> validMoves = new ArrayList<>();
            for(int row = 0; row < BOARD_SIZE; row++) {
                for(int col = 0; col < BOARD_SIZE; col++) {
                    if(othelloModel.canMove(row, col, currentPlayer)) {
                        validMoves.add(new int[]{row, col});
                    }
                }
            }
            int[][] validMovesArray =  new int [validMoves.size()][0];
            for(int i = 0; i < validMoves.size(); i++) {
                validMovesArray[i] = validMoves.get(i);
            }
            return validMovesArray;
        }
        
        
        
        
        /**
         * Controls the game menus
         */
        public void controlMenus() {
            
            canadianMenuItem.setOnAction((e) -> changeBoardColors("RED", "WHITE"));
            blueMenuItem.setOnAction((e) -> changeBoardColors("#0330fc", "WHITE"));
            purpleMenuItem.setOnAction((e) -> changeBoardColors("#be03fc", "WHITE"));
            yellowMenuItem.setOnAction((e) -> changeBoardColors("#f8fc03", "WHITE"));
        }
        
        /**
         * Skips a player's turn if they do not have any valid moves
         */
        private void skipTurn() {
            logToGameChat("Player " + currentPlayer + "has no valid moves. Press skip.");
            moveButton.setText("Skip");
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
        }
        
        private void play() {
            if(!othelloModel.moveTest(currentPlayer)) {
                skipTurn();
            }
            else {
                moveButton.setText("Move");
            }
        }
        
        /**
         * Changes the colour of the squares on the board to the
         * colours specified by strings
         * @param firstColour the colour to give to give to the first set of squares
         * @param secondColour the colour to give to the second set of squares
         */
        private void changeBoardColours(String firstColour, String secondColour) {
            
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
                
            });
            
            downButton.setOnAction((e) -> {
                if(cursorPosition[0] < 7) {
                    updateCursorPosition(cursorPosition[0] + 1, cursorPosition[1]);
                    cursorPosition[0] += 1;
                }
            });
            
            leftButton.setOnAction((e) -> {
                if(cursorPosition[1] > 0) {
                    updateCursorPosition(cursorPosition[0], cursorPosition[1] - 1);
                    cursorPosition[1] -= 1;
                }
                
            });
            
            rightButton.setOnAction((e) -> {
                if(cursorPosition[1] < 7) {
                    updateCursorPosition(cursorPosition[0], cursorPosition[1] + 1);
                    cursorPosition[1] += 1;
                }
                
            });
        }
        
        /**
         * Attempts a move. If the move is valid, the player's piece will move 
         * to that particular position. If the move is not valid. Nothing happens
         */
        private void attemptMove() {
            int chipsCaptured = othelloModel.tryMove(cursorPosition[0], cursorPosition[1], currentPlayer);
            if(chipsCaptured != 0) {
                updateBoardState(othelloModel.getBoard());
                updateScore(currentPlayer, currentPlayer);
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
                showValidMoves(new int[0][0]);
                showValidMoves(getValidMoves());
            }
        }
        
        
        /**
         * Logs a message to the game chat
         * @param message the message to log
         */
        private void logToGameChat(String message) {
            
        }
        /**
         * Controls showing of valid moves
         */
        public void controlshowingOfValidMoves() {
//            showValidMovesCheckBox.selectedProperty().addListener((ov) -> {
//                if(showValidMovesCheckBox.isSelected()) {
//                    int[][] validMoves = {{0, 0}};
//                    showValidMoves(validMoves);
//                }
//                });
            }
        }
}
    

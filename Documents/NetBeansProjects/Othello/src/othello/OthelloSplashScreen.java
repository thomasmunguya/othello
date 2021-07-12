
package othello;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * A class that models a splash screen
 * The splash screen stays on the screen for a maximum of ten seconds
 */
public class OthelloSplashScreen extends Stage {
    public static final int DURATION = 10000;
    
    public OthelloSplashScreen() {
    }
    
    public void setUpSplashScreen() {
        
        ImageView splashScreenImageView = new ImageView("images/splashscreen.png");
        splashScreenImageView.setFitHeight(191.0);
        splashScreenImageView.setFitWidth(335.0);
        
        Label nameLabel = new Label("Arzaan Irani");
        nameLabel.setFont(new Font(29));
        
        BorderPane mainBorderPane = new BorderPane();
        mainBorderPane.setPrefHeight(301.0);
        mainBorderPane.setPrefWidth(477.0);
        mainBorderPane.setCenter(splashScreenImageView);
        mainBorderPane.setBottom(nameLabel);
        BorderPane.setAlignment(nameLabel, Pos.CENTER);
        
        Scene splashScreenScene = new Scene(mainBorderPane, 477.0, 301.0);
        this.setScene(splashScreenScene);
        this.setResizable(false);
        this.initStyle(StageStyle.UNDECORATED);
        this.show();
        
    }
}

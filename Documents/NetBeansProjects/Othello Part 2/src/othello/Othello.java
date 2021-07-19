package othello;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *The main class that starts the application
 */
public class Othello extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        OthelloSplashScreen splashScreen = new OthelloSplashScreen();
        splashScreen.setUpSplashScreen();
        
        Platform.runLater(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(OthelloSplashScreen.DURATION);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Othello.class.getName()).log(Level.SEVERE, null, ex);
                }
                OthelloViewController othelloViewController = new OthelloViewController();
        
                Scene scene = new Scene(othelloViewController, 1082, 733);
                
                splashScreen.close();
                
                primaryStage.setTitle("Arzaan's Othello Client");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            }
        });
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

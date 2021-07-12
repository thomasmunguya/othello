/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* CST8221-JAP: LAB 02, Application Splash Screen
   File name: SplashScreenDemo.java
*/
import java.awt.*;
import javax.swing.*;
/**
 A simple class demonstrating how to create a splash screen for an application using JWindow as a container.
 The splash screen will appear on the screen for the duration given to the constructor.
 The class includes a main() method for testing purposes, but it should be used as a part of
 an application.
 Note: Since JWindow uses a default frame when made visible it does not receives events.
       If you want to process events using JWindow, you must create an undecorated frame
       and attach JWindow to this frame using an appropriate JWindow constructor.
 @version 1.16.2
*/
public class SplashScreen extends JWindow {
 /* Swing components are serializable and require serialVersionUID */
  private static final long serialVersionUID = 6248477390124803341L;
  private final int duration;
/**
  Default constructor. Sets the show time of the splash screen.
*/
  public SplashScreen(int duration) {
    this.duration = duration;
  }
/**
 Shows a splash screen in the center of the desktop
 for the amount of time given in the constructor.
*/
  public void showSplashWindow() {
   //create content pane
     JPanel content = new JPanel(new BorderLayout());
    
   // or use the window content pane
   //  JPanel content = (JPanel)getContentPane();
     content.setBackground(new Color(180, 0, 0));
    
    // Set the window's bounds, position the window in the center of the screen
    int width =  563+12;
    int height = 853+12;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-width)/2;
    int y = (screen.height-height)/2;
    //set the location and the size of the window
    setBounds(x,y,width,height);

    // create the splash screen
    JLabel label = new JLabel(new ImageIcon("SplashScreen1.jpg"));
    JLabel demo = new JLabel("Mehmet Guzel's App Splash Window Demo", JLabel.CENTER);
    demo.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
    content.add(label, BorderLayout.CENTER);
    content.add(demo, BorderLayout.SOUTH);
    // create custom RGB color
    Color customColor = new Color(0, 0, 0);
    content.setBorder(BorderFactory.createLineBorder(customColor, 12));
    
//   Container contentPane = getContentPane();
//   contentPane.add(content);
//      add(content);
     //replace the window content pane with the content JPanel
      setContentPane(content);

    // Display it
//    pack();
    //make the splash window visible
    setVisible(true);

    // Wait a little while doing nothing, while the application is loading
    try {
    	
    	 Thread.sleep(duration); }
    catch (Exception e) {e.printStackTrace();}
    //destroy the window and release all resources
    dispose(); 
    //hide the splash window, the resources will not be released
    //setVisible(false);
  }

/** 
  The main method. Used for testing purposes.
  @param args the time duration of the splash screen in milliseconds.
              If not specified, the default duration is 10000 msec (10 sec).  
*/
  public static void main(String[] args) {
    int duration = 10000;
    if(args.length == 1){
    	try{
    	 duration = Integer.parseInt(args[0]);
    	}catch (NumberFormatException mfe){
    	  System.out.println("Wrong command line argument: must be an integer number");
    	  System.out.println("The default duration 10000 milliseconds will be used");
    	  //mfe.printStackTrace();	
    	} 
    }
    // Create the screen
    SplashScreen splashWindow = new SplashScreen(duration);
    // Normally,the splash.showSplashWindow() will be called by the main application class.
    splashWindow.showSplashWindow();
    EventQueue.invokeLater(new Runnable(){
        @Override
        public void run(){ 	
         JFrame frame = new JFrame("Application Frame");
         frame.setMinimumSize(new Dimension(350, 350));
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setLocationRelativeTo(null);//screen center
       //frame.setLocationByPlatform(true);
         frame.setVisible(true);  
        }
      });
   }//end main
 }// end SplashScreenDemo class

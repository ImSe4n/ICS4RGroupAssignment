
/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

//imports for FileIO and GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Game
{
    //constance
    
    //instance variables
    private byte gameDifficulty;
    private int highScore;
    //private Deck deck;
    //private HumanPlayer humanPlayer;
    //private AIPlayer aiPlayer
    
    //main window for gui
    private JFrame mainFrame;
    
    //gui screen components
    private JPanel boardPanel;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JLabel movesLabel;
    
    //Constructor
    public Game(){
        //
    }
    
    public void gameStart(){
        mainFrame = new JFrame("Card Memory Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        
        showMenuScreen();
        
        mainFrame.setVisible(true);
    }
    
    private void showMenuScreen(){
        //
    }
    
    private void showGameScreen(){
        //
    }
    
    private void showGameOverScreen(){
        //
    }
    
    private void handleCardClick(){
        //
    }
    
    // public void flipCard(stuff here){
        // //
    // }
       
    // public boolean checkMatch(stuff here){
        // //
    // }
    
    // public boolean checkWin(stuff here){
        // //
    // }
    
    public void switchTurn(){
        //
    }
    
    private void endGame(){
        //
    }
    
    //file io methods
    public void saveHighestScore(){
        //
    }
    
    // public int loadHighScore(){
        // //
    // }
    
    //encapsulation methods
    public byte getGameDifficulty(){
        return this.gameDifficulty;
    }
    
    public int getHighScore(){
        return this.highScore;
    }
}
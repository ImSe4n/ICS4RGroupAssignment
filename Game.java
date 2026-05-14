
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
    //constants
    public final byte EASY = 1;
    public final byte MEDIUM = 2;
    public final byte HARD = 3;
    
    //instance variables
    private byte gameDifficulty;
    private Deck deck;
    // private humanPlayer humanPlayer;
    // private AIplayer aiPlayer;
    
    //main window for gui
    private JFrame mainFrame;
    
    //gui screen components
    private JPanel boardPanel;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JLabel movesLabel;
    
    //Constructor
    public Game(){
        //highScore
        this.gameDifficulty = MEDIUM;
    }
    
    public void gameStart(){
        mainFrame = new JFrame("Card Memory Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(600, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(true);
        
        showMenuScreen();
        
        mainFrame.setVisible(true);
    }
    
    private void showMenuScreen(){
        JPanel gameMenu = new JPanel();
        gameMenu.setLayout(new BoxLayout(gameMenu, BoxLayout.Y_AXIS));
        gameMenu.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        gameMenu.setBackground(new Color(85, 85, 85)); //grey for now
        
        JLabel gameTitle = new JLabel("Card Memory Game");
        gameTitle.setFont(new Font("SansSerif", Font.BOLD, 28)); //can change if wanted
        gameTitle.setForeground(Color.WHITE);
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel highScore = new JLabel("High Score: retrieve from HumanPlayer class later"); //TODO
        highScore.setFont(new Font("SansSerif", Font.PLAIN, 16)); //can change if wanted
        highScore.setForeground(new Color(200, 230, 200)); // can change
        highScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel nameLabel = new JLabel("Your Name: ");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14)); //can change if wanted
        nameLabel.setForeground(Color.WHITE); // can change
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField nameField = new JTextField("Player");
        nameField.setMaximumSize(new Dimension(200, 30)); //can change if wanted
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel difficultyLabel = new JLabel("Difficulty: ");
        difficultyLabel.setFont(new Font("SansSerif", Font.PLAIN, 14)); //can change if wanted
        difficultyLabel.setForeground(Color.WHITE); // can change
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        String[] difficultyOptions = {"1 - Easy", "2 - Medium", "3 - Hard"};
        JComboBox<String> difficultyBox = new JComboBox<>(difficultyOptions);
        difficultyBox.setMaximumSize(new Dimension(220, 30));
        difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton gameStartButton = new JButton("Start Game");
        gameStartButton.setFont(new Font("SansSerif", Font.BOLD, 16)); //can change if wanted
        gameStartButton.setForeground(Color.BLACK);
        gameStartButton.setBackground(new Color(200, 160, 50)); // can change
        gameStartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameStartButton.setFocusPainted(false);
        gameStartButton.addActionListener(e -> {
            String name = nameField.getText(); // can use .trim() if needed
            if (name.isEmpty()){
                name = "Player";
            }
            gameDifficulty = (byte)(difficultyBox.getSelectedIndex() + 1);
            //humanPlayer = new HumanPlayer(name);
            //aiPlayer = new AIPlayer(difficulty);
            setupGame();
            showGameScreen();
        });
        
        gameMenu.add(gameTitle);
        gameMenu.add(Box.createVerticalStrut(8));
        gameMenu.add(highScore);
        gameMenu.add(Box.createVerticalStrut(30));
        gameMenu.add(nameLabel);
        gameMenu.add(Box.createVerticalStrut(6));
        gameMenu.add(nameField);
        gameMenu.add(Box.createVerticalStrut(16));
        gameMenu.add(difficultyLabel);
        gameMenu.add(Box.createVerticalStrut(6));
        gameMenu.add(difficultyBox);
        gameMenu.add(Box.createVerticalStrut(24));
        gameMenu.add(gameStartButton);
        
        
        mainFrame.setContentPane(gameMenu);
        mainFrame.revalidate();
    }
    
    private void showGameScreen(){
        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(50, 120, 50)); //grey for now
        
        JPanel gameStatusBar = new JPanel();
        gameStatusBar.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        gameStatusBar.setBackground(new Color(40, 90, 40)); //grey for now
        
        JLabel gameStatusLabel = new JLabel("Your Turn");
        gameStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        gameStatusLabel.setForeground(Color.WHITE);
        
        // add score label later
        
        //add num moves later
        
        //add the score and num moves to the status bar
        gameStatusBar.add(gameStatusLabel);
        
        JPanel boardPanel = new JPanel();
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        boardPanel.setBackground(new Color(50, 120, 50));
        boardPanel.setLayout(new GridLayout(deck.gridY, deck.gridX, 15, 15));
        
        Card[][] gameGrid = deck.getDeck(); //need a getter from deck class
        for (int row = 0; row < deck.gridY; row++) {
            for (int col = 0; col < deck.gridX; col++) {
                Card card = gameGrid[row][col];
            
                // Add the card's existing JPanel to the grid
                JPanel cardPanel = card.getPanel();
                boardPanel.add(cardPanel);
            
                // Attach the click listener to each card
                // p.addMouseListener(new MouseAdapter() {
                    // @Override
                    // public void mouseClicked(MouseEvent e) {
                        // handleCardClick(card);
                    // }
                // });
            }
        }
        
        
        gamePanel.add(gameStatusBar, BorderLayout.NORTH);
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        
        mainFrame.setContentPane(gamePanel);
        mainFrame.revalidate();
    }
    
    private void showGameOverScreen(){
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
        gameOverPanel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));
        gameOverPanel.setBackground(new Color(85, 85, 85)); //grey for now
        
        JLabel titleLabel = new JLabel("Game Over!");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30)); //can change if wanted
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //JLabel finalScoreLabel = new JLabel("Final Score: " + player.getScore());
        
        //JLabel finalMovesLabel = new JLabel("Total Moves: " + player.getNumMoves);
        
        //JLabel highScoreLabel = new JLabel();
        
        JButton playAgainButton = new JButton();
        playAgainButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        playAgainButton.setForeground(Color.BLACK);
        playAgainButton.setBackground(new Color(200, 160, 50));
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.setFocusPainted(false);
        playAgainButton.addActionListener(e -> {
            showMenuScreen();    
        });
        
        
        gameOverPanel.add(titleLabel);
        gameOverPanel.add(Box.createVerticalStrut(30));
        gameOverPanel.add(playAgainButton);
        
        mainFrame.setContentPane(gameOverPanel);
        mainFrame.revalidate();
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
    
    // public boolean checkWin(){
        // // return numPairsFound == totalPairs;
    // }
    
    public void switchTurn(){
        //
    }
    
    private void setupGame(){
        int deckSize;
        switch (gameDifficulty){
            case EASY:
                deckSize = 12;
            break;
            case HARD:
                deckSize = 30;
            break;
            default:
                deckSize = 16;
            break;
        }
        
        deck = new Deck(deckSize);
        deck.generateCards();
        
        //more stuff later
    }
    public void AI(byte level, Deck deck){
        //create a new AIplayer
        AIPlayer ai = new AIPlayer(level);
        
        //ai choose two cards
        ai.playTurn(deck);
        
    }
    private void endGame(){
        // if (player.getScore() > highScore){
            
        // }
    }
    
    //file io methods
    public void userLogin(){
        //
    }
    
    public void createAccount(){
        //
    }
    
    public void saveHighestScore(){
        //
    }
    
    
    //encapsulation methods
    public byte getGameDifficulty(){
        return this.gameDifficulty;
    }
    
    public void setGameDifficulty(byte g){
        this.gameDifficulty = g;
    }
}
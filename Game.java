
/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//hi
public class Game {
    public final byte EASY = 1;
    public final byte MEDIUM = 2;
    public final byte HARD = 3;

    private byte gameDifficulty;
    private Deck deck;
    private humanPlayer humanPlayer;
    private AIPlayer aiPlayer;
    private int numMoves;
    private int humanPairsFound; // how many pairs the human has matched so far
    private boolean waitingForFlipBack; // true while two unmatched cards are still showing, so the player cannot click
    private int totalPairs; // total number of pairs on the board; the game ends when all of them are found
    private Card firstFlipped; // the first card the player flipped this turn
    private Card secondFlipped; // the second card the player flipped; once set, we check if they match

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JLabel movesLabel;

    public Game() {
        this.gameDifficulty = MEDIUM;
        this.numMoves = 0;
        this.humanPairsFound = 0;
        this.waitingForFlipBack = false;
        this.totalPairs = 0;
        this.firstFlipped = null;
        this.secondFlipped = null;
    }

    public void gameStart() {
        this.mainFrame = new JFrame("Card Memory Game");
        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setResizable(true);
        showMenuScreen();
        this.mainFrame.setVisible(true);
    }

    private void showMenuScreen() {
        JPanel gameMenu = new JPanel();
        gameMenu.setLayout(new BoxLayout(gameMenu, BoxLayout.Y_AXIS));
        gameMenu.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        gameMenu.setBackground(new Color(85, 85, 85));

        JLabel gameTitle = new JLabel("Card Memory Game");
        gameTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        gameTitle.setForeground(Color.WHITE);
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel highScore = new JLabel("High Score: " + loadHighScore()); // TODO: load the player's saved high score
                                                                         // from their file once we have their name
        highScore.setFont(new Font("SansSerif", Font.PLAIN, 16));
        highScore.setForeground(new Color(200, 230, 200));
        highScore.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Your Name: ");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameField = new JTextField("Player");
        nameField.setMaximumSize(new Dimension(200, 30));
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel difficultyLabel = new JLabel("Difficulty: ");
        difficultyLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        difficultyLabel.setForeground(Color.WHITE);
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] difficultyOptions = { "1 - Easy", "2 - Medium", "3 - Hard" };
        JComboBox<String> difficultyBox = new JComboBox<>(difficultyOptions);
        difficultyBox.setMaximumSize(new Dimension(220, 30));
        difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton gameStartButton = new JButton("Start Game");
        gameStartButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        gameStartButton.setForeground(Color.BLACK);
        gameStartButton.setBackground(new Color(200, 160, 50));
        gameStartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameStartButton.setFocusPainted(false);
        gameStartButton.addActionListener(e -> {
            String name = nameField.getText();
            if (name.isEmpty() == true) {
                name = "Player";
            }
            // the dropdown starts counting from 0, but our difficulty levels start at 1, so
            // we add 1
            this.gameDifficulty = (byte) (difficultyBox.getSelectedIndex() + 1);
            this.humanPlayer = new humanPlayer(name, "", 0);
            this.aiPlayer = new AIPlayer(this.gameDifficulty);
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

        this.mainFrame.setContentPane(gameMenu);
        this.mainFrame.pack();
        this.mainFrame.setLocationRelativeTo(null);
    }

    private void showGameScreen() {
        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(50, 120, 50));

        JPanel gameStatusBar = new JPanel();
        gameStatusBar.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        gameStatusBar.setBackground(new Color(40, 90, 40));

        this.statusLabel = new JLabel("Your Turn");
        this.statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        this.statusLabel.setForeground(Color.WHITE);

        this.scoreLabel = new JLabel("Pairs: " + this.humanPairsFound);
        this.scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        this.scoreLabel.setForeground(Color.WHITE);

        this.movesLabel = new JLabel("Moves: " + this.numMoves);
        this.movesLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        this.movesLabel.setForeground(Color.WHITE);

        gameStatusBar.add(this.statusLabel);
        gameStatusBar.add(this.scoreLabel);
        gameStatusBar.add(this.movesLabel);

        JPanel boardPanel = new JPanel();
        boardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        boardPanel.setBackground(new Color(50, 120, 50));
        boardPanel.setLayout(new GridLayout(this.deck.gridY, this.deck.gridX, 15, 15));

        // calculate the exact space needed so GridLayout gives each card its natural 120x168 size
        int boardWidth = this.deck.gridX * 120 + (this.deck.gridX - 1) * 15 + 40;
        int boardHeight = this.deck.gridY * 168 + (this.deck.gridY - 1) * 15 + 40;
        boardPanel.setPreferredSize(new Dimension(boardWidth, boardHeight));

        Card[][] gameGrid = this.deck.getDeck();
        for (int row = 0; row < this.deck.gridY; row++) {
            for (int col = 0; col < this.deck.gridX; col++) {
                Card card = gameGrid[row][col];
                // row and col change every loop, so we save copies here so the click handler
                // uses the correct position
                final int finalRow = row;
                final int finalCol = col;
                card.getPanel().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleCardClick(card, finalRow, finalCol);
                    }
                });
                boardPanel.add(card.getPanel());
            }
        }

        gamePanel.add(gameStatusBar, BorderLayout.NORTH);
        gamePanel.add(boardPanel, BorderLayout.CENTER);

        this.mainFrame.setContentPane(gamePanel);
        this.mainFrame.pack();
        this.mainFrame.setLocationRelativeTo(null);
    }

    private void showGameOverScreen() {
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
        gameOverPanel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));
        gameOverPanel.setBackground(new Color(85, 85, 85));

        JLabel titleLabel = new JLabel("Game Over!");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // more pairs wins; if tied on pairs, fewer moves wins for the human
        int aiPairs = this.aiPlayer.score;
        String result;
        if (this.humanPairsFound > aiPairs) {
            result = "You Win!";
        } else if (aiPairs > this.humanPairsFound) {
            result = "AI Wins!";
        } else if (this.numMoves <= aiPairs) {
            // when tied on pairs and the human used fewer or equal moves,
            // the human wins the tiebreaker
            result = "You Win! (Tiebreaker)";
        } else {
            result = "AI Wins! (Tiebreaker)";
        }

        JLabel resultLabel = new JLabel(result);
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        if (result.startsWith("You Win")) {
            resultLabel.setForeground(new Color(100, 230, 100));
        } else {
            resultLabel.setForeground(Color.WHITE);
        }
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel humanScoreLabel = new JLabel("Your Pairs: " + this.humanPairsFound);
        humanScoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        humanScoreLabel.setForeground(Color.WHITE);
        humanScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel aiScoreLabel = new JLabel("AI Pairs: " + aiPairs);
        aiScoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        aiScoreLabel.setForeground(Color.WHITE);
        aiScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel finalMovesLabel = new JLabel("Your Moves: " + this.numMoves);
        finalMovesLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        finalMovesLabel.setForeground(Color.WHITE);
        finalMovesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        playAgainButton.setForeground(Color.BLACK);
        playAgainButton.setBackground(new Color(200, 160, 50));
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.setFocusPainted(false);
        playAgainButton.addActionListener(e -> {
            showMenuScreen();
        });

        gameOverPanel.add(titleLabel);
        gameOverPanel.add(Box.createVerticalStrut(12));
        gameOverPanel.add(resultLabel);
        gameOverPanel.add(Box.createVerticalStrut(16));
        gameOverPanel.add(humanScoreLabel);
        gameOverPanel.add(Box.createVerticalStrut(6));
        gameOverPanel.add(aiScoreLabel);
        gameOverPanel.add(Box.createVerticalStrut(6));
        gameOverPanel.add(finalMovesLabel);
        gameOverPanel.add(Box.createVerticalStrut(24));
        gameOverPanel.add(playAgainButton);

        this.mainFrame.setContentPane(gameOverPanel);
        this.mainFrame.pack();
        this.mainFrame.setLocationRelativeTo(null);
    }

    // row and col are passed so the AI can learn from each card the human reveals
    private void handleCardClick(Card clickedCard, int row, int col) {
        if (this.waitingForFlipBack == true) {
            return;
        }
        if (clickedCard.existOrNot == false || clickedCard.isFlipped == true) {
            return;
        }

        flipCard(clickedCard);
        this.numMoves++;
        this.movesLabel.setText("Moves: " + this.numMoves);
        this.aiPlayer.rememberCard(clickedCard, row, col);

        if (this.firstFlipped == null) {
            this.firstFlipped = clickedCard;
        } else {
            this.secondFlipped = clickedCard;
            checkMatch();
        }
    }

    public void flipCard(Card card) {
        card.isFlipped = !card.isFlipped;
        // TODO: call card.updateFlipVisual() to
        // show the card face or back image based on isFlipped
    }

    public boolean checkMatch() {
        if (this.firstFlipped == null || this.secondFlipped == null) {
            return false;
        }

        byte firstNum = this.firstFlipped.getNum();
        byte secondNum = this.secondFlipped.getNum();
        boolean numMatch = (firstNum == secondNum);

        String firstSuit = this.firstFlipped.getSuit();
        String secondSuit = this.secondFlipped.getSuit();
        boolean suitMatch = firstSuit.equals(secondSuit);

        if (numMatch == true && suitMatch == true) {
            this.firstFlipped.existOrNot = false;
            this.secondFlipped.existOrNot = false;
            this.humanPairsFound++;
            this.scoreLabel.setText("Pairs: " + this.humanPairsFound);
            this.firstFlipped = null;
            this.secondFlipped = null;

            if (checkWin() == true) {
                endGame();
            }

            return true;
        } else {
            this.waitingForFlipBack = true;
            // cleared early so clicking during the delay does not accidentally count as the
            // next turn
            Card firstCard = this.firstFlipped;
            Card secondCard = this.secondFlipped;
            this.firstFlipped = null;
            this.secondFlipped = null;

            // hold both cards face-up for 800ms so the player can see them before flipping
            // back
            Timer flipBackTimer = new Timer(800, e -> {
                flipCard(firstCard);
                flipCard(secondCard);
                this.waitingForFlipBack = false;
                switchTurn();
            });
            flipBackTimer.setRepeats(false);
            flipBackTimer.start();

            return false;
        }
    }

    public boolean checkWin() {
        int totalPairsFound = this.humanPairsFound + this.aiPlayer.score;
        boolean allPairsFound;
        if (totalPairsFound == this.totalPairs) {
            allPairsFound = true;
        } else {
            allPairsFound = false;
        }
        return allPairsFound;
    }

    public void switchTurn() {
        this.humanPlayer.isTheirTurn = !this.humanPlayer.isTheirTurn;
        this.aiPlayer.isTheirTurn = !this.aiPlayer.isTheirTurn;

        if (this.humanPlayer.isTheirTurn == true) {
            this.statusLabel.setText("Your Turn");
        } else {
            this.statusLabel.setText("AI Turn");
            performAITurn();
        }
    }

    private void performAITurn() {
        // wait 1 second so the player can see "AI Turn" before the AI starts picking
        // cards
        Timer aiTimer = new Timer(1000, e -> {
            // check if the game already ended before letting the AI take its turn
            if (checkWin() == true) {
                return;
            }

            int[][] choice = this.aiPlayer.chooseCards(this.deck);
            Card[][] cardGrid = this.deck.getDeck();
            Card card1 = cardGrid[choice[0][0]][choice[0][1]];
            Card card2 = cardGrid[choice[1][0]][choice[1][1]];

            this.aiPlayer.rememberCard(card1, choice[0][0], choice[0][1]);
            this.aiPlayer.rememberCard(card2, choice[1][0], choice[1][1]);

            flipCard(card1);

            // flip second card after a short pause so both are visible in sequence
            Timer secondFlip = new Timer(600, e2 -> {
                flipCard(card2);

                // wait before checking if they matched, so the player can see both cards
                Timer resolve = new Timer(800, e3 -> {
                    boolean matched = card1.getNum() == card2.getNum()
                            && card1.getSuit().equals(card2.getSuit());
                    if (matched == true) {
                        card1.existOrNot = false;
                        card2.existOrNot = false;
                        this.aiPlayer.score++;
                        if (checkWin() == true) {
                            endGame();
                            return;
                        }
                        performAITurn(); // AI found a match, so it gets another turn
                    } else {
                        flipCard(card1);
                        flipCard(card2);
                        switchTurn();
                    }
                });
                resolve.setRepeats(false);
                resolve.start();
            });
            secondFlip.setRepeats(false);
            secondFlip.start();
        });
        aiTimer.setRepeats(false);
        aiTimer.start();
    }

    // used for both starting a new game and playing again, so everything is reset
    // to a clean state
    private void setupGame() {
        int deckSize;
        switch (this.gameDifficulty) {
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

        this.totalPairs = deckSize / 2;
        this.numMoves = 0;
        this.humanPairsFound = 0;
        this.firstFlipped = null;
        this.secondFlipped = null;
        this.waitingForFlipBack = false;

        this.humanPlayer.score = 0;
        this.humanPlayer.isTheirTurn = true;
        this.aiPlayer.score = 0;
        this.aiPlayer.isTheirTurn = false;

        this.deck = new Deck(deckSize);
        this.deck.generateCards();
    }

    private void endGame() {
        // lower moves is better, so save if this is the first game or if the player
        // beat their previous move count
        if (this.humanPlayer.highestScore == 0 || this.numMoves < this.humanPlayer.highestScore) {
            this.humanPlayer.highestScore = this.numMoves;
        }
        saveHighestScore();
        showGameOverScreen();
    }

    public void saveHighestScore() {
        this.humanPlayer.storePlayer(this.humanPlayer);
    }

    public int loadHighScore() {
        // TODO: load from player file - need name before game starts
        return 0;
    }

    public byte getGameDifficulty() {
        return this.gameDifficulty;
    }

    public void setGameDifficulty(byte difficulty) {
        this.gameDifficulty = difficulty;
    }
}

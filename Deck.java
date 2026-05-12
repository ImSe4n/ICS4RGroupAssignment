
/**
 * Write a description of class Deck here.
 *
 * @author (Atal Wardak)
 * @version (a version number or a date)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class Deck
{
    private Card[][] gridOfCards;
    
    //byte so it can be referenced for randomization when creating a card later
    //row = suit col = number
    final byte[][] deckOfCardsOrdered = 
    {
        //Spades
        {1,2,3,4,5,6,7,8,9,10,11,12,13},
        
        //Hearts
        {1,2,3,4,5,6,7,8,9,10,11,12,13},
        
        //Diamonds
        {1,2,3,4,5,6,7,8,9,10,11,12,13},
        
        //Clovers
        {1,2,3,4,5,6,7,8,9,10,11,12,13},
    };
    
    //constant bytes that represent each suit
    final byte SPADE_NUM = 0;
    final byte HEART_NUM = 1;
    final byte DIAMOND_NUM = 2;
    final byte CLOVER_NUM = 3;
    
    //deckSize
    int deckSize;
    
    //grid properties (width length)
    int gridX;
    int gridY;
    
    //debugging var
    private JFrame mainFrame;
    
    //constructor to set up playing deck size (only size for now)
    Deck(int d)
    {
        this.deckSize = d;
        
        //find out appropriate grid size based on deck size
        int[] gridValues = getGrid(this.deckSize);
        this.gridX = gridValues[0];
        this.gridY = gridValues[1];
        
        //change size of 2d array to match grid
        this.gridOfCards = new Card[this.gridY][this.gridX];
    }
    
    //method to find the best grid size (as balanced as possible) given the deck size
    // x and y values ^
    public static int[] getGrid(int deckSize)
    {
        //get square root
        int x = (int)Math.sqrt(deckSize);
        
        //while loop to continously find the first divisor going lower and lower from the sqrt
        while (deckSize % x != 0)
        {
            //as long as deckSize has a remainder from its sqrt keep going down till its a whole number
            x--;
        }
        
        //y axis
        int y = deckSize / x; // N = x * y rearrange to find y
        
        //return width,height but use Math.max and math.min to ensure the biggest number is always 
        //on the width (x) because its better for monitors
        return new int[]{Math.max(x,y), Math.min(x,y)};
    }
    
    //generate random cards based on difficulty
    public void generateCards()
    {
        //grab difficulty from game class
        //byte diff = Game.getDifficulty();
        
        //temp var to hold the current row we are on of the grid
        int currentRowIndex = 0;
        
        //temp var to hold the current col we are on of the grid
        int currentColIndex = 0;
        
        
        
        //now run a loop based on HALF the deck size to generate random cards
        for(int i = 0; i<(this.deckSize/2); i++)
        {
            //boolean variable for the do while loop
            boolean cardFound = false;
            
            
            
            //do while loop to ensure a card that exists is being chosen
            do
            {
                //pick a random suit
                byte suitIndex = (byte)(Math.random() * 4);
            
                //pick a random index from that row to choose a number/jack/queen/king
                byte numIndex = (byte)(Math.random() * 13);
            
                //declare var for the card num
                byte cardNum = deckOfCardsOrdered[suitIndex][numIndex];
                
                
                
                //now make sure this number is available first and hasnt been chosen already
                if (cardNum != -1)
                {
                    cardFound = true;
                    
                    //temp var to hold the suit of the card
                    String suit;
                    switch(suitIndex)
                    {
                        case SPADE_NUM: 
                            suit = "Spade";
                            break;
                        
                        case HEART_NUM:
                            suit = "Heart";
                            break;
                        
                        case DIAMOND_NUM:
                            suit = "Diamond";
                            break;
                        
                        case CLOVER_NUM:
                            suit = "Clover";
                            break;
                        
                        default:
                            suit = "None";
                            break;
                    }
                
                    //add cards into 2d array in order (will re-randomize later) based on grid size 
                    
                    //loop twice because we want duplicates of each card for the memory game concept
                    for(int j = 0; j<2; j++)
                    {
                        
                        //check if current col is larger than the specified width
                        if ((currentColIndex+1) <= this.gridX)
                        {
                            
                            
                            //make a new card object and put it in the array
                            gridOfCards[currentRowIndex][currentColIndex] = new Card(cardNum,suit,false);
                        
                            //increment the col index
                            currentColIndex++;
                        }
                        else
                        {
                            //its higher than the width so increment row index and reset the col index
                            currentRowIndex++;
                            currentColIndex = 0;
                        
                            //make a new card object and put it in the array
                            gridOfCards[currentRowIndex][currentColIndex] = new Card(cardNum,suit,false);
                            
                            //increment the col index
                            currentColIndex++;
                        }
                        
                        
                    }   
                    
                }
            }while(!cardFound);
            
            
        }
        
        outputGrid();
        displayDeck();
    }
    
    
    
    
    
    //=========Below are Debugging Methods only==============//
    
    //temp method to output the grid of cards (debugging purposes)
    public void outputGrid()
    {
        for(int row = 0; row<gridOfCards.length;row++)
        {
            for (int col = 0; col<gridOfCards[row].length;col++)
            {
                System.out.print("( " + gridOfCards[row][col].getNum() + " )" + " ( " + gridOfCards[row][col].getSuit() + " )");
            }
            System.out.println();
        }
    }
    
    public void displayDeck()
    {
        mainFrame = new JFrame("Card Memory Game");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1400, 1000);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        
        
        
        mainFrame.setVisible(true);
        
        //loop thru grid of cards now and add their panels to mainframe
           for(int row = 0; row<gridOfCards.length;row++)
        {
            for (int col = 0; col<gridOfCards[row].length;col++)
            {
                Card card = gridOfCards[row][col];
                card.getPanel().setLocation(200+(col*150),150+(row*120));
                mainFrame.add(card.getPanel());
            }
    
        }
    }

}
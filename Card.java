
/**
 * Write a description of class Card here.
 *
 * @author (Atal Wardak)
 * @version (a version number or a date)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Card
{
    //basic instance variables for info about the card
    
    private byte cardNum;
    private String flippedCardNum;
    private String cardSuit;
    boolean existOrNot; //exist in play or not
    boolean isFlipped;
    
    //GUI variables
    private JPanel cardPanel;
    private JLabel cardNumLabel1;
    private JLabel cardNumLabel2;
    private JLabel cardNumLabel3;
    private JLabel cardNumLabel4;
    
    private JLabel cardSuitLabel1;
    private JLabel cardSuitLabel2;
    private JLabel cardSuitLabel3;
    private JLabel cardSuitLabel4;
    
    //the path for the current card
    private String cardIconPath;
    
    //png paths for images for each suit icon
    private static String pathHeart = "HeartIcon.png";
    private static String pathSpade = "SpadeIcon.png";
    private static String pathClover = "CloverIcon.png";
    private static String pathDiamond = "DiamondIcon.png";
    
    //constants for non number indentifier
    final byte JACK_NUM = 11;
    final byte QUEEN_NUM = 12;
    final byte KING_NUM = 13;
    final byte ACE_NUM = 1;
    
    //constants for flipped versions of numbers/letters
    //to do
    
    //constructor set up variables
    Card(byte n, String s, boolean f)
    {
        this.cardNum = n;
        this.cardSuit = s;
        this.isFlipped = f;
        this.existOrNot = true;
      
        //check which suit the card is and assign a path
        switch(this.cardSuit)
        {
            case "Heart":
                {
                    this.cardIconPath = pathHeart;
                    break;
                }
            case "Clover":
                {
                    this.cardIconPath = pathClover;
                    break;
                }
            case "Diamond":
                {
                    this.cardIconPath = pathDiamond;
                    break;
                }
            case "Spade":
                {
                    this.cardIconPath = pathSpade;
                    break;
                }
        }
        
        //setup GUI
        setupCardPanel();
    }
    
    public void setupCardPanel()
    {
        
        System.out.println("GUI for Card: " + this.cardNum + " " + this.cardSuit);
        //create the gui panel with everything placed properly
        this.cardPanel = new JPanel();
        this.cardPanel.setLayout(null);
        this.cardPanel.setSize(120 ,168); // perfect size in my opinion
        this.cardPanel.setBorder(BorderFactory.createLineBorder(Color.red,3));
        //cardPanel.setLocation(500,500); location will be done only when adding to grid because Main frame from game class is needed
        
        //check if number or letter is needed. (2-10 or J,Q,K,A)
        if (this.cardNum >= 2 && this.cardNum <= 10)
        {
            //card num
            //card number label
            this.cardNumLabel1 = new JLabel("" + this.cardNum);
        
            this.cardNumLabel1.setFont(new Font("Arial", Font.BOLD,22));
            this.cardNumLabel1.setForeground(Color.black);
            this.cardNumLabel1.setSize(100,100);
            this.cardNumLabel1.setLayout(null);
            
            /*different positioning whether its double digit or not
            if (this.cardNum == 10)
            {
                this.cardNumLabel.setLocation(8,20);
            }
            else
            {
                this.cardNumLabel.setLocation(22,20);
            }*/
            
            
            
        }
        else
        {
            //letter to be added
            char letter;
            
            switch(this.cardNum)
            {
                case ACE_NUM:
                    {
                        letter = 'A';
                        break;
                    }
                case JACK_NUM:
                    {
                        letter = 'J';
                        break;
                    }
                case QUEEN_NUM:
                    {
                        letter = 'Q';
                        break;
                    }
                case KING_NUM:
                    {
                        letter = 'K';
                        break;
                    }
                default:
                    letter = ' ';
                    break;
            }
            
            //create a label for the card
            JLabel cardLetter = new JLabel(""+letter);

            cardLetter.setFont(new Font("Arial", Font.BOLD,22));
            cardLetter.setForeground(Color.black);
            cardLetter.setSize(100,100);
            
            cardLetter.setLayout(null);
            
            this.cardNumLabel1 = cardLetter;

        }
        
        //place in the corner TOP LEFT
        this.cardNumLabel1.setLocation(5,-32);
        
        //now make suit labels
         
        
        //now clone this 3 times and put them in the corners
        for (int i = 0; i<3; i++)
        {
            //switch statement inside to do a different corner each iteration
            switch (i)
            {
                case 0:
                    {
                        //TOP RIGHT 
                        
                        JLabel newLabel = cloneLabel(this.cardNumLabel1);
            
                        //we want to reposition based on the OG position
                        //grab its x and y values
                        int x = newLabel.getX();
                        int y = newLabel.getY();
                        
                        //set new loco whehter its a 1 digit or two digit num
                        
                        if (this.cardNum == 10)
                        {
                            newLabel.setLocation(x+83,y);
                        }
                        else
                        {
                            newLabel.setLocation(x+94,y);
                        }
                        
            
                        this.cardNumLabel2 = newLabel;
                        break;
                    }
                case 1:
                    {
                        //BOTTOM LEFT 
                        
                        JLabel newLabel = cloneLabel(this.cardNumLabel1);
            
                        //we want to reposition based on the OG position
                        //grab its x and y values
                        int x = newLabel.getX();
                        int y = newLabel.getY();
                        
                        //set new loco whehter its a 1 digit or two digit num
                        newLabel.setLocation(x,y+135);
                        
                        
            
                        this.cardNumLabel3 = newLabel;
                        break;
                    }
                case 2:
                    {
                        
                        
                        JLabel newLabel = cloneLabel(this.cardNumLabel1);
            
                        //we want to reposition based on the OG position
                        //grab its x and y values
                        int x = newLabel.getX();
                        int y = newLabel.getY();
                        
                        //set new loco whehter its a 1 digit or two digit num
                        
                        if (this.cardNum == 10)
                        {
                            newLabel.setLocation(x+83,y+135);
                        }
                        else
                        {
                            newLabel.setLocation(x+94,y+135);
                        }
                        
            
                        this.cardNumLabel4 = newLabel;
                        break;
                    }
                default:
                    break;
            }
        }
                
        
        //add  a name to this component so i can find it later when I need to change it if needed
        this.cardNumLabel1.setName("CardNum1");
        this.cardNumLabel2.setName("CardNum2");
        this.cardNumLabel3.setName("CardNum3");
        this.cardNumLabel4.setName("CardNum4");
        
        
        
        //add it to the cardPanel
        this.cardPanel.add(this.cardNumLabel1);
        this.cardPanel.add(this.cardNumLabel2);
        this.cardPanel.add(this.cardNumLabel3);
        this.cardPanel.add(this.cardNumLabel4);
    }
    
    public JLabel cloneLabel(JLabel label)
    {
        //clone an existing label (used for the 4 diff instances of a card num or letter)
        JLabel clone = new JLabel(label.getText());
        clone.setFont(label.getFont());
        clone.setForeground(label.getForeground());
        clone.setBackground(label.getBackground());
        clone.setOpaque(label.isOpaque());
        clone.setHorizontalAlignment(label.getHorizontalAlignment());
        clone.setSize(label.getSize());
        clone.setLocation(label.getX(),label.getY());
    
        return clone;
    }
    
    //getters for private instance vars
    
    public byte getNum()
    {
        return this.cardNum;
    }
    
    public String getSuit()
    {
        return this.cardSuit;
    }
    
    public JPanel getPanel()
    {
        return this.cardPanel;
    }
    
    
    
}

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
    private String cardSuit;
    boolean existOrNot; //exist in play or not
    boolean isFlipped;
    
    //GUI variables
    private JPanel cardPanel;
    private JLabel cardNumLabel;
    private JLabel cardSuitLabel;
    
    //png paths for images for each suit icon
    private String pathHeart = "HeartIcon.png";
    private String pathSpade = "SpadeIcon.png";
    private String pathClover = "CloverIcon.png";
    private String pathDiamond = "DiamondIcon.png";
    
    //constants for non number indentifier
    final byte JACK_NUM = 11;
    final byte QUEEN_NUM = 12;
    final byte KING_NUM = 13;
    final byte ACE_NUM = 1;
    
    //constructor set up variables
    Card(byte n, String s, boolean f)
    {
        this.cardNum = n;
        this.cardSuit = s;
        this.isFlipped = f;
        this.existOrNot = true;
        
        setupCardPanel();
    }
    
    public void setupCardPanel()
    {
        //create the gui panel with everything placed properly
        this.cardPanel = new JPanel();
        this.cardPanel.setLayout(null);
        this.cardPanel.setSize(75 ,117); // perfect size in my opinion
        this.cardPanel.setBorder(BorderFactory.createLineBorder(Color.red,3));
        //cardPanel.setLocation(500,500); location will be done only when adding to grid because Main frame from game class is needed
        
        //check if number or letter is needed. (2-10 or J,Q,K,A)
        if (this.cardNum >= 2 && this.cardNum <= 10)
        {
            //card num
            //card number laber
            this.cardNumLabel = new JLabel("" + this.cardNum);
        
            this.cardNumLabel.setFont(new Font("Arial", Font.PLAIN,50));
            this.cardNumLabel.setForeground(Color.black);
            this.cardNumLabel.setSize(100,100);
            this.cardNumLabel.setLayout(null);
            
            //different positioning whether its double digit or not
            if (this.cardNum == 10)
            {
                this.cardNumLabel.setLocation(10,20);
            }
            else
            {
                this.cardNumLabel.setLocation(20,20);
            }
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

            cardLetter.setFont(new Font("Arial", Font.PLAIN,25));
            cardLetter.setForeground(Color.black);
            cardLetter.setSize(100,100);
            cardLetter.setLocation(5,-25);
            cardLetter.setLayout(null);
            
            this.cardNumLabel = cardLetter;

        }
                
        
        //add  a name to this component so i can find it later when I need to change it if needed
        this.cardNumLabel.setName("CardNum");
        
        //add it to the cardPanel
        this.cardPanel.add(this.cardNumLabel);
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

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
    private String pathHeart = "";
    
    
    //constructor set up variables
    Card(byte n, String s, boolean f)
    {
        this.cardNum = n;
        this.cardSuit = s;
        this.isFlipped = f;
        this.existOrNot = true;
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
    
    
    
    
}
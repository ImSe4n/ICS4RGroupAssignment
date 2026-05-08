
/**
 * Write a description of class Card here.
 *
 * @author (Atal Wardak)
 * @version (a version number or a date)
 */

import javax.swing.*;
public class Card
{
    //basic instance variables for info about the card
    
    private byte cardNum;
    private String cardSuit;
    boolean existOrNot; //exist in play or not
    boolean isFlipped;
    
    //GUI variables
    
    
    
    //constructor set up variables
    Card(byte n, String s, boolean f)
    {
        this.cardNum = n;
        this.cardSuit = s;
        this.isFlipped = f;
        this.existOrNot = true;
    }
    
    
}
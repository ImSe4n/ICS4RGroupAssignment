
/**
 * Write a description of class Deck here.
 *
 * @author (Atal Wardak)
 * @version (a version number or a date)
 */
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

}
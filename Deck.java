
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
    
    //constant bytes that represent each suit
    final byte SPADE_NUM = 0;
    final byte HEART_NUM = 1;
    final byte DIAMOND_NUM = 2;
    final byte CLOVER_NUM = 3;
    
    //deckSize
    int deckSize;
    
    
    
    //constructor to set up playing deck size (only size for now)
    Deck(int d)
    {
        this.deckSize = d;
    }
    
    //generate random cards based on difficulty
    public void generateCards()
    {
        //grab difficulty from game class
        //byte diff = Game.getDifficulty();
        
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
                    
                    //find out the suit in string form to be passed to Card class for creating
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
                            break;
                    }
                
                    //pass all variables to create a new card class and setup the card
                
                }
            }while(!cardFound);
            
            
        }
    }

}
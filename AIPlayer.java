import java.util.ArrayList;
import java.util.Random;
//an AI to play against human player which have 3 different difficulty levels 
public class AIPlayer extends player
{
    byte level;
    int memoryLimit;
    //use two arrayList to store AI's remembered cards
    ArrayList<Card> rememberedCards;
    ArrayList<int[]> rememberedPositions;

    Random rand;
    //code a contructor for Aiplayer
    public AIPlayer(byte level)
    {
        this.level = level;
        this.rand = new Random();

        rememberedCards = new ArrayList<Card>();
        rememberedPositions = new ArrayList<int[]>();
        //set up AI's memoryLimit based on the level
        if(level == 1)
        {
            memoryLimit = 4;
        }
        else if(level == 2)
        {
            memoryLimit = 6;
        }
        else
        {
            memoryLimit = 8;
        }

        this.score = 0;
        this.isTheirTurn = false;
    }
    //a method to memorize cards that have been selected
    public void rememberCard(Card card, int row, int col)
    {
        //memorize the card and its location
        rememberedCards.add(card);
        rememberedPositions.add(new int[]{row, col});
        
        //if the memory is exceeding limit, delete the first one
        //
        if(rememberedCards.size() >= memoryLimit)
        {
            rememberedCards.remove(0);
            rememberedPositions.remove(0);
        }
    }
    
    //a method to choose card, if there are two same card in memory, choose them. If not, randomly choose two cards.
    public int[][] chooseCards(Deck deck)
    {
        //first try to find a matching pair from memory
        for(int i = 0; i < rememberedCards.size(); i++)
        {
            for(int j = i + 1; j < rememberedCards.size(); j++)
            {
                //loop through AIPlayer's memory and check if there are two same cards
                Card c1 = rememberedCards.get(i);
                Card c2 = rememberedCards.get(j);
                
                //if they are exactly same and unselected, the choice will be both of them
                if(c1.existOrNot && c2.existOrNot)
                {
                    if(c1.getNum() == c2.getNum() && c1.getSuit().equals(c2.getSuit()))
                    {
                        int[][] choice =
                        {
                            rememberedPositions.get(i),
                            rememberedPositions.get(j)
                        };
                        return choice;
                    }
                }
            }
        }

        //if no remembered match, choose random cards
        return randomChoice(deck);
    }
    
    //a method to randomly choose two cards from the deck
    public int[][] randomChoice(Deck deck)
    {
        Card[][] grid = deck.getDeck();

        int row1;
        int col1;
        int row2;
        int col2;
        //randomize the first card and check if it can be selected
        do
        {
            row1 = rand.nextInt(grid.length);
            col1 = rand.nextInt(grid[0].length);
        }
        while(grid[row1][col1].existOrNot == false);

        do
        {
            row2 = rand.nextInt(grid.length);
            col2 = rand.nextInt(grid[0].length);
        }
        //choosw the second card, can not the same as the first card
        while(grid[row2][col2].existOrNot == false || 
              (row1 == row2 && col1 == col2));
        
        //return the choice using a 2D array
        int[][] choice =
        {
            {row1, col1},
            {row2, col2}
        };

        return choice;
    }
    //a method to operate the full play of AI's turn
    public boolean playTurn(Deck deck)
    {
        //Ai make its choice
        int[][] choice = chooseCards(deck);
        
        //convert AI's choice into variablrs
        
        int row1 = choice[0][0];
        int col1 = choice[0][1];
        int row2 = choice[1][0];
        int col2 = choice[1][1];
        
        //get the deck from the Deck class
        Card[][] grid = deck.getDeck();
        
        //convert the choice into two cards
        Card card1 = grid[row1][col1];
        Card card2 = grid[row2][col2];
        
        //output Ai's choice
        System.out.println("AI chose:");
        System.out.println(row1 + ", " + col1 + ": " + card1.getNum() + " of " + card1.getSuit());
        System.out.println(row2 + ", " + col2 + ": " + card2.getNum() + " of " + card2.getSuit());
        
        //remember Ai's choice
        rememberCard(card1, row1, col1);
        rememberCard(card2, row2, col2);
        
        //check if AI got two same card, if so, eliminate those card and add score for AI
        if(card1.getNum() == card2.getNum() && card1.getSuit().equals(card2.getSuit()))
        {
            System.out.println("AI found a match!");

            card1.existOrNot = false;
            card2.existOrNot = false;

            this.score++;

            removeMatchedFromMemory();

            return true;
        }
        else
        {
            System.out.println("AI did not find a match.");
            return false;
        }
    }
    // a method to remove eliminated cards from the memory
    private void removeMatchedFromMemory()
    {
        for(int i = rememberedCards.size() - 1; i >= 0; i--)
        {
            if(rememberedCards.get(i).existOrNot == false){
                rememberedCards.remove(i);
                rememberedPositions.remove(i);
            }
        }
    }

}
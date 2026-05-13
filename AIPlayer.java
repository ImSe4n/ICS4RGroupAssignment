import java.util.ArrayList;
import java.util.Random;

public class AIPlayer
{
    private int level;
    private int memoryLimit;

    //stores remembered cards
    private ArrayList<Card> rememberedCards;

    //stores remembered positions
    private ArrayList<int[]> rememberedPositions;

    Random rand = new Random();
    
    public AIPlayer(int level)
    {
        this.level = level;
    
        rememberedCards = new ArrayList<Card>();
        rememberedPositions = new ArrayList<int[]>();

        //different difficulty levels represent different momeryLimit
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
    }

    //AI remembers a random card
    public void rememberCard(Card card, int row, int col)
    {
        //check if already remembered
        for(int i = 0; i < rememberedCards.size(); i++)
        {
            int[] pos = rememberedPositions.get(i);

            if(pos[0] == row && pos[1] == col)
            {
                return;
            }
        }

        //if memory full remove oldest memory
        if(rememberedCards.size() >= memoryLimit)
        {
            rememberedCards.remove(0);
            rememberedPositions.remove(0);
        }

        rememberedCards.add(card);
        rememberedPositions.add(new int[]{row, col});
    }

    //find matching pair from memory
    public int[][] chooseCards()
    {
        for(int i = 0; i < rememberedCards.size(); i++)
        {
            for(int j = i + 1; j < rememberedCards.size(); j++)
            {
                Card c1 = rememberedCards.get(i);
                Card c2 = rememberedCards.get(j);

                //same number means match
                if(c1.getNumber() == c2.getNumber())
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

        //no match remembered
        return null;
    }

    //random move if no remembered pair
    public int[][] randomChoice(Deck deck)
    {
        int row1;
        int col1;

        int row2;
        int col2;

        do
        {
            row1 = rand.nextInt(deck.getGridX());
            col1 = rand.nextInt(deck.getGridY());

        }while(deck.getCard(row1, col1).isMatched());

        do
        {
            row2 = rand.nextInt(deck.getGridX());
            col2 = rand.nextInt(deck.getGridY());

        }while(deck.getCard(row2, col2).isMatched()
                || (row1 == row2 && col1 == col2));

        int[][] choice =
        {
            {row1, col1},
            {row2, col2}
        };

        return choice;
    }
}
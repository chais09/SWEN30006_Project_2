package cribbage;

import ch.aplu.jcardgame.Hand;

public class CalculateFlush extends Calculation{
    private Hand starter;
    public CalculateFlush(Hand hand, Hand starter){
        super(hand);
        this.starter = starter;
    }

    @Override
    public int calculate(IPlayer player, int[] scores) {
        if(hand.getNumberOfCardsWithSuit(Cribbage.Suit.SPADES) > 3){
            if(starter.getNumberOfCardsWithSuit(Cribbage.Suit.SPADES) == 1){
                return 5;
            }
            return 4;
        }else if(hand.getNumberOfCardsWithSuit(Cribbage.Suit.HEARTS) > 3){
            if(starter.getNumberOfCardsWithSuit(Cribbage.Suit.HEARTS) == 1){
                return 5;
            }
            return 4;
        }else if(hand.getNumberOfCardsWithSuit(Cribbage.Suit.DIAMONDS) > 3){
            if(starter.getNumberOfCardsWithSuit(Cribbage.Suit.DIAMONDS) == 1){
                return 5;
            }
            return 4;
        }else if(hand.getNumberOfCardsWithSuit(Cribbage.Suit.CLUBS) > 3){
            if(starter.getNumberOfCardsWithSuit(Cribbage.Suit.CLUBS) == 1){
                return 5;
            }
            return 4;
        }
        return 0;
    }


}

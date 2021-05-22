package cribbage;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class CalculateTotal extends Calculation{
    public CalculateTotal(Hand hand){
        super(hand);
    }

    @Override
    public int calculate() {
        int total = 0;
        for (Card c: hand.getCardList()){
            total += c.getValue();
        }
        if(total == 15 || total == 31){
            return 2;
        }
        return 0;
    }
}

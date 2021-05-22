package cribbage;

import ch.aplu.jcardgame.Hand;

public class CalculateJack extends Calculation{
    private Hand starter;
    public CalculateJack(Hand hand, Hand starter){
        super(hand);
        this.starter = starter;
    }

    @Override
    public int calculate() {
        Enum start = starter.getCardList().get(0).getSuit();
        if(hand.getCard(start, Cribbage.Rank.JACK) != null){
            return 1;
        }
        return 0;
    }

}

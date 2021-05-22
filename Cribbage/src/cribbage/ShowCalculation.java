package cribbage;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class ShowCalculation extends Calculation{
    private Hand starter;
    public ShowCalculation(Hand hand, Hand starter){
        super(hand);
        this.starter = starter;
    }

    @Override
    public int calculate() {
        CalculateJack jack = new CalculateJack(hand,starter);
        CalculateFlush flush = new CalculateFlush(hand,starter);
        Card newCard= starter.getCardList().get(0);
        Hand newHand = hand;
        newHand.insert(newCard.getSuit(), newCard.getRank(), false);
        CalculatePair pair = new CalculatePair(newHand);
        CalculateRunShow run = new CalculateRunShow(newHand);

        return 0;
    }
}

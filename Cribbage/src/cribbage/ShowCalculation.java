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
    public int calculate(IPlayer player, int[] scores) {
        CalculateJack jack = new CalculateJack(hand,starter);
        CalculateFlush flush = new CalculateFlush(hand,starter);
        Card newCard= starter.getCardList().get(0).clone();
        Hand newHand = hand;
        newHand.insert(newCard.clone(), false);
        CalculatePairShow pair = new CalculatePairShow(newHand);
        CalculateRunShow run = new CalculateRunShow(newHand);
        CalculateFifteen fifteen = new CalculateFifteen(newHand);

        return jack.calculate(player, scores) + flush.calculate(player, scores) + pair.calculate(player, scores) + run.calculate(player, scores) + fifteen.calculate(player, scores);
    }
}

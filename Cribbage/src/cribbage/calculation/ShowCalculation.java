package cribbage.calculation;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.IPlayer;
import cribbage.calculation.*;

public class ShowCalculation extends Calculation{
    private Hand starter;
    public ShowCalculation(Hand hand, Hand starter){
        super(hand);
        this.starter = starter;
    }

    @Override
    public void calculate(IPlayer player, int[] scores) {
        CalculateJack jack = new CalculateJack(hand,starter);
        CalculateFlush flush = new CalculateFlush(hand,starter);
        Card newCard= starter.getCardList().get(0).clone();
        jack.calculate(player, scores);
        flush.calculate(player, scores);
        Hand newHand = hand;
        newHand.insert(newCard.clone(), false);
        CalculatePairShow pair = new CalculatePairShow(newHand);
        CalculateRunShow run = new CalculateRunShow(newHand);
        CalculateFifteen fifteen = new CalculateFifteen(newHand);
        pair.calculate(player, scores);
        run.calculate(player, scores);
        fifteen.calculate(player, scores);
    }
}

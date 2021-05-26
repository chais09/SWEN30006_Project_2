package cribbage.calculation;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
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
        if(Cribbage.Rule.JACK.score != 0){
            jack.calculate(player, scores);
        }
        if(Cribbage.Rule.FLUSH4.score != 0){
            flush.calculate(player, scores);
        }
        // combine the starter and hand
        hand.insert(newCard.clone(), false);
        CalculatePairShow pair = new CalculatePairShow(hand);
        CalculateRunShow run = new CalculateRunShow(hand);
        CalculateFifteen fifteen = new CalculateFifteen(hand);
        pair.calculate(player, scores);
        run.calculate(player, scores);
        if(Cribbage.Rule.FIFTEEN.score != 0){
            fifteen.calculate(player, scores);
        }
    }
}

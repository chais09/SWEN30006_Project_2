package cribbage.calculation;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import cribbage.IPlayer;
import cribbage.Logging;

public class CalculateJack extends Calculation {
    private Hand starter;
    public CalculateJack(Hand hand, Hand starter){
        super(hand);
        this.starter = starter;
    }

    @Override
    public void calculate(IPlayer player, int[] scores) {
        Enum start = starter.getCardList().get(0).getSuit();
        Card jack = hand.getCard(start, Cribbage.Rank.JACK);
        if(jack != null){
            scores[player.getId()] += 1;
            Logging.getInstance().addToLog(String.format("score,P%d,%d,1,jack,[%s]",player.getId(),scores[player.getId()],canonical(jack)));
        }
    }

}

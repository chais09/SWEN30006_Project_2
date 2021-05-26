package cribbage.calculation;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.IPlayer;
import cribbage.Logging;

import java.util.ArrayList;

public class CalculateRunShow extends Calculation {
    public CalculateRunShow(Hand hand){
        super(hand);
    }

    @Override
    public void calculate(IPlayer player, int[] scores) {
        Hand[] extract;
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 5; i>2; i--) {
            if (hand.getSequences(i).size() != 0) {
                extract = hand.extractSequences(i);
                for (Hand h : extract) {
                    h.sort(Hand.SortType.POINTPRIORITY, false);
                    scores[player.getId()] += i;
                    for (Card c : h.getCardList()) {
                        strings.add(canonical(c));
                    }
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d,%s", player.getId(), scores[player.getId()], i, i, strings.toString().replaceAll(" ", "")));
                    strings.clear();
                }
            }
        }
    }
}

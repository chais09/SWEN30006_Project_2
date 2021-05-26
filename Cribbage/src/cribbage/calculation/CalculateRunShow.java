package cribbage.calculation;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import cribbage.IPlayer;
import cribbage.Logging;

import java.util.ArrayList;

public class CalculateRunShow extends Calculation {
    public CalculateRunShow(Hand hand){
        super(hand);
    }

    @Override
    // calculate all possible run in showing part
    public void calculate(IPlayer player, int[] scores) {

        Hand[] extract;
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 5; i>2; i--) {
            if (hand.getSequences(i).size() != 0) {
                extract = hand.extractSequences(i);
                for (Hand h : extract) {
                    // eliminate the case that King and Ace can be a sequence
                    if(h.getCardsWithRank(Cribbage.Rank.KING).size() != 0 && h.getCardsWithRank(Cribbage.Rank.ACE).size() != 0){
                        continue;
                    }
                    h.sort(Hand.SortType.POINTPRIORITY, false);
                    for (Card c : h.getCardList()) {
                        strings.add(canonical(c));
                    }
                    switch (i) {
                        case 5:
                            if(Cribbage.Rule.RUN5.score != 0) {
                                scores[player.getId()] += Cribbage.Rule.RUN5.score;
                                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d,%s", player.getId(), scores[player.getId()], Cribbage.Rule.RUN5.score, i, strings.toString().replaceAll(" ", "")));
                            }
                        case 4:
                            if(Cribbage.Rule.RUN4.score != 0) {
                                scores[player.getId()] += Cribbage.Rule.RUN4.score;
                                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d,%s", player.getId(), scores[player.getId()], Cribbage.Rule.RUN4.score, i, strings.toString().replaceAll(" ", "")));
                            }
                        case 3:
                            if(Cribbage.Rule.RUN3.score != 0) {
                                scores[player.getId()] += Cribbage.Rule.RUN3.score;
                                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d,%s", player.getId(), scores[player.getId()], Cribbage.Rule.RUN3.score, i, strings.toString().replaceAll(" ", "")));

                            }
                    }
                    strings.clear();
                }
            }
        }
    }
}

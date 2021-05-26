package cribbage.calculation;

import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import cribbage.IPlayer;
import cribbage.Logging;

public class CalculateRunPlay extends Calculation {
    public CalculateRunPlay(Hand hand){
        super(hand);
    }

    @Override
    //calculate run during playing
    public void calculate(IPlayer player, int[] scores) {
        Hand[] extract;
        for (int i = 7;i>2;i--){
            if(hand.getSequences(i).size() != 0){
                extract = hand.extractSequences(i);
                for (Hand h : extract) {
                    // eliminate the case that King and Ace can be a sequence
                    if (h.getCardsWithRank(Cribbage.Rank.KING).size() != 0 && h.getCardsWithRank(Cribbage.Rank.ACE).size() != 0) {
                        continue;
                    }
                    switch (i) {
                        case 7:
                            if(Cribbage.Rule.RUN7.score != 0) {
                                scores[player.getId()] += Cribbage.Rule.RUN7.score;
                                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d", player.getId(), scores[player.getId()], Cribbage.Rule.RUN7.score, i));
                            }
                        case 6:
                            if(Cribbage.Rule.RUN6.score != 0) {
                                scores[player.getId()] += Cribbage.Rule.RUN6.score;
                                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d", player.getId(), scores[player.getId()], Cribbage.Rule.RUN6.score, i));

                            }
                        case 5:
                            if(Cribbage.Rule.RUN5.score != 0) {
                                scores[player.getId()] += Cribbage.Rule.RUN5.score;
                                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d", player.getId(), scores[player.getId()], Cribbage.Rule.RUN5.score, i));
                            }
                        case 4:
                            if(Cribbage.Rule.RUN4.score != 0) {
                                scores[player.getId()] += Cribbage.Rule.RUN4.score;
                                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d", player.getId(), scores[player.getId()], Cribbage.Rule.RUN4.score, i));

                            }
                        case 3:
                            if(Cribbage.Rule.RUN3.score != 0) {
                                scores[player.getId()] += Cribbage.Rule.RUN3.score;
                                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d", player.getId(), scores[player.getId()], Cribbage.Rule.RUN3.score, i));

                            }
                    }

                }

            }
        }
    }

}

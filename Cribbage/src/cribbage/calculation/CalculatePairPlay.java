package cribbage.calculation;

import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import cribbage.IPlayer;
import cribbage.Logging;

public class CalculatePairPlay extends Calculation {
    public CalculatePairPlay(Hand hand){
        super(hand);
    }

    @Override
    public void calculate(IPlayer player, int[] scores) {
        Hand[] extract;
        if(hand.getQuads().size() != 0 && Cribbage.Rule.PAIR4.score != 0){
            extract = hand.extractQuads();
            for(Hand h : extract){
                if(hand.getLast().isInHand(h)){
                    scores[player.getId()] += Cribbage.Rule.PAIR4.score;;
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,pair4",player.getId(),scores[player.getId()],Cribbage.Rule.PAIR4.score));
                }
            }

        }else if(hand.getTrips().size() != 0 && Cribbage.Rule.PAIR3.score != 0){
            extract = hand.extractTrips();
            for(Hand h : extract) {
                if (hand.getLast().isInHand(h)) {
                    scores[player.getId()] += Cribbage.Rule.PAIR3.score;;
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,pair3", player.getId(), scores[player.getId()],Cribbage.Rule.PAIR3.score));
                }
            }
        }else if(hand.getPairs().size() != 0 && Cribbage.Rule.PAIR2.score != 0){
            extract = hand.extractPairs();
            for(Hand h : extract) {
                if (hand.getLast().isInHand(h) && hand.getCardList().get(hand.getCardList().size()-2).isInHand(h)) {
                    scores[player.getId()] += Cribbage.Rule.PAIR2.score;
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,pair2", player.getId(), scores[player.getId()],Cribbage.Rule.PAIR2.score));
                    System.out.printf("calculate pair\n");
                }
            }
        }
    }
}

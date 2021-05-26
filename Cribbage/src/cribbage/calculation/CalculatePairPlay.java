package cribbage.calculation;

import ch.aplu.jcardgame.Hand;
import cribbage.IPlayer;
import cribbage.Logging;

public class CalculatePairPlay extends Calculation {
    public CalculatePairPlay(Hand hand){
        super(hand);
    }

    @Override
    public void calculate(IPlayer player, int[] scores) {
        Hand[] extract;
        if(hand.getQuads().size() != 0){
            extract = hand.extractQuads();
            for(Hand h : extract){
                if(hand.getLast().isInHand(h)){
                    scores[player.getId()] += 12;
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,12,pair4",player.getId(),scores[player.getId()]));
                }
            }

        }else if(hand.getTrips().size() != 0){
            extract = hand.extractTrips();
            for(Hand h : extract) {
                if (hand.getLast().isInHand(h)) {
                    scores[player.getId()] += 6;
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,6,pair3", player.getId(), scores[player.getId()]));
                }
            }
        }else if(hand.getPairs().size() != 0){
            extract = hand.extractPairs();
            for(Hand h : extract) {
                if (hand.getLast().isInHand(h) && hand.getCardList().get(hand.getCardList().size()-2).isInHand(h)) {
                    scores[player.getId()] += 2;
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,2,pair2", player.getId(), scores[player.getId()]));
                    System.out.printf("calculate pair\n");
                }
            }
        }
    }
}

package cribbage;

import ch.aplu.jcardgame.Hand;

public class CalculatePairPlay extends Calculation {
    public CalculatePairPlay(Hand hand){
        super(hand);
    }

    @Override
    public int calculate(IPlayer player, int[] scores) {
        Hand[] extract;
        if(hand.getQuads().size() != 0){
            extract = hand.extractQuads();
            for(Hand h : extract){
                if(hand.getLast().isInHand(h)){
                    scores[player.id] += 12;
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,12,pair4",player.id,scores[player.id]));
                    return 12;
                }
            }

        }else if(hand.getTrips().size() != 0){
            extract = hand.extractTrips();
            for(Hand h : extract) {
                if (hand.getLast().isInHand(h)) {
                    scores[player.id] += 6;
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,6,pair3", player.id, scores[player.id]));
                    return 6;
                }
            }
        }else if(hand.getPairs().size() != 0){
            extract = hand.extractPairs();
            for(Hand h : extract) {
                if (hand.getLast().isInHand(h) && hand.getCardList().get(hand.getCardList().size()-2).isInHand(h)) {
                    scores[player.id] += 2;
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,2,pair2", player.id, scores[player.id]));
                    System.out.printf("calculate pair\n");
                    return 2;
                }
            }
        }
        return 0;
    }
}

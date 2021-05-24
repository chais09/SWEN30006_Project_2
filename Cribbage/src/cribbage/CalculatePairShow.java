package cribbage;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import java.util.ArrayList;

public class CalculatePairShow extends Calculation {
    public CalculatePairShow(Hand hand){
        super(hand);
    }

    @Override
    public int calculate(IPlayer player, int[] scores) {
        Hand[] extract;
        ArrayList<String> strings = new ArrayList<>();
        if(hand.getQuads().size() != 0){
            extract = hand.extractQuads();
            for(Hand h : extract){
                scores[player.id] += 12;
                for (Card c : h.getCardList()){
                    strings.add(canonical(c));
                }
                Logging.getInstance().addToLog(String.format("score,P%d,%d,12,pair4,%s",player.id,scores[player.id],strings.toString().replaceAll(" ", "")));
            }
            return 12;
        }
        else if(hand.getTrips().size() != 0){
            extract = hand.extractTrips();
            for(Hand h : extract) {
                scores[player.id] += 6;
                for (Card c : h.getCardList()) {
                    strings.add(canonical(c));
                }
                Logging.getInstance().addToLog(String.format("score,P%d,%d,6,pair3,%s", player.id, scores[player.id],strings.toString().replaceAll(" ", "")));
            }
            return 6;
        }else if(hand.getPairs().size() != 0){
            extract = hand.extractPairs();
            for(Hand h : extract) {
                scores[player.id] += 2;
                for (Card c : h.getCardList()) {
                    strings.add(canonical(c));
                }
                Logging.getInstance().addToLog(String.format("score,P%d,%d,2,pair2,%s", player.id, scores[player.id],strings.toString().replaceAll(" ", "")));
            }
            return 2;
        }
        return 0;
    }
}


package cribbage.calculation;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.IPlayer;
import cribbage.Logging;

import java.util.ArrayList;

public class CalculatePairShow extends Calculation {
    public CalculatePairShow(Hand hand){
        super(hand);
    }

    @Override
    public void calculate(IPlayer player, int[] scores) {
        Hand[] extract;
        ArrayList<String> strings = new ArrayList<>();
        if(hand.getQuads().size() != 0){
            extract = hand.extractQuads();
            for(Hand h : extract){
                h.sort(Hand.SortType.POINTPRIORITY, false);
                scores[player.getId()] += 12;
                for (Card c : h.getCardList()){
                    strings.add(canonical(c));
                }
                Logging.getInstance().addToLog(String.format("score,P%d,%d,12,pair4,%s",player.getId(),scores[player.getId()],strings.toString().replaceAll(" ", "")));
                strings.clear();
            }
        }
        else if(hand.getTrips().size() != 0){
            extract = hand.extractTrips();
            for(Hand h : extract) {
                h.sort(Hand.SortType.POINTPRIORITY, false);
                scores[player.getId()] += 6;
                for (Card c : h.getCardList()) {
                    strings.add(canonical(c));
                }
                Logging.getInstance().addToLog(String.format("score,P%d,%d,6,pair3,%s", player.getId(), scores[player.getId()],strings.toString().replaceAll(" ", "")));
                strings.clear();
            }
        }else if(hand.getPairs().size() != 0){
            extract = hand.extractPairs();
            for(Hand h : extract) {
                h.sort(Hand.SortType.POINTPRIORITY, false);
                scores[player.getId()] += 2;
                for (Card c : h.getCardList()) {
                    strings.add(canonical(c));
                }
                Logging.getInstance().addToLog(String.format("score,P%d,%d,2,pair2,%s", player.getId(), scores[player.getId()],strings.toString().replaceAll(" ", "")));
                strings.clear();
            }
        }
    }
}


package cribbage.calculation;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
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
        if(hand.getQuads().size() != 0 && Cribbage.Rule.PAIR4.score != 0){
            extract = hand.extractQuads();
            for(Hand h : extract){
                h.sort(Hand.SortType.POINTPRIORITY, false);
                scores[player.getId()] += Cribbage.Rule.PAIR4.score;;
                for (Card c : h.getCardList()){
                    strings.add(canonical(c));
                }
                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,pair4,%s",player.getId(),scores[player.getId()],Cribbage.Rule.PAIR4.score,strings.toString().replaceAll(" ", "")));
                strings.clear();
            }
        }
        else if(hand.getTrips().size() != 0 && Cribbage.Rule.PAIR3.score != 0){
            extract = hand.extractTrips();
            for(Hand h : extract) {
                h.sort(Hand.SortType.POINTPRIORITY, false);
                scores[player.getId()] += Cribbage.Rule.PAIR3.score;;
                for (Card c : h.getCardList()) {
                    strings.add(canonical(c));
                }
                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,pair3,%s", player.getId(), scores[player.getId()],Cribbage.Rule.PAIR3.score,strings.toString().replaceAll(" ", "")));
                strings.clear();
            }
        }else if(hand.getPairs().size() != 0 && Cribbage.Rule.PAIR2.score != 0){
            extract = hand.extractPairs();
            for(Hand h : extract) {
                h.sort(Hand.SortType.POINTPRIORITY, false);
                scores[player.getId()] += Cribbage.Rule.PAIR2.score;;
                for (Card c : h.getCardList()) {
                    strings.add(canonical(c));
                }
                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,pair2,%s", player.getId(), scores[player.getId()],Cribbage.Rule.PAIR2.score,strings.toString().replaceAll(" ", "")));
                strings.clear();
            }
        }
    }
}


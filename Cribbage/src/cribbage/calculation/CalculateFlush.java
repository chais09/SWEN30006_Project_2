package cribbage.calculation;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import cribbage.IPlayer;
import cribbage.Logging;

import java.util.ArrayList;

public class CalculateFlush extends Calculation {
    private Hand starter;
    public CalculateFlush(Hand hand, Hand starter){
        super(hand);
        this.starter = starter;
    }

    @Override
    public void calculate(IPlayer player, int[] scores) {
        Hand extract;
        Cribbage.Suit suits[] = Cribbage.Suit.values();
        ArrayList<String> strings = new ArrayList<>();
        for(Cribbage.Suit suit : suits){
            if(hand.getNumberOfCardsWithSuit(suit) > 3){
                extract = hand.extractCardsWithSuit(suit);
                // check if starter card also got same suit
                if(starter.getNumberOfCardsWithSuit(suit) == 1){
                    scores[player.getId()] += Cribbage.Rule.FLUSH5.score;
                    for (Card c : extract.getCardList()){
                        strings.add(canonical(c));
                    }
                    Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,flush5,%s",player.getId(),scores[player.getId()],Cribbage.Rule.FLUSH5.score,strings.toString().replaceAll(" ", "")));
                }
                for (Card c : extract.getCardList()){
                    strings.add(canonical(c));
                }
                scores[player.getId()] += Cribbage.Rule.FLUSH4.score;
                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,flush4,%s",player.getId(),scores[player.getId()],Cribbage.Rule.FLUSH4.score,strings.toString().replaceAll(" ", "")));
            }
        }
    }


}

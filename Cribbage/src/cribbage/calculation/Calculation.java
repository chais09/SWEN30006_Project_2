package cribbage.calculation;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import cribbage.IPlayer;

import java.util.stream.Collectors;

public abstract class Calculation {


    protected Hand hand;
    public Calculation(Hand hand){
        this.hand = hand;
    }

    public abstract void calculate(IPlayer player, int[] scores);
    public String canonical(Cribbage.Suit s) { return s.toString().substring(0, 1); }

    public String canonical(Cribbage.Rank r) {
        switch (r) {
            case ACE:case KING:case QUEEN:case JACK:case TEN:
                return r.toString().substring(0, 1);
            default:
                return String.valueOf(r.value);
        }
    }

    public String canonical(Card c) { return canonical((Cribbage.Rank) c.getRank()) + canonical((Cribbage.Suit) c.getSuit()); }

}

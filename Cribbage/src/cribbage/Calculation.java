package cribbage;

import ch.aplu.jcardgame.Hand;

public abstract class Calculation {
    protected Hand hand;
    public Calculation(Hand hand){
        this.hand = hand;
    }

    public abstract int calculate();
}

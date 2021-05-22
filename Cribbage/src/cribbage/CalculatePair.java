package cribbage;

import ch.aplu.jcardgame.Hand;

public class CalculatePair extends Calculation {
    public CalculatePair(Hand hand){
        super(hand);
    }

    @Override
    public int calculate() {
        if(hand.getQuads().size() != 0){
            return hand.getQuads().size() / 4;
        }else if(hand.getTrips().size() != 0){
            return hand.getTrips().size() / 3;
        }else if(hand.getPairs().size() != 0){
            return hand.getPairs().size() / 2;
        }
        return 0;
    }
}

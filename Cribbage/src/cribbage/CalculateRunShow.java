package cribbage;

import ch.aplu.jcardgame.Hand;

public class CalculateRunShow extends Calculation{
    public CalculateRunShow(Hand hand){
        super(hand);
    }

    @Override
    public int calculate() {
        if(hand.getSequences(5).size() != 0){
            return hand.getSequences(5).size();
        }else if(hand.getSequences(4).size() != 0){
            return hand.getSequences(4).size();
        }else if(hand.getSequences(3).size() != 0){
            return hand.getSequences(3).size();
        }
        return 0;
    }
}

package cribbage;

import ch.aplu.jcardgame.Hand;

public class CalculateRunPlay extends Calculation{
    public CalculateRunPlay(Hand hand){
        super(hand);
    }

    @Override
    public int calculate() {
        if(hand.getSequences(7).size() != 0) {
            return hand.getSequences(7).size();
        }else if(hand.getSequences(6).size() != 0) {
            return hand.getSequences(6).size();
        }else if(hand.getSequences(5).size() != 0){
            return hand.getSequences(5).size();
        }else if(hand.getSequences(4).size() != 0){
            return hand.getSequences(4).size();
        }else if(hand.getSequences(3).size() != 0){
            return hand.getSequences(3).size();
        }
        return 0;
    }

}

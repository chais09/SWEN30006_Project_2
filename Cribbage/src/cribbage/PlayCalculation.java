package cribbage;

import ch.aplu.jcardgame.Hand;

public class PlayCalculation extends Calculation{
    public PlayCalculation(Hand hand){
        super(hand);
    }

    @Override
    public int calculate() {
        int result = 0;
        CalculateTotal total = new CalculateTotal(hand);
        CalculateRunPlay run = new CalculateRunPlay(hand);
        CalculatePair pair = new CalculatePair(hand);
        return result + total.calculate() + run.calculate() + pair.calculate();
    }
}

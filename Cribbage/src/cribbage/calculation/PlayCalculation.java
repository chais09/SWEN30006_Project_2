package cribbage.calculation;

import ch.aplu.jcardgame.Hand;
import cribbage.IPlayer;
import cribbage.calculation.CalculatePairPlay;
import cribbage.calculation.CalculateRunPlay;
import cribbage.calculation.Calculation;

public class PlayCalculation extends Calculation {
    public PlayCalculation(Hand hand){
        super(hand);
    }

    @Override
    public void calculate(IPlayer player, int[] scores) {

        CalculateRunPlay run = new CalculateRunPlay(hand);
        CalculatePairPlay pair = new CalculatePairPlay(hand);
        run.calculate(player, scores);
        pair.calculate(player, scores);

    }
}

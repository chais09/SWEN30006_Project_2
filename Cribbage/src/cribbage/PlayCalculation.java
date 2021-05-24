package cribbage;

import ch.aplu.jcardgame.Hand;

public class PlayCalculation extends Calculation{
    public PlayCalculation(Hand hand){
        super(hand);
    }

    @Override
    public int calculate(IPlayer player, int[] scores) {
        int result = scores[player.id];
        CalculateRunPlay run = new CalculateRunPlay(hand);
        CalculatePairPlay pair = new CalculatePairPlay(hand);
        result = result + run.calculate(player, scores) + pair.calculate(player, scores);
        scores[player.id] = result;
        System.out.printf("update score: %d\n", result);
        return result;
    }
}

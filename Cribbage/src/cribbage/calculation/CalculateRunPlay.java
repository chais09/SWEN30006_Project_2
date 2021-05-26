package cribbage.calculation;

import ch.aplu.jcardgame.Hand;
import cribbage.IPlayer;
import cribbage.Logging;

public class CalculateRunPlay extends Calculation {
    public CalculateRunPlay(Hand hand){
        super(hand);
    }

    @Override
    public void calculate(IPlayer player, int[] scores) {
        for (int i = 7;i>2;i--){
            if(hand.getSequences(i).size() != 0){
                scores[player.getId()] += i;
                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d",player.getId(),scores[player.getId()],i,i));
            }
        }
    }

}

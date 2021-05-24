package cribbage;

import ch.aplu.jcardgame.Hand;

public class CalculateRunPlay extends Calculation{
    public CalculateRunPlay(Hand hand){
        super(hand);
    }

    @Override
    public int calculate(IPlayer player, int[] scores) {
        System.out.println("calculate run\n");
        for (int i = 7;i>2;i--){
            if(hand.getSequences(i).size() != 0){
                scores[player.id] += i;
                Logging.getInstance().addToLog(String.format("score,P%d,%d,%d,run%d",player.id,scores[player.id],i,i));
                return i;
            }
        }
        return 0;
    }

}

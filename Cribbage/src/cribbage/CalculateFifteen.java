package cribbage;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class CalculateFifteen extends Calculation{
    public CalculateFifteen(Hand hand){
        super(hand);
    }

    @Override
    public int calculate(IPlayer player, int[] scores) {

        int score = 0;
        final int aimNumber = 15;
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<Card> current = new ArrayList<>();
        ArrayList<Card> input = new ArrayList<>();
        for(Card card : hand.getCardList()){
            input.add(card);
        }
        ArrayList<ArrayList<Card>> result = new ArrayList<>();
        findCombinations(input, current, result, 0, input.size(), input.size());
        for(ArrayList<Card> combination: result){
            if(sum(combination) == aimNumber){
                for(Card card : combination){
                    String s = String.format("%s", super.canonical(card));
                    strings.add(s);
                }
                score += 2;
                scores[player.id] += 2;
                String format = String.format("score,P%d,%d,%d,fifteen,%s",player.id,scores[player.id],strings.size(),strings);
                Logging.getInstance().addToLog(format);
            }
        }
        return score;
    }
    private void findCombinations(ArrayList<Card> input, ArrayList<Card> current,ArrayList<ArrayList<Card>> result
            , int i, int n, int k) {
        if (k > n) {
            return;
        }
        if (k == 0) {
            return;
        }
        for (int j = i; j < n; j++) {
            current.add(input.get(j));
            result.add((ArrayList<Card>) current.clone());
            findCombinations(input, current ,result , j + 1, n, k - 1);
            current.remove(current.size()-1);
        }

    }
    private int sum(ArrayList<Card> in){
        int result = 0;
        for(Card i : in){
            result += i.getValue();
        }
        return result;
    }
}

package cribbage;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class CalculateFifteen extends Calculation{
    public CalculateFifteen(Hand hand){
        super(hand);
    }

    @Override
    public int calculate() {
        int score = 0;
        final int aimNumber = 15;
        ArrayList<Integer> current = new ArrayList<Integer>();
        ArrayList<Integer> input = new ArrayList<Integer>();
        for(Card card : hand.getCardList()){
            input.add(card.getValue());
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        findCombinations(input, current, result, 0, input.size(), input.size());
        for(ArrayList<Integer> combination: result){
            if(sum(combination) == aimNumber){
                score += 2;
            }
        }
        return score;
    }
    private void findCombinations(ArrayList<Integer> input, ArrayList<Integer> current,ArrayList<ArrayList<Integer>> result
            , int i, int n, int k) {
        if (k > n) {
            return;
        }
        if (k == 0) {
            return;
        }
        for (int j = i; j < n; j++) {
            current.add(input.get(j));
            result.add((ArrayList<Integer>) current.clone());
            findCombinations(input, current ,result , j + 1, n, k - 1);
            current.remove(current.size()-1);
        }

    }
    private int sum(ArrayList<Integer> in){
        int result = 0;
        for(int i : in){
            result += i;
        }
        return result;
    }
}

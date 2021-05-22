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
        final int aimNumber = 15;
        int totalScore = 0;
        ArrayList<Card> cardList = hand.getCardList();
        for(int i = 0; i<cardList.size(); i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            int sum = cardList.get(i).getValue();
            list.add(i);
            for (int j = i+1; j<cardList.size(); j++){
                if(sum+cardList.get(j).getValue() > aimNumber){
                    continue;
                }else if(sum+cardList.get(j).getValue() <= aimNumber){
                    list.add(j);
                    sum += cardList.get(j).getValue();
                }
                if(sum == 15){
                    totalScore += 2;
                    list.remove(list.size()-1);
                }
                if(j == cardList.size()-1){

                }
            }
        }
        return 0;
    }
    private ArrayList<Integer> getAllCombination(ArrayList<Integer> list, ArrayList<Card> cardList, int size){
        if(size == 1){
            for(int i =0; i<cardList.size(); i++){
                list.set(i, cardList.get(i).getValue());
            }
        }else{

        }
    }
}

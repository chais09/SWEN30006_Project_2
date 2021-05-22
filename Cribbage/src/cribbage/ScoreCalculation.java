package cribbage;

import ch.aplu.jcardgame.Hand;

public class ScoreCalculation extends Cribbage {
    private Segment s;
    private Hand hand;
    private Hand starter;
    public ScoreCalculation(Segment s, Hand starter){
        this.s = s;
        s.segment.sort(Hand.SortType.POINTPRIORITY, false);
        this.hand = s.segment;
        this.starter = starter;
    }

    public int calculatePair(){
        if(hand.getQuads().size() != 0){
            return hand.getQuads().size() / 4;
        }else if(hand.getTrips().size() != 0){
            return hand.getTrips().size() / 3;
        }else if(hand.getPairs().size() != 0){
            return hand.getPairs().size() / 2;
        }
        return 0;
    }
    public int calculateRun(){
        if(hand.getSequences(5).size() != 0){
            return hand.getSequences(5).size();
        }else if(hand.getSequences(4).size() != 0){
            return hand.getSequences(4).size();
        }else if(hand.getSequences(3).size() != 0){
            return hand.getSequences(3).size();
        }
        return 0;
    }
    public int calculateSuit(){
        if(hand.getNumberOfCardsWithSuit(Suit.SPADES) > 3){
            if(starter.getNumberOfCardsWithSuit(Suit.SPADES) == 1){
                return 5;
            }
            return 4;
        }else if(hand.getNumberOfCardsWithSuit(Suit.HEARTS) > 3){
            if(starter.getNumberOfCardsWithSuit(Suit.HEARTS) == 1){
                return 5;
            }
            return 4;
        }else if(hand.getNumberOfCardsWithSuit(Suit.DIAMONDS) > 3){
            if(starter.getNumberOfCardsWithSuit(Suit.DIAMONDS) == 1){
                return 5;
            }
            return 4;
        }else if(hand.getNumberOfCardsWithSuit(Suit.CLUBS) > 3){
            if(starter.getNumberOfCardsWithSuit(Suit.CLUBS) == 1){
                return 5;
            }
            return 4;
        }
        return 0;
    }
    public int sameJackSuit(){
        Enum start = starter.getCardList().get(0).getSuit();
        if(hand.getCard(start, Rank.JACK) != null){
            return 1;
        }
        return 0;
    }

}

package cribbage;

// Cribbage.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import cribbage.calculation.PlayCalculation;
import cribbage.calculation.ShowCalculation;

import java.awt.Color;
import java.awt.Font;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cribbage extends CardGame {
	static Cribbage cribbage;  // Provide access to singleton

	public enum Suit {
		CLUBS, DIAMONDS, HEARTS, SPADES
	}

	public enum Rank {
		// Order of cards is tied to card images
		ACE(1,1), KING(13,10), QUEEN(12,10), JACK(11,10), TEN(10,10), NINE(9,9), EIGHT(8,8), SEVEN(7,7), SIX(6,6), FIVE(5,5), FOUR(4,4), THREE(3,3), TWO(2,2);
		public final int order;
		public final int value;
		Rank(int order, int value) {
			this.order = order;
			this.value = value;
		}
	}

	public static int cardValue(Card c) { return ((Cribbage.Rank) c.getRank()).value; }

	/*
	Canonical String representations of Suit, Rank, Card, and Hand
	*/
	String canonical(Suit s) { return s.toString().substring(0, 1); }

	String canonical(Rank r) {
		switch (r) {
			case ACE:case KING:case QUEEN:case JACK:case TEN:
				return r.toString().substring(0, 1);
			default:
				return String.valueOf(r.value);
		}
	}

    String canonical(Card c) { return canonical((Rank) c.getRank()) + canonical((Suit) c.getSuit()); }

    String canonical(Hand h) {
		Hand h1 = new Hand(deck); // Clone to sort without changing the original hand
		for (Card C: h.getCardList()) h1.insert(C.getSuit(), C.getRank(), false);
		h1.sort(Hand.SortType.POINTPRIORITY, false);
		return "[" + h1.getCardList().stream().map(this::canonical).collect(Collectors.joining(",")) + "]";
    }

	class MyCardValues implements Deck.CardValues { // Need to generate a unique value for every card
		public int[] values(Enum suit) {  // Returns the value for each card in the suit
			return Stream.of(Rank.values()).mapToInt(r -> (((Rank) r).order-1)*(Suit.values().length)+suit.ordinal()).toArray();
		}
	}

	static Random random;

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }

	static boolean ANIMATE;

	void transfer(Card c, Hand h) {
		if (ANIMATE) {
			c.transfer(h, true);
		} else {
			c.removeFromHand(true);
			h.insert(c, true);
		}
  }
  
  private void dealingOut(Hand pack, Hand[] hands) {
	  for (int i = 0; i < nStartCards; i++) {
		  for (int j=0; j < nPlayers; j++) {
			  Card dealt = randomCard(pack);
			  dealt.setVerso(false);  // Show the face
			  transfer(dealt, hands[j]);
		  }
	  }
  }

	static int SEED;

	public static Card randomCard(Hand hand){
      int x = random.nextInt(hand.getNumberOfCards());
      return hand.get(x);
  }

  private final String version = "0.1";
  static public final int nPlayers = 2;
  public final int nStartCards = 6;
  public final int nDiscards = 2;
  private final int handWidth = 400;
  private final int cribWidth = 150;
  private final int segmentWidth = 180;
  private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover", new MyCardValues());
  private final Location[] handLocations = {
			  new Location(360, 75),
			  new Location(360, 625)
	  };
  private final Location[] scoreLocations = {
			  new Location(590, 25),
			  new Location(590, 675)
	  };
  private final Location[] segmentLocations = {  // need at most three as 3x31=93 > 2x4x10=80
			new Location(150, 350),
			new Location(400, 350),
			new Location(650, 350)
	};
  private final Location starterLocation = new Location(50, 625);
  private final Location cribLocation = new Location(700, 625);
  private final Location seedLocation = new Location(5, 25);
  // private final TargetArea cribTarget = new TargetArea(cribLocation, CardOrientation.NORTH, 1, true);
  private final Actor[] scoreActors = {null, null}; //, null, null };
  private final Location textLocation = new Location(350, 450);
  private final Hand[] hands = new Hand[nPlayers];
  private Hand[] initHands = new Hand[nPlayers];
  private Hand starter;
  private Hand crib;

  public static void setStatus(String string) { cribbage.setStatusText(string); }

	static private final IPlayer[] players = new IPlayer[nPlayers];
	private final int[] scores = new int[nPlayers];

	final Font normalFont = new Font("Serif", Font.BOLD, 24);
	final Font bigFont = new Font("Serif", Font.BOLD, 36);

	private void initScore() {
	 for (int i = 0; i < nPlayers; i++) {
		 scores[i] = 0;
		 scoreActors[i] = new TextActor("0", Color.WHITE, bgColor, bigFont);
		 addActor(scoreActors[i], scoreLocations[i]);
	 }
	}


	private void updateScore(int player) {
		removeActor(scoreActors[player]);
		scoreActors[player] = new TextActor(String.valueOf(scores[player]), Color.WHITE, bgColor, bigFont);
		addActor(scoreActors[player], scoreLocations[player]);
	}

	private void deal(Hand pack, Hand[] hands) {
		for (int i = 0; i < nPlayers; i++) {
			hands[i] = new Hand(deck);
			// players[i] = (1 == i ? new HumanPlayer() : new RandomPlayer());
			players[i].setId(i);
			players[i].startSegment(deck, hands[i]);
		}
		RowLayout[] layouts = new RowLayout[nPlayers];
		for (int i = 0; i < nPlayers; i++)
		{
			layouts[i] = new RowLayout(handLocations[i], handWidth);
			layouts[i].setRotationAngle(0);
			// layouts[i].setStepDelay(10);
			hands[i].setView(this, layouts[i]);
			hands[i].draw();
		}
		layouts[0].setStepDelay(0);

		dealingOut(pack, hands);
		for (int i = 0; i < nPlayers; i++) {
			hands[i].sort(Hand.SortType.POINTPRIORITY, true);
		}
		layouts[0].setStepDelay(0);
	}

	private void discardToCrib() {
		Hand cards = new Hand(deck);
		crib = new Hand(deck);
		RowLayout layout = new RowLayout(cribLocation, cribWidth);
		layout.setRotationAngle(0);
		crib.setView(this, layout);
		// crib.setTargetArea(cribTarget);
		crib.draw();
		for (IPlayer player: players) {
			for (int i = 0; i < nDiscards; i++) {
				Card card = player.discard();
				cards.insert(card.clone(),false);
				transfer(card, crib);
			}
			Logging.getInstance().addToLog(String.format("discard,P%d,%s",player.id,canonical(cards)));
			cards = new Hand(deck);
			crib.sort(Hand.SortType.POINTPRIORITY, true);
		}
		for (int i = 0; i < nPlayers; i++) {
			initHands[i] = new Hand(deck);
			for(Card c : hands[i].getCardList()){
				initHands[i].insert(c.clone(),false);
			}
		}


	}

	private void starter(Hand pack) {
		starter = new Hand(deck);  // if starter is a Jack, the dealer gets 2 points
		RowLayout layout = new RowLayout(starterLocation, 0);
		layout.setRotationAngle(0);
		starter.setView(this, layout);
		starter.draw();
		Card dealt = randomCard(pack);
		Logging.getInstance().addToLog(String.format("starter,%s",canonical(dealt)));
		if (dealt.getRank().compareTo(Rank.JACK) == 0){
			scores[1] += 2;
			Logging.getInstance().addToLog(String.format("score,P1,%d,2,starter,[%s]",scores[1],canonical(dealt)));
		}
		dealt.setVerso(false);
		transfer(dealt, starter);
		updateScore(1);
	}

	int total(Hand hand) {
		int total = 0;
		for (Card c: hand.getCardList()) total += cardValue(c);
		return total;
	}

	class Segment {
			Hand segment;
			boolean go;
			int lastPlayer;
			boolean newSegment;

			void reset(final List<Hand> segments) {
				segment = new Hand(deck);
				segment.setView(Cribbage.this, new RowLayout(segmentLocations[segments.size()], segmentWidth));
				segment.draw();
				go = false;        // No-one has said "go" yet
				lastPlayer = -1;   // No-one has played a card yet in this segment
				newSegment = false;  // Not ready for new segment yet
			}
	}

	private void play() {
		final int thirtyone = 31;
		List<Hand> segments = new ArrayList<>();
		int currentPlayer = 0; // Player 1 is dealer
		Segment s = new Segment();
		s.reset(segments);
		int value = 0;
		while (!(players[0].emptyHand() && players[1].emptyHand())) {
			Card nextCard = players[currentPlayer].lay(thirtyone-total(s.segment));

			if (nextCard == null) {

				if (s.go) {
					// Another "go" after previous one with no intervening cards
					// lastPlayer gets 1 point for a "go"

					scores[currentPlayer] += 1;
					Logging.getInstance().addToLog(String.format("score,P%d,%d,1,go",currentPlayer,scores[currentPlayer]));
					updateScore(currentPlayer);
					s.newSegment = true;
				} else {
					// currentPlayer says "go"
					s.go = true;
				}
				currentPlayer = (currentPlayer+1) % 2;
			} else {
				s.lastPlayer = currentPlayer; // last Player to play a card in this segment
				transfer(nextCard, s.segment);

				value += cardValue(nextCard);
				Logging.getInstance().addToLog(String.format("play,P%d,%d,%s",currentPlayer,value,canonical(nextCard)));
				PlayCalculation playCalculation = new PlayCalculation(s.segment);
				playCalculation.calculate(players[currentPlayer], scores);
				updateScore(currentPlayer);
				if (total(s.segment) == thirtyone) {
					// lastPlayer gets 2 points for a 31
					scores[currentPlayer] += 2;
					Logging.getInstance().addToLog(String.format("score,P%d,%d,2,thirtyone",currentPlayer,scores[currentPlayer]));
					s.newSegment = true;
					currentPlayer = (currentPlayer+1) % 2;
				} else {
					if (total(s.segment) == 15){
						scores[currentPlayer] += 2;
						Logging.getInstance().addToLog(String.format("score,P%d,%d,2,fifteen",currentPlayer,scores[currentPlayer]));
					}
					if (!s.go) { // if it is "go" then same player gets another turn
						currentPlayer = (currentPlayer+1) % 2;
					}
				}

			}

			if (s.newSegment) {
				value = 0;
				segments.add(s.segment);
				s.reset(segments);
			}

		}
		if(total(s.segment) != thirtyone || total(s.segment) != 15) {
			currentPlayer = (currentPlayer + 1) % 2;
			scores[currentPlayer] += 1;
			Logging.getInstance().addToLog(String.format("score,P%d,%d,1,go", currentPlayer, scores[currentPlayer]));
			updateScore(currentPlayer);
		}

	}

	void showHandsCrib() {
		Logging.getInstance().addToLog(String.format("show,P0,%s+%s", canonical(starter.getFirst()), canonical(initHands[0])));
		ShowCalculation showPlayerZero =  new ShowCalculation(initHands[0], starter);
		showPlayerZero.calculate(players[0], scores);
		// score player 0 (non dealer)
		// score player 1 (dealer)
		Logging.getInstance().addToLog(String.format("show,P1,%s+%s", canonical(starter.getFirst()), canonical(initHands[1])));
		ShowCalculation showPlayerOne =  new ShowCalculation(initHands[1], starter);
		showPlayerOne.calculate(players[1], scores);
		// score crib (for dealer)
		Logging.getInstance().addToLog(String.format("show,P1,%s+%s", canonical(starter.getFirst()), canonical(crib)));
		ShowCalculation showPlayerDealer =  new ShowCalculation(crib,starter);
		showPlayerDealer.calculate(players[1],scores);
		updateScore(0);
		updateScore(1);
	}


  public Cribbage()
  {
    super(850, 700, 30);
    cribbage = this;
    setTitle("Cribbage (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");
    setSimulationPeriod(5);
    initScore();

	  Hand pack = deck.toHand(false);
	  RowLayout layout = new RowLayout(starterLocation, 0);
	  layout.setRotationAngle(0);
	  pack.setView(this, layout);
	  pack.setVerso(true);
	  pack.draw();
	  addActor(new TextActor("Seed: " + SEED, Color.BLACK, bgColor, normalFont), seedLocation);

	  /* Play the round */
	  deal(pack, hands);
	  Logging.getInstance().addToLog(String.format("deal,P0,%s",canonical(hands[0])));
	  Logging.getInstance().addToLog(String.format("deal,P1,%s",canonical(hands[1])));
	  discardToCrib();
	  starter(pack);
	  play();
	  showHandsCrib();


    addActor(new Actor("sprites/gameover.gif"), textLocation);
    setStatusText("Game over.");
    refresh();
  }

  public static void main(String[] args)
		  throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
		  	InstantiationException, IllegalAccessException {
	  /* Handle Properties */
	  Properties cribbageProperties = new Properties();
	  // Default properties
	  cribbageProperties.setProperty("Animate", "true");
	  cribbageProperties.setProperty("Player0", "cribbage.RandomPlayer");
	  cribbageProperties.setProperty("Player1", "cribbage.HumanPlayer");

	  // Read properties
	  try (FileReader inStream = new FileReader("cribbage.properties")) {
		  cribbageProperties.load(inStream);
	  }

	  // Control Graphics
	  ANIMATE = Boolean.parseBoolean(cribbageProperties.getProperty("Animate"));

	  // Control Randomisation
	  /* Read the first argument and save it as a seed if it exists */
	  if (args.length > 0 ) { // Use arg seed - overrides property
		  SEED = Integer.parseInt(args[0]);
	  } else { // No arg
		  String seedProp = cribbageProperties.getProperty("Seed");  //Seed property
		  if (seedProp != null) { // Use property seed
			  SEED = Integer.parseInt(seedProp);
		  } else { // and no property
			  SEED = new Random().nextInt(); // so randomise
		  }
	  }
	  Logging.getInstance().addToLog(String.format("seed,%d",SEED));
	  random = new Random(SEED);

	  // Control Player Types
	  Class<?> clazz;
	  clazz = Class.forName(cribbageProperties.getProperty("Player0"));
	  players[0] = (IPlayer) clazz.getConstructor().newInstance();
	  clazz = Class.forName(cribbageProperties.getProperty("Player1"));
	  players[1] = (IPlayer) clazz.getConstructor().newInstance();
	  // End properties
	  Logging.getInstance().addToLog(String.format("%s,P0",cribbageProperties.getProperty("Player0")));
	  Logging.getInstance().addToLog(String.format("%s,P1",cribbageProperties.getProperty("Player1")));
	  new Cribbage();

  }

}

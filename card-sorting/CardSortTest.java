import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class CardSortTest {
    public static void main(String[] args) {
        List<Card> deck = new ArrayList<Card>();
        for(Card.Suit suit : Card.Suit.values()) {
            for (int i = 1; i < 14; i++) {
                deck.add(new Card(suit, i));
            }
        }
        Comparator<Card> rankComparator = new Comparator<Card>() {
                                                  public int compare(Card firstCard, Card secondCard) {
                                                      return firstCard.rank() - secondCard.rank();
                                                  }
                                              };

        Comparator<Card> suitComparator = new Comparator<Card>() {
                                                  public int compare(Card firstCard, Card secondCard) {
                                                      return firstCard.suit().value() - secondCard.suit().value();
                                                  }
                                              };

        Collections.shuffle(deck);
        System.out.println("shuffled deck:");
        System.out.println(deck);
        System.out.println();

        Collections.sort(deck, rankComparator);
        System.out.println("rank sort:");
        System.out.println(deck);
        System.out.println();

        Collections.sort(deck, suitComparator);
        System.out.println("suit sort:");
        System.out.println(deck);
        System.out.println();
        
        Collections.shuffle(deck);
        System.out.println("shuffled deck:");
        System.out.println(deck);
        System.out.println();

        Collections.sort(deck, suitComparator);
        System.out.println("suit sort:");
        System.out.println(deck);
        System.out.println();

        Collections.sort(deck, rankComparator);
        System.out.println("rank sort:");
        System.out.println(deck);
    }
}

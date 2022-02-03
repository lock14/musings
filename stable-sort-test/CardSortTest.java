import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class CardSortTest {
    public static void main(String[] args) {
        List<Card> deck =  Arrays.stream(Card.Suit.values())
                                 .flatMap(suit -> IntStream.range(1, 14)
                                                           .mapToObj(i -> new Card(suit, i)))
                                 .collect(Collectors.toList());

        // shuffle the deck
        Collections.shuffle(deck);
        System.out.println("shuffled deck:");
        System.out.println(deck);
        System.out.println();

        // sort by rank
        Collections.sort(deck, Comparator.comparing(Card::rank));
        System.out.println("rank sort:");
        System.out.println(deck);
        System.out.println();
        
        // then sort by suit
        Collections.sort(deck, Comparator.comparing(Card::suit));
        System.out.println("suit sort:");
        System.out.println(deck);
        System.out.println();
        
        // shuffle the deck again to try other order
        Collections.shuffle(deck);
        System.out.println("shuffled deck:");
        System.out.println(deck);
        System.out.println();

        // sort by suit
        Collections.sort(deck, Comparator.comparing(Card::suit));
        System.out.println("suit sort:");
        System.out.println(deck);
        System.out.println();

        // then sort by rank
        Collections.sort(deck, Comparator.comparing(Card::rank));
        System.out.println("rank sort:");
        System.out.println(deck);
        System.out.println();

        // shuffle the deck again to try natural order
        Collections.shuffle(deck);
        System.out.println("shuffled deck:");
        System.out.println(deck);
        System.out.println();

        // sort once
        Collections.sort(deck);
        System.out.println("first sort:");
        System.out.println(deck);
        System.out.println();

        // sort again
        Collections.sort(deck);
        System.out.println("second sort:");
        System.out.println(deck);
        System.out.println();
    }
}

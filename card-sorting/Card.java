public class Card implements Comparable<Card> {
    private final Suit suit;
    private final int rank;

    public Card(Suit suit, int rank) {
        if (validRank(rank)) {
            this.suit = suit;
            this.rank = rank;
        } else {
            throw new IllegalArgumentException("Invalid rank: " + rank);
        }
    }

    public int rank() {
        return this.rank;
    }

    public Suit suit() {
        return this.suit;
    }

    public int compareTo(Card other) {
        if (this.suit != other.suit) {
            return this.suit.value() - other.suit.value();
        } else {
            return this.rank - other.rank;
        }
    }

    public String toString() {
        return suit + ":" + rank;
    }

    private boolean validRank(int rank) {
        return rank > 0 && rank < 14;
    }

    public static enum Suit {
        SPADES(4),
        HEARTS(3),
        DIAMONDS(2),
        CLUBS(1);
        private int value;
        
        private Suit(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}

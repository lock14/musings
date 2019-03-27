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
        return rank;
    }

    public Suit suit() {
        return suit;
    }

    public int compareTo(Card other) {
        if (this.rank != other.rank) {
            return this.rank - other.rank;
        }
        return this.suit.ordinal() - other.suit.ordinal();
    }

    public String toString() {
        return suit + ":" + modifiedRank(rank);
    }

    private String modifiedRank(int rank) {
        switch (rank) {
            case 1:
                return "A";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return "" + rank;
        }
    }

    private boolean validRank(int rank) {
        return rank > 0 && rank < 14;
    }

    public static enum Suit {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES
    }
}

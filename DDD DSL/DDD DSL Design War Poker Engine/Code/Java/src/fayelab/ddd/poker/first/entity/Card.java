package fayelab.ddd.poker.first.entity;

public class Card
{
    private int rank;
    private Suit suit;

    private Card(int rank, Suit suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    public static Card card(int rank, Suit suit)
    {
        return new Card(rank, suit);
    }

    public int getRank()
    {
        return rank;
    }

    public Suit getSuit()
    {
        return suit;
    }
}
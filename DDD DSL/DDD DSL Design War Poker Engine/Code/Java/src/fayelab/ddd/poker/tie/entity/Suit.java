package fayelab.ddd.poker.tie.entity;

import java.util.stream.Stream;

public enum Suit
{
    S('S'), H('H'), C('C'), D('D');

    private char abbrChar;

    private Suit(char abbrChar)
    {
        this.abbrChar = abbrChar;
    }

    public char getAbbrChar()
    {
        return abbrChar;
    }

    public static Suit getEnum(char abbrChar)
    {
        return Stream.of(Suit.values())
                     .filter(suit -> suit.abbrChar == abbrChar)
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException(String.format("Illegal abbrChar (%c) of Suit.", abbrChar)));
    }
}
package fayelab.ddd.poker.distribution;

import static fayelab.ddd.poker.distribution.entity.Card.card;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fayelab.ddd.poker.distribution.entity.Card;
import fayelab.ddd.poker.distribution.entity.Suit;

public class PokerEngineUI
{
    private static final String RANK_CHARS = "--23456789TJQKA";

    public static String play(List<String> handStrs)
    {
        return toStr(PokerEngine.run(toHands(handStrs)));
    }

    private static List<List<Card>> toHands(List<String> handStrs)
    {
        return handStrs.stream().map(PokerEngineUI::toHand).collect(Collectors.toList());
    }

    private static List<Card> toHand(String handStr)
    {
        String[] cardStrs = handStr.trim().split(" +");
        return Stream.of(cardStrs)
                     .map(cardStr -> card(toRank(cardStr.charAt(0)), toSuit(cardStr.charAt(1))))
                     .collect(Collectors.toList());
    }

    private static int toRank(char rankChar)
    {
        return RANK_CHARS.indexOf(rankChar);
    }

    private static Suit toSuit(char suitChar)
    {
        return Suit.getEnum(suitChar);
    }

    private static String toStr(List<Card> hand)
    {
        return hand.stream().map(PokerEngineUI::toCardStr).collect(Collectors.joining(" "));
    }

    private static String toCardStr(Card card)
    {
        return String.format("%c%c", toRankChar(card.getRank()), toSuitChar(card.getSuit()));
    }

    private static char toRankChar(int rank)
    {
        return RANK_CHARS.charAt(rank);
    }

    private static char toSuitChar(Suit suit)
    {
        return suit.getAbbrChar();
    }
}
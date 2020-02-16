package fayelab.ddd.poker.tie;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fayelab.ddd.poker.tie.entity.Card;

public class PokerEngine
{
    public static List<List<Card>> run(List<List<Card>> hands)
    {
        Comparator<List<Card>> comparator = Comparator.comparing(HandRankCalculator::calculate, HandRankComparator::compare);
        List<Card> maxHand = Collections.max(hands, comparator);
        return hands.stream()
                    .filter(hand -> comparator.compare(maxHand, hand) == 0)
                    .collect(Collectors.toList());
    }
}
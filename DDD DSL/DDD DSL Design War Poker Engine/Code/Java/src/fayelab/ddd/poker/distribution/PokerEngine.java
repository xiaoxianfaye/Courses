package fayelab.ddd.poker.distribution;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fayelab.ddd.poker.distribution.entity.Card;

public class PokerEngine
{
    public static List<Card> run(List<List<Card>> hands)
    {
        return Collections.max(hands, Comparator.comparing(HandRankCalculator::calculate, HandRankComparator::compare));
    }
}
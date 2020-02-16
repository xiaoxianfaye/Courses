package fayelab.ddd.poker.distribution;

import static fayelab.ddd.poker.distribution.HandRankCalculator.calculate;
import static fayelab.ddd.poker.distribution.entity.Card.card;
import static fayelab.ddd.poker.distribution.entity.Suit.*;
import static fayelab.ddd.poker.distribution.entity.HandRank.*;
import static java.util.Arrays.asList;

import fayelab.ddd.poker.distribution.entity.HandRank;
import junit.framework.TestCase;

public class HandRankCalculatorTest extends TestCase
{
    public void test_calculate_when_straight_flush()
    {
        HandRank actual = calculate(asList(card(2, D), card(3, D), card(4, D), card(5, D), card(6, D)));

        assertEquals(TYPE_RANK_STRAIGHT_FLUSH, actual.getTypeRank());
        assertEquals(asList(6, 5, 4, 3, 2), actual.getDistributedRanks());
    }

    public void test_calculate_when_four_kind()
    {
        HandRank actual = calculate(asList(card(2, D), card(2, H), card(2, C), card(2, S), card(9, C)));

        assertEquals(TYPE_RANK_FOUR_KIND, actual.getTypeRank());
        assertEquals(asList(2, 9), actual.getDistributedRanks());
    }

    public void test_calculate_when_full_house()
    {
        HandRank actual = calculate(asList(card(3, D), card(3, H), card(3, C), card(4, S), card(4, D)));

        assertEquals(TYPE_RANK_FULL_HOUSE, actual.getTypeRank());
        assertEquals(asList(3, 4), actual.getDistributedRanks());
    }

    public void test_calculate_when_flush()
    {
        HandRank actual = calculate(asList(card(2, D), card(3, D), card(5, D), card(7, D), card(9, D)));

        assertEquals(TYPE_RANK_FLUSH, actual.getTypeRank());
        assertEquals(asList(9, 7, 5, 3, 2), actual.getDistributedRanks());
    }

    public void test_calculate_when_straight()
    {
        HandRank actual = calculate(asList(card(3, S), card(4, D), card(5, C), card(6, D), card(7, C)));

        assertEquals(TYPE_RANK_STRAIGHT, actual.getTypeRank());
        assertEquals(asList(7, 6, 5, 4, 3), actual.getDistributedRanks());
    }

    public void test_calculate_when_straight_with_A()
    {
        HandRank actual = calculate(asList(card(2, C), card(3, S), card(4, D), card(5, C), card(14, D)));

        assertEquals(TYPE_RANK_STRAIGHT, actual.getTypeRank());
        assertEquals(asList(5, 4, 3, 2, 1), actual.getDistributedRanks());
    }

    public void test_calculate_when_three_kind()
    {
        HandRank actual = calculate(asList(card(3, D), card(3, C), card(3, S), card(2, D), card(7, C)));

        assertEquals(TYPE_RANK_THREE_KIND, actual.getTypeRank());
        assertEquals(asList(3, 7, 2), actual.getDistributedRanks());
    }

    public void test_calculate_when_two_pairs()
    {
        HandRank actual = calculate(asList(card(3, D), card(3, C), card(4, H), card(4, S), card(6, D)));

        assertEquals(TYPE_RANK_TWO_PAIRS, actual.getTypeRank());
        assertEquals(asList(4, 3, 6), actual.getDistributedRanks());
    }

    public void test_calculate_when_one_pair()
    {
        HandRank actual = calculate(asList(card(2, D), card(3, C), card(3, H), card(9, D), card(7, C)));

        assertEquals(TYPE_RANK_ONE_PAIR, actual.getTypeRank());
        assertEquals(asList(3, 9, 7, 2), actual.getDistributedRanks());
    }

    public void test_calculate_when_high_card()
    {
        HandRank actual = calculate(asList(card(7, D), card(2, C), card(3, H), card(4, C), card(9, S)));

        assertEquals(TYPE_RANK_HIGH_CARD, actual.getTypeRank());
        assertEquals(asList(9, 7, 4, 3, 2), actual.getDistributedRanks());
    }
}
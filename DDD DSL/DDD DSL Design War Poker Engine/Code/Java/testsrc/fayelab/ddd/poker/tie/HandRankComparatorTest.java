package fayelab.ddd.poker.tie;

import static fayelab.ddd.poker.tie.HandRankComparator.compare;
import static fayelab.ddd.poker.tie.entity.HandRank.*;
import static java.util.Arrays.asList;

import fayelab.ddd.poker.tie.entity.HandRank;
import junit.framework.TestCase;

public class HandRankComparatorTest extends TestCase
{
    public void test_compare_when_different_hand_type_ranks()
    {
        HandRank straightFlushHandRank = handRank(TYPE_RANK_STRAIGHT_FLUSH, asList(6, 5, 4, 3, 2));
        HandRank fourKindHandRank = handRank(TYPE_RANK_FOUR_KIND, asList(2, 9));
        HandRank fullHouseHandRank = handRank(TYPE_RANK_FULL_HOUSE, asList(3, 4));
        HandRank flushHandRank = handRank(TYPE_RANK_FLUSH, asList(9, 7, 5, 3, 2));
        HandRank straightHandRank = handRank(TYPE_RANK_STRAIGHT, asList(7, 6, 5, 4, 3));
        HandRank threeKindHandRank = handRank(TYPE_RANK_THREE_KIND, asList(3, 7, 2));
        HandRank twoPairsHandRank = handRank(TYPE_RANK_TWO_PAIRS, asList(4, 3, 6));
        HandRank onePairHandRank = handRank(TYPE_RANK_ONE_PAIR, asList(3, 9, 7, 2));
        HandRank highCardHandRank = handRank(TYPE_RANK_HIGH_CARD, asList(9, 7, 4, 3, 2));

        assertTrue(compare(straightFlushHandRank, fourKindHandRank) > 0);
        assertTrue(compare(fourKindHandRank, fullHouseHandRank) > 0);
        assertTrue(compare(fullHouseHandRank, flushHandRank) > 0);
        assertTrue(compare(flushHandRank, straightHandRank) > 0);
        assertTrue(compare(straightHandRank, threeKindHandRank) > 0);
        assertTrue(compare(threeKindHandRank, twoPairsHandRank) > 0);
        assertTrue(compare(twoPairsHandRank, onePairHandRank) > 0);
        assertTrue(compare(onePairHandRank, highCardHandRank) > 0);
    }

    public void test_compare_straight_flush_hand_ranks()
    {
        HandRank straightFlushHandRank1 = handRank(TYPE_RANK_STRAIGHT_FLUSH, asList(5, 4, 3, 2, 1));
        HandRank straightFlushHandRank2 = handRank(TYPE_RANK_STRAIGHT_FLUSH, asList(6, 5, 4, 3, 2));
        HandRank straightFlushHandRank3 = handRank(TYPE_RANK_STRAIGHT_FLUSH, asList(7, 6, 5, 4, 3));

        assertTrue(compare(straightFlushHandRank1, straightFlushHandRank2) < 0);
        assertTrue(compare(straightFlushHandRank2, straightFlushHandRank2) == 0);
        assertTrue(compare(straightFlushHandRank3, straightFlushHandRank2) > 0);
    }

    public void test_compare_four_kind_hand_ranks()
    {
        HandRank fourKindHandRank1 = handRank(TYPE_RANK_FOUR_KIND, asList(4, 4, 4, 4, 3));
        HandRank fourKindHandRank2 = handRank(TYPE_RANK_FOUR_KIND, asList(5, 5, 5, 5, 3));
        HandRank fourKindHandRank3 = handRank(TYPE_RANK_FOUR_KIND, asList(6, 6, 6, 6, 4));
        HandRank fourKindHandRank4 = handRank(TYPE_RANK_FOUR_KIND, asList(4, 4, 4, 4, 6));

        assertTrue(compare(fourKindHandRank1, fourKindHandRank2) < 0);
        assertTrue(compare(fourKindHandRank2, fourKindHandRank2) == 0);
        assertTrue(compare(fourKindHandRank3, fourKindHandRank2) > 0);

        assertTrue(compare(fourKindHandRank4, fourKindHandRank2) < 0);
        assertTrue(compare(fourKindHandRank2, fourKindHandRank4) > 0);

        assertTrue(compare(fourKindHandRank1, fourKindHandRank4) < 0);
        assertTrue(compare(fourKindHandRank4, fourKindHandRank1) > 0);
    }

    public void test_compare_full_house_hand_ranks()
    {
        HandRank fullHouseHandRank1 = handRank(TYPE_RANK_FULL_HOUSE, asList(4, 4, 4, 3, 3));
        HandRank fullHouseHandRank2 = handRank(TYPE_RANK_FULL_HOUSE, asList(5, 5, 5, 3, 3));
        HandRank fullHouseHandRank3 = handRank(TYPE_RANK_FULL_HOUSE, asList(4, 4, 4, 6, 6));

        assertTrue(compare(fullHouseHandRank1, fullHouseHandRank2) < 0);
        assertTrue(compare(fullHouseHandRank2, fullHouseHandRank2) == 0);
        assertTrue(compare(fullHouseHandRank2, fullHouseHandRank1) > 0);

        assertTrue(compare(fullHouseHandRank3, fullHouseHandRank2) < 0);
        assertTrue(compare(fullHouseHandRank2, fullHouseHandRank3) > 0);

        assertTrue(compare(fullHouseHandRank1, fullHouseHandRank3) < 0);
        assertTrue(compare(fullHouseHandRank3, fullHouseHandRank1) > 0);
    }

    public void test_compare_flush_hand_ranks()
    {
        HandRank flushHandRank1 = handRank(TYPE_RANK_FLUSH, asList(7, 5, 4, 3, 2));
        HandRank flushHandRank2 = handRank(TYPE_RANK_FLUSH, asList(8, 6, 5, 4, 3));

        assertTrue(compare(flushHandRank1, flushHandRank2) < 0);
        assertTrue(compare(flushHandRank2, flushHandRank2) == 0);
        assertTrue(compare(flushHandRank2, flushHandRank1) > 0);
    }

    public void test_compare_straight_hand_ranks()
    {
        HandRank straightHandRank1 = handRank(TYPE_RANK_STRAIGHT, asList(5, 4, 3, 2, 1));
        HandRank straightHandRank2 = handRank(TYPE_RANK_STRAIGHT, asList(6, 5, 4, 3, 2));
        HandRank straightHandRank3 = handRank(TYPE_RANK_STRAIGHT, asList(7, 6, 5, 4, 3));

        assertTrue(compare(straightHandRank1, straightHandRank2) < 0);
        assertTrue(compare(straightHandRank2, straightHandRank2) == 0);
        assertTrue(compare(straightHandRank3, straightHandRank2) > 0);
    }

    public void test_compare_three_kind_hand_ranks()
    {
        HandRank threeKindHandRank1 = handRank(TYPE_RANK_THREE_KIND, asList(4, 4, 4, 3, 2));
        HandRank threeKindHandRank2 = handRank(TYPE_RANK_THREE_KIND, asList(5, 5, 5, 3, 2));
        HandRank threeKindHandRank3 = handRank(TYPE_RANK_THREE_KIND, asList(4, 4, 4, 6, 5));
        HandRank threeKindHandRank4 = handRank(TYPE_RANK_THREE_KIND, asList(4, 4, 4, 3, 5));

        assertTrue(compare(threeKindHandRank1, threeKindHandRank2) < 0);
        assertTrue(compare(threeKindHandRank2, threeKindHandRank2) == 0);
        assertTrue(compare(threeKindHandRank2, threeKindHandRank1) > 0);

        assertTrue(compare(threeKindHandRank3, threeKindHandRank2) < 0);
        assertTrue(compare(threeKindHandRank2, threeKindHandRank3) > 0);

        assertTrue(compare(threeKindHandRank1, threeKindHandRank3) < 0);
        assertTrue(compare(threeKindHandRank3, threeKindHandRank1) > 0);
        assertTrue(compare(threeKindHandRank4, threeKindHandRank1) > 0);
    }

    public void test_compare_two_pairs_hand_ranks()
    {
        HandRank twoPairsHandRank1 = handRank(TYPE_RANK_TWO_PAIRS, asList(5, 5, 4, 4, 2));
        HandRank twoPairsHandRank2 = handRank(TYPE_RANK_TWO_PAIRS, asList(6, 6, 5, 5, 2));
        HandRank twoPairsHandRank3 = handRank(TYPE_RANK_TWO_PAIRS, asList(5, 5, 4, 4, 3));

        assertTrue(compare(twoPairsHandRank1, twoPairsHandRank2) < 0);
        assertTrue(compare(twoPairsHandRank2, twoPairsHandRank2) == 0);
        assertTrue(compare(twoPairsHandRank2, twoPairsHandRank1) > 0);

        assertTrue(compare(twoPairsHandRank3, twoPairsHandRank2) < 0);
        assertTrue(compare(twoPairsHandRank2, twoPairsHandRank3) > 0);

        assertTrue(compare(twoPairsHandRank1, twoPairsHandRank3) < 0);
        assertTrue(compare(twoPairsHandRank3, twoPairsHandRank1) > 0);
    }

    public void test_compare_one_pair_hand_ranks()
    {
        HandRank onePairHandRank1 = handRank(TYPE_RANK_ONE_PAIR, asList(5, 5, 4, 3, 2));
        HandRank onePairHandRank2 = handRank(TYPE_RANK_ONE_PAIR, asList(6, 6, 4, 3, 2));
        HandRank onePairHandRank3 = handRank(TYPE_RANK_ONE_PAIR, asList(5, 5, 7, 6, 4));

        assertTrue(compare(onePairHandRank1, onePairHandRank2) < 0);
        assertTrue(compare(onePairHandRank2, onePairHandRank2) == 0);
        assertTrue(compare(onePairHandRank2, onePairHandRank1) > 0);

        assertTrue(compare(onePairHandRank3, onePairHandRank2) < 0);
        assertTrue(compare(onePairHandRank2, onePairHandRank3) > 0);

        assertTrue(compare(onePairHandRank1, onePairHandRank3) < 0);
        assertTrue(compare(onePairHandRank3, onePairHandRank1) > 0);
    }

    public void test_compare_high_card_hand_ranks()
    {
        HandRank highCardHandRank1 = handRank(TYPE_RANK_HIGH_CARD, asList(7, 5, 4, 3, 2));
        HandRank highCardHandRank2 = handRank(TYPE_RANK_HIGH_CARD, asList(8, 7, 5, 4, 3));

        assertTrue(compare(highCardHandRank1, highCardHandRank2) < 0);
        assertTrue(compare(highCardHandRank2, highCardHandRank2) == 0);
        assertTrue(compare(highCardHandRank2, highCardHandRank1) > 0);
    }
}
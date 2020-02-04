package fayelab.ddd.poker.first;

import static fayelab.ddd.poker.first.entity.handrank.HandRank.*;
import static fayelab.ddd.poker.first.entity.handrank.FourKindHandRankContent.fourKindHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.FullHouseHandRankContent.fullHouseHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.StraightFlushHandRankContent.straightFlushHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.FlushHandRankContent.flushHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.StraightHandRankContent.straightHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.ThreeKindHandRankContent.threeKindHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.TwoPairsHandRankContent.twoPairsHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.OnePairHandRankContent.onePairHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.HighCardHandRankContent.highCardHandRankContent;
import static fayelab.ddd.poker.first.HandRankComparator.compare;
import static java.util.Arrays.asList;

import fayelab.ddd.poker.first.entity.handrank.FlushHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.FourKindHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.FullHouseHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.HandRank;
import fayelab.ddd.poker.first.entity.handrank.HighCardHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.OnePairHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.StraightFlushHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.StraightHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.ThreeKindHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.TwoPairsHandRankContent;
import junit.framework.TestCase;

public class HandRankComparatorTest extends TestCase
{
    public void test_compare_when_different_hand_type_ranks()
    {
        HandRank<StraightFlushHandRankContent> straightFlushHandRank =
                handRank(TYPE_RANK_STRAIGHT_FLUSH, straightFlushHandRankContent(6));
        HandRank<FourKindHandRankContent> fourKindHandRank =
                handRank(TYPE_RANK_FOUR_KIND, fourKindHandRankContent(2, 9));
        HandRank<FullHouseHandRankContent> fullHouseHandRank =
                handRank(TYPE_RANK_FULL_HOUSE, fullHouseHandRankContent(3, 4));
        HandRank<FlushHandRankContent> flushHandRank =
                handRank(TYPE_RANK_FLUSH, flushHandRankContent(asList(9, 7, 5, 3, 2)));
        HandRank<StraightHandRankContent> straightHandRank =
                handRank(TYPE_RANK_STRAIGHT, straightHandRankContent(7));
        HandRank<ThreeKindHandRankContent> threeKindHandRank =
                handRank(TYPE_RANK_THREE_KIND, threeKindHandRankContent(3, asList(7, 2)));
        HandRank<TwoPairsHandRankContent> twoPairsHandRank =
                handRank(TYPE_RANK_TWO_PAIRS, twoPairsHandRankContent(asList(4, 3), 6));
        HandRank<OnePairHandRankContent> onePairHandRank =
                handRank(TYPE_RANK_ONE_PAIR, onePairHandRankContent(3, asList(9, 7, 2)));
        HandRank<HighCardHandRankContent> highCardHandRank =
                handRank(TYPE_RANK_HIGH_CARD, highCardHandRankContent(asList(9, 7, 4, 3, 2)));

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
        HandRank<StraightFlushHandRankContent> straightFlushHandRank1 =
                handRank(TYPE_RANK_STRAIGHT_FLUSH, straightFlushHandRankContent(4));
        HandRank<StraightFlushHandRankContent> straightFlushHandRank2 =
                handRank(TYPE_RANK_STRAIGHT_FLUSH, straightFlushHandRankContent(5));
        HandRank<StraightFlushHandRankContent> straightFlushHandRank3 =
                handRank(TYPE_RANK_STRAIGHT_FLUSH, straightFlushHandRankContent(6));

        assertTrue(compare(straightFlushHandRank1, straightFlushHandRank2) < 0);
        assertTrue(compare(straightFlushHandRank2, straightFlushHandRank2) == 0);
        assertTrue(compare(straightFlushHandRank3, straightFlushHandRank2) > 0);
    }

    public void test_compare_four_kind_hand_ranks()
    {
        HandRank<FourKindHandRankContent> fourKindHandRank1 =
                handRank(TYPE_RANK_FOUR_KIND, fourKindHandRankContent(4, 3));
        HandRank<FourKindHandRankContent> fourKindHandRank2 =
                handRank(TYPE_RANK_FOUR_KIND, fourKindHandRankContent(5, 3));
        HandRank<FourKindHandRankContent> fourKindHandRank3 =
                handRank(TYPE_RANK_FOUR_KIND, fourKindHandRankContent(6, 4));
        HandRank<FourKindHandRankContent> fourKindHandRank4 =
                handRank(TYPE_RANK_FOUR_KIND, fourKindHandRankContent(4, 6));

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
        HandRank<FullHouseHandRankContent> fullHouseHandRank1 =
                handRank(TYPE_RANK_FULL_HOUSE, fullHouseHandRankContent(4, 3));
        HandRank<FullHouseHandRankContent> fullHouseHandRank2 =
                handRank(TYPE_RANK_FULL_HOUSE, fullHouseHandRankContent(5, 3));
        HandRank<FullHouseHandRankContent> fullHouseHandRank3 =
                handRank(TYPE_RANK_FULL_HOUSE, fullHouseHandRankContent(4, 6));

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
        HandRank<FlushHandRankContent> flushHandRank1 =
                handRank(TYPE_RANK_FLUSH, flushHandRankContent(asList(7, 5, 4, 3, 2)));
        HandRank<FlushHandRankContent> flushHandRank2 =
                handRank(TYPE_RANK_FLUSH, flushHandRankContent(asList(8, 6, 5, 4, 3)));

        assertTrue(compare(flushHandRank1, flushHandRank2) < 0);
        assertTrue(compare(flushHandRank2, flushHandRank2) == 0);
        assertTrue(compare(flushHandRank2, flushHandRank1) > 0);
    }

    public void test_compare_straight_hand_ranks()
    {
        HandRank<StraightHandRankContent> straightHandRank1 =
                handRank(TYPE_RANK_STRAIGHT, straightHandRankContent(4));
        HandRank<StraightHandRankContent> straightHandRank2 =
                handRank(TYPE_RANK_STRAIGHT, straightHandRankContent(5));
        HandRank<StraightHandRankContent> straightHandRank3 =
                handRank(TYPE_RANK_STRAIGHT, straightHandRankContent(6));

        assertTrue(compare(straightHandRank1, straightHandRank2) < 0);
        assertTrue(compare(straightHandRank2, straightHandRank2) == 0);
        assertTrue(compare(straightHandRank3, straightHandRank2) > 0);
    }

    public void test_compare_three_kind_hand_ranks()
    {
        HandRank<ThreeKindHandRankContent> threeKindHandRank1 =
                handRank(TYPE_RANK_THREE_KIND, threeKindHandRankContent(4, asList(3, 2)));
        HandRank<ThreeKindHandRankContent> threeKindHandRank2 =
                handRank(TYPE_RANK_THREE_KIND, threeKindHandRankContent(5, asList(3, 2)));
        HandRank<ThreeKindHandRankContent> threeKindHandRank3 =
                handRank(TYPE_RANK_THREE_KIND, threeKindHandRankContent(4, asList(6, 5)));
        HandRank<ThreeKindHandRankContent> threeKindHandRank4 =
                handRank(TYPE_RANK_THREE_KIND, threeKindHandRankContent(4, asList(3, 5)));

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
        HandRank<TwoPairsHandRankContent> twoPairsHandRank1 =
                handRank(TYPE_RANK_TWO_PAIRS, twoPairsHandRankContent(asList(5, 4), 2));
        HandRank<TwoPairsHandRankContent> twoPairsHandRank2 =
                handRank(TYPE_RANK_TWO_PAIRS, twoPairsHandRankContent(asList(6, 5), 2));
        HandRank<TwoPairsHandRankContent> twoPairsHandRank3 =
                handRank(TYPE_RANK_TWO_PAIRS, twoPairsHandRankContent(asList(5, 4), 3));

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
        HandRank<OnePairHandRankContent> onePairHandRank1 =
                handRank(TYPE_RANK_ONE_PAIR, onePairHandRankContent(5, asList(4, 3, 2)));
        HandRank<OnePairHandRankContent> onePairHandRank2 =
                handRank(TYPE_RANK_ONE_PAIR, onePairHandRankContent(6, asList(4, 3, 2)));
        HandRank<OnePairHandRankContent> onePairHandRank3 =
                handRank(TYPE_RANK_ONE_PAIR, onePairHandRankContent(5, asList(7, 6, 4)));

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
        HandRank<HighCardHandRankContent> highCardHandRank1 =
                handRank(TYPE_RANK_HIGH_CARD, highCardHandRankContent(asList(7, 5, 4, 3, 2)));
        HandRank<HighCardHandRankContent> highCardHandRank2 =
                handRank(TYPE_RANK_HIGH_CARD, highCardHandRankContent(asList(8, 7, 5, 4, 3)));

        assertTrue(compare(highCardHandRank1, highCardHandRank2) < 0);
        assertTrue(compare(highCardHandRank2, highCardHandRank2) == 0);
        assertTrue(compare(highCardHandRank2, highCardHandRank1) > 0);
    }
}
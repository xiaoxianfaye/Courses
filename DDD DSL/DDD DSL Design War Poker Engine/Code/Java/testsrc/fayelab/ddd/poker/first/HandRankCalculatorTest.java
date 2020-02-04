package fayelab.ddd.poker.first;

import static fayelab.ddd.poker.first.HandRankCalculator.calculate;
import static fayelab.ddd.poker.first.entity.Card.card;
import static fayelab.ddd.poker.first.entity.Suit.*;
import static fayelab.ddd.poker.first.entity.handrank.HandRank.*;
import static java.util.Arrays.asList;

import fayelab.ddd.poker.first.entity.handrank.FlushHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.FourKindHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.FullHouseHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.HandRank;
import fayelab.ddd.poker.first.entity.handrank.HighCardHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.OnePairHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.StraightHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.ThreeKindHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.TwoPairsHandRankContent;
import fayelab.ddd.poker.first.entity.handrank.StraightFlushHandRankContent;
import junit.framework.TestCase;

public class HandRankCalculatorTest extends TestCase
{
    public void test_calculate_when_straight_flush()
    {
        HandRank<?> actual = calculate(asList(card(2, D), card(3, D), card(4, D), card(5, D), card(6, D)));

        assertEquals(TYPE_RANK_STRAIGHT_FLUSH, actual.getTypeRank());
        assertEquals(StraightFlushHandRankContent.class, actual.getContent().getClass());
        StraightFlushHandRankContent actualStraightFlushHandRankContent = (StraightFlushHandRankContent)actual.getContent();
        assertEquals(6, actualStraightFlushHandRankContent.getMaxRank());
    }

    public void test_calculate_when_four_kind()
    {
        HandRank<?> actual = calculate(asList(card(2, D), card(2, H), card(2, C), card(2, S), card(9, C)));

        assertEquals(TYPE_RANK_FOUR_KIND, actual.getTypeRank());
        assertEquals(FourKindHandRankContent.class, actual.getContent().getClass());
        FourKindHandRankContent actualFourKindHandRankContent = (FourKindHandRankContent)actual.getContent();
        assertEquals(2, actualFourKindHandRankContent.getFourKindRank());
        assertEquals(9, actualFourKindHandRankContent.getRestRank());
    }

    public void test_calculate_when_full_house()
    {
        HandRank<?> actual = calculate(asList(card(3, D), card(3, H), card(3, C), card(4, S), card(4, D)));

        assertEquals(TYPE_RANK_FULL_HOUSE, actual.getTypeRank());
        assertEquals(FullHouseHandRankContent.class, actual.getContent().getClass());
        FullHouseHandRankContent actualFullHouseHandRankContent = (FullHouseHandRankContent)actual.getContent();
        assertEquals(3, actualFullHouseHandRankContent.getThreeKindRank());
        assertEquals(4, actualFullHouseHandRankContent.getTwoKindRank());
    }

    public void test_calculate_when_flush()
    {
        HandRank<?> actual = calculate(asList(card(2, D), card(3, D), card(5, D), card(7, D), card(9, D)));

        assertEquals(TYPE_RANK_FLUSH, actual.getTypeRank());
        assertEquals(FlushHandRankContent.class, actual.getContent().getClass());
        FlushHandRankContent actualFlushHandRankContent = (FlushHandRankContent)actual.getContent();
        assertEquals(asList(9, 7, 5, 3, 2), actualFlushHandRankContent.getRanks());
    }

    public void test_calculate_when_straight()
    {
        HandRank<?> actual = calculate(asList(card(3, S), card(4, D), card(5, C), card(6, D), card(7, C)));

        assertEquals(TYPE_RANK_STRAIGHT, actual.getTypeRank());
        assertEquals(StraightHandRankContent.class, actual.getContent().getClass());
        StraightHandRankContent actualStraightHandRankContent = (StraightHandRankContent)actual.getContent();
        assertEquals(7, actualStraightHandRankContent.getMaxRank());
    }

    public void test_calculate_when_three_kind()
    {
        HandRank<?> actual = calculate(asList(card(3, D), card(3, C), card(3, S), card(2, D), card(7, C)));

        assertEquals(TYPE_RANK_THREE_KIND, actual.getTypeRank());
        assertEquals(ThreeKindHandRankContent.class, actual.getContent().getClass());
        ThreeKindHandRankContent actualThreeKindHandRankContent = (ThreeKindHandRankContent)actual.getContent();
        assertEquals(3, actualThreeKindHandRankContent.getThreeKindRank());
        assertEquals(asList(7, 2), actualThreeKindHandRankContent.getRestRanks());
    }

    public void test_calculate_when_two_pairs()
    {
        HandRank<?> actual = calculate(asList(card(3, D), card(3, C), card(4, H), card(4, S), card(6, D)));

        assertEquals(TYPE_RANK_TWO_PAIRS, actual.getTypeRank());
        assertEquals(TwoPairsHandRankContent.class, actual.getContent().getClass());
        TwoPairsHandRankContent actualTwoPairsHandRankContent = (TwoPairsHandRankContent)actual.getContent();
        assertEquals(asList(4, 3), actualTwoPairsHandRankContent.getTwoPairsRanks());
        assertEquals(6, actualTwoPairsHandRankContent.getRestRank());
    }

    public void test_calculate_when_one_pair()
    {
        HandRank<?> actual = calculate(asList(card(2, D), card(3, C), card(3, H), card(9, D), card(7, C)));

        assertEquals(TYPE_RANK_ONE_PAIR, actual.getTypeRank());
        assertEquals(OnePairHandRankContent.class, actual.getContent().getClass());
        OnePairHandRankContent actualOnePairHandRankContent = (OnePairHandRankContent)actual.getContent();
        assertEquals(3, actualOnePairHandRankContent.getOnePairRank());
        assertEquals(asList(9, 7, 2), actualOnePairHandRankContent.getRestRanks());
    }

    public void test_calculate_when_high_card()
    {
        HandRank<?> actual = calculate(asList(card(7, D), card(2, C), card(3, H), card(4, C), card(9, S)));

        assertEquals(TYPE_RANK_HIGH_CARD, actual.getTypeRank());
        assertEquals(HighCardHandRankContent.class, actual.getContent().getClass());
        HighCardHandRankContent actualHighCardHandRankContent = (HighCardHandRankContent)actual.getContent();
        assertEquals(asList(9, 7, 4, 3, 2), actualHighCardHandRankContent.getRanks());
    }
}
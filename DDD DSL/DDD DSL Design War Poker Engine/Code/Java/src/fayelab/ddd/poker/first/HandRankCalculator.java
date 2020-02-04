package fayelab.ddd.poker.first;

import static fayelab.ddd.poker.first.entity.handrank.HandRank.*;
import static fayelab.ddd.poker.first.entity.handrank.StraightFlushHandRankContent.straightFlushHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.FourKindHandRankContent.fourKindHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.FullHouseHandRankContent.fullHouseHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.FlushHandRankContent.flushHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.StraightHandRankContent.straightHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.ThreeKindHandRankContent.threeKindHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.TwoPairsHandRankContent.twoPairsHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.OnePairHandRankContent.onePairHandRankContent;
import static fayelab.ddd.poker.first.entity.handrank.HighCardHandRankContent.highCardHandRankContent;

import static java.util.Arrays.asList;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import fayelab.ddd.poker.first.entity.Card;
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
import fayelab.ddd.poker.first.util.Lists;

public class HandRankCalculator
{
    private static Map<BiPredicate<List<Integer>, List<Card>>, Function<List<Integer>, HandRank<?>>> predAndCalcFuncMap;
    static
    {
        predAndCalcFuncMap = new LinkedHashMap<>();
        predAndCalcFuncMap.put(HandRankCalculator::isStraightFlush, HandRankCalculator::calcStraightFlush);
        predAndCalcFuncMap.put(HandRankCalculator::isFourKind, HandRankCalculator::calcFourKind);
        predAndCalcFuncMap.put(HandRankCalculator::isFullHouse, HandRankCalculator::calcFullHouse);
        predAndCalcFuncMap.put(HandRankCalculator::isFlush, HandRankCalculator::calcFlush);
        predAndCalcFuncMap.put(HandRankCalculator::isStraight, HandRankCalculator::calcStraight);
        predAndCalcFuncMap.put(HandRankCalculator::isThreeKind, HandRankCalculator::calcThreeKind);
        predAndCalcFuncMap.put(HandRankCalculator::isTwoPairs, HandRankCalculator::calcTwoPairs);
        predAndCalcFuncMap.put(HandRankCalculator::isOnePair, HandRankCalculator::calcOnePair);
        predAndCalcFuncMap.put(HandRankCalculator::isHighCard, HandRankCalculator::calcHighCard);
    }

    public static HandRank<?> calculate(List<Card> hand)
    {
        List<Integer> descentRanks = extractDescentRanks(hand);

        return predAndCalcFuncMap.entrySet()
                                 .stream()
                                 .filter(entry -> entry.getKey().test(descentRanks, hand))
                                 .findFirst()
                                 .map(entry -> entry.getValue().apply(descentRanks))
                                 .orElseThrow(IllegalArgumentException::new);
    }

    private static List<Integer> extractDescentRanks(List<Card> hand)
    {
         List<Integer> descentRanks = hand.stream()
                                          .sorted(Comparator.comparing(Card::getRank, (rank1, rank2) -> rank2 - rank1))
                                          .map(Card::getRank)
                                          .collect(Collectors.toList());

         return asList(14, 5, 4, 3, 2).equals(descentRanks) ? asList(5, 4, 3, 2, 1) : descentRanks;
    }

    private static boolean isStraightFlush(List<Integer> descentRanks, List<Card> hand)
    {
        return isStraight(descentRanks) && isFlush(hand);
    }

    private static boolean isFourKind(List<Integer> descentRanks, List<Card> hand)
    {
        return Lists.findByOccurrences(4, descentRanks).isPresent();
    }

    private static boolean isFullHouse(List<Integer> descentRanks, List<Card> hand)
    {
        return Lists.findByOccurrences(3, descentRanks).isPresent()
                && Lists.findByOccurrences(2, descentRanks).isPresent();
    }

    private static boolean isFlush(List<Integer> descentRanks, List<Card> hand)
    {
        return isFlush(hand);
    }

    private static boolean isFlush(List<Card> hand)
    {
        return hand.stream().map(Card::getSuit).distinct().count() == 1;
    }

    private static boolean isStraight(List<Integer> descentRanks, List<Card> hand)
    {
        return isStraight(descentRanks);
    }

    private static boolean isStraight(List<Integer> descentRanks)
    {
        return (descentRanks.stream().distinct().count() == 5) && (descentRanks.get(0) - descentRanks.get(4) == 4);
    }

    private static boolean isThreeKind(List<Integer> descentRanks, List<Card> hand)
    {
        return Lists.findByOccurrences(3, descentRanks).isPresent();
    }

    private static boolean isTwoPairs(List<Integer> descentRanks, List<Card> hand)
    {
        Optional<Integer> optFirstPairRank = Lists.findByOccurrences(2, descentRanks);
        Optional<Integer> optSecondPairRank = Lists.findByOccurrences(2, Lists.reverse(descentRanks));
        return optFirstPairRank.isPresent() && optSecondPairRank.isPresent()
                && optFirstPairRank.get() != optSecondPairRank.get();
    }

    private static boolean isOnePair(List<Integer> descentRanks, List<Card> hand)
    {
        return Lists.findByOccurrences(2, descentRanks).isPresent();
    }

    private static boolean isHighCard(List<Integer> descentRanks, List<Card> hand)
    {
        return true;
    }

    private static HandRank<StraightFlushHandRankContent> calcStraightFlush(List<Integer> descentRanks)
    {
        return handRank(TYPE_RANK_STRAIGHT_FLUSH, straightFlushHandRankContent(descentRanks.get(0)));
    }

    private static HandRank<FourKindHandRankContent> calcFourKind(List<Integer> descentRanks)
    {
        Optional<Integer> optFourKindRank = Lists.findByOccurrences(4, descentRanks);
        Optional<Integer> optRestRank = Lists.findByOccurrences(1, descentRanks);
        if(!optFourKindRank.isPresent() || !optRestRank.isPresent())
        {
            throw new IllegalArgumentException("The descentRanks doesn't match FourKind.");
        }

        return handRank(TYPE_RANK_FOUR_KIND, fourKindHandRankContent(optFourKindRank.get(), optRestRank.get()));
    }

    private static HandRank<FullHouseHandRankContent> calcFullHouse(List<Integer> descentRanks)
    {
        Optional<Integer> optThreeKindRank = Lists.findByOccurrences(3, descentRanks);
        Optional<Integer> optTwoKindRank = Lists.findByOccurrences(2, descentRanks);
        if(!optThreeKindRank.isPresent() || !optTwoKindRank.isPresent())
        {
            throw new IllegalArgumentException("The descentRanks doesn't match FullHouse.");
        }

        return handRank(TYPE_RANK_FULL_HOUSE, fullHouseHandRankContent(optThreeKindRank.get(), optTwoKindRank.get()));
    }

    private static HandRank<FlushHandRankContent> calcFlush(List<Integer> descentRanks)
    {
        return handRank(TYPE_RANK_FLUSH, flushHandRankContent(descentRanks));
    }

    private static HandRank<StraightHandRankContent> calcStraight(List<Integer> descentRanks)
    {
        return handRank(TYPE_RANK_STRAIGHT, straightHandRankContent(descentRanks.get(0)));
    }

    private static HandRank<ThreeKindHandRankContent> calcThreeKind(List<Integer> descentRanks)
    {
        Optional<Integer> optThreeKindRank = Lists.findByOccurrences(3, descentRanks);
        if(!optThreeKindRank.isPresent())
        {
            throw new IllegalArgumentException("The descentRanks doesn't match ThreeKind.");
        }

        int threeKindRank = optThreeKindRank.get();
        List<Integer> restRanks = Lists.filterOut(threeKindRank, descentRanks);
        return handRank(TYPE_RANK_THREE_KIND, threeKindHandRankContent(threeKindRank, restRanks));
    }

    private static HandRank<TwoPairsHandRankContent> calcTwoPairs(List<Integer> descentRanks)
    {
        Optional<Integer> optFirstPairRank = Lists.findByOccurrences(2, descentRanks);
        Optional<Integer> optSecondPairRank = Lists.findByOccurrences(2, Lists.reverse(descentRanks));
        Optional<Integer> optRestRank = Lists.findByOccurrences(1, descentRanks);
        if(!optFirstPairRank.isPresent() || !optSecondPairRank.isPresent() || optFirstPairRank.get() == optSecondPairRank.get())
        {
            throw new IllegalArgumentException("The descentRanks doesn't match TwoPairs.");
        }

        return handRank(TYPE_RANK_TWO_PAIRS,
                twoPairsHandRankContent(asList(optFirstPairRank.get(), optSecondPairRank.get()), optRestRank.get()));
    }

    private static HandRank<OnePairHandRankContent> calcOnePair(List<Integer> descentRanks)
    {
        Optional<Integer> optOnePairRank = Lists.findByOccurrences(2, descentRanks);
        if(!optOnePairRank.isPresent())
        {
            throw new IllegalArgumentException("The descentRanks doesn't match OnePair.");
        }

        int onePairRank = optOnePairRank.get();
        List<Integer> restRanks = Lists.filterOut(onePairRank, descentRanks);
        return handRank(TYPE_RANK_ONE_PAIR, onePairHandRankContent(onePairRank, restRanks));
    }

    private static HandRank<HighCardHandRankContent> calcHighCard(List<Integer> descentRanks)
    {
        return handRank(TYPE_RANK_HIGH_CARD, highCardHandRankContent(descentRanks));
    }
}
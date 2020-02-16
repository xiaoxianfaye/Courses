package fayelab.ddd.poker.tie;

import static fayelab.ddd.poker.tie.entity.HandRank.*;
import static java.util.Arrays.asList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import fayelab.ddd.poker.tie.entity.Card;
import fayelab.ddd.poker.tie.entity.HandRank;
import fayelab.ddd.poker.tie.util.Lists;

public class HandRankCalculator
{
    private static Map<HandTypePredication, Function<List<Integer>, HandRank>> predAndCalcFuncMap;
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

    @FunctionalInterface
    interface HandTypePredication
    {
        boolean predicate(List<Integer> rankDistributionPattern, List<Integer> distributedRanks, List<Card> hand);
    }

    public static HandRank calculate(List<Card> hand)
    {
        List<List<Integer>> rankDistribution = calcRankDistribution(hand);

        return predAndCalcFuncMap.entrySet()
                                 .stream()
                                 .filter(entry -> entry.getKey().predicate(rankDistribution.get(0), rankDistribution.get(1), hand))
                                 .findFirst()
                                 .map(entry -> entry.getValue().apply(rankDistribution.get(1)))
                                 .orElseThrow(IllegalArgumentException::new);
    }

    private static List<List<Integer>> calcRankDistribution(List<Card> hand)
    {
        List<List<Integer>> rankDistribution = Lists.calcDistribution(extractRanks(hand));

        if(asList(14, 5, 4, 3, 2).equals(rankDistribution.get(1)))
        {
            rankDistribution.set(1, asList(5, 4, 3, 2, 1));
        }

        return rankDistribution;
    }

    private static List<Integer> extractRanks(List<Card> hand)
    {
        return hand.stream().map(Card::getRank).collect(Collectors.toList());
    }

    private static boolean isStraightFlush(List<Integer> rankDistributionPattern, List<Integer> distributedRanks, List<Card> hand)
    {
        return isStraight(distributedRanks) && isFlush(hand);
    }

    private static boolean isFourKind(List<Integer> rankDistributionPattern, List<Integer> distributedRanks, List<Card> hand)
    {
        return asList(4, 1).equals(rankDistributionPattern);
    }

    private static boolean isFullHouse(List<Integer> rankDistributionPattern, List<Integer> distributedRanks, List<Card> hand)
    {
        return asList(3, 2).equals(rankDistributionPattern);
    }

    private static boolean isFlush(List<Integer> rankDistributionPattern, List<Integer> distributedRanks, List<Card> hand)
    {
        return isFlush(hand);
    }

    private static boolean isFlush(List<Card> hand)
    {
        return hand.stream().map(Card::getSuit).distinct().count() == 1;
    }

    private static boolean isStraight(List<Integer> rankDistributionPattern, List<Integer> distributedRanks, List<Card> hand)
    {
        return isStraight(distributedRanks);
    }

    private static boolean isStraight(List<Integer> distributedRanks)
    {
        return distributedRanks.stream().distinct().count() == 5 && (distributedRanks.get(0) - distributedRanks.get(4) == 4);
    }

    private static boolean isThreeKind(List<Integer> rankDistributionPattern, List<Integer> distributedRanks, List<Card> hand)
    {
        return asList(3, 1, 1).equals(rankDistributionPattern);
    }

    private static boolean isTwoPairs(List<Integer> rankDistributionPattern, List<Integer> distributedRanks, List<Card> hand)
    {
        return asList(2, 2, 1).equals(rankDistributionPattern);
    }

    private static boolean isOnePair(List<Integer> rankDistributionPattern, List<Integer> distributedRanks, List<Card> hand)
    {
        return asList(2, 1, 1, 1).equals(rankDistributionPattern);
    }

    private static boolean isHighCard(List<Integer> rankDistributionPattern, List<Integer> distributedRanks, List<Card> hand)
    {
        return true;
    }

    private static HandRank calcStraightFlush(List<Integer> distributedRanks)
    {
        return handRank(TYPE_RANK_STRAIGHT_FLUSH, distributedRanks);
    }

    private static HandRank calcFourKind(List<Integer> distributedRanks)
    {
        return handRank(TYPE_RANK_FOUR_KIND, distributedRanks);
    }

    private static HandRank calcFullHouse(List<Integer> distributedRanks)
    {
        return handRank(TYPE_RANK_FULL_HOUSE, distributedRanks);
    }

    private static HandRank calcFlush(List<Integer> distributedRanks)
    {
        return handRank(TYPE_RANK_FLUSH, distributedRanks);
    }

    private static HandRank calcStraight(List<Integer> distributedRanks)
    {
        return handRank(TYPE_RANK_STRAIGHT, distributedRanks);
    }

    private static HandRank calcThreeKind(List<Integer> distributedRanks)
    {
        return handRank(TYPE_RANK_THREE_KIND, distributedRanks);
    }

    private static HandRank calcTwoPairs(List<Integer> distributedRanks)
    {
        return handRank(TYPE_RANK_TWO_PAIRS, distributedRanks);
    }

    private static HandRank calcOnePair(List<Integer> distributedRanks)
    {
        return handRank(TYPE_RANK_ONE_PAIR, distributedRanks);
    }

    private static HandRank calcHighCard(List<Integer> distributedRanks)
    {
        return handRank(TYPE_RANK_HIGH_CARD, distributedRanks);
    }
}
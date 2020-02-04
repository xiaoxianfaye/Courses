package fayelab.ddd.poker.first;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

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

public class HandRankComparator
{
    private static Map<Class<?>, BiFunction<Object, Object, Integer>> contentClassAndCompareFuncMap;
    static
    {
        contentClassAndCompareFuncMap = new HashMap<>();
        contentClassAndCompareFuncMap.put(StraightFlushHandRankContent.class, HandRankComparator::compareStraightFlushHandRankContent);
        contentClassAndCompareFuncMap.put(FourKindHandRankContent.class, HandRankComparator::compareFourKindHandRankContent);
        contentClassAndCompareFuncMap.put(FullHouseHandRankContent.class, HandRankComparator::compareFullHouseHandRankContent);
        contentClassAndCompareFuncMap.put(FlushHandRankContent.class, HandRankComparator::compareFlushHandRankContent);
        contentClassAndCompareFuncMap.put(StraightHandRankContent.class, HandRankComparator::compareStraightHandRankContent);
        contentClassAndCompareFuncMap.put(ThreeKindHandRankContent.class, HandRankComparator::compareThreeKindHandRankContent);
        contentClassAndCompareFuncMap.put(TwoPairsHandRankContent.class, HandRankComparator::compareTwoPairsHandRankContent);
        contentClassAndCompareFuncMap.put(OnePairHandRankContent.class, HandRankComparator::compareOnePairHandRankContent);
        contentClassAndCompareFuncMap.put(HighCardHandRankContent.class, HandRankComparator::compareHighCardHandRankContent);
    }

    public static int compare(HandRank<?> handRank1, HandRank<?> handRank2)
    {
        return handRank1.getTypeRank() != handRank2.getTypeRank()
                ? handRank1.getTypeRank() - handRank2.getTypeRank()
                : compareHandRankContent(handRank1.getContent(), handRank2.getContent());
    }

    private static int compareHandRankContent(Object content1, Object content2)
    {
        if(content1.getClass() != content2.getClass())
        {
            throw new IllegalArgumentException("The classes of two contents are different.");
        }

        if(!contentClassAndCompareFuncMap.containsKey(content1.getClass()))
        {
            throw new IllegalArgumentException("The classes of two contents are out of range.");
        }

        return contentClassAndCompareFuncMap.get(content1.getClass()).apply(content1, content2);
    }

    private static int compareStraightFlushHandRankContent(Object content1, Object content2)
    {
        if(content1.getClass() != StraightFlushHandRankContent.class)
        {
            throw new IllegalArgumentException("The content is not an instance of StraightFlushHandRankContent.");
        }

        StraightFlushHandRankContent straightFlushHandRankContent1 = (StraightFlushHandRankContent)content1;
        StraightFlushHandRankContent straightFlushHandRankContent2 = (StraightFlushHandRankContent)content2;
        return straightFlushHandRankContent1.getMaxRank() - straightFlushHandRankContent2.getMaxRank();
    }

    private static int compareFourKindHandRankContent(Object content1, Object content2)
    {
        if(content1.getClass() != FourKindHandRankContent.class)
        {
            throw new IllegalArgumentException("The content is not an instance of FourKindHandRankContent.");
        }

        FourKindHandRankContent fourKindHandRankContent1 = (FourKindHandRankContent)content1;
        FourKindHandRankContent fourKindHandRankContent2 = (FourKindHandRankContent)content2;
        return fourKindHandRankContent1.getFourKindRank() != fourKindHandRankContent2.getFourKindRank()
                ? fourKindHandRankContent1.getFourKindRank() - fourKindHandRankContent2.getFourKindRank()
                : fourKindHandRankContent1.getRestRank() - fourKindHandRankContent2.getRestRank();
    }

    private static int compareFullHouseHandRankContent(Object content1, Object content2)
    {
        if(content1.getClass() != FullHouseHandRankContent.class)
        {
            throw new IllegalArgumentException("The content is not an instance of FullHouseHandRankContent.");
        }

        FullHouseHandRankContent fullHouseHandRankContent1 = (FullHouseHandRankContent)content1;
        FullHouseHandRankContent fullHouseHandRankContent2 = (FullHouseHandRankContent)content2;
        return fullHouseHandRankContent1.getThreeKindRank() != fullHouseHandRankContent2.getThreeKindRank()
                ? fullHouseHandRankContent1.getThreeKindRank() - fullHouseHandRankContent2.getThreeKindRank()
                : fullHouseHandRankContent1.getTwoKindRank() - fullHouseHandRankContent2.getTwoKindRank();
    }

    private static int compareFlushHandRankContent(Object content1, Object content2)
    {
        if(content1.getClass() != FlushHandRankContent.class)
        {
            throw new IllegalArgumentException("The content is not an instance of FlushHandRankContent.");
        }

        FlushHandRankContent flushHandRankContent1 = (FlushHandRankContent)content1;
        FlushHandRankContent flushHandRankContent2 = (FlushHandRankContent)content2;
        return Lists.compare(flushHandRankContent1.getRanks(), flushHandRankContent2.getRanks());
    }

    private static int compareStraightHandRankContent(Object content1, Object content2)
    {
        if(content1.getClass() != StraightHandRankContent.class)
        {
            throw new IllegalArgumentException("The content is not an instance of StraightHandRankContent.");
        }

        StraightHandRankContent straightHandRankContent1 = (StraightHandRankContent)content1;
        StraightHandRankContent straightHandRankContent2 = (StraightHandRankContent)content2;
        return straightHandRankContent1.getMaxRank() - straightHandRankContent2.getMaxRank();
    }

    private static int compareThreeKindHandRankContent(Object content1, Object content2)
    {
        if(content1.getClass() != ThreeKindHandRankContent.class)
        {
            throw new IllegalArgumentException("The content is not an instance of ThreeKindHandRankContent.");
        }

        ThreeKindHandRankContent threeKindHandRankContent1 = (ThreeKindHandRankContent)content1;
        ThreeKindHandRankContent threeKindHandRankContent2 = (ThreeKindHandRankContent)content2;
        return threeKindHandRankContent1.getThreeKindRank() != threeKindHandRankContent2.getThreeKindRank()
                ? threeKindHandRankContent1.getThreeKindRank() - threeKindHandRankContent2.getThreeKindRank()
                : Lists.compare(threeKindHandRankContent1.getRestRanks(), threeKindHandRankContent2.getRestRanks());
    }

    private static int compareTwoPairsHandRankContent(Object content1, Object content2)
    {
        if(content1.getClass() != TwoPairsHandRankContent.class)
        {
            throw new IllegalArgumentException("The content is not an instance of TwoPairsHandRankContent.");
        }

        TwoPairsHandRankContent twoPairsHandRankContent1 = (TwoPairsHandRankContent)content1;
        TwoPairsHandRankContent twoPairsHandRankContent2 = (TwoPairsHandRankContent)content2;
        int twoPairsComparedResult = Lists.compare(twoPairsHandRankContent1.getTwoPairsRanks(), twoPairsHandRankContent2.getTwoPairsRanks());
        return twoPairsComparedResult != 0
                ? twoPairsComparedResult
                : twoPairsHandRankContent1.getRestRank() - twoPairsHandRankContent2.getRestRank();
    }

    private static int compareOnePairHandRankContent(Object content1, Object content2)
    {
        if(content1.getClass() != OnePairHandRankContent.class)
        {
            throw new IllegalArgumentException("The content is not an instance of OnePairHandRankContent.");
        }

        OnePairHandRankContent onePairHandRankContent1 = (OnePairHandRankContent)content1;
        OnePairHandRankContent onePairHandRankContent2 = (OnePairHandRankContent)content2;
        return onePairHandRankContent1.getOnePairRank() != onePairHandRankContent2.getOnePairRank()
                ? onePairHandRankContent1.getOnePairRank() - onePairHandRankContent2.getOnePairRank()
                : Lists.compare(onePairHandRankContent1.getRestRanks(), onePairHandRankContent2.getRestRanks());
    }

    private static int compareHighCardHandRankContent(Object content1, Object content2)
    {
        if(content1.getClass() != HighCardHandRankContent.class)
        {
            throw new IllegalArgumentException("The content is not an instance of HighCardHandRankContent.");
        }

        HighCardHandRankContent highCardHandRankContent1 = (HighCardHandRankContent)content1;
        HighCardHandRankContent highCardHandRankContent2 = (HighCardHandRankContent)content2;
        return Lists.compare(highCardHandRankContent1.getRanks(), highCardHandRankContent2.getRanks());
    }
}
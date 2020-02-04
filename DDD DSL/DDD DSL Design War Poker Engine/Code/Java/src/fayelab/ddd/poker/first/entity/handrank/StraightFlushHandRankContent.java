package fayelab.ddd.poker.first.entity.handrank;

public class StraightFlushHandRankContent
{
    private int maxRank;

    private StraightFlushHandRankContent(int maxRank)
    {
        this.maxRank = maxRank;
    }

    public static StraightFlushHandRankContent straightFlushHandRankContent(int maxRank)
    {
        return new StraightFlushHandRankContent(maxRank);
    }

    public int getMaxRank()
    {
        return maxRank;
    }
}
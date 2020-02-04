package fayelab.ddd.poker.first.entity.handrank;

public class StraightHandRankContent
{
    private int maxRank;

    private StraightHandRankContent(int maxRank)
    {
        this.maxRank = maxRank;
    }

    public static StraightHandRankContent straightHandRankContent(int maxRank)
    {
        return new StraightHandRankContent(maxRank);
    }

    public int getMaxRank()
    {
        return maxRank;
    }
}
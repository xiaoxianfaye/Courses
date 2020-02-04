package fayelab.ddd.poker.first.entity.handrank;

public class FullHouseHandRankContent
{
    private int threeKindRank;
    private int twoKindRank;

    private FullHouseHandRankContent(int threeKindRank, int twoKindRank)
    {
        this.threeKindRank = threeKindRank;
        this.twoKindRank = twoKindRank;
    }

    public static FullHouseHandRankContent fullHouseHandRankContent(int threeKindRank, int twoKindRank)
    {
        return new FullHouseHandRankContent(threeKindRank, twoKindRank);
    }

    public int getThreeKindRank()
    {
        return threeKindRank;
    }

    public int getTwoKindRank()
    {
        return twoKindRank;
    }
}
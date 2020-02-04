package fayelab.ddd.poker.first.entity.handrank;

public class FourKindHandRankContent
{
    private int fourKindRank;
    private int restRank;

    private FourKindHandRankContent(int fourKindRank, int restRank)
    {
        this.fourKindRank = fourKindRank;
        this.restRank = restRank;
    }

    public static FourKindHandRankContent fourKindHandRankContent(int fourKindRank, int restRank)
    {
        return new FourKindHandRankContent(fourKindRank, restRank);
    }

    public int getFourKindRank()
    {
        return fourKindRank;
    }

    public int getRestRank()
    {
        return restRank;
    }
}
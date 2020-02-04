package fayelab.ddd.poker.first.entity.handrank;

import java.util.List;

public class ThreeKindHandRankContent
{
    private int threeKindRank;
    private List<Integer> restRanks;

    private ThreeKindHandRankContent(int threeKindRank, List<Integer> restRanks)
    {
        this.threeKindRank = threeKindRank;
        this.restRanks = restRanks;
    }

    public static ThreeKindHandRankContent threeKindHandRankContent(int threeKindRank, List<Integer> restRanks)
    {
        return new ThreeKindHandRankContent(threeKindRank, restRanks);
    }

    public int getThreeKindRank()
    {
        return threeKindRank;
    }

    public List<Integer> getRestRanks()
    {
        return restRanks;
    }
}
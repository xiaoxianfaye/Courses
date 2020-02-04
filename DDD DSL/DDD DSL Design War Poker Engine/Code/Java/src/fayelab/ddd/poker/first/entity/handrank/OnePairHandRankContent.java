package fayelab.ddd.poker.first.entity.handrank;

import java.util.List;

public class OnePairHandRankContent
{
    private int onePairRank;
    private List<Integer> restRanks;

    private OnePairHandRankContent(int onePairRank, List<Integer> restRanks)
    {
        this.onePairRank = onePairRank;
        this.restRanks = restRanks;
    }

    public static OnePairHandRankContent onePairHandRankContent(int onePairRank, List<Integer> restRanks)
    {
        return new OnePairHandRankContent(onePairRank, restRanks);
    }

    public int getOnePairRank()
    {
        return onePairRank;
    }

    public List<Integer> getRestRanks()
    {
        return restRanks;
    }
}
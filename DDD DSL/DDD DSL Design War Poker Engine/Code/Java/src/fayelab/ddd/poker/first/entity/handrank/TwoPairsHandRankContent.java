package fayelab.ddd.poker.first.entity.handrank;

import java.util.List;

public class TwoPairsHandRankContent
{
    private List<Integer> twoPairsRanks;
    private int restRank;

    private TwoPairsHandRankContent(List<Integer> twoPairsRanks, int restRank)
    {
        this.twoPairsRanks = twoPairsRanks;
        this.restRank = restRank;
    }

    public static TwoPairsHandRankContent twoPairsHandRankContent(List<Integer> twoPairsRanks, int restRank)
    {
        return new TwoPairsHandRankContent(twoPairsRanks, restRank);
    }

    public List<Integer> getTwoPairsRanks()
    {
        return twoPairsRanks;
    }

    public int getRestRank()
    {
        return restRank;
    }
}
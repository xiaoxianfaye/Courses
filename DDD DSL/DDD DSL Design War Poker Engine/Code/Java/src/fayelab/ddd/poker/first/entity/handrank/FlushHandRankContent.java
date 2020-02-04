package fayelab.ddd.poker.first.entity.handrank;

import java.util.List;

public class FlushHandRankContent
{
    private List<Integer> ranks;

    private FlushHandRankContent(List<Integer> ranks)
    {
        this.ranks = ranks;
    }

    public static FlushHandRankContent flushHandRankContent(List<Integer> ranks)
    {
        return new FlushHandRankContent(ranks);
    }

    public List<Integer> getRanks()
    {
        return ranks;
    }
}
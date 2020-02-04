package fayelab.ddd.poker.first.entity.handrank;

import java.util.List;

public class HighCardHandRankContent
{
    private List<Integer> ranks;

    private HighCardHandRankContent(List<Integer> ranks)
    {
        this.ranks = ranks;
    }

    public static HighCardHandRankContent highCardHandRankContent(List<Integer> ranks)
    {
        return new HighCardHandRankContent(ranks);
    }

    public List<Integer> getRanks()
    {
        return ranks;
    }
}
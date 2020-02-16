package fayelab.ddd.poker.tie;

import fayelab.ddd.poker.tie.entity.HandRank;
import fayelab.ddd.poker.tie.util.Lists;

public class HandRankComparator
{
    public static int compare(HandRank handRank1, HandRank handRank2)
    {
        return handRank1.getTypeRank() != handRank2.getTypeRank()
                ? handRank1.getTypeRank() - handRank2.getTypeRank()
                : Lists.compare(handRank1.getDistributedRanks(), handRank2.getDistributedRanks());
    }
}
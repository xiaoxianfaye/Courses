package fayelab.ddd.poker.distribution.entity;

import java.util.List;

public class HandRank
{
    public static final int TYPE_RANK_STRAIGHT_FLUSH = 8;
    public static final int TYPE_RANK_FOUR_KIND = 7;
    public static final int TYPE_RANK_FULL_HOUSE = 6;
    public static final int TYPE_RANK_FLUSH = 5;
    public static final int TYPE_RANK_STRAIGHT = 4;
    public static final int TYPE_RANK_THREE_KIND = 3;
    public static final int TYPE_RANK_TWO_PAIRS = 2;
    public static final int TYPE_RANK_ONE_PAIR = 1;
    public static final int TYPE_RANK_HIGH_CARD = 0;

    private int typeRank;
    private List<Integer> distributedRanks;

    private HandRank(int typeRank, List<Integer> distributedRanks)
    {
        this.typeRank = typeRank;
        this.distributedRanks = distributedRanks;
    }

    public static HandRank handRank(int typeRank, List<Integer> distributedRanks)
    {
        return new HandRank(typeRank, distributedRanks);
    }

    public int getTypeRank()
    {
        return typeRank;
    }

    public List<Integer> getDistributedRanks()
    {
        return distributedRanks;
    }
}
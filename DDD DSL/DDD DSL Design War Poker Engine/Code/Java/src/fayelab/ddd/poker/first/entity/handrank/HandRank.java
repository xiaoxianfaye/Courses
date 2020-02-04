package fayelab.ddd.poker.first.entity.handrank;

public class HandRank<T>
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
    private T content;

    private HandRank(int typeRank, T content)
    {
        this.typeRank = typeRank;
        this.content = content;
    }

    public static <T> HandRank<T> handRank(int typeRank, T content)
    {
        return new HandRank<>(typeRank, content);
    }

    public int getTypeRank()
    {
        return typeRank;
    }

    public T getContent()
    {
        return content;
    }
}
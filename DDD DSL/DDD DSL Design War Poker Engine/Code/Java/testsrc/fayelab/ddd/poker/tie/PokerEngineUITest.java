package fayelab.ddd.poker.tie;

import static java.util.Arrays.asList;

import junit.framework.TestCase;

public class PokerEngineUITest extends TestCase
{
    private static final String straightFlush = "2D 3D 4D 5D 6D";
    private static final String straightFlushHigher = "3C 4C 5C 6C 7C";
    private static final String straightFlush2 = "2C 3C 4C 5C 6C";

    private static final String fourKind = "2D 2H 2C 2S 9C";
    private static final String fourKindHigher = "3D 3H 3C 3S 9C";
    private static final String fourKindHigher2 = "3D 3H 3C 3S TC";

    private static final String fullHouse = "3D 3H 3C 4S 4D";
    private static final String fullHouseHigher = "4D 4H 4S 2H 2C";
    private static final String fullHouseHigher2 = "4D 4H 4S 3H 3C";

    private static final String flush = "6D 3D 5D JD QD";
    private static final String flushHigher = "2C 3C 4C JC AC";

    private static final String straight = "2C 3S 4D 5C 6D";
    private static final String straightHigher = "3S 4D 5C 6D 7C";
    private static final String straightIncludingA = "2C 3S 4D 5C AD";

    private static final String threeKind = "2D 2C 2S TS QH";
    private static final String threeKindHigher = "3D 3C 3H 9S KH";
    private static final String threeKindHigher2 = "3D 3C 3H TS KH";

    private static final String twoPairs = "3D 3C 4H 4S 6D";
    private static final String twoPairsHigher = "2D 2C 5H 5S 6D";
    private static final String twoPairsHigher2 = "2D 2C 5H 5S 7D";

    private static final String onePair = "3D 3C 2H 9S JC";
    private static final String onePairHigher = "4D 4C 2H 9S JC";
    private static final String onePairHigher2 = "4D 4C AH 3D 7C";

    private static final String highCard = "7D 2C 3H 4C 9S";
    private static final String highCardHigher = "7D 2C 3H 4C TS";
    private static final String highCardIncludingA = "2C 3S 4D 6C AD";

    public void test_play()
    {
        assertEquals(asList(straightFlush), PokerEngineUI.play(asList(straightFlush, fourKind)));
    }

    public void test_play_all()
    {
        assertEquals(asList(straightFlush), PokerEngineUI.play(asList(straightFlush, fourKind)));
        assertEquals(asList(fourKind), PokerEngineUI.play(asList(fourKind, fullHouse)));
        assertEquals(asList(fullHouse), PokerEngineUI.play(asList(fullHouse, flush)));
        assertEquals(asList(flush), PokerEngineUI.play(asList(flush, straight)));
        assertEquals(asList(straight), PokerEngineUI.play(asList(straight, threeKind)));
        assertEquals(asList(threeKind), PokerEngineUI.play(asList(threeKind, twoPairs)));
        assertEquals(asList(twoPairs), PokerEngineUI.play(asList(twoPairs, onePair)));
        assertEquals(asList(onePair), PokerEngineUI.play(asList(onePair, highCard)));

        assertEquals(asList(straightFlushHigher), PokerEngineUI.play(asList(straightFlush, straightFlushHigher)));

        assertEquals(asList(fourKindHigher), PokerEngineUI.play(asList(fourKind, fourKindHigher)));
        assertEquals(asList(fourKindHigher2), PokerEngineUI.play(asList(fourKindHigher, fourKindHigher2)));

        assertEquals(asList(fullHouseHigher), PokerEngineUI.play(asList(fullHouse, fullHouseHigher)));
        assertEquals(asList(fullHouseHigher2), PokerEngineUI.play(asList(fullHouseHigher, fullHouseHigher2)));

        assertEquals(asList(flushHigher), PokerEngineUI.play(asList(flush, flushHigher)));

        assertEquals(asList(straightHigher), PokerEngineUI.play(asList(straight, straightHigher)));

        assertEquals(asList(threeKindHigher), PokerEngineUI.play(asList(threeKind, threeKindHigher)));
        assertEquals(asList(threeKindHigher2), PokerEngineUI.play(asList(threeKindHigher, threeKindHigher2)));

        assertEquals(asList(twoPairsHigher), PokerEngineUI.play(asList(twoPairs, twoPairsHigher)));
        assertEquals(asList(twoPairsHigher2), PokerEngineUI.play(asList(twoPairsHigher, twoPairsHigher2)));

        assertEquals(asList(onePairHigher), PokerEngineUI.play(asList(onePair, onePairHigher)));
        assertEquals(asList(onePairHigher2), PokerEngineUI.play(asList(onePairHigher, onePairHigher2)));

        assertEquals(asList(highCardHigher), PokerEngineUI.play(asList(highCard, highCardHigher)));

        assertEquals(asList(straightFlush), PokerEngineUI.play(asList(straightFlush, fourKind, fullHouse, flush,
                straight, threeKind, twoPairs, onePair, highCard)));

        assertEquals(asList(highCard), PokerEngineUI.play(asList(highCard)));
    }

    public void test_play_when_straight_including_A()
    {
        assertEquals(asList(straightIncludingA), PokerEngineUI.play(asList(straightIncludingA, highCardIncludingA)));
        assertEquals(asList(straight), PokerEngineUI.play(asList(straight, straightIncludingA)));
    }

    public void test_play_when_tie()
    {
        assertEquals(asList(straightFlush, straightFlush2),
                PokerEngineUI.play(asList(straightFlush, straightFlush2, fourKind, fullHouse)));
    }
}
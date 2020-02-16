package fayelab.ddd.poker.tie;

import static fayelab.ddd.poker.tie.entity.Suit.*;
import static fayelab.ddd.poker.tie.entity.Card.card;
import static java.util.Arrays.asList;

import java.util.List;

import fayelab.ddd.poker.tie.entity.Card;
import junit.framework.TestCase;

public class PokerEngineTest extends TestCase
{
    private static final List<Card> straightFlush = asList(card(2, D), card(3, D), card(4, D), card(5, D), card(6, D));
    private static final List<Card> straightFlushHigher = asList(card(3, C), card(4, C), card(5, C), card(6, C), card(7, C));
    private static final List<Card> straightFlushEqual = asList(card(2, H), card(3, H), card(4, H), card(5, H), card(6, H));

    private static final List<Card> fourKind = asList(card(2, D), card(2, H), card(2, C), card(2, S), card(9, C));
    private static final List<Card> fourKindHigher = asList(card(3, D), card(3, H), card(3, C), card(3, S), card(9, C));
    private static final List<Card> fourKindHigher2 = asList(card(3, D), card(3, H), card(3, C), card(3, S), card(10, C));

    private static final List<Card> fullHouse = asList(card(3, D), card(3, H), card(3, C), card(4, S), card(4, D));
    private static final List<Card> fullHouseHigher = asList(card(4, D), card(4, H), card(4, S), card(2, H), card(2, C));
    private static final List<Card> fullHouseHigher2 = asList(card(4, D), card(4, H), card(4, S), card(3, H), card(3, C));

    private static final List<Card> flush = asList(card(6, D), card(3, D), card(5, D), card(11, D), card(12, D));
    private static final List<Card> flushHigher = asList(card(2, C), card(3, C), card(4, C), card(11, C), card(14, C));

    private static final List<Card> straight = asList(card(2, C), card(3, S), card(4, D), card(5, C), card(6, D));
    private static final List<Card> straightHigher = asList(card(3, S), card(4, D), card(5, C), card(6, D), card(7, C));

    private static final List<Card> threeKind = asList(card(2, D), card(2, C), card(2, S), card(10, S), card(12, H));
    private static final List<Card> threeKindHigher = asList(card(3, D), card(3, C), card(3, H), card(9, S), card(13, H));
    private static final List<Card> threeKindHigher2 = asList(card(3, D), card(3, C), card(3, H), card(10, S), card(13, H));

    private static final List<Card> twoPairs = asList(card(3, D), card(3, C), card(4, H), card(4, S), card(6, D));
    private static final List<Card> twoPairsHigher = asList(card(2, D), card(2, C), card(5, H), card(5, S), card(6, D));
    private static final List<Card> twoPairsHigher2 = asList(card(2, D), card(2, C), card(5, H), card(5, S), card(7, D));

    private static final List<Card> onePair = asList(card(3, D), card(3, C), card(2, H), card(9, S), card(11, C));
    private static final List<Card> onePairHigher = asList(card(4, D), card(4, C), card(2, H), card(9, S), card(11, C));
    private static final List<Card> onePairHigher2 = asList(card(4, D), card(4, C), card(14, H), card(3, D), card(7, C));

    private static final List<Card> highCard = asList(card(7, D), card(2, C), card(3, H), card(4, C), card(9, S));
    private static final List<Card> highCardHigher = asList(card(7, D), card(2, C), card(3, H), card(4, C), card(10, S));

    public void test_run()
    {
        assertEquals(asList(straightFlush), PokerEngine.run(asList(straightFlush, fourKind)));
        assertEquals(asList(fourKind), PokerEngine.run(asList(fourKind, fullHouse)));
        assertEquals(asList(fullHouse), PokerEngine.run(asList(fullHouse, flush)));
        assertEquals(asList(flush), PokerEngine.run(asList(flush, straight)));
        assertEquals(asList(straight), PokerEngine.run(asList(straight, threeKind)));
        assertEquals(asList(threeKind), PokerEngine.run(asList(threeKind, twoPairs)));
        assertEquals(asList(twoPairs), PokerEngine.run(asList(twoPairs, onePair)));
        assertEquals(asList(onePair), PokerEngine.run(asList(onePair, highCard)));

        assertEquals(asList(straightFlushHigher), PokerEngine.run(asList(straightFlush, straightFlushHigher)));

        assertEquals(asList(fourKindHigher), PokerEngine.run(asList(fourKind, fourKindHigher)));
        assertEquals(asList(fourKindHigher2), PokerEngine.run(asList(fourKindHigher, fourKindHigher2)));

        assertEquals(asList(fullHouseHigher), PokerEngine.run(asList(fullHouse, fullHouseHigher)));
        assertEquals(asList(fullHouseHigher2), PokerEngine.run(asList(fullHouseHigher, fullHouseHigher2)));

        assertEquals(asList(flushHigher), PokerEngine.run(asList(flush, flushHigher)));

        assertEquals(asList(straightHigher), PokerEngine.run(asList(straight, straightHigher)));

        assertEquals(asList(threeKindHigher), PokerEngine.run(asList(threeKind, threeKindHigher)));
        assertEquals(asList(threeKindHigher2), PokerEngine.run(asList(threeKindHigher, threeKindHigher2)));

        assertEquals(asList(twoPairsHigher), PokerEngine.run(asList(twoPairs, twoPairsHigher)));
        assertEquals(asList(twoPairsHigher2), PokerEngine.run(asList(twoPairsHigher, twoPairsHigher2)));

        assertEquals(asList(onePairHigher), PokerEngine.run(asList(onePair, onePairHigher)));
        assertEquals(asList(onePairHigher2), PokerEngine.run(asList(onePairHigher, onePairHigher2)));

        assertEquals(asList(highCardHigher), PokerEngine.run(asList(highCard, highCardHigher)));

        assertEquals(asList(straightFlush), PokerEngine.run(asList(straightFlush, fourKind, fullHouse, flush,
                straight, threeKind, twoPairs, onePair, highCard)));

        assertEquals(asList(highCard), PokerEngine.run(asList(highCard)));
    }

    public void test_run_when_tie()
    {
        assertEquals(asList(straightFlush, straightFlushEqual),
                PokerEngine.run(asList(straightFlush, straightFlushEqual, fourKind, fullHouse)));
    }
}
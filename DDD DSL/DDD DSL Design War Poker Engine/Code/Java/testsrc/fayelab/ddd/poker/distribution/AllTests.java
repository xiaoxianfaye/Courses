package fayelab.ddd.poker.distribution;

import fayelab.ddd.poker.distribution.util.ListsTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(ListsTest.class);
        suite.addTestSuite(HandRankCalculatorTest.class);
        suite.addTestSuite(HandRankComparatorTest.class);
        suite.addTestSuite(PokerEngineTest.class);
        suite.addTestSuite(PokerEngineUITest.class);
        //$JUnit-END$
        return suite;
    }
}

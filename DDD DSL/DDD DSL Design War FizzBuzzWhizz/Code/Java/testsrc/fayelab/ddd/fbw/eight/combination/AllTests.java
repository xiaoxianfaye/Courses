package fayelab.ddd.fbw.eight.combination;

import fayelab.ddd.fbw.eight.combination.action.AllActionTests;
import fayelab.ddd.fbw.eight.combination.predication.AllPredicationTests;
import fayelab.ddd.fbw.eight.combination.rule.RuleTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(AllActionTests.suite());
        suite.addTest(AllPredicationTests.suite());
        suite.addTestSuite(RuleTest.class);
        suite.addTestSuite(ListToolTest.class);
        //$JUnit-END$
        return suite;
    }
}

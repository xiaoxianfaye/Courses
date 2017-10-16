package fayelab.ddd.fbw.eight.straightforward;

import fayelab.ddd.fbw.eight.straightforward.action.AllActionTests;
import fayelab.ddd.fbw.eight.straightforward.predication.AllPredicationTests;
import fayelab.ddd.fbw.eight.straightforward.rule.RuleTest;
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
        //$JUnit-END$
        return suite;
    }
}

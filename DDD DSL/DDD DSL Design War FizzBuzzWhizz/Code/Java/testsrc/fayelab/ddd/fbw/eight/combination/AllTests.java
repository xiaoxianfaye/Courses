package fayelab.ddd.fbw.eight.combination;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.ddd.fbw.eight.combination.action.AllTests.suite());
        suite.addTest(fayelab.ddd.fbw.eight.combination.predication.AllTests.suite());
        suite.addTest(fayelab.ddd.fbw.eight.combination.rule.AllTests.suite());
        suite.addTestSuite(ListToolTest.class);
        //$JUnit-END$
        return suite;
    }
}

package fayelab.ddd.fbw.original;

import fayelab.ddd.fbw.original.action.AllActionTests;
import fayelab.ddd.fbw.original.predication.AllPredicationTests;
import fayelab.ddd.fbw.original.rule.RuleTest;
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

package fayelab.ddd.fbw.eight.straightforward;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.ddd.fbw.eight.straightforward.action.AllTests.suite());
        suite.addTest(fayelab.ddd.fbw.eight.straightforward.predication.AllTests.suite());
        suite.addTest(fayelab.ddd.fbw.eight.straightforward.rule.AllTests.suite());
        //$JUnit-END$
        return suite;
    }
}

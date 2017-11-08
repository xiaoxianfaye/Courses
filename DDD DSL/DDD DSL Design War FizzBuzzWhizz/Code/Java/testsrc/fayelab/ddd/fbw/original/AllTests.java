package fayelab.ddd.fbw.original;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.ddd.fbw.original.action.AllTests.suite());
        suite.addTest(fayelab.ddd.fbw.original.predication.AllTests.suite());
        suite.addTest(fayelab.ddd.fbw.original.rule.AllTests.suite());
        //$JUnit-END$
        return suite;
    }
}

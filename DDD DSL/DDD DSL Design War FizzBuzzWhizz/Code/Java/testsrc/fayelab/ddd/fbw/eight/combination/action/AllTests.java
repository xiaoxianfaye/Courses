package fayelab.ddd.fbw.eight.combination.action;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(ToFizzTest.class);
        suite.addTestSuite(ToBuzzTest.class);
        suite.addTestSuite(ToWhizzTest.class);
        suite.addTestSuite(ToStrTest.class);
        suite.addTestSuite(ToHazzTest.class);
        //$JUnit-END$
        return suite;
    }
}

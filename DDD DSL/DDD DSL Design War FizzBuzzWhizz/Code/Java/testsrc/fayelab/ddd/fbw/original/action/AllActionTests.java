package fayelab.ddd.fbw.original.action;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllActionTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllActionTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(ToBuzzTest.class);
        suite.addTestSuite(ToFizzTest.class);
        suite.addTestSuite(ToStrTest.class);
        suite.addTestSuite(ToWhizzTest.class);
        //$JUnit-END$
        return suite;
    }
}

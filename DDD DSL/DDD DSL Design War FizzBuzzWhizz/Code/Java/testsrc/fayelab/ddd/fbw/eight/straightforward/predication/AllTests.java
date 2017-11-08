package fayelab.ddd.fbw.eight.straightforward.predication;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(TimesTest.class);
        suite.addTestSuite(ContainsTest.class);
        suite.addTestSuite(AlwaysTrueTest.class);
        //$JUnit-END$
        return suite;
    }
}

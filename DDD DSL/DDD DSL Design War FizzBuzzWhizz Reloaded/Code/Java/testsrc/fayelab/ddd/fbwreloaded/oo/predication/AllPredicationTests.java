package fayelab.ddd.fbwreloaded.oo.predication;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllPredicationTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllPredicationTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(AlwaysTrueTest.class);
        suite.addTestSuite(ContainsTest.class);
        suite.addTestSuite(TimesTest.class);
        //$JUnit-END$
        return suite;
    }
}

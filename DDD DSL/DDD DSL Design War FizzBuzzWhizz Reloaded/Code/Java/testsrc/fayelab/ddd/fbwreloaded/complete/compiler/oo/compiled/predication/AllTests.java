package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(OOTimesTest.class);
        suite.addTestSuite(OOContainsTest.class);
        suite.addTestSuite(OOAlwaysTrueTest.class);
        //$JUnit-END$
        return suite;
    }
}

package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllOOPredicationTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllOOPredicationTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(OOAlwaysTrueTest.class);
        suite.addTestSuite(OOContainsTest.class);
        suite.addTestSuite(OOTimesTest.class);
        //$JUnit-END$
        return suite;
    }
}

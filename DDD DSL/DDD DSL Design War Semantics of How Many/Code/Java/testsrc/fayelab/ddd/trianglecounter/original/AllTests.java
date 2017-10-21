package fayelab.ddd.trianglecounter.original;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(CombinatorTest.class);
        suite.addTestSuite(SetOpTest.class);
        suite.addTestSuite(TriangleCounterTest.class);
        //$JUnit-END$
        return suite;
    }
}

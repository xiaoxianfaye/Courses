package fayelab.tdd.arithmetic.doublestack.original;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(DoubleStackProcessorTest.class);
        suite.addTestSuite(ExpressionTest.class);
        //$JUnit-END$
        return suite;
    }
}

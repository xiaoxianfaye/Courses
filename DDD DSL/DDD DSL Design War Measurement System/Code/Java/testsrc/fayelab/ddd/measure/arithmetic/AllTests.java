package fayelab.ddd.measure.arithmetic;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(ListOpTest.class);
        suite.addTestSuite(MeasureSystemTest.class);
        suite.addTestSuite(MeasureSystemUITest.class);
        suite.addTestSuite(VecOpTest.class);
        //$JUnit-END$
        return suite;
    }
}

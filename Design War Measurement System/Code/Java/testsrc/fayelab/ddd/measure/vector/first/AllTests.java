package fayelab.ddd.measure.vector.first;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{

    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(MeasureSystemTest.class);
        suite.addTestSuite(MeasureSystemUITest.class);
        suite.addTestSuite(VecOp2Test.class);
        suite.addTestSuite(VecOpTest.class);
        //$JUnit-END$
        return suite;
    }

}
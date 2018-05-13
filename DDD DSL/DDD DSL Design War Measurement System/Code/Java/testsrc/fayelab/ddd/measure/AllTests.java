package fayelab.ddd.measure;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.ddd.measure.vecarith.AllTests.suite());
        suite.addTest(fayelab.ddd.measure.vector.original.AllTests.suite());
        suite.addTest(fayelab.ddd.measure.vector.scale.AllTests.suite());
        //$JUnit-END$
        return suite;
    }
}

package fayelab.ddd.layout.globalparam.oo.position;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(AboveTest.class);
        suite.addTestSuite(BesideTest.class);
        //$JUnit-END$
        return suite;
    }
}

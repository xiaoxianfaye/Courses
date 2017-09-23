package fayelab.ddd.gcdlcm;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{

    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(fayelab.ddd.gcdlcm.interpreter.GcdLcmTest.class);
        suite.addTestSuite(fayelab.ddd.gcdlcm.functional.GcdLcmTest.class);
        suite.addTestSuite(fayelab.ddd.gcdlcm.util.ListOpTest.class);
        suite.addTestSuite(fayelab.ddd.gcdlcm.util.MathOpTest.class);
        //$JUnit-END$
        return suite;
    }

}

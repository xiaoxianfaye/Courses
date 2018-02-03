package fayelab.tdd.arithmetic.doublestack;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.tdd.arithmetic.doublestack.original.AllTests.suite());
        suite.addTest(fayelab.tdd.arithmetic.doublestack.rem.AllTests.suite());
        suite.addTest(fayelab.tdd.arithmetic.doublestack.multidigit.AllTests.suite());
        suite.addTest(fayelab.tdd.arithmetic.doublestack.pow.AllTests.suite());
        suite.addTest(fayelab.tdd.arithmetic.doublestack.negint.AllTests.suite());
        //$JUnit-END$
        return suite;
    }
}

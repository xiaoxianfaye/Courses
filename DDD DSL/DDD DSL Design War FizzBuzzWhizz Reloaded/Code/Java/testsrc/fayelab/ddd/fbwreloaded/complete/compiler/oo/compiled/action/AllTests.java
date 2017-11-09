package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(OOToFizzTest.class);
        suite.addTestSuite(OOToBuzzTest.class);
        suite.addTestSuite(OOToWhizzTest.class);
        suite.addTestSuite(OOToStrTest.class);
        //$JUnit-END$
        return suite;
    }
}

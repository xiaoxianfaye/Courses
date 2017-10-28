package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllOOActionTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllOOActionTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(OOToBuzzTest.class);
        suite.addTestSuite(OOToFizzTest.class);
        suite.addTestSuite(OOToStrTest.class);
        suite.addTestSuite(OOToWhizzTest.class);
        //$JUnit-END$
        return suite;
    }
}

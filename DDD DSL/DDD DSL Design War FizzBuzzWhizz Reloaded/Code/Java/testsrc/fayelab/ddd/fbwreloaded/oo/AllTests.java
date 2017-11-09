package fayelab.ddd.fbwreloaded.oo;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.ddd.fbwreloaded.oo.action.AllTests.suite());
        suite.addTest(fayelab.ddd.fbwreloaded.oo.predication.AllTests.suite());
        suite.addTest(fayelab.ddd.fbwreloaded.oo.rule.AllTests.suite());
        //$JUnit-END$
        return suite;
    }
}

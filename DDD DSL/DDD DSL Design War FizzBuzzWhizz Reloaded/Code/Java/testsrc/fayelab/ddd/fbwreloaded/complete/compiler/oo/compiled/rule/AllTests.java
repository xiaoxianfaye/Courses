package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.rule;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(OORuleTest.class);
        //$JUnit-END$
        return suite;
    }
}

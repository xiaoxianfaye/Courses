package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action.AllTests.suite());
        suite.addTest(fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication.AllTests.suite());
        suite.addTest(fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.rule.AllTests.suite());
        //$JUnit-END$
        return suite;
    }
}

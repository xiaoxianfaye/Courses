package fayelab.ddd.fbwreloaded.complete.compiler.oo;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.AllTests.suite());
        suite.addTestSuite(CompilerTest.class);
        suite.addTestSuite(ParserTest.class);
        suite.addTestSuite(RuleDescToolTest.class);
        suite.addTestSuite(RuleEqualityToolTest.class);
        //$JUnit-END$
        return suite;
    }
}

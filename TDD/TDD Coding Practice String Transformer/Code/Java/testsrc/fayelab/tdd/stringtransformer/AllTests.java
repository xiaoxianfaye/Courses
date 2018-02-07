package fayelab.tdd.stringtransformer;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.tdd.stringtransformer.addall.AllTests.suite());
        suite.addTest(fayelab.tdd.stringtransformer.original.AllTests.suite());
        suite.addTest(fayelab.tdd.stringtransformer.reverse.AllTests.suite());
        //$JUnit-END$
        return suite;
    }
}

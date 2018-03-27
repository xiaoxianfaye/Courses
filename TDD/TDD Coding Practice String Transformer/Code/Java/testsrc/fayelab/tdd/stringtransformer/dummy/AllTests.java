package fayelab.tdd.stringtransformer.dummy;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.tdd.stringtransformer.dummy.addall.AllTests.suite());
        suite.addTest(fayelab.tdd.stringtransformer.dummy.original.AllTests.suite());
        suite.addTest(fayelab.tdd.stringtransformer.dummy.reverse.AllTests.suite());
        //$JUnit-END$
        return suite;
    }
}

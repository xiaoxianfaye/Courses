package fayelab.tdd.stringtransformer.instruction;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(fayelab.tdd.stringtransformer.instruction.addall.AllTests.suite());
        suite.addTest(fayelab.tdd.stringtransformer.instruction.original.AllTests.suite());
        suite.addTest(fayelab.tdd.stringtransformer.instruction.reverse.AllTests.suite());
        //$JUnit-END$
        return suite;
    }
}

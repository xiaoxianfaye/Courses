package fayelab.tdd.stringtransformer.original;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(BusinessLogicImplTest.class);
        suite.addTestSuite(PresenterTest.class);
        //$JUnit-END$
        return suite;
    }
}

package fayelab.ddd.fbwreloaded.oo;

import fayelab.ddd.fbwreloaded.oo.action.AllActionTests;
import fayelab.ddd.fbwreloaded.oo.predication.AllPredicationTests;
import fayelab.ddd.fbwreloaded.oo.rule.RuleTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{
    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(AllActionTests.suite());
        suite.addTest(AllPredicationTests.suite());
        suite.addTestSuite(RuleTest.class);
        //$JUnit-END$
        return suite;
    }
}

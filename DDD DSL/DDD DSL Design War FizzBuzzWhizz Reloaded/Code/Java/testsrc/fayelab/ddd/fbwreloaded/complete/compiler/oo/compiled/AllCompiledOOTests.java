package fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled;

import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.action.AllOOActionTests;
import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.predication.AllOOPredicationTests;
import fayelab.ddd.fbwreloaded.complete.compiler.oo.compiled.rule.OORuleTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllCompiledOOTests
{

    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllCompiledOOTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTest(AllOOActionTests.suite());
        suite.addTest(AllOOPredicationTests.suite());
        suite.addTestSuite(OORuleTest.class);
        //$JUnit-END$
        return suite;
    }

}

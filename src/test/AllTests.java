package test;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite kerho-ohjelmalle
 * @author vesal
 * @version 3.1.2019
 */
@RunWith(Suite.class)
@SuiteClasses({
    Treenipvk.test.SarjaTest.class,
    Treenipvk.test.SarjatTest.class,
    Treenipvk.test.HarjoiteTest.class,
    Treenipvk.test.HarjoitteetTest.class,
    Treenipvk.test.TreeniTest.class,
    Treenipvk.test.TreenitTest.class,
    Treenipvk.test.PaivakirjaTest.class,
    })
public class AllTests {
 //
}

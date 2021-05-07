package Treenipvk.test;
// Generated by ComTest BEGIN
import java.time.LocalDate;
import static org.junit.Assert.*;
import org.junit.*;
import Treenipvk.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.05.06 22:12:09 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class TreenitTest {



  // Generated by ComTest BEGIN
  /** testLisaaTreeni48 */
  @Test
  public void testLisaaTreeni48() {    // Treenit: 48
    Treenit treenit = new Treenit(); 
    assertEquals("From: Treenit line: 50", 0, treenit.getTreeniLkm()); 
    Treeni treeni1 = new Treeni("testi1", null); treeni1.rekisteroi(); treenit.lisaaTreeni(treeni1); 
    assertEquals("From: Treenit line: 52", 1, treenit.getTreeniLkm()); 
    Treeni treeni2 = new Treeni("testi2", null); treeni2.rekisteroi(); treenit.lisaaTreeni(treeni2); 
    assertEquals("From: Treenit line: 54", 2, treenit.getTreeniLkm()); 
    Treeni treeni3 = new Treeni("testi3", null); treeni3.rekisteroi(); treenit.lisaaTreeni(treeni3); 
    assertEquals("From: Treenit line: 56", 3, treenit.getTreeniLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetTreeni77 */
  @Test
  public void testGetTreeni77() {    // Treenit: 77
    Treenit treenit = new Treenit(); 
    Treeni treeni1 = new Treeni("testi1", null); treeni1.rekisteroi(); treenit.lisaaTreeni(treeni1); 
    Treeni treeni2 = new Treeni("testi2", null); treeni2.rekisteroi(); treenit.lisaaTreeni(treeni2); 
    Treeni treeni3 = new Treeni("testi3", null); treeni3.rekisteroi(); treenit.lisaaTreeni(treeni3); 
    assertEquals("From: Treenit line: 82", 1, treenit.getTreeni(1).getId()); 
    assertEquals("From: Treenit line: 83", 2, treenit.getTreeni(2).getId()); 
    assertEquals("From: Treenit line: 84", 3, treenit.getTreeni(3).getId()); 
    assertEquals("From: Treenit line: 85", null, treenit.getTreeni(4).getId()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEtsi137 */
  @Test
  public void testEtsi137() {    // Treenit: 137
    Treenit treenit = new Treenit(); 
    Treeni treeni1 = new Treeni("testi1", null); treeni1.rekisteroi(); treenit.lisaaTreeni(treeni1); 
    Treeni treeni2 = new Treeni("testi2", null); treeni2.rekisteroi(); treenit.lisaaTreeni(treeni2); 
    Treeni treeni3 = new Treeni("testi3", null); treeni3.rekisteroi(); treenit.lisaaTreeni(treeni3); 
    assertEquals("From: Treenit line: 142", "testi1", treenit.etsi("testi1").get(0).getNimi()); 
    assertEquals("From: Treenit line: 143", "testi2", treenit.etsi("testi2").get(1).getNimi()); 
    assertEquals("From: Treenit line: 144", "testi3", treenit.etsi("testi3").get(2).getNimi()); 
    assertEquals("From: Treenit line: 145", true, treenit.etsi("testi").isEmpty()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEtsiPvm163 */
  @Test
  public void testEtsiPvm163() {    // Treenit: 163
    Treenit treenit = new Treenit(); 
    Treeni treeni1 = new Treeni("testi1", LocalDate.now()); treeni1.rekisteroi(); treenit.lisaaTreeni(treeni1); 
    Treeni treeni2 = new Treeni("testi2", LocalDate.now()); treeni2.rekisteroi(); treenit.lisaaTreeni(treeni2); 
    Treeni treeni3 = new Treeni("testi3", LocalDate.now()); treeni3.rekisteroi(); treenit.lisaaTreeni(treeni3); 
    assertEquals("From: Treenit line: 169", "testi1", treenit.etsiPvm(treeni1.pvmToString()).get(0).getNimi()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPoista188 */
  @Test
  public void testPoista188() {    // Treenit: 188
    Treenit treenit = new Treenit(); 
    Treeni treeni1 = new Treeni("testi1", null); treeni1.rekisteroi(); treenit.lisaaTreeni(treeni1); 
    Treeni treeni2 = new Treeni("testi2", null); treeni2.rekisteroi(); treenit.lisaaTreeni(treeni2); 
    Treeni treeni3 = new Treeni("testi3", null); treeni3.rekisteroi(); treenit.lisaaTreeni(treeni3); 
    treenit.poista(1); 
    assertEquals("From: Treenit line: 194", null, treenit.getTreeni(1)); 
  } // Generated by ComTest END
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wad.domain.*;
import java.util.*;

/**
 *
 * @author hannu
 */
public class KirjoittajaTest {
    
    Kirjoittaja kirjoittaja;
    
    public KirjoittajaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi("Kirjoittaja");
        
        ArrayList<Uutinen> uutiset = new ArrayList();
        
        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko("Otsikko");
        uutinen.setIngressi("Ingressi");
        uutinen.setTeksti("Teksti");
        uutiset.add(uutinen);
        
        kirjoittaja.setUutiset(uutiset);
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void setNimiToimii(){
        assertEquals("Kirjoittaja", kirjoittaja.getNimi());
    }
    
    @Test
    public void setUutisetToimii(){
        ArrayList<Uutinen> uutiset = new ArrayList();
        
        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko("Otsikko");
        uutinen.setIngressi("Ingressi");
        uutinen.setTeksti("Teksti");
        uutiset.add(uutinen);
        assertEquals(uutiset.get(0), kirjoittaja.getUutiset().get(0));
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

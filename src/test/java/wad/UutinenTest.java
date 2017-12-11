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
public class UutinenTest {
    
    Uutinen uutinen;
    
    public UutinenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        uutinen = new Uutinen();
        uutinen.setOtsikko("Otsikko");
        uutinen.setIngressi("Ingressi");
        ArrayList<Kirjoittaja> kirjoittajat = new ArrayList();
        Kirjoittaja kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi("Kirjoittaja");
        kirjoittajat.add(kirjoittaja);
        uutinen.setKirjoittajat(kirjoittajat);
        uutinen.setTeksti("Sisältö");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void setOtsikkoToimii(){
        assertEquals("Otsikko", uutinen.getOtsikko());
    }
    
    @Test
    public void setIngressiToimii(){
        assertEquals("Ingressi", uutinen.getIngressi());
    }
    
    @Test
    public void setTekstiToimii(){
        assertEquals("Sisältö", uutinen.getTeksti());
    }
    
    @Test
    public void setKirjoittajatToimii(){
        ArrayList<Kirjoittaja> kirjoittajat = new ArrayList();
        Kirjoittaja kirjoittaja = new Kirjoittaja();
        kirjoittaja.setNimi("Kirjoittaja");
        kirjoittajat.add(kirjoittaja);
        assertEquals(kirjoittajat, uutinen.getKirjoittajat());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

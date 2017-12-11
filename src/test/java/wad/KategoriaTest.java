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
public class KategoriaTest {
    
    Kategoria kategoria;
    
    public KategoriaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kategoria = new Kategoria();
        kategoria.setNimi("kategoria");
        ArrayList<Uutinen> uutiset = new ArrayList();
        Uutinen uutinen = new Uutinen();
        uutinen.setOtsikko("Otsikko");
        uutinen.setIngressi("Ingressi");
        uutinen.setTeksti("Sisältö");
        uutiset.add(uutinen);
        kategoria.setUutiset(uutiset);
        kategoria.setUutistenLKM(1);
        
    }
    
    @Test
    public void setNimiToimii(){
        assertEquals("kategoria", kategoria.getNimi());
    }
    
    @Test
    public void setUutisetToimii(){
        assertEquals("Otsikko",kategoria.getUutiset().get(0).getOtsikko());
        assertEquals("Ingressi",kategoria.getUutiset().get(0).getIngressi());
        assertEquals("Sisältö",kategoria.getUutiset().get(0).getTeksti());
    }
    
    @Test
    public void uutistenLukumääräOikein(){
        assertEquals(1,kategoria.getUutistenLKM());
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

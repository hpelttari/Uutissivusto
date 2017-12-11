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
public class AccountTest {
    
    Account account;
    
    public AccountTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        account = new Account();
        account.setUsername("Käyttäjä");
        account.setPassword("salasana");
        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void setUsernameToimii(){
        assertEquals("Käyttäjä", account.getUsername());
    }
    
    @Test
    public void setPasswordToimii(){
        assertEquals("salasana", account.getPassword());
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

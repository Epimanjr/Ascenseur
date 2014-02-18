/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MonAscenseur;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author blaise
 */
public class AscenseurTest {
    Ascenseur a;
    
    public AscenseurTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Cabine c = new Cabine(new Etage(5), 5);
        a = new Ascenseur(8,-1, c);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setEtages method, of class Ascenseur.
     */
    @Test
    public void testSetEtages() {
        System.out.println("setEtages");
        String affichea = a.toString();
        //System.out.println(a.toString());
    }

    /**
     * Test of toString method, of class Ascenseur.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        
        
        a.afficheLaSituation();
    }
    
}

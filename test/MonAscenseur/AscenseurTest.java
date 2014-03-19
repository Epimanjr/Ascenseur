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
        Cabine c = new Cabine(new Etage(5), 5, false);
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
    
    @Test
    public void testGetEtateSuperieur() {
        setUp();
        Cabine c = a.getCabine();
        System.out.println("GetEtageSuperieur");
        assertEquals(c.getEtage().getNumero(), 5);
        
        Etage e = a.getEtageSuivant(c.getEtage());
        assertEquals(e.getNumero(), 6);
        
        e = a.getEtageSuivant(e);
        assertEquals(e, null);
        
        c.setEtage(new Etage(2));
        e = a.getEtagePrecedant(c.getEtage());
        assertEquals(e.getNumero(), 1);
        
        e = a.getEtagePrecedant(e);
        assertEquals(e.getNumero(), 0);
        
        e = a.getEtagePrecedant(e);
        assertEquals(e.getNumero(), -1);
        
        Etage testNull = a.getEtagePrecedant(e);
        assertEquals(testNull, null);
        
        e = a.getEtageSuivant(e);
        assertEquals(e.getNumero(), 0);
        
        e = a.getEtageSuivant(e);
        assertEquals(e.getNumero(), 1);
    }
    
}

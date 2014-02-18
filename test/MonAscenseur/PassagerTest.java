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
public class PassagerTest {
    
    public PassagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of genererEtage method, of class Passager.
     */
    @Test
    public void testGenererEtage() {
        System.out.println("genererEtage");
        
        for(int i=0;i<100;i++) {
            int num = Passager.genererEtage().getNumero();
            assert(num >= (-1) && num <= 8);
        }
        
        
    }

    /**
     * Test of toString method, of class Passager.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
       
    }

    /**
     * Test of getNumero method, of class Passager.
     */
    @Test
    public void testGetNumero() {
        System.out.println("getNumero");
        
    }

    /**
     * Test of getCompteurDePassagersConstruits method, of class Passager.
     */
    @Test
    public void testGetCompteurDePassagersConstruits() {
        System.out.println("getCompteurDePassagersConstruits");
       
    }

    /**
     * Test of getEtageDepart method, of class Passager.
     */
    @Test
    public void testGetEtageDepart() {
        System.out.println("getEtageDepart");
       
    }

    /**
     * Test of getEtageDestination method, of class Passager.
     */
    @Test
    public void testGetEtageDestination() {
        System.out.println("getEtageDestination");
      
    }

    /**
     * Test of getDateApparition method, of class Passager.
     */
    @Test
    public void testGetDateApparition() {
        System.out.println("getDateApparition");
       
    }
    
}

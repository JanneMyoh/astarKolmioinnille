/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package astarpathing;

import junit.framework.TestCase;

/**
 *
 * @author janne
 */
public class KolmioTest extends TestCase {
    
    public KolmioTest(String testName) {
        super(testName);
    }

    /**
     * Test of vieressa method, of class Kolmio.
     */
    public void testVieressa() {
        
    }

    /**
     * Test of addVierus method, of class Kolmio.
     */
    public void testAddVierus() {
        Kolmio k1 = new Kolmio(new float[][]{{-1,0},{0,0},{0,1}});
        Kolmio k2 = new Kolmio(new float[][]{{0,0},{1,0},{0,1}});
        Kolmio k3 = new Kolmio(new float[][]{{-1,1},{0,2},{-1,2}});
        Kolmio k4 = new Kolmio(new float[][]{{0,0},{0,-1},{1,0}});
        Kolmio k5 = new Kolmio(new float[][]{{1,0},{1,1},{0,1}});
        if(!k1.addVierus(k2)) fail("ei saatu lisättyä k1 k2 viereen!");
        if(!k4.addVierus(k2)) fail("ei saatu lisättyä k4 k2 viereen!");
        if(!k2.addVierus(k5)) fail("ei saatu lisättyä k2 k5 viereen!");
        if(k3.addVierus(k2)) fail("k3 ei ole k2 vieressä!");
        if(k1.addVierus(k4)) fail("only tips touch!");
        if(k1.vieressa(k4)) fail("k1 ei pitäs olla k4 vieressä");
        if(!k1.vieressa(k2)) fail("k2 pitäs olla k1 vieressa");

    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package astarpathing;

/**
 *
 * @author janne
 */
public class AstarPathing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Graph grp = new Graph("output.txt");
        Visualizer v = new Visualizer(grp);
        v.setVisible(true);
    }
    
}

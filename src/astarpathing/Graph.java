/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package astarpathing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Luo kolmio verkon annetusta tiedostosta
 * @author janne
 */
public class Graph {

    /**
     *verkon kolmiot
     */
    public ArrayList<Kolmio> kolmiot = new ArrayList<Kolmio>();
    
    /**
     *
     * @param fname tiedosto nimi jonka pohjalta verkko luodaan
     */
    public Graph(String fname)
    {
         FileReader fr = null;
        BufferedReader br = null;
        ArrayList<float[]> pisteet = new ArrayList<>();
        try {
            fr = new FileReader(fname);
            br = new BufferedReader(fr);
            String rivi = br.readLine();
            //luetaan pisteet
            rivi = br.readLine();
            while(rivi.compareTo("[JANAT]") != 0)
            {
                String[] splt = rivi.split(";");
                pisteet.add(new float[]{Float.parseFloat(splt[0]),Float.parseFloat(splt[1]),Float.parseFloat(splt[2])});
                rivi = br.readLine();
            }
            //kelataan janojen ohi
            while(rivi.compareTo("[KOLMIOT]") != 0)
            {
                rivi = br.readLine();
            }
            rivi = br.readLine(); //luetaan ensimmäinen kolmio
            while(rivi != null)
            {
                String[] splt = rivi.split(";");
                float[] p1=null,p2=null,p3=null;
                for(float[] f: pisteet)
                {
                    if(Float.parseFloat(splt[0]) == f[0]) {p1 = new float[]{f[1],f[2]};}
                    else if(Float.parseFloat(splt[1]) == f[0]) {p2 = new float[]{f[1],f[2]};}
                    else if(Float.parseFloat(splt[2]) == f[0]) {p3 = new float[]{f[1],f[2]};}
                    if(p1 != null && p2!= null && p3!= null) break;
                }
                //nyt pitäs olla kaikki kolmioiden pisteet
                kolmiot.add(new Kolmio(new float[][]{p1,p2,p3}));
                rivi = br.readLine();
            }
            //luodaan graaffi
            for(int i = 0; i < kolmiot.size()-1; i++)
            {
                int hit = 0;
                for(int j = i+1; j < kolmiot.size(); j++)
                {
                    if(kolmiot.get(i).addVierus(kolmiot.get(j))) hit++;
                    if(hit == 3) break; //jokasella kolmiolla maksimissaan 3 naapuria
                }
            }
            
            
        } catch (IOException ex) {
            System.out.println("Ny kusi, ei saatu luettua tiedostoa " + fname);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                System.out.println("ei saatu suljettua FR");
            }
        }
    }
    
    /**
     * Laskee polun annetusta alku kolmiosta annettuun maali kolmioon käyttäen A* algoritmiä
     * @param alku alku kolmio
     * @param maali maali kolmio
     * @return polku jota seurataan. Ensimmäinen on alku kolmio ja viimeinen on maali kolmio. Null mikäli polkua ei ole olemassa
     */
    public ArrayList<Kolmio> getPath(Kolmio alku, Kolmio maali)
    {
        maali.updateBdis(0);
        ArrayList<Kolmio> openList = new ArrayList<>();
        //ArrayList<Kolmio> closedList = new ArrayList<>();
        if(alku.bdis == Integer.MAX_VALUE) return null; //tässä graaffissa ei päästä alusta maaliin
        Kolmio kohde = alku;
        while(kohde != maali){
        //closedList.add(kohde);
        kohde.state = 2;
        ArrayList<Kolmio> tmp = kohde.updateNeighbors();
        if(tmp != null) openList.addAll(tmp);
        //etsitään avoimelta listalta se kolmio jonka fArvo on pienin
        float min = Float.MAX_VALUE;
        Kolmio ehdokas = null;
        for(Kolmio k: openList)
        {
            if(min > k.bdis+k.gval) {min = k.bdis+k.gval;ehdokas = k;}
        }
        //poistetaan ehdokas suljetulta listalta
        openList.remove(ehdokas);
        kohde = ehdokas;
        }
        ArrayList<Kolmio> reitti = new ArrayList<>();
        while(kohde.par != null)
        {
            reitti.add(0,kohde);
            kohde = kohde.par;
        }
        reitti.add(0,kohde);
        return reitti;
    }
    
}

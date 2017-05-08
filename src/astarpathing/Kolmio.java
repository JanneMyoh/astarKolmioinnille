/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package astarpathing;

import java.util.ArrayList;

/**
 * Ykisttäinen kolmio
 * @author janne
 */
public class Kolmio {
    
    /**
     * kolmion kärkien koordinaatit
     */
    public float[][] karjet;

    /**
     * kolmion paino piste
     */
    public float[] cog = new float[2];
    
    /**
     *etäisyys kolmioina tästä kolmiosta maaliin
     */
    public int bdis = Integer.MAX_VALUE;

    /**
     *pathfindingin käyttämä arvo
     */
    public float gval = 0;

    /**
     *tila, millä listalla kolmio on
     */
    public int state = 0; //0, ei listalla. 1, avoimella listalla. 2, suljetulla listalla

    /**
     *pathfindingin käyttämä arvo
     */
    public Kolmio par = null;
    
    
    private ArrayList<Kolmio> neighb = new ArrayList<Kolmio>();
    
    /**
     *
     * @param pp kärkien koordinaatit
     */
    public Kolmio(float[][] pp)
    {
        float xsum = 0;
        float ysum = 0;
        karjet = new float[3][2];
        for(int i = 0; i < 3; i++)
        {
            karjet[i][0] = pp[i][0];
            karjet[i][1] = pp[i][1];
            xsum += pp[i][0];
            ysum += pp[i][1];
        }
        cog[0] = xsum/3;
        cog[1] = ysum/3;
        
        
    }
    
    //tullaanko tätä tarvitsemaan ikinä?

    /**
     *Tutkii onko annettu kolmio jo tämän naapuri
     * @param k ehdokas kolmio
     * @return oliko annettu kolmio tämän naapuri
     */
        public boolean vieressa(Kolmio k)
    {
        for(Kolmio kol: neighb)
        {
            if(kol == k) return true;
        }
        return false;
    }
    
    //lisätään annettu kolmio naapuriksi mikäli aiheellista

    /**
     * Tutkitaan onko annettu kolmio tämän kolmion naapuri, ja lisätään se mikäli tarvetta
     * @param k kohde kolmio
     * @return true mikäli kolmio on naapuri. Muutoin false
     */
        public boolean addVierus(Kolmio k)
    {
        if(vieressa(k)) return true; //kolmio on jo lisätty... tulisikohan palauttaa tässä kohtaa false?
        if(neighb.size() == 3) return false; //on jo olemassa 3 kolmiota, ja annettu kolmio ei ollut yksi niistä. Ei siis voi olla vieressä
        //katsotaan vastaavatko kärjet toisiaan
        int sim = 0;
        for(float[] f:karjet)
        {
            //otetaan yksi kärki ja katsotaan onko se olemassa annetussa kolmiossa
            for(float[] kf:k.karjet)
            {
                if(f[0] == kf[0] && f[1] == kf[1]) {sim++;break;}
            }
        }
        //mikäli sim==2 on annettu kolmio tämän vieressä. Arvo 1 merkitsee että kärjet koskettavat ja arvo 3 että ne ovat sama kolmio
        if(sim != 2) return false;
        neighb.add(k);
        k.addVierus(this);
        return true;
    }
    
    /**
     *palauttaa kolmion kärki pisteet
     * @return
     */
    public float[][] getPisteet()
    {
        return new float[][]{{karjet[0][0],karjet[0][1]},{karjet[1][0],karjet[1][1]},{karjet[2][0],karjet[2][1]}};
    }
    
    /**
     * päivitetään, jos aiheellista, kolmion etäisyys maalista
     * @param given
     */
    public void updateBdis(int given)
    {
        if(given >= bdis) return;
        bdis = given;
        for(Kolmio k: neighb)
        {
            k.updateBdis(given+1);
        }
    }
    
    
    /**
     * laskee seuraavan askeleen
     * @return
     */
    /*
    public Kolmio getNextStep()
    {
        Kolmio pal = null;
        if(bdis == 0) return pal;
        for(Kolmio k: neighb)
        {
            if(k.bdis < bdis) pal = k;
        }
        return pal;
    }
    */


    /**
     *laskee etäisyyden neliön tämän ja annetun kolmion CoG välillä
     * @param k
     * @return
     */
        public float calcDS(Kolmio k)
    {
        float dx = k.cog[0]-cog[0];
        float dy = k.cog[1]-cog[1];
        return dx*dx + dy*dy;
    }
    
    /**
     * päivittää naapurien f(?) arvon. Algoritmin metodi
     * @return Ne kolmiot jotka eivät olleet avoimella listalla mutta tulee lisätä sinne
     */
    public ArrayList<Kolmio> updateNeighbors()
    {
        ArrayList<Kolmio> pal = new ArrayList<>();
        for(Kolmio k: neighb)
        {
            if(k.updateGVal(calcDS(k)+gval,this)) pal.add(k);
        }
        return pal;
    }
    

    /**
     * päivittää f(?) arvon. Algoritmin metodi
     * @param val arvo
     * @param caller kolmio joka lähetti kutsun
     * @return tuleeko lisätä avoimelle listalle
     */
        public boolean updateGVal(float val, Kolmio caller)
    {
        if(state == 0)
        {
            gval = val;
            par = caller;
            state = 1;
            return true;
        }else if(state == 2) return false;
        //tila oli 1
        if(val < gval)
        {
            //löytyi parempi reitti tähän
            gval = val;
            par = caller;
        }
        return false;
        
    }
}

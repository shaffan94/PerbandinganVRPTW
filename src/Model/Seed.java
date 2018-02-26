/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author USER
 */
public class Seed {
    private int seed;
    private String kromosom;
    private int[] kromosom_number;
    
    public Seed(){}
    
    public Seed(int seed, int[] kromosom_number){
        this.seed = seed;
        this.kromosom_number = kromosom_number;
    }
    
    public Seed(int seed, String kromosom){
        this.seed = seed;
        this.kromosom = kromosom;
    }
    
    public void set_kromosomToString(){
        String tambah;
        int i;
        
        for(i=0; i<kromosom_number.length; i++){
            tambah = String.valueOf(kromosom_number[i]);
            kromosom = kromosom + tambah;
        }
    }
    
    public void set_kromosomToArrayInt(){
        String kromosomSplit[];
        int i;
        
        kromosomSplit = kromosom.split(",");
        for(i=0; i<kromosomSplit.length; i++){
            kromosom_number[i] = Integer.valueOf(kromosomSplit[i]);
        }
    }
}

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
public class KromosomPopulasi {
    private int individu;
    private String kromosomString;
    private int[] kromosomNumber;
    private int jumlahKendaraan;
    private double jumlahTerlewati;
    private double fitness;
    
    public KromosomPopulasi(){}
    
    public void setIndividu(int individu){
        this.individu = individu;
    }
    
    public void setkromosomNumber(int[] kromosomNumber){
        this.kromosomNumber = kromosomNumber;
    }
    
    public void setjumlahKendaraan(int jumlahKendaraan){
        this.jumlahKendaraan = jumlahKendaraan;
    }
    
    public void setjumlahTerlewati(double jumlahTerlewati){
        this.jumlahTerlewati = jumlahTerlewati;
    }
    
    public void setFitness(double fitness){
        this.fitness = fitness;
    }
    
    public int getIndividu(){
        return individu;
    }
    
    public int[] getkromosomNumber(){
        return kromosomNumber;
    }
    
    public int getjumlahKendaraan(){
        return jumlahKendaraan;
    }
    
    public double getjumlahTerlewati(){
        return jumlahTerlewati;
    }
    
    public double getFitness(){
        return fitness;
    }
    
    public void setKromosomString(){
        int i;
        
        kromosomString = "";
        for(i=0; i<kromosomNumber.length; i++){
            if(i == kromosomNumber.length-1){
                kromosomString = kromosomString + String.valueOf(kromosomNumber[i]);
            }
            else{
                kromosomString = kromosomString + String.valueOf(kromosomNumber[i] + ", ");
            }
        }
    }
    
    public String getkromosomString(){
        return kromosomString;
    }
}

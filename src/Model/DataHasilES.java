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
public class DataHasilES {
    private int id;
    private int jumlah_lokasi_kunjungan;
    private int jumlah_populasi;
    private int jumlah_generasi;
    private double probabilitas_mutasi;
    private int laju_kendaraan;
    private int kapasitas_angkutan;
    private double nilai_fitness;
    private double waktu_komputasi;
    
    public DataHasilES(){}
    
    public void set_idES(int id){
        this.id = id;
    }
    
    public void set_jumlahLokasiKunjungan(int jumlah_lokasi_kunjungan){
        this.jumlah_lokasi_kunjungan = jumlah_lokasi_kunjungan;
    }
    
    public void set_jumlahPopulasi(int jumlah_populasi){
        this.jumlah_populasi = jumlah_populasi;
    }
    
    public void set_jumlahGenerasi(int jumlah_generasi){
        this.jumlah_generasi = jumlah_generasi;
    }
    
    public void set_probabilitasMutasi(double probabilitas_mutasi){
        this.probabilitas_mutasi = probabilitas_mutasi;
    }
    
    public void set_lajuKendaraan(int laju_kendaraan){
        this.laju_kendaraan = laju_kendaraan;
    }
    
    public void set_kapasitasAngkutan(int kapasitas_angkutan){
        this.kapasitas_angkutan = kapasitas_angkutan;
    }
    
    public void set_nilaiFitness(double nilai_fitness){
        this.nilai_fitness = nilai_fitness;
    }
    
    public void set_waktuKomputasi(double waktu_komputasi){
        this.waktu_komputasi = waktu_komputasi;
    }
    
    
    public int get_idES(){
        return id;
    }
    
    public int get_jumlahLokasiKunjungan(){
        return jumlah_lokasi_kunjungan;
    }
    
    public int get_jumlahPopulasi(){
        return jumlah_populasi;
    }
    
    public int get_jumlahGenerasi(){
        return jumlah_generasi;
    }
    
    public double get_probabilitasMutasi(){
        return probabilitas_mutasi;
    }
    
    public int get_lajuKendaraan(){
        return laju_kendaraan;
    }
    
    public int get_kapasitasAngkutan(){
        return kapasitas_angkutan;
    }
    
    public double get_nilaiFitness(){
        return nilai_fitness;
    }
    
    public double get_waktuKomputasi(){
        return waktu_komputasi;
    }
}

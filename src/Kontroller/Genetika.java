/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kontroller;

import Model.DataHasilAG;
import java.util.ArrayList;
import java.util.Collections;
import Model.DataTujuan;
import Model.KromosomPopulasi;
import Model.Seed;

/**
 *
 * @author USER
 */
public class Genetika {
    private int ukuranPopulasi;
    private int generasi;
    private double prob_crossover;
    private double prob_mutation;
    private double waktu_komputasi;
    private double fitness_terbaik;
    private int kapasitasAngkut;
    private int kecepatan;
    private int n_dataPelanggan;
    private int n_dataTujuan;
    private int[][] dataPelanggan;
    private int[][] dataJarak;
    private ArrayList<int[]> mPopulasi;
    private ArrayList<Double> fitnessPopulasi;
    private ArrayList<ArrayList> populasiKendaraan;
    private ArrayList<ArrayList> datapopulasiKendaraan;
    private ArrayList<KromosomPopulasi> kromosomlistLama;
    private ArrayList<KromosomPopulasi> kromosomlistBaru;
    private ArrayList<ArrayList> dataPopulasiTujuanKendaraan;
    private KromosomPopulasi kp;
    
    public Genetika(){}
    
    public void setUkuranPopulasi(int ukuranPopulasi){this.ukuranPopulasi = ukuranPopulasi;}
    public void setProbCrossover(double prob_crossover){this.prob_crossover = prob_crossover;}
    public void setProbMutation(double prob_mutation){this.prob_mutation = prob_mutation;}
    public void setKapasitasAngkut(int kapasitasAngkut){this.kapasitasAngkut = kapasitasAngkut;}
    public void setKecepatan(int kecepatan){this.kecepatan = kecepatan;}
    public void setGenerasi(int generasi){this.generasi = generasi;}
    
    
    public void insialisasiData(int[][] dataPelanggan, int[][] dataJarak){
        this.dataPelanggan = dataPelanggan;
        this.dataJarak = dataJarak;
        n_dataPelanggan = dataPelanggan.length;
        //System.out.println("data pelanggan:"+n_dataPelanggan);
        n_dataTujuan = dataJarak.length;
        //System.out.println("data jarak:"+n_dataTujuan);
        System.out.println("Inisialisasi data is Done");
    }
    
    public void populasiAwal(){
        int i,j;
        int rand;
        int[] populasi;
        Seed seed;
        ArrayList<Integer> randNum;
        ArrayList<Seed> seeds;
        
        
        randNum = new ArrayList<>();
        mPopulasi = new ArrayList<>();
        seeds = new ArrayList<>();
        
        for(i=1; i<=34; i++){
            randNum.add(i);
        }
        
        for(i=0; i<ukuranPopulasi; i++){
            Collections.shuffle(randNum);
            //System.out.println("Populasi lama ke-"+i+": ");
            populasi = new int[n_dataTujuan-1];
            
            for(j=0; j<n_dataTujuan-1; j++){  
                populasi[j] = randNum.get(j);
                //System.out.println("kromosom ke-"+j+": "+populasi[j]);
            }
            
            //seed = new Seed(i, populasi);
            //seeds.add(seed);
            mPopulasi.add(populasi);
            //System.out.println();
        }
        
        System.out.println("Populasi Awal is Done");
    }
    
    public void pembagianKendaraan(){
        int i,j, k,prm;
        int jumlah;
        int[] populasi;
        ArrayList<Integer> ruteKendaraan;
        ArrayList<ArrayList> Kendaraan;
        
        if(mPopulasi.size() > ukuranPopulasi){        
            for(i=ukuranPopulasi; i<mPopulasi.size(); i++){
                populasi = (int[])mPopulasi.get(i);
                jumlah = 0;
                Kendaraan = new ArrayList<>();
                ruteKendaraan = new ArrayList<>();
                for(j=0; j<n_dataTujuan-1; j++){
                    int temp = populasi[j];
                    prm =  dataPelanggan[temp-1][6];

                    if((jumlah+prm) <= 1680){
                        jumlah = jumlah + prm;
                        ruteKendaraan.add(temp);
                    }
                    else{
                        Kendaraan.add(ruteKendaraan);
                        ruteKendaraan = new ArrayList<>();
                        ruteKendaraan.add(temp);
                        jumlah = 0;
                        jumlah = jumlah + prm;
                    }

                    if(j == n_dataTujuan-2){
                        Kendaraan.add(ruteKendaraan);
                        jumlah = 0;
                    }
                }

                populasiKendaraan.add(Kendaraan);
            }
            System.out.println("Pembagian Kendaraan baru is Done");
        }
        else{        
            populasiKendaraan = new ArrayList<>();
            
            for(i=0; i<ukuranPopulasi; i++){
                populasi = (int[])mPopulasi.get(i);
                jumlah = 0;
                Kendaraan = new ArrayList<>();
                ruteKendaraan = new ArrayList<>();
                for(j=0; j<n_dataTujuan-1; j++){
                    int temp = populasi[j];
                    prm =  dataPelanggan[temp-1][6];

                    if((jumlah+prm) <= 1680){
                        jumlah = jumlah + prm;
                        ruteKendaraan.add(temp);
                    }
                    else{
                        Kendaraan.add(ruteKendaraan);
                        ruteKendaraan = new ArrayList<>();
                        ruteKendaraan.add(temp);
                        jumlah = 0;
                        jumlah = jumlah + prm;
                    }

                    if(j == n_dataTujuan-2){
                        Kendaraan.add(ruteKendaraan);
                        jumlah = 0;
                    }
                }

                populasiKendaraan.add(Kendaraan);
            }
            
            System.out.println("Pembagian Kendaraan is Done");
        }
    }
    
    public void rute_kendaraan(){
        int i, j, k;
        int[] _rute;
        int[] inisial_waktu = new int[2];
        int[] waktu_sampai;
        int[] waktu_buka = new int[2];
        int[] waktu_mulai;
        int[] waktu_selesai;
        int[] waktu_tutup = new int[2];
        int[] waktuSelesai1 = new int[2];
        int[] waktuSelesai2 = new int[2];
        DataTujuan dataTujuan;
        
        int waktu_tempuh_menit, waktu_tunggu, waktu_layanan, jumlah_waktuSelesai, penalti, jumlah_penalti, banyak_penalti;
        int[][] data;
        ArrayList<int[]> dataKendaraan;
        ArrayList<ArrayList> dataTujuanKendaraan;
        ArrayList<DataTujuan> dataTujuanList;
        
        if(mPopulasi.size() > ukuranPopulasi){   
            data = dataPelanggan;
            
            for(i=ukuranPopulasi; i<mPopulasi.size(); i++){
                dataTujuanKendaraan = new ArrayList<>();
                ArrayList<ArrayList> kend = (ArrayList<ArrayList>)populasiKendaraan.get(i);
                dataKendaraan = new ArrayList<>();
                //System.out.println("Populasi ke-"+i+": ");
                for(j=0; j<kend.size(); j++){
                    dataTujuanList = new ArrayList<>();
                    int[] data_tujuan = new int[3];
                    ArrayList<Integer> rute = (ArrayList<Integer>)kend.get(j);
                    _rute = new int[rute.size()];

                    inisial_waktu[0] = 7;
                    inisial_waktu[1] = 0;
                    waktu_selesai = new int[2];

                    for(k=0; k<rute.size(); k++){
                        _rute[k] = (int)rute.get(k);
                    }

                    jumlah_penalti = banyak_penalti = 0;

                    //System.out.println("Kendaraan ke-"+j+": ");
                    //System.out.println("Jumlah Rute:"+rute.size());
                    for(k=0; k<rute.size(); k++){
                        dataTujuan = new DataTujuan();
                        if(k == 0){
                            waktu_tempuh_menit = (int)(((double)dataJarak[0][_rute[k]]/(double)kecepatan)*60);
                            waktu_sampai = tambah_waktu(inisial_waktu, waktu_tempuh_menit);
                            
                            waktu_buka[0] = data[_rute[k]-1][1];
                            waktu_buka[1] = data[_rute[k]-1][2];
                            
                            waktu_tunggu = getWaktuTunggu(waktu_sampai, waktu_buka);

                            if(waktu_tunggu == 0){
                                waktu_mulai = waktu_sampai;
                            }
                            else{
                                waktu_mulai = waktu_buka;
                            }

                            waktu_layanan = data[_rute[k]-1][5];
                            waktu_selesai = tambah_waktu(waktu_mulai, waktu_layanan);
                            
                            waktu_tutup[0] = data[_rute[k]-1][3];
                            waktu_tutup[1] = data[_rute[k]-1][4];

                            penalti = getWaktuPenalti(waktu_tutup, waktu_selesai);
                            waktuSelesai1[0] = waktu_selesai[0];
                            waktuSelesai1[1] = waktu_selesai[1];

                            if(penalti == 0){
                                jumlah_penalti = jumlah_penalti + penalti;
                            }
                            else{
                                jumlah_penalti = jumlah_penalti + penalti;
                                banyak_penalti = banyak_penalti + 1;
                            }
                            
                            String sampai = String.valueOf(waktu_sampai[0]) + ":" +String.valueOf(waktu_sampai[1]);
                            String buka = String.valueOf(waktu_buka[0]) + ":" +String.valueOf(waktu_buka[1]);
                            String mulai = String.valueOf(waktu_mulai[0]) + ":" +String.valueOf(waktu_mulai[1]);
                            String selesai = String.valueOf(waktu_selesai[0]) + ":" +String.valueOf(waktu_selesai[1]);
                            String tutup = String.valueOf(waktu_tutup[0]) + ":" +String.valueOf(waktu_tutup[1]);

                            dataTujuan.setIndividu(String.valueOf(i));
                            dataTujuan.setKendaraan(String.valueOf(j));
                            dataTujuan.setNode(String.valueOf(_rute[k]));
                            dataTujuan.setWaktu(String.valueOf(waktu_tempuh_menit));
                            dataTujuan.setSampai(sampai);
                            dataTujuan.setBuka(String.valueOf(buka));
                            dataTujuan.setTunggu(String.valueOf(waktu_tunggu));
                            dataTujuan.setMulai(String.valueOf(mulai));
                            dataTujuan.setLayanan(String.valueOf(waktu_layanan));
                            dataTujuan.setSelesai(selesai);
                            dataTujuan.setTutup(tutup);
                            dataTujuan.setPenalti(String.valueOf(penalti));
                            
                            if(rute.size() == 1){
                                data_tujuan[0] = 0;
                                data_tujuan[1] = jumlah_penalti;
                                data_tujuan[2] = banyak_penalti;    
                            }
                        }
                        else{
                            waktu_tempuh_menit = (int)(((double)dataJarak[_rute[k-1]][_rute[k]]/(double)kecepatan)*60);
                            waktu_sampai = tambah_waktu(waktu_selesai, waktu_tempuh_menit);

                            waktu_buka[0] = data[_rute[k]-1][1];
                            waktu_buka[1] = data[_rute[k]-1][2];

                            waktu_tunggu = getWaktuTunggu(waktu_sampai, waktu_buka);

                            if(waktu_tunggu == 0){
                                waktu_mulai = waktu_sampai;
                            }
                            else{
                                waktu_mulai = waktu_buka;
                            }

                            waktu_layanan = data[_rute[k]-1][5];
                            waktu_selesai = tambah_waktu(waktu_mulai, waktu_layanan);
                            
                            waktu_tutup[0] = data[_rute[k]-1][3];
                            waktu_tutup[1] = data[_rute[k]-1][4];

                            penalti = getWaktuPenalti(waktu_tutup, waktu_selesai);
                            if(penalti == 0){
                                jumlah_penalti = jumlah_penalti + penalti;
                            }
                            else{
                                jumlah_penalti = jumlah_penalti + penalti;
                                banyak_penalti = banyak_penalti + 1;
                            }
                            
                            String sampai = String.valueOf(waktu_sampai[0]) + ":" +String.valueOf(waktu_sampai[1]);
                            String buka = String.valueOf(waktu_buka[0]) + ":" +String.valueOf(waktu_buka[1]);
                            String mulai = String.valueOf(waktu_mulai[0]) + ":" +String.valueOf(waktu_mulai[1]);
                            String selesai = String.valueOf(waktu_selesai[0]) + ":" +String.valueOf(waktu_selesai[1]);
                            String tutup = String.valueOf(waktu_tutup[0]) + ":" +String.valueOf(waktu_tutup[1]);
                            
                            dataTujuan.setIndividu(String.valueOf(i));
                            dataTujuan.setKendaraan(String.valueOf(j));
                            dataTujuan.setNode(String.valueOf(_rute[k]));
                            dataTujuan.setWaktu(String.valueOf(waktu_tempuh_menit));
                            dataTujuan.setSampai(sampai);
                            dataTujuan.setBuka(String.valueOf(buka));
                            dataTujuan.setTunggu(String.valueOf(waktu_tunggu));
                            dataTujuan.setMulai(String.valueOf(mulai));
                            dataTujuan.setLayanan(String.valueOf(waktu_layanan));
                            dataTujuan.setSelesai(selesai);
                            dataTujuan.setTutup(tutup);
                            dataTujuan.setPenalti(String.valueOf(penalti));

                            if(k == rute.size()-1){      
                                //System.out.println("Rute ke-"+k+": ");
                                waktuSelesai2[0] = waktu_selesai[0];
                                waktuSelesai2[1] = waktu_selesai[1];

                                jumlah_waktuSelesai = selisih_waktu(waktuSelesai1, waktuSelesai2);
                                //System.out.println("selisih waktu selesai : "+jumlah_waktuSelesai);
                                //System.out.println();
                                data_tujuan[0] = jumlah_waktuSelesai;
                                data_tujuan[1] = jumlah_penalti;
                                data_tujuan[2] = banyak_penalti;    
                            }
                        }    
                        dataTujuanList.add(dataTujuan);
                    }
                    dataKendaraan.add(data_tujuan);
                    dataTujuanKendaraan.add(dataTujuanList);
                }
                dataPopulasiTujuanKendaraan.add(dataTujuanKendaraan);
                datapopulasiKendaraan.add(dataKendaraan);
            }
            
            //System.out.println("Populasi baru : "+datapopulasiKendaraan.size());
            //System.out.println("Tujuan Baru : "+dataTujuanList.size());
            System.out.println("Rute Kendaraan Baru is Done");
        }
        else{
            data = dataPelanggan;
            datapopulasiKendaraan = new ArrayList<>();
            dataPopulasiTujuanKendaraan = new ArrayList<>();
            
            for(i=0; i<ukuranPopulasi; i++){      
                dataTujuanKendaraan = new ArrayList<>();
                ArrayList<ArrayList> kend = (ArrayList<ArrayList>)populasiKendaraan.get(i);
                dataKendaraan = new ArrayList<>();
                //System.out.println("Populasi ke-"+i+": ");
                for(j=0; j<kend.size(); j++){
                    dataTujuanList = new ArrayList<>();
                    int[] data_tujuan = new int[3];
                    ArrayList<Integer> rute = (ArrayList<Integer>)kend.get(j);
                    _rute = new int[rute.size()];

                    inisial_waktu[0] = 7;
                    inisial_waktu[1] = 0;
                    waktu_selesai = new int[2];
                    
                    for(k=0; k<rute.size(); k++){
                        _rute[k] = (int)rute.get(k);
                    }

                    jumlah_penalti = banyak_penalti = 0;

                    //System.out.println("Kendaraan ke-"+j+": ");
                    //System.out.println("Jumlah Rute:"+rute.size());
                    for(k=0; k<rute.size(); k++){
                        dataTujuan = new DataTujuan();
                        if(k == 0){
                            waktu_tempuh_menit = (int)(((double)dataJarak[0][_rute[k]]/(double)kecepatan)*60);
                            waktu_sampai = tambah_waktu(inisial_waktu, waktu_tempuh_menit);
                            
                            waktu_buka[0] = data[_rute[k]-1][1];
                            waktu_buka[1] = data[_rute[k]-1][2];
                            
                            waktu_tunggu = getWaktuTunggu(waktu_sampai, waktu_buka);
                            
                            if(waktu_tunggu == 0){
                                waktu_mulai = waktu_sampai;
                            }
                            else{
                                waktu_mulai = waktu_buka;
                            }

                            waktu_layanan = data[_rute[k]-1][5];
                            waktu_selesai = tambah_waktu(waktu_mulai, waktu_layanan);
                            
                            waktu_tutup[0] = data[_rute[k]-1][3];
                            waktu_tutup[1] = data[_rute[k]-1][4];

                            penalti = getWaktuPenalti(waktu_tutup, waktu_selesai);
                            waktuSelesai1[0] = waktu_selesai[0];
                            waktuSelesai1[1] = waktu_selesai[1];

                            if(penalti == 0){
                                jumlah_penalti = jumlah_penalti + penalti;
                            }
                            else{
                                jumlah_penalti = jumlah_penalti + penalti;
                                banyak_penalti = banyak_penalti + 1;
                            }
                            
                            String sampai = String.valueOf(waktu_sampai[0]) + ":" +String.valueOf(waktu_sampai[1]);
                            String buka = String.valueOf(waktu_buka[0]) + ":" +String.valueOf(waktu_buka[1]);
                            String mulai = String.valueOf(waktu_mulai[0]) + ":" +String.valueOf(waktu_mulai[1]);
                            String selesai = String.valueOf(waktu_selesai[0]) + ":" +String.valueOf(waktu_selesai[1]);
                            String tutup = String.valueOf(waktu_tutup[0]) + ":" +String.valueOf(waktu_tutup[1]);
                            
                            dataTujuan.setIndividu(String.valueOf(i));
                            dataTujuan.setKendaraan(String.valueOf(j));
                            dataTujuan.setNode(String.valueOf(_rute[k]));
                            dataTujuan.setWaktu(String.valueOf(waktu_tempuh_menit));
                            dataTujuan.setSampai(sampai);
                            dataTujuan.setBuka(buka);
                            dataTujuan.setTunggu(String.valueOf(waktu_tunggu));
                            dataTujuan.setMulai(mulai);
                            dataTujuan.setLayanan(String.valueOf(waktu_layanan));
                            dataTujuan.setSelesai(selesai);
                            dataTujuan.setTutup(tutup);
                            dataTujuan.setPenalti(String.valueOf(penalti));
                            
                            if(rute.size() == 1){
                                data_tujuan[0] = 0;
                                data_tujuan[1] = jumlah_penalti;
                                data_tujuan[2] = banyak_penalti;    
                            }
                        }
                        else{
                            waktu_tempuh_menit = (int)(((double)dataJarak[_rute[k-1]][_rute[k]]/(double)kecepatan)*60);
                            waktu_sampai = tambah_waktu(waktu_selesai, waktu_tempuh_menit);
                            
                            waktu_buka[0] = data[_rute[k]-1][1];
                            waktu_buka[1] = data[_rute[k]-1][2];
                            
                            waktu_tunggu = getWaktuTunggu(waktu_sampai, waktu_buka);
                            
                            if(waktu_tunggu == 0){
                                waktu_mulai = waktu_sampai;
                            }
                            else{
                                waktu_mulai = waktu_buka;
                            }
                            
                            waktu_layanan = data[_rute[k]-1][5];
                            waktu_selesai = tambah_waktu(waktu_mulai, waktu_layanan);
                            
                            waktu_tutup[0] = data[_rute[k]-1][3];
                            waktu_tutup[1] = data[_rute[k]-1][4];

                            penalti = getWaktuPenalti(waktu_tutup, waktu_selesai);
                            if(penalti == 0){
                                jumlah_penalti = jumlah_penalti + penalti;
                            }
                            else{
                                jumlah_penalti = jumlah_penalti + penalti;
                                banyak_penalti = banyak_penalti + 1;
                            }

                            String sampai = String.valueOf(waktu_sampai[0]) + ":" +String.valueOf(waktu_sampai[1]);
                            String buka = String.valueOf(waktu_buka[0]) + ":" +String.valueOf(waktu_buka[1]);
                            String mulai = String.valueOf(waktu_mulai[0]) + ":" +String.valueOf(waktu_mulai[1]);
                            String selesai = String.valueOf(waktu_selesai[0]) + ":" +String.valueOf(waktu_selesai[1]);
                            String tutup = String.valueOf(waktu_tutup[0]) + ":" +String.valueOf(waktu_tutup[1]);
                            
                            dataTujuan.setIndividu(String.valueOf(i));
                            dataTujuan.setKendaraan(String.valueOf(j));
                            dataTujuan.setNode(String.valueOf(_rute[k]));
                            dataTujuan.setWaktu(String.valueOf(waktu_tempuh_menit));
                            dataTujuan.setSampai(sampai);
                            dataTujuan.setBuka(buka);
                            dataTujuan.setTunggu(String.valueOf(waktu_tunggu));
                            dataTujuan.setMulai(mulai);
                            dataTujuan.setLayanan(String.valueOf(waktu_layanan));
                            dataTujuan.setSelesai(selesai);
                            dataTujuan.setTutup(tutup);
                            dataTujuan.setPenalti(String.valueOf(penalti));
                            
                            if(k == rute.size()-1){      
                                //System.out.println("Rute ke-"+k+": ");
                                waktuSelesai2[0] = waktu_selesai[0];
                                waktuSelesai2[1] = waktu_selesai[1];

                                jumlah_waktuSelesai = selisih_waktu(waktuSelesai1, waktuSelesai2);
                                //System.out.println("selisih waktu selesai : "+jumlah_waktuSelesai);
                                //System.out.println();
                                data_tujuan[0] = jumlah_waktuSelesai;
                                data_tujuan[1] = jumlah_penalti;
                                data_tujuan[2] = banyak_penalti;    
                            }
                        }  
                        dataTujuanList.add(dataTujuan);
                    }
                    dataKendaraan.add(data_tujuan);
                    dataTujuanKendaraan.add(dataTujuanList);
                }
                datapopulasiKendaraan.add(dataKendaraan);
                dataPopulasiTujuanKendaraan.add(dataTujuanKendaraan);
            }
            
            System.out.println("Rute Kendaraan is Done");
        }
        
        /*for(i=0; i<datapopulasiKendaraan.size(); i++){
            ArrayList<int[]> kend = (ArrayList<int[]>)datapopulasiKendaraan.get(i);
            int[] fitness = new int[3];
            
            System.out.println("Populasi ke-"+i+": ");
            
            for(j=0; j<kend.size(); j++){
                for(k=0; k<3; k++){
                    fitness[k] = kend.get(j)[k];
                }
            }
            
            for(j=0; j<3; j++){
                System.out.println("Data Tujuan ke-"+j+": "+fitness[j]);
            }
            
            System.out.println();
        }*/
    }
    
    public void doCrossOver(){
        double child_crossOver;
        int[][] krom;
        int[][] k;
        ArrayList<Integer> randPopulasi;
        ArrayList<Integer> kromosom1List;
        ArrayList<Integer> kromosom2List;
        ArrayList<Integer> kromosomChild;
        
        int[] kromosom1, kromosom2, temp1, temp2;
        int randPop1, randPop2;
        int i,j;
        
        child_crossOver = (int)(prob_crossover * (double)ukuranPopulasi);
        randPopulasi = new ArrayList<>();
        kromosom1List = new ArrayList<>();
        kromosom2List = new ArrayList<>();
        kromosomChild = new ArrayList<>();
        krom = new int[2][2];        
        
        for(i=0; i<ukuranPopulasi; i++){
            randPopulasi.add(i);
        }
        
        Collections.shuffle(randPopulasi);
        
        for(i=1; i<=child_crossOver; i++){
            //pemilihan populasi dilakukan secara berpasangan/dua-dua 
            if(i%2 == 0){
                k = new int[2][2];
                
                //proses pemilihan acak populasi untuk cross over
                randPop1 = randPopulasi.get(0);
                randPop2 = randPopulasi.get(1);
                
                kromosom1 = new int[34];
                kromosom2 = new int[34];
            
                //System.out.println("Populasi pertama : "+randPop1);
                //System.out.println("Populasi kedua : "+randPop2);
                temp1 = (int[])mPopulasi.get(randPopulasi.get(0));
                temp2 = (int[])mPopulasi.get(randPopulasi.get(1));

                for(j=0; j<n_dataTujuan-1; j++){
                    kromosom1[j] = temp1[j];
                    kromosom2[j] = temp2[j];
                }
                
                //proses pengubahan array integer ke array list integer
                for(j=0; j<n_dataTujuan-1; j++){
                    kromosom1List.add(kromosom1[j]);
                    kromosom2List.add(kromosom2[j]);
                }
                
                //pengacakan dua data kromosom pada list kromosom pertama dan kedua
                Collections.shuffle(kromosom1List);
                Collections.shuffle(kromosom2List);
                
                //proses mapping
                for(j=0; j<2; j++){
                    krom[0][j] = kromosom1List.get(j);
                    krom[1][j] = kromosom2List.get(j);
                }
                
                /*System.out.println("Index ke-"+i+": ");
                for(j=0; j<2; j++){
                    System.out.println("Kromosom1 ke-"+j+": "+krom[i-2][j]);
                    System.out.println("Kromosom2 ke-"+j+": "+krom[i-1][j]);
                }*/
                
                for(j=0; j<n_dataTujuan-1; j++){
                    if(kromosom1[j] == krom[0][0]){
                        kromosom1[j] = krom[1][0];
                        k[0][0] = j;
                    }
                    
                    if(kromosom1[j] == krom[0][1]){
                        kromosom1[j] = krom[1][1];
                        k[0][1] = j;
                    }
                    
                    if(kromosom2[j] == krom[1][0]){
                        kromosom2[j] = krom[0][0];
                        k[1][0] = j;
                    }
                    
                    if(kromosom2[j] == krom[1][1]){
                        kromosom2[j] = krom[0][1];
                        k[1][1] = j;
                    }
                }
                
                for(j=0; j<n_dataTujuan-1; j++){
                    if(kromosom1[j] == krom[1][0] && k[0][0] != j){
                        kromosom1[j] = krom[0][0];
                    }
                    
                    if(kromosom1[j] == krom[1][1] && k[0][1] != j){
                        kromosom1[j] = krom[0][1];
                    }
                    
                    if(kromosom2[j] == krom[0][0] && k[1][0] != j){
                        kromosom2[j] = krom[1][0];
                    }
                    
                    if(kromosom2[j] == krom[0][1] && k[1][1] != j){
                        kromosom2[j] = krom[1][1];
                    }
                }
                
                mPopulasi.add(kromosom1);
                mPopulasi.add(kromosom2);
                
                Collections.shuffle(randPopulasi);
                
                while(randPop1 == randPopulasi.get(0) && randPop2 == randPopulasi.get(1)){
                    Collections.shuffle(randPopulasi);
                }
            }
        }
        
        /*for(i=0; i<mPopulasi.size(); i++){
            kromosom1 = mPopulasi.get(i);
            System.out.println("Populasi baru ke-"+i+": ");
            for(j=0; j<n_dataTujuan-1; j++){
                System.out.println("kromosom ke-"+j+": "+kromosom1[j]);
            }
            System.out.println();
        }*/
        System.out.println(); 
        System.out.println("Proses CrossOver is Done");
    }
    
    public void doMutasi(){
        double child_mutasi;
        int i, j, l, randPop, simpan;
        int[] kromosom, krom, temp;
        ArrayList<Integer> kromosomList;
        ArrayList<Integer> randPopulasi;
        
        randPopulasi = new ArrayList<>();
        kromosomList = new ArrayList<>();
        
        child_mutasi = (int)(prob_mutation * (double)ukuranPopulasi);
        
        for(i=0; i<ukuranPopulasi; i++){
            randPopulasi.add(i);
        }
        
        Collections.shuffle(randPopulasi);
        
        for(i=0; i<child_mutasi; i++){
            randPop = randPopulasi.get(0);
            
            kromosom = new int[34];
            
            temp = (int[])mPopulasi.get(randPopulasi.get(0));
            
            for(j=0; j<n_dataTujuan-1; j++){
                kromosom[j] = temp[j];
            }
            
            for(j=0; j<n_dataTujuan-1; j++){
                kromosomList.add(kromosom[j]);
            }

            Collections.shuffle(kromosomList);
            
            //proses mapping
            krom = new int[2];
            for(j=0; j<2; j++){
                krom[j] = kromosomList.get(j);
                //System.out.println("kromosom ke-"+j+": "+krom[j]);
            }
            
            l=0;
            for(j=0; j<n_dataTujuan-1; j++){
                if(kromosom[j] == krom[0] || kromosom[j] == krom[1]){
                    if(l==0){
                        if(kromosom[j] == krom[0]){
                            kromosom[j] = krom[1];
                        }
                        else if(kromosom[j] == krom[1]){
                            kromosom[j] = krom[0];
                        }
                        l = l+1;
                    }
                    else{
                        if(kromosom[j] == krom[0]){
                            kromosom[j] = krom[1];
                        }
                        else if(kromosom[j] == krom[1]){
                            kromosom[j] = krom[0];
                        }
                    }
                }
            }
            
            mPopulasi.add(kromosom);
            
            Collections.shuffle(randPopulasi);

            while(randPop == randPopulasi.get(0)){
                Collections.shuffle(randPopulasi);
            }
        }
        
        System.out.println("Proses Mutation is Done");
    }
    
    public void hitungFitness(){
        int i,j,k,l;
        int jumlah; 
        int[] dataWaktu;
       
        if(mPopulasi.size() > ukuranPopulasi){
            for(i=ukuranPopulasi; i<mPopulasi.size(); i++){
                ArrayList<int[]> kend = (ArrayList<int[]>)datapopulasiKendaraan.get(i);
                double fitness = 0.0;
                //System.out.println("Populasi ke-"+i+": ");
                for(j=0; j<kend.size(); j++){
                    dataWaktu = (int[])kend.get(j);
                    jumlah = 0;
                    for(k=0; k<dataWaktu.length; k++){
                        jumlah = jumlah + dataWaktu[k];
                    }

                    if(jumlah == 0){
                        fitness = fitness + 0;
                    }else{
                        fitness =  fitness + 1.0/(double)jumlah;
                    }
                }
                //System.out.println(); 
                //System.out.println("fitness : "+fitness);
                fitnessPopulasi.add(fitness);
            }
            
            /*System.out.println("fitness baru : "+fitnessPopulasi.size());
            System.out.println(); 
            for(i=0; i<fitnessPopulasi.size(); i++){
                System.out.println("Populasi ke-"+i+": "+fitnessPopulasi.get(i));
            }*/
            
            System.out.println("hitung fitness baru is Done");
        }
        else{
            fitnessPopulasi = new ArrayList<>();
       
            for(i=0; i<datapopulasiKendaraan.size(); i++){
                ArrayList<int[]> kend = (ArrayList<int[]>)datapopulasiKendaraan.get(i);
                double fitness = 0.0;
                //System.out.println("Populasi ke-"+i+": ");
                for(j=0; j<kend.size(); j++){
                    dataWaktu = (int[])kend.get(j);
                    jumlah = 0;
                    for(k=0; k<dataWaktu.length; k++){
                        jumlah = jumlah + dataWaktu[k];
                    }

                    if(jumlah == 0){
                        fitness = fitness + 0;
                    }else{
                        fitness =  fitness + 1.0/(double)jumlah;
                    }
                }
                //System.out.println(); 
                //System.out.println("fitness : "+fitness);
                fitnessPopulasi.add(fitness);
            }
            
            System.out.println("hitung fitness is Done");
        }
    }
    
    public void setPopulasiKromosomBaru(){
        int i,j,k,l;
        ArrayList<Integer> nAmbil;
        ArrayList<int[]> populasiBarus;
        int[][] populasiBaru;
        int[] populasiTemp;
        ArrayList<Double> fitnessTemp;      
        
        fitnessTemp = new ArrayList<>();
        populasiBarus = new ArrayList<>();
        
        for(i=0; i<mPopulasi.size(); i++){
            fitnessTemp.add((double)fitnessPopulasi.get(i));
        }
        
        //System.out.print("fitness Populasi : ");

        //System.out.println();
        //System.out.println();
        
        
        /*System.out.println("populasi kromosom lama : ");
        for(i=0; i<mPopulasi.size(); i++){
            populasiTemp = mPopulasi.get(i);
            
            System.out.print("populasi ke-"+i+": ");
            for(j=0; j<n_dataTujuan-1; j++){
                System.out.print(populasiTemp[j]+" ");
            }
            
            System.out.println();
        }
        System.out.println();
        */
        
        int isSama;
        for(i=0; i<ukuranPopulasi; i++){
            nAmbil = new ArrayList<>();
            for(j=0; j<mPopulasi.size(); j++){
                double maxToMin = 0.0;
                int n = -1;
                
                //System.out.println("populasi ke-"+j+": ");
                for(k=0; k<mPopulasi.size(); k++){                    
                    if(nAmbil.isEmpty()){
                        if(maxToMin < fitnessTemp.get(k)){
                            maxToMin = fitnessTemp.get(k);
                            n=k;
                        }
                    }
                    else{
                        isSama = 0;
                        
                        //System.out.println("nAmbil : "+nAmbil.size());
                        for(l=0; l<nAmbil.size(); l++){
                            if(nAmbil.get(l) == k){
                                isSama = 1;
                            }
                        }
                        
                        if(isSama == 0){
                            if(maxToMin < fitnessTemp.get(k)){
                                maxToMin = fitnessTemp.get(k);
                                n=k;
                            }
                        }
                    }
                }
                
                if(n != -1){
                    nAmbil.add(n);
                }
            }
            
            ArrayList<ArrayList> KendaraanBaru;            
            ArrayList<int[]> dataKendaraanBaru;
            ArrayList<ArrayList> dataTujuanKendaraan;
            
            ArrayList<ArrayList> populasiKendaraanBaru;            
            ArrayList<ArrayList> dataPopulasiKendaraanBaru;
            ArrayList<ArrayList> dataTujuanKendaraanPopulasi;
            
            populasiKendaraanBaru = new ArrayList<>();
            dataPopulasiKendaraanBaru = new ArrayList<>();
            fitnessTemp = new ArrayList<>();
            dataTujuanKendaraanPopulasi = new ArrayList<>(); 
            
            populasiBaru = new int[ukuranPopulasi][n_dataTujuan-1];
            for(j=0; j<ukuranPopulasi; j++){
                int pTerbesar = nAmbil.get(j);
                double fitness_temp; 
                
                dataTujuanKendaraan = new ArrayList<>(dataPopulasiTujuanKendaraan.get(pTerbesar));   
                KendaraanBaru = new ArrayList<>(populasiKendaraan.get(pTerbesar));
                dataKendaraanBaru = new ArrayList<>(datapopulasiKendaraan.get(pTerbesar));
                populasiTemp = mPopulasi.get(pTerbesar);
                fitness_temp = fitnessPopulasi.get(pTerbesar);
                
                dataTujuanKendaraanPopulasi.add(dataTujuanKendaraan);
                populasiKendaraanBaru.add(KendaraanBaru);
                dataPopulasiKendaraanBaru.add(dataKendaraanBaru);
                fitnessTemp.add(fitness_temp);
                
                for(k=0; k<n_dataTujuan-1; k++){
                    populasiBaru[j][k] = populasiTemp[k];
                }
            }
            
            mPopulasi = new ArrayList<>();
            populasiKendaraan = new ArrayList<>();
            datapopulasiKendaraan = new ArrayList<>();
            fitnessPopulasi = new ArrayList<>();
            dataPopulasiTujuanKendaraan = new ArrayList<>();
            
            for(j=0; j<ukuranPopulasi; j++){
                populasiTemp = new int[n_dataTujuan-1];
                double fitness_temp;
                
                dataTujuanKendaraan = new ArrayList<>(dataTujuanKendaraanPopulasi.get(j));  
                KendaraanBaru = new ArrayList<>(populasiKendaraanBaru.get(j));
                dataKendaraanBaru = new ArrayList<>(dataPopulasiKendaraanBaru.get(j));
                fitness_temp = fitnessTemp.get(j);
                        
                dataPopulasiTujuanKendaraan.add(dataTujuanKendaraan);
                populasiKendaraan.add(KendaraanBaru);
                datapopulasiKendaraan.add(dataKendaraanBaru); 
                fitnessPopulasi.add(fitness_temp);
                
                for(k=0; k<n_dataTujuan-1; k++){
                    populasiTemp[k] = populasiBaru[j][k];
                }
                
                mPopulasi.add(populasiTemp);
            }
        }
        
        /*System.out.println("populasi kromosom baru : ");
        for(i=0; i<mPopulasi.size(); i++){
            populasiTemp = mPopulasi.get(i);
            
            System.out.print("populasi ke-"+i+": ");
            for(j=0; j<n_dataTujuan-1; j++){
                System.out.print(populasiTemp[j]+" ");
            }
            System.out.println();
        }
        System.out.println("populasi kendaraan baru: "+populasiKendaraan.size());
        System.out.println("data populasi kendaraan baru: "+datapopulasiKendaraan.size());
        System.out.println();
        */
        /*for(i=0; i<ukuranPopulasi; i++){
            ArrayList<ArrayList> kend = (ArrayList<ArrayList>)populasiKendaraan.get(i);
            System.out.println("Populasi kendaraan ke-"+i+": ");
            System.out.println("Banyak Kendaraan : "+kend.size());
            for(j=0; j<kend.size(); j++){
                ArrayList<Integer> rute_kend = (ArrayList<Integer>)kend.get(j);
                System.out.println("Banyak Rute: "+rute_kend.size());
                for(k=0; k<rute_kend.size(); k++){
                   System.out.println("Rute ke-"+k+": "+rute_kend.get(k));
                }
                System.out.println();
            }
            System.out.println();
        }*/
        
        System.out.println("Pembuatan Populasi baru is Done");
    }
    
    public void setKromosomTerbaik(){
        int i, fitnessKe;
        double fitnessTerbesar;
        int[] kromosom;
        
        /*for(i=0; i<mPopulasi.size(); i++){
            System.out.println("fitness sebelum ke-"+i+": "+fitnessPopulasi.get(i));
        }*/
        
        setkromosomPopulasiBaru();
        
        /*fitnessTerbesar = 0;
        fitnessKe = 0;
        
        for(i=0; i<mPopulasi.size(); i++){
            double fitnessNow = fitnessPopulasi.get(i);
            if(fitnessTerbesar < fitnessNow){
                fitnessTerbesar = fitnessNow;
                fitnessKe = i;
            }
        }*/
        
        /*for(i=0; i<mPopulasi.size(); i++){
            System.out.println("fitness ke-"+i+": "+fitnessPopulasi.get(i));
        }*/
        
       // System.out.println("Fitness terbesar terletak pada Populasi ke-"+fitnessKe);
        kp = kromosomlistBaru.get(0);
        kp.setKromosomString();
        
        /*System.out.println("Kromosom terbaiknya : "+fitnessKe);
        System.out.println("Kromosomnya : "+kp.getkromosomString());
        System.out.println("Jumlah kendaraan : "+kp.getjumlahKendaraan());
        System.out.println("Jumlah terlewati : "+kp.getjumlahTerlewati());
        System.out.println("Nilai fitness : "+kp.getFitness());*/
    }
    
    public KromosomPopulasi getKromosomTerbaik(){
        return kp;
    }
    
    public ArrayList<KromosomPopulasi> getKromosomListLama(){
        return kromosomlistLama;
    }
    
    public ArrayList<ArrayList> getDataTujuan(){
        return dataPopulasiTujuanKendaraan;
    }
    
    public void setkromosomPopulasiLama(){
        int i,j,k,l, jumlah, jumlah_terlewati;
        int[] kromosom, dataWaktu;
        double[] fitnessTemp;
        ArrayList<Integer> nAmbil;
        KromosomPopulasi kromosomPopulasi;
        KromosomPopulasi kromosomPopulasiTemp;
        ArrayList<KromosomPopulasi> kromosomPopulasis;
        
        kromosomlistLama = new ArrayList<>();
        kromosomPopulasis = new ArrayList<>();
        for(i=0; i<mPopulasi.size(); i++){
            kromosom = (int[])mPopulasi.get(i); 
            ArrayList<int[]> kend = (ArrayList<int[]>)datapopulasiKendaraan.get(i);
            double fitness = 0.0;
            
            jumlah_terlewati = 0;
            //menghitung fitness dan kendaraan size
            for(j=0; j<kend.size(); j++){
                dataWaktu = (int[])kend.get(j);
                jumlah = 0;
                
                jumlah_terlewati = jumlah_terlewati + dataWaktu[2]; 
                for(k=0; k<dataWaktu.length; k++){
                    
                    jumlah = jumlah + dataWaktu[k];
                }

                if(jumlah == 0){
                    fitness = fitness + 0;
                }else{
                    fitness =  fitness + 1.0/(double)jumlah;
                }
            }
            
            /*System.out.println("Populasi ke-"+i+" : ");
            System.out.println(kromosom.length);
            System.out.println(kend.size());
            System.out.println(jumlah_terlewati);
            System.out.println(fitness);*/
            kromosomPopulasi = new KromosomPopulasi();     
            kromosomPopulasi.setIndividu(i);
            kromosomPopulasi.setkromosomNumber(kromosom);
            kromosomPopulasi.setjumlahKendaraan(kend.size());
            kromosomPopulasi.setjumlahTerlewati(jumlah_terlewati);
            kromosomPopulasi.setFitness(fitness);
            kromosomPopulasis.add(kromosomPopulasi);
        }
        
        fitnessTemp = new double[mPopulasi.size()];
        for(i=0; i<mPopulasi.size(); i++){
            fitnessTemp[i] = kromosomPopulasis.get(i).getFitness();
        }
        
        int isSama;
        
        nAmbil = new ArrayList<>();
        for(j=0; j<mPopulasi.size(); j++){
            double maxToMin = 0.0;
            int n = -1;

            //System.out.println("populasi ke-"+j+": ");
            for(k=0; k<mPopulasi.size(); k++){                    
                if(nAmbil.isEmpty()){
                    if(maxToMin < fitnessTemp[k]){
                        maxToMin = fitnessTemp[k];
                        n=k;
                    }
                }
                else{
                    isSama = 0;

                    //System.out.println("nAmbil : "+nAmbil.size());
                    for(l=0; l<nAmbil.size(); l++){
                        if(nAmbil.get(l) == k){
                            isSama = 1;
                        }
                    }

                    if(isSama == 0){
                        if(maxToMin < fitnessTemp[k]){
                            maxToMin = fitnessTemp[k];
                            n=k;
                        }
                    }
                }
            }

            if(n != -1){
                nAmbil.add(n);
            }
        }
        
        int fitnessTerbesarIndex;
        
        for(i=0; i<mPopulasi.size(); i++){
            fitnessTerbesarIndex = nAmbil.get(i);
            
            kromosomPopulasiTemp = new KromosomPopulasi();
            kromosomPopulasiTemp.setIndividu(kromosomPopulasis.get(fitnessTerbesarIndex).getIndividu());
            kromosomPopulasiTemp.setkromosomNumber(kromosomPopulasis.get(fitnessTerbesarIndex).getkromosomNumber());
            kromosomPopulasiTemp.setjumlahKendaraan(kromosomPopulasis.get(fitnessTerbesarIndex).getjumlahKendaraan());
            kromosomPopulasiTemp.setjumlahTerlewati(kromosomPopulasis.get(fitnessTerbesarIndex).getjumlahTerlewati());
            kromosomPopulasiTemp.setFitness(kromosomPopulasis.get(fitnessTerbesarIndex).getFitness());
            kromosomlistLama.add(kromosomPopulasiTemp);
        }
    }
    
    public ArrayList<KromosomPopulasi> getKromosomListBaru(){
        return kromosomlistBaru;
    }
    
    public void setkromosomPopulasiBaru(){
        int i,j,k,l, jumlah, jumlah_terlewati;
        ArrayList<Integer> nAmbil;
        int[] kromosom, dataWaktu;
        double[] fitnessTemp;
        KromosomPopulasi kromosomPopulasi;
        KromosomPopulasi kromosomPopulasiTemp;
        ArrayList<KromosomPopulasi> kromosomPopulasis;
        
        kromosomlistBaru = new ArrayList<>();
        kromosomPopulasis = new ArrayList<>();
        
        for(i=0; i<mPopulasi.size(); i++){
            kromosom = (int[])mPopulasi.get(i); 
            ArrayList<int[]> kend = (ArrayList<int[]>)datapopulasiKendaraan.get(i);
            double fitness = 0.0;
            //System.out.println("Populasi ke-"+i+": ");
            
            jumlah_terlewati = 0;
            //menghitung fitness dan kendaraan size
            for(j=0; j<kend.size(); j++){
                dataWaktu = (int[])kend.get(j);
                jumlah = 0;
                jumlah_terlewati = jumlah_terlewati + dataWaktu[2]; 
                for(k=0; k<dataWaktu.length; k++){
                    jumlah = jumlah + dataWaktu[k];
                }

                if(jumlah == 0){
                    fitness = fitness + 0;
                }else{
                    fitness =  fitness + 1.0/(double)jumlah;
                }
            }
            //System.out.println("fitness : "+fitness);
            
            /*System.out.println("Populasi ke-"+i+" : ");
            System.out.println(kromosom.length);
            System.out.println(kend.size());
            System.out.println(jumlah_terlewati);
            System.out.println(fitness);*/
            
            kromosomPopulasi = new KromosomPopulasi();
            kromosomPopulasi.setIndividu(i);
            kromosomPopulasi.setkromosomNumber(kromosom);
            kromosomPopulasi.setjumlahKendaraan(kend.size());
            kromosomPopulasi.setjumlahTerlewati(jumlah_terlewati);
            kromosomPopulasi.setFitness(fitness);
            kromosomPopulasis.add(kromosomPopulasi);
            //kp = new KromosomPopulasi(i, kromosom, kend.size(), jumlah_terlewati, fitness);            
            //kromosomlistBaru.add(kp);
        }
        
        fitnessTemp = new double[mPopulasi.size()];
        for(i=0; i<mPopulasi.size(); i++){
            fitnessTemp[i] = kromosomPopulasis.get(i).getFitness();
        }
        
        int isSama;
        
        nAmbil = new ArrayList<>();
        for(j=0; j<mPopulasi.size(); j++){
            double maxToMin = 0.0;
            int n = -1;

            //System.out.println("populasi ke-"+j+": ");
            for(k=0; k<mPopulasi.size(); k++){                    
                if(nAmbil.isEmpty()){
                    if(maxToMin < fitnessTemp[k]){
                        maxToMin = fitnessTemp[k];
                        n=k;
                    }
                }
                else{
                    isSama = 0;

                    //System.out.println("nAmbil : "+nAmbil.size());
                    for(l=0; l<nAmbil.size(); l++){
                        if(nAmbil.get(l) == k){
                            isSama = 1;
                        }
                    }

                    if(isSama == 0){
                        if(maxToMin < fitnessTemp[k]){
                            maxToMin = fitnessTemp[k];
                            n=k;
                        }
                    }
                }
            }

            if(n != -1){
                nAmbil.add(n);
            }
        }
        
        int fitnessTerbesarIndex;
        
        for(i=0; i<mPopulasi.size(); i++){
            fitnessTerbesarIndex = nAmbil.get(i);
            
            kromosomPopulasiTemp = new KromosomPopulasi();
            kromosomPopulasiTemp.setIndividu(kromosomPopulasis.get(fitnessTerbesarIndex).getIndividu());
            kromosomPopulasiTemp.setkromosomNumber(kromosomPopulasis.get(fitnessTerbesarIndex).getkromosomNumber());
            kromosomPopulasiTemp.setjumlahKendaraan(kromosomPopulasis.get(fitnessTerbesarIndex).getjumlahKendaraan());
            kromosomPopulasiTemp.setjumlahTerlewati(kromosomPopulasis.get(fitnessTerbesarIndex).getjumlahTerlewati());
            kromosomPopulasiTemp.setFitness(kromosomPopulasis.get(fitnessTerbesarIndex).getFitness());
            kromosomlistBaru.add(kromosomPopulasiTemp);
        }
    }
    
    public int[] tambah_waktu(int[] waktu, int menit){
        int jamLama, menitLama, menitBaru, jamBaru, perubahanJam;
        int[] waktu_baru = new int[2];
        
        jamLama = waktu[0];
        menitLama = waktu[1];
    
        perubahanJam = 0;
        
        menitBaru = menitLama+menit;
        
        if(menitBaru >= 60){
            while(menitBaru >= 60){
                menitBaru = menitBaru-60;
                perubahanJam = perubahanJam + 1;
            }
        }
        
        waktu_baru[0] = jamLama + perubahanJam;
        waktu_baru[1] = menitBaru;
        
        if(waktu_baru[0] >= 24){
            waktu_baru[0] = waktu_baru[0]-24;
        }
        
        return waktu_baru; 
    }
    
    public int selisih_waktu(int[] waktu1, int[] waktu2){
        int[] waktuTerbesar =  new int[2];
        int[] waktuPengurang = new int[2];
        int[] waktu = new int[2];
        int selisih_waktu;

        //waktu1 untuk waktu awal selesai
        //waktu2 untuk waktu akhir selesai
        if(waktu1[0] > waktu2[0]){
            waktuTerbesar = waktu1;
            waktuPengurang = waktu2;
        }
        else if(waktu1[0] < waktu2[0]){
            waktuTerbesar = waktu2;
            waktuPengurang = waktu1;
        }
        else if(waktu1[0] == waktu1[0]){
            if(waktu1[1] > waktu2[1]){
                waktuTerbesar = waktu1;
                waktuPengurang = waktu2;
            }
            else if(waktu1[1] < waktu2[1]){
                waktuTerbesar = waktu2;
                waktuPengurang = waktu1;
            }
            else{
                waktuTerbesar = waktuPengurang = waktu1;
                
            }
        }
        
        if(waktuTerbesar[1] > waktuPengurang[1]){
            waktu[0] = waktuTerbesar[0]-waktuPengurang[0];
            waktu[1] = waktuTerbesar[1]-waktuPengurang[1];
        }
        else if(waktuTerbesar[1] < waktuPengurang[1]){
            waktuTerbesar[1] = waktuTerbesar[1]+60;
            waktuTerbesar[0] = waktuTerbesar[0]-1;

            waktu[0] = waktuTerbesar[0]-waktuPengurang[0];
            waktu[1] = waktuTerbesar[1]-waktuPengurang[1];
        }
        else{
            waktu[0] = waktuTerbesar[0]-waktuPengurang[0];
            waktu[1] = waktuTerbesar[1]-waktuPengurang[1];
        }        
        
        selisih_waktu = konversiJamkeMenit(waktu);
        
        return selisih_waktu; 
    }
    
    public int getWaktuTunggu(int[] waktu1, int[] waktu2){
        int[] waktuTerbesar =  new int[2];
        int[] waktuPengurang = new int[2];
        int[] waktu = new int[2];
        int[] waktu_temp1 = new int[2];
        int[] waktu_temp2 = new int[2];
        int waktu_tunggu = 0;
        int isWaktuBukaBesar = 0;

        waktu_temp1[0] = waktu1[0];
        waktu_temp1[1] = waktu1[1];
        
        waktu_temp2[0] = waktu2[0];
        waktu_temp2[1] = waktu2[1];
        
        //waktu1 untuk waktu sampai
        //waktu2 untuk waktu buka
        if(waktu_temp1[0] > waktu_temp2[0]){
            waktu_tunggu = 0;
        }
        else if(waktu_temp1[0] < waktu_temp2[0]){
            waktuTerbesar = waktu_temp2;
            waktuPengurang = waktu_temp1;
            isWaktuBukaBesar = 1;
        }
        else if(waktu_temp1[0] == waktu_temp2[0]){
            if(waktu_temp1[1] > waktu_temp2[1]){
                waktu_tunggu = 0;
            }
            else if(waktu_temp1[1] < waktu_temp2[1]){
                waktuTerbesar = waktu_temp2;
                waktuPengurang = waktu_temp1;
                isWaktuBukaBesar = 1;
            }
            else{
                waktu_tunggu = 0;
            }
        }
        
        if(isWaktuBukaBesar == 1){
            if(waktuTerbesar[1] > waktuPengurang[1]){
                waktu[0] = waktuTerbesar[0]-waktuPengurang[0];
                waktu[1] = waktuTerbesar[1]-waktuPengurang[1];
            }
            else if(waktuTerbesar[1] < waktuPengurang[1]){
                waktuTerbesar[1] = waktuTerbesar[1]+60;
                waktuTerbesar[0] = waktuTerbesar[0]-1;

                waktu[0] = waktuTerbesar[0]-waktuPengurang[0];
                waktu[1] = waktuTerbesar[1]-waktuPengurang[1];
            }
            
            waktu_tunggu = konversiJamkeMenit(waktu);
        }
        
        return waktu_tunggu; 
    }
    
    public int getWaktuPenalti(int[] waktu1, int[] waktu2){
        int[] waktuTerbesar =  new int[2];
        int[] waktuPengurang = new int[2];
        int[] waktu = new int[2];
        int[] waktu_temp1 = new int[2];
        int[] waktu_temp2 = new int[2];
        int penalti = 0;
        int isWaktuSelesaiBesar = 0;

        waktu_temp1[0] = waktu1[0];
        waktu_temp1[1] = waktu1[1];
        
        waktu_temp2[0] = waktu2[0];
        waktu_temp2[1] = waktu2[1];
        
        //waktu1 untuk waktu tutup
        //waktu2 untuk waktu selesai
        if(waktu_temp1[0] > waktu_temp2[0]){
            penalti = 0;
        }
        else if(waktu_temp1[0] < waktu_temp2[0]){
            waktuTerbesar = waktu_temp2;
            waktuPengurang = waktu_temp1;
            isWaktuSelesaiBesar = 1;
        }
        else if(waktu_temp1[0] == waktu_temp2[0]){
            if(waktu_temp1[1] > waktu_temp2[1]){
                penalti = 0;
            }
            else if(waktu_temp1[1] < waktu_temp2[1]){
                waktuTerbesar = waktu_temp2;
                waktuPengurang = waktu_temp1;
                isWaktuSelesaiBesar = 1;
            }
            else{
                penalti = 0;
            }
        }
        
        if(isWaktuSelesaiBesar == 1){
            if(waktuTerbesar[1] > waktuPengurang[1]){
                waktu[0] = waktuTerbesar[0]-waktuPengurang[0];
                waktu[1] = waktuTerbesar[1]-waktuPengurang[1];
            }
            else if(waktuTerbesar[1] < waktuPengurang[1]){
                waktuTerbesar[1] = waktuTerbesar[1]+60;
                waktuTerbesar[0] = waktuTerbesar[0]-1;

                waktu[0] = waktuTerbesar[0]-waktuPengurang[0];
                waktu[1] = waktuTerbesar[1]-waktuPengurang[1];
            }
            
            penalti = konversiJamkeMenit(waktu);
        }
        
        return penalti; 
    }
    
    public int konversiJamkeMenit(int[] waktu){
        int menit;
        
        menit = (waktu[0]*60) + waktu[1];
        
        return menit;
    }
    
    public void bersihkanData(){
        fitness_terbaik = 0.0;
        mPopulasi = new ArrayList<>();
        fitnessPopulasi = new ArrayList<>();
        populasiKendaraan = new ArrayList<>();
        datapopulasiKendaraan = new ArrayList<>();
        kromosomlistLama = new ArrayList<>();
        kromosomlistBaru = new ArrayList<>();
        dataPopulasiTujuanKendaraan = new ArrayList<>();
    }
    
    public void set_waktuKomputasi(double waktu_komp){
        waktu_komputasi = waktu_komp;
    }
    
    public double get_waktuKomputasi(){
        return waktu_komputasi;
    }
    
    public void set_fitnessTerbaik(double fitness_terbaik){
        this.fitness_terbaik = fitness_terbaik;
    }
    
    public double get_fitnessTerbaik(){
        return fitness_terbaik;
    }
    
    public void simpan_parameterHasilAG(){
        DatabaseManager databaseManager;
        String genetika = "INSERT INTO data_genetika (id_genetika, jum_lok_kunjungan, jum_populasi, jum_generasi, prob_crossover, prob_mutasi, laju_kendaraan, kapasitas_angkutan, nilai_fitness, waktu_komputasi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        databaseManager = new DatabaseManager();
        databaseManager.set_KoneksiDatabase();
        
        DataHasilAG modelDataHasilAG = new DataHasilAG();
        modelDataHasilAG.set_idAG(1);
        modelDataHasilAG.set_jumlahLokasiKunjungan(n_dataTujuan-1);
        modelDataHasilAG.set_jumlahPopulasi(ukuranPopulasi);
        modelDataHasilAG.set_jumlahGenerasi(generasi);
        modelDataHasilAG.set_probabilitasCrossover(prob_crossover);
        modelDataHasilAG.set_probabilitasMutasi(prob_mutation);
        modelDataHasilAG.set_lajuKendaraan(kecepatan);
        modelDataHasilAG.set_kapasitasAngkutan(kapasitasAngkut);
        modelDataHasilAG.set_nilaiFitness(get_fitnessTerbaik());
        modelDataHasilAG.set_waktuKomputasi(get_waktuKomputasi());
        
        databaseManager.insertDataAG(genetika, modelDataHasilAG);
    }
    
    public void update_parameterHasilAG(){
        DatabaseManager databaseManager;
        String sqlGenetika = "UPDATE data_genetika set jum_lok_kunjungan=?, jum_populasi=?, jum_generasi=?, prob_crossover=?, prob_mutasi=?, laju_kendaraan=?, kapasitas_angkutan=?, nilai_fitness=?, waktu_komputasi=? where id_genetika=?";
        
        databaseManager = new DatabaseManager();
        databaseManager.set_KoneksiDatabase();
        
        DataHasilAG modelDataHasilAG = new DataHasilAG();
        modelDataHasilAG.set_idAG(1);
        modelDataHasilAG.set_jumlahLokasiKunjungan(n_dataTujuan-1);
        modelDataHasilAG.set_jumlahPopulasi(ukuranPopulasi);
        modelDataHasilAG.set_jumlahGenerasi(generasi);
        modelDataHasilAG.set_probabilitasCrossover(prob_crossover);
        modelDataHasilAG.set_probabilitasMutasi(prob_mutation);
        modelDataHasilAG.set_lajuKendaraan(kecepatan);
        modelDataHasilAG.set_kapasitasAngkutan(kapasitasAngkut);
        modelDataHasilAG.set_nilaiFitness(get_fitnessTerbaik());
        modelDataHasilAG.set_waktuKomputasi(get_waktuKomputasi());
        
        databaseManager.updateDataAG(sqlGenetika, modelDataHasilAG);
    }
    
    public int get_jumlahDataDababaseAG(){
        int jumlah_data;
        DatabaseManager dbManager;
        String sql_AG = "select count(*) from data_genetika";
        
        dbManager = new DatabaseManager();
        dbManager.set_KoneksiDatabase();
        
        jumlah_data = dbManager.get_jumlahData(sql_AG);
        
        return jumlah_data;
    }
}

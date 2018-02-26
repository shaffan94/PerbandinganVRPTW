/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kontroller;

import Model.DataHasilAG;
import Model.DataHasilES;
import java.util.ArrayList;
import java.util.Collections;
import Model.DataTujuan;
import Model.KromosomPopulasi;
import Model.Seed;

/**
 *
 * @author USER
 */
public class ES {
    private int ukuranPopulasi;
    private int konstanta;
    private int kapasitasAngkut;
    private int kecepatan;
    private int generasi;
    private double parameter_a;
    private double fitness_terbaik;
    private double waktu_komputasi;
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
    private ArrayList<double[]> populasiAturanStngh;
    private ArrayList<ArrayList> dataPopulasiTujuanKendaraan;
    private KromosomPopulasi kp;
    
    public ES(){}
    
    public void setUkuranPopulasi(int ukuranPopulasi){this.ukuranPopulasi = ukuranPopulasi;}
    public void setParameter_a(double parameter_a){this.parameter_a = parameter_a;}
    public void setKonstanta(int konstanta){this.konstanta = konstanta;}
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
        System.out.println();
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
        System.out.println();
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
            
            //System.out.println("banyak populasi: "+populasiKendaraan.size());
            System.out.println("Pembagian Kendaraan baru is Done");
            System.out.println();
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
            System.out.println();
        }        
        
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
        
        int waktu_tempuh_menit, waktu_tunggu, waktu_layanan, jumlah_waktuSelesai, penalti;
        int[][] data;
        double jumlah_penalti, banyak_penalti;
        ArrayList<double[]> dataKendaraan;
        ArrayList<DataTujuan> dataTujuanList;
        ArrayList<ArrayList> dataTujuanKendaraan;
        DataTujuan dataTujuan;
        
        if(mPopulasi.size() > ukuranPopulasi){   
            data = dataPelanggan;

            for(i=ukuranPopulasi; i<mPopulasi.size(); i++){
                dataTujuanKendaraan = new ArrayList<>();
                ArrayList<ArrayList> kend = (ArrayList<ArrayList>)populasiKendaraan.get(i);
                dataKendaraan = new ArrayList<>();
                //System.out.println("Populasi ke-"+i+": ");
                for(j=0; j<kend.size(); j++){
                    dataTujuanList = new ArrayList<>();
                    double[] data_tujuan = new double[3];
                    ArrayList<Integer> rute = (ArrayList<Integer>)kend.get(j);
                    _rute = new int[rute.size()];

                    inisial_waktu[0] = 7;
                    inisial_waktu[1] = 0;
                    waktu_selesai = new int[2];

                    for(k=0; k<rute.size(); k++){
                        _rute[k] = (int)rute.get(k);
                    }

                    jumlah_penalti = banyak_penalti = 0.0;

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
                                data_tujuan[0] = 0.0;
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
                datapopulasiKendaraan.add(dataKendaraan);
                dataPopulasiTujuanKendaraan.add(dataTujuanKendaraan);
            }
            
            //System.out.println("Populasi baru : "+datapopulasiKendaraan.size());
            System.out.println("Rute Kendaraan Baru is Done");
            System.out.println();
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
                    double[] data_tujuan = new double[3];
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
                datapopulasiKendaraan.add(dataKendaraan);
                dataPopulasiTujuanKendaraan.add(dataTujuanKendaraan);
            }
            
            System.out.println("Rute Kendaraan is Done");
            System.out.println();
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
    
    public void hitungFitness(){
        int i,j,k,l;
        double jumlah; 
        double[] dataWaktu;
       
        if(mPopulasi.size() > ukuranPopulasi){
            for(i=ukuranPopulasi; i<mPopulasi.size(); i++){
                ArrayList<double[]> kend = (ArrayList<double[]>)datapopulasiKendaraan.get(i);
                double fitness = 0.0;
                //System.out.println("Populasi ke-"+i+": ");
                for(j=0; j<kend.size(); j++){
                    dataWaktu = (double[])kend.get(j);
                    jumlah = 0;
                    //System.out.println("Kendaraan ke-"+j+": ");
                    for(k=0; k<dataWaktu.length; k++){
                        //System.out.println("Data ke-"+k+": "+dataWaktu[k]);
                        switch (k) {
                            case 1:
                                jumlah = jumlah + dataWaktu[k]*50;
                                break;
                            case 0:
                                jumlah = jumlah + (dataWaktu[k]*0.5);
                                break;
                            case 2:
                                jumlah = jumlah + dataWaktu[k]*50;
                                break;
                            default:
                                break;
                        }
                    }

                    if(jumlah == 0.0){
                        fitness = fitness + 0.0;
                    }else{
                        fitness =  fitness + 1000.0/jumlah;
                    }
                }
                //System.out.println(); 
                fitnessPopulasi.add(fitness);
            }
            
            System.out.println("fitness baru : "+fitnessPopulasi.size());
            /*System.out.println(); 
            for(i=0; i<fitnessPopulasi.size(); i++){
                System.out.println("Populasi ke-"+i+": "+fitnessPopulasi.get(i));
            }*/
            
            System.out.println("hitung fitness baru is Done");
            System.out.println(); 
        }
        else{
            fitnessPopulasi = new ArrayList<>();
       
            for(i=0; i<datapopulasiKendaraan.size(); i++){
                ArrayList<double[]> kend = (ArrayList<double[]>)datapopulasiKendaraan.get(i);
                double fitness = 0.0;
                //System.out.println("Populasi ke-"+i+": ");
                for(j=0; j<kend.size(); j++){
                    dataWaktu = (double[])kend.get(j);
                    jumlah = 0;
                    //System.out.println("Kendaraan ke-"+j+": ");
                    for(k=0; k<dataWaktu.length; k++){
                        //System.out.println("Data ke-"+k+": "+dataWaktu[k]);
                        switch (k) {
                            case 1:
                                jumlah = jumlah + dataWaktu[k]*50;
                                break;
                            case 0:
                                jumlah = jumlah + (dataWaktu[k]*0.5);
                                break;
                            case 2:
                                jumlah = jumlah + dataWaktu[k]*50;
                                break;
                            default:
                                break;
                        }
                    }

                    if(jumlah == 0){
                        fitness = fitness + 0;
                    }else{
                        fitness =  fitness + 1000.0/jumlah;
                    }
                }
                //System.out.println(); 
                fitnessPopulasi.add(fitness);
            }
            
            System.out.println("hitung fitness is Done");
            System.out.println(); 
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
        for(i=0; i<mPopulasi.size(); i++){
            double temp = fitnessTemp.get(i);
            //System.out.print(temp+" ");
        }
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
        int i;
        //double fitnessTerbesar, fitnessNow;
        
        setkromosomPopulasiBaru();
        
        //fitnessTerbesar = 0;
        //fitnessKe = 0;
        
        /*for(i=0; i<mPopulasi.size(); i++){
            fitnessNow = fitnessPopulasi.get(i);
            if(fitnessTerbesar < fitnessNow){
                fitnessTerbesar = fitnessNow;
                fitnessKe = i;
            }
        }*/
        
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
        int i,j,k,l;
        double jumlah_terlewati, jumlah;
        int[] kromosom;
        double[] dataWaktu;
        double[] fitnessTemp;
        ArrayList<Integer> nAmbil;
        KromosomPopulasi kromosomPopulasi;
        KromosomPopulasi kromosomPopulasiTemp;
        ArrayList<KromosomPopulasi> kromosomPopulasis;
        
        kromosomlistLama = new ArrayList<>();
        kromosomPopulasis = new ArrayList<>();
        
        for(i=0; i<mPopulasi.size(); i++){
            kromosom = (int[])mPopulasi.get(i); 
            ArrayList<double[]> kend = (ArrayList<double[]>)datapopulasiKendaraan.get(i);
            double fitness = 0.0;
            
            jumlah_terlewati = 0.0;
            //menghitung fitness dan kendaraan size
            for(j=0; j<kend.size(); j++){
                dataWaktu = (double[])kend.get(j);
                jumlah = 0;
                
                jumlah_terlewati = jumlah_terlewati + dataWaktu[2]; 
                for(k=0; k<dataWaktu.length; k++){
                    switch (k) {
                            case 1:
                                jumlah = jumlah + dataWaktu[k]*50;
                                break;
                            case 0:
                                jumlah = jumlah + (dataWaktu[k]*0.5);
                                break;
                            case 2:
                                jumlah = jumlah + dataWaktu[k]*50;
                                break;
                            default:
                                break;
                        }
                }

                if(jumlah == 0){
                    fitness = fitness + 0.0;
                }else{
                    fitness =  fitness + 1000.0/jumlah;
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
        int i,j,k,l;
        double jumlah_terlewati, jumlah;
        int[] kromosom;
        double[] dataWaktu;
        double[] fitnessTemp;
        ArrayList<Integer> nAmbil;
        KromosomPopulasi kromosomPopulasi;
        KromosomPopulasi kromosomPopulasiTemp;
        ArrayList<KromosomPopulasi> kromosomPopulasis;
        
        kromosomlistBaru = new ArrayList<>();
        kromosomPopulasis = new ArrayList<>();
        
        for(i=0; i<mPopulasi.size(); i++){
            kromosom = (int[])mPopulasi.get(i); 
            ArrayList<double[]> kend = (ArrayList<double[]>)datapopulasiKendaraan.get(i);
            double fitness = 0.0;
            
            jumlah_terlewati = 0.0;
            //menghitung fitness dan kendaraan size
            for(j=0; j<kend.size(); j++){
                dataWaktu = (double[])kend.get(j);
                jumlah = 0.0;
                
                jumlah_terlewati = jumlah_terlewati + dataWaktu[2]; 
                for(k=0; k<dataWaktu.length; k++){
                    switch (k) {
                            case 1:
                                jumlah = jumlah + dataWaktu[k]*50;
                                break;
                            case 0:
                                jumlah = jumlah + (dataWaktu[k]*0.5);
                                break;
                            case 2:
                                jumlah = jumlah + dataWaktu[k]*50;
                                break;
                            default:
                                break;
                        }
                }

                if(jumlah == 0.0){
                    fitness = fitness + 0.0;
                }else{
                    fitness =  fitness + 1000.0/jumlah;
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
            kromosomlistBaru.add(kromosomPopulasiTemp);
        }
    }
    
    public void setAturanSetengah(){
        int i,j;
        double[] aturanStngh;
        KromosomPopulasi kromPop;
        
        populasiAturanStngh = new ArrayList<>();
        for(i=0; i<kromosomlistBaru.size(); i++){
            kromPop = kromosomlistBaru.get(i);
            aturanStngh = new double[3]; 
            
            if(i<ukuranPopulasi){
                aturanStngh[0] = (double)(kromPop.getFitness()*20)/(double)100;
                aturanStngh[1] = kromPop.getFitness()+aturanStngh[0];
                aturanStngh[2] = kromPop.getFitness()-aturanStngh[0];

                populasiAturanStngh.add(aturanStngh);
            }
            else{
                if(kromPop.getFitness() < populasiAturanStngh.get(i-ukuranPopulasi)[2]){
                    aturanStngh[0] = -2;
                    aturanStngh[1] = kromPop.getFitness()/parameter_a;
                    aturanStngh[2] = kromPop.getFitness()*0.9;
                }
                else{
                    aturanStngh[0] = -1;
                    aturanStngh[1] = kromPop.getFitness()*parameter_a;
                    aturanStngh[2] = kromPop.getFitness()*1.1;
                }
                
                populasiAturanStngh.add(aturanStngh);
            }
        }
    }
    
    public ArrayList<double[]> getAturanSetengah(){
        return populasiAturanStngh;
    }
    
    public void doMutasi(){
        double offspring;
        int i, j, l, randPop, simpan;
        int[] kromosom, krom, temp;
        ArrayList<Integer> kromosomList;
        ArrayList<Integer> randPopulasi;
        
        randPopulasi = new ArrayList<>();
        kromosomList = new ArrayList<>();
        
        offspring = (int)(konstanta * (double)ukuranPopulasi);
        
        for(i=0; i<ukuranPopulasi; i++){
            randPopulasi.add(i);
        }
        
        Collections.shuffle(randPopulasi);
        
        for(i=0; i<offspring; i++){
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
        System.out.println(); 
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
        populasiAturanStngh = new ArrayList<>();
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
    
    public void simpan_parameterHasilES(){
        DatabaseManager databaseManager;
        String genetika = "INSERT INTO data_ES (id_es, jum_lok_kunjungan, jum_populasi, jum_generasi, konstanta, laju_kendaraan, kapasitas_angkutan, nilai_fitness, waktu_komputasi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        databaseManager = new DatabaseManager();
        databaseManager.set_KoneksiDatabase();
        
        DataHasilES modelDataHasilES = new DataHasilES();
        modelDataHasilES.set_idES(1);
        modelDataHasilES.set_jumlahLokasiKunjungan(n_dataTujuan-1);
        modelDataHasilES.set_jumlahPopulasi(ukuranPopulasi);
        modelDataHasilES.set_jumlahGenerasi(generasi);
        modelDataHasilES.set_probabilitasMutasi(konstanta);
        modelDataHasilES.set_lajuKendaraan(kecepatan);
        modelDataHasilES.set_kapasitasAngkutan(kapasitasAngkut);
        modelDataHasilES.set_nilaiFitness(get_fitnessTerbaik());
        modelDataHasilES.set_waktuKomputasi(get_waktuKomputasi());
        
        databaseManager.insertDataES(genetika, modelDataHasilES);
    }
    
    public void update_parameterHasilES(){
        DatabaseManager databaseManager;
        String sqlES = "UPDATE data_es set jum_lok_kunjungan=?, jum_populasi=?, jum_generasi=?, konstanta=?, laju_kendaraan=?, kapasitas_angkutan=?, nilai_fitness=?, waktu_komputasi=? where id_es=?";
        
        databaseManager = new DatabaseManager();
        databaseManager.set_KoneksiDatabase();
        
        DataHasilES modelDataHasilES = new DataHasilES();
        modelDataHasilES.set_idES(1);
        modelDataHasilES.set_jumlahLokasiKunjungan(n_dataTujuan-1);
        modelDataHasilES.set_jumlahPopulasi(ukuranPopulasi);
        modelDataHasilES.set_jumlahGenerasi(generasi);
        modelDataHasilES.set_probabilitasMutasi(konstanta);
        modelDataHasilES.set_lajuKendaraan(kecepatan);
        modelDataHasilES.set_kapasitasAngkutan(kapasitasAngkut);
        modelDataHasilES.set_nilaiFitness(get_fitnessTerbaik());
        modelDataHasilES.set_waktuKomputasi(get_waktuKomputasi());
        
        databaseManager.updateDataES(sqlES, modelDataHasilES);
    }
    
    public int get_jumlahDataDababaseES(){
        int jumlah_data;
        DatabaseManager dbManager;
        String sql_ES = "select count(*) from data_es";
        
        dbManager = new DatabaseManager();
        dbManager.set_KoneksiDatabase();
        
        jumlah_data = dbManager.get_jumlahData(sql_ES);
        
        return jumlah_data;
    }
}

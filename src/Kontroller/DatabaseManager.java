/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Kontroller;

import Model.DataHasilAG;
import Model.DataHasilES;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class DatabaseManager {
    private Connection conn = null;
    
    private final String user = "root";
    private final String password = "";
    
    public DatabaseManager(){}
    
    public void set_KoneksiDatabase(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vrptw", user, password);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int get_jumlahData(String sql){
        int jumlah = 0;
        
        try {            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            result.next();
            jumlah = result.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return jumlah;
    }
    
    public void insertDataAG(String sql, DataHasilAG modelDataAG){
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, modelDataAG.get_idAG());
            statement.setInt(2, modelDataAG.get_jumlahLokasiKunjungan());
            statement.setInt(3, modelDataAG.get_jumlahPopulasi());
            statement.setInt(4, modelDataAG.get_jumlahGenerasi());
            statement.setDouble(5, modelDataAG.get_probabilitasCrossover());
            statement.setDouble(6, modelDataAG.get_probabilitasMutasi());
            statement.setInt(7, modelDataAG.get_lajuKendaraan());
            statement.setInt(8, modelDataAG.get_kapasitasAngkutan());
            statement.setDouble(9, modelDataAG.get_nilaiFitness());
            statement.setDouble(10, modelDataAG.get_waktuKomputasi());
            
            int rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertDataES(String sql, DataHasilES modelDataES){
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, modelDataES.get_idES());
            statement.setInt(2, modelDataES.get_jumlahLokasiKunjungan());
            statement.setInt(3, modelDataES.get_jumlahPopulasi());
            statement.setInt(4, modelDataES.get_jumlahGenerasi());
            statement.setDouble(5, modelDataES.get_probabilitasMutasi());
            statement.setInt(6, modelDataES.get_lajuKendaraan());
            statement.setInt(7, modelDataES.get_kapasitasAngkutan());
            statement.setDouble(8, modelDataES.get_nilaiFitness());
            statement.setDouble(9, modelDataES.get_waktuKomputasi());
            
            int rowsInserted = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Model.DataHasilAG get_selectDataAG(String sql){
        DataHasilAG modelDataAG = new DataHasilAG();
        
        try {            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()){
                modelDataAG.set_idAG(result.getInt(1));
                modelDataAG.set_jumlahLokasiKunjungan(result.getInt(2));
                modelDataAG.set_jumlahPopulasi(result.getInt(3));
                modelDataAG.set_jumlahGenerasi(result.getInt(4));
                modelDataAG.set_probabilitasCrossover(result.getDouble(5));
                modelDataAG.set_probabilitasMutasi(result.getDouble(6));
                modelDataAG.set_lajuKendaraan(result.getInt(7));
                modelDataAG.set_kapasitasAngkutan(result.getInt(8));
                modelDataAG.set_nilaiFitness(result.getDouble(9));
                modelDataAG.set_waktuKomputasi(result.getDouble(10));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return modelDataAG;
    }
    
    public Model.DataHasilES get_selectDataES(String sql){
        DataHasilES modelDataES = new DataHasilES();
        
        try {            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next()){
                modelDataES.set_idES(result.getInt(1));
                modelDataES.set_jumlahLokasiKunjungan(result.getInt(2));
                modelDataES.set_jumlahPopulasi(result.getInt(3));
                modelDataES.set_jumlahGenerasi(result.getInt(4));
                modelDataES.set_probabilitasMutasi(result.getDouble(5));
                modelDataES.set_lajuKendaraan(result.getInt(6));
                modelDataES.set_kapasitasAngkutan(result.getInt(7));
                modelDataES.set_nilaiFitness(result.getDouble(8));
                modelDataES.set_waktuKomputasi(result.getDouble(9));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return modelDataES;
    }
    
    public void updateDataAG(String sql, DataHasilAG modelDataAG){
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, modelDataAG.get_jumlahLokasiKunjungan());
            statement.setInt(2, modelDataAG.get_jumlahPopulasi());
            statement.setInt(3, modelDataAG.get_jumlahGenerasi());
            statement.setDouble(4, modelDataAG.get_probabilitasCrossover());
            statement.setDouble(5, modelDataAG.get_probabilitasMutasi());
            statement.setInt(6, modelDataAG.get_lajuKendaraan());
            statement.setInt(7, modelDataAG.get_kapasitasAngkutan());
            statement.setDouble(8, modelDataAG.get_nilaiFitness());
            statement.setDouble(9, modelDataAG.get_waktuKomputasi());
            statement.setInt(10, modelDataAG.get_idAG());
            
            
            int rowsUpdated = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateDataES(String sql, DataHasilES modelDataES){
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, modelDataES.get_jumlahLokasiKunjungan());
            statement.setInt(2, modelDataES.get_jumlahPopulasi());
            statement.setInt(3, modelDataES.get_jumlahGenerasi());
            statement.setDouble(4, modelDataES.get_probabilitasMutasi());
            statement.setInt(5, modelDataES.get_lajuKendaraan());
            statement.setInt(6, modelDataES.get_kapasitasAngkutan());
            statement.setDouble(7, modelDataES.get_nilaiFitness());
            statement.setDouble(8, modelDataES.get_waktuKomputasi());
            statement.setInt(9, modelDataES.get_idES());
            
            
            int rowsUpdated = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

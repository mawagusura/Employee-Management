/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author LUCASMasson
 */
public class DataAccess {
        
    private Connection connection;
    
    
    public DataAccess(String url, String user,String password){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection(url,user,password);
        }
        catch(SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex){
             Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public List<Employes> getEmployes(String sql) {
        List<Employes> listEmployes=new ArrayList<Employes>();
        try{
            Statement stm = connection.createStatement();
            ResultSet rs=stm.executeQuery(sql);
            Employes employes;
            while(rs.next()){
                employes=new Employes();
                employes.setId(rs.getInt("ID"));
                employes.setNom(rs.getString("NOM"));
                employes.setPrenom(rs.getString("PRENOM"));
                employes.setTeldom(rs.getString("TELDOM"));
                employes.setTelport(rs.getString("TELPORT"));
                employes.setTelpro(rs.getString("TELPRO"));
                employes.setAdresse(rs.getString("ADRESSE"));
                employes.setCodepostal(rs.getString("CODEPOSTAL"));
                employes.setVille(rs.getString("VILLE"));
                employes.setEmail(rs.getString("EMAIL"));
                listEmployes.add(employes);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return listEmployes;
    }

    public Identifiant getIdentifiant(String sql,String login, String pwd) {        
        Identifiant id=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pwd);
            ResultSet rs=preparedStatement.executeQuery();
            
            while(rs.next()){
                id= new Identifiant();
                id.setLogin(rs.getString("LOGIN"));
                id.setPwd(rs.getString("PASSWORD"));
            }
            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    
    public void closeConnection() {
     try {
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean delete(String sql, String id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    Employes getEmployes(String sql, String id) {
        Employes employes=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                employes=new Employes();
                employes.setId(rs.getInt("ID"));
                employes.setNom(rs.getString("NOM"));
                employes.setPrenom(rs.getString("PRENOM"));
                employes.setTeldom(rs.getString("TELDOM"));
                employes.setTelport(rs.getString("TELPORT"));
                employes.setTelpro(rs.getString("TELPRO"));
                employes.setAdresse(rs.getString("ADRESSE"));
                employes.setCodepostal(rs.getString("CODEPOSTAL"));
                employes.setVille(rs.getString("VILLE"));
                employes.setEmail(rs.getString("EMAIL"));
            }
            
            return employes;
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    boolean updateEmployes(String sql, Employes employes) {
        try {PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employes.getNom());
            preparedStatement.setString(2, employes.getPrenom());
            preparedStatement.setString(3, employes.getTeldom());
            preparedStatement.setString(4, employes.getTelport());
            preparedStatement.setString(5, employes.getTelpro());
            preparedStatement.setString(6, employes.getAdresse());
            preparedStatement.setString(7, employes.getCodepostal());
            preparedStatement.setString(8, employes.getVille());
            preparedStatement.setString(9, employes.getEmail());
            preparedStatement.setInt(10, employes.getId());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

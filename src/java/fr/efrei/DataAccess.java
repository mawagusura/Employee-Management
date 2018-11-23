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
        catch(SQLException | ClassNotFoundException ex){
             Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public List<Employes> getEmployes(String sql) {
        List<Employes> listEmployes=new ArrayList<>();
        try{
            Statement stm = connection.createStatement();
            ResultSet rs=stm.executeQuery(sql);
            Employes employes;
            while(rs.next()){
                employes=new Employes();
            }            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
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
}

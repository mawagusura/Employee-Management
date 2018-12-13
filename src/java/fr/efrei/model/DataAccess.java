/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.model;

import fr.efrei.entities.Employee;
import fr.efrei.entities.Login;
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
        
    private static final String DRIVER="com.mysql.jdbc.Driver";
    
    private static final String EMPLOYES_ID="ID";
    private static final String EMPLOYES_NOM="NOM";
    private static final String EMPLOYES_PRENOM="PRENOM";
    private static final String EMPLOYES_TELDOM="TELDOM";
    private static final String EMPLOYES_TELPRO="TELPRO";
    private static final String EMPLOYES_TELPORT="TELPORT";
    private static final String EMPLOYES_ADRESSE="ADRESSE";
    private static final String EMPLOYES_VILLE="VILLE";
    private static final String EMPLOYES_CODEPOSTAL="CODEPOSTAL";
    private static final String EMPLOYES_EMAIL="EMAIL";
    
    private static final String IDENTIFIANT_LOGIN="LOGIN";
    private static final String IDENTIFIANT_PASSWORD="PASSWORD";        
    
    private Connection connection;    
    
    /**
     * Constructor of DataAccess, and create a DB connection based on parameters
     * @param url
     * @param user
     * @param password 
     */
    public DataAccess(String url, String user,String password){
        try{
            Class.forName(DRIVER).newInstance();
            this.connection = DriverManager.getConnection(url,user,password);
        }
        catch(SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex){
             Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Execute a statement to query employees based on the sql given in paramerter
     * @param sql
     * @return 
     */
    public List<Employee> getEmployees(String sql) {
        List<Employee> listEmployes=new ArrayList<>();
        try{
            Statement stm = connection.createStatement();
            try (ResultSet rs = stm.executeQuery(sql)) {
                Employee employes;
                while(rs.next()){
                    employes=new Employee();
                    employes.setId(rs.getInt(EMPLOYES_ID));
                    employes.setNom(rs.getString(EMPLOYES_NOM));
                    employes.setPrenom(rs.getString(EMPLOYES_PRENOM));
                    employes.setTeldom(rs.getString(EMPLOYES_TELDOM));
                    employes.setTelport(rs.getString(EMPLOYES_TELPORT));
                    employes.setTelpro(rs.getString(EMPLOYES_TELPRO));
                    employes.setAdresse(rs.getString(EMPLOYES_ADRESSE));
                    employes.setCodepostal(rs.getString(EMPLOYES_CODEPOSTAL));
                    employes.setVille(rs.getString(EMPLOYES_VILLE));
                    employes.setEmail(rs.getString(EMPLOYES_EMAIL));
                    listEmployes.add(employes);
                }
            }
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return listEmployes;
    }

    /**
     * Execute a statement to get an identifant based on the sql given in paramerter and a login/ a password
     * @param sql
     * @param login
     * @param pwd
     * @return 
     */
    public Login getLogin(String sql,String login, String pwd) {        
        Login id=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pwd);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()){
                    id= new Login();
                    id.setLogin(rs.getString(IDENTIFIANT_LOGIN));
                    id.setPwd(rs.getString(IDENTIFIANT_PASSWORD));
                }
            }
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * Close the connection to the database
     */
    public void closeConnection() {
     try {
         if(!this.connection.isClosed()){
            this.connection.close();   
         }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Execute a preparedStatement to delete an employee based on his id, and the sql given
     * @param sql
     * @param id
     * @return 
     */
    public boolean deleteEmployee(String sql, String id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Execute a preparedStatement to get an employee based on his id, and the sql given
     * @param sql
     * @param id
     * @return 
     */
    public Employee getEmployee(String sql, String id) {
        Employee employes=null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                employes=new Employee();
                employes.setId(rs.getInt(EMPLOYES_ID));
                employes.setNom(rs.getString(EMPLOYES_NOM));
                employes.setPrenom(rs.getString(EMPLOYES_PRENOM));
                employes.setTeldom(rs.getString(EMPLOYES_TELDOM));
                employes.setTelport(rs.getString(EMPLOYES_TELPORT));
                employes.setTelpro(rs.getString(EMPLOYES_TELPRO));
                employes.setAdresse(rs.getString(EMPLOYES_ADRESSE));
                employes.setCodepostal(rs.getString(EMPLOYES_CODEPOSTAL));
                employes.setVille(rs.getString(EMPLOYES_VILLE));
                employes.setEmail(rs.getString(EMPLOYES_EMAIL));
            }
            
            return employes;
        } catch (SQLException | NullPointerException ex ) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Execute a preparedStatement to update an employee based on his id, and the sql given
     * @param sql
     * @param employee
     * @return 
     */
    public boolean updateEmployee(String sql, Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getNom());
            preparedStatement.setString(2, employee.getPrenom());
            preparedStatement.setString(3, employee.getTeldom());
            preparedStatement.setString(4, employee.getTelport());
            preparedStatement.setString(5, employee.getTelpro());
            preparedStatement.setString(6, employee.getAdresse());
            preparedStatement.setString(7, employee.getCodepostal());
            preparedStatement.setString(8, employee.getVille());
            preparedStatement.setString(9, employee.getEmail());
            preparedStatement.setInt(10, employee.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Execute a preparedStatement to create an new employee based on his id, and the sql given
     * @param sql
     * @param employee
     * @return 
     */
    public boolean insertEmployee(String sql, Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getNom());
            preparedStatement.setString(2, employee.getPrenom());
            preparedStatement.setString(3, employee.getTeldom());
            preparedStatement.setString(4, employee.getTelport());
            preparedStatement.setString(5, employee.getTelpro());
            preparedStatement.setString(6, employee.getAdresse());
            preparedStatement.setString(7, employee.getCodepostal());
            preparedStatement.setString(8, employee.getVille());
            preparedStatement.setString(9, employee.getEmail());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

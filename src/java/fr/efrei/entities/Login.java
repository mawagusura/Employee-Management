/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.entities;

/**
 *
 * @author LUCASMasson
 */
public class Login {
    
    private String login="";
    private String pwd="";
    
    /**
     * 
     */
    public Login(){
        
    }
    
    /**
     * 
     * @return 
     */
    public String getLogin(){
        return login;
    }
    
    /**
     * 
     * @param login 
     */
    public void setLogin(String login){
        this.login=login;
    }
    
    /**
     * 
     * @return 
     */
    public String getPwd(){
        return pwd;
    }
    
    /**
     * 
     * @param password 
     */
    public void setPwd(String password){
        this.pwd=password;
    }
}

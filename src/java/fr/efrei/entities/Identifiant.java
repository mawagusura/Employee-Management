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
public class Identifiant {
    
    private String login="";
    private String pwd="";
    
    public Identifiant(){
        
    }
    
    public String getLogin(){
        return login;
    }
    
    public void setLogin(String login){
        this.login=login;
    }
    
    public String getPwd(){
        return pwd;
    }
    
    public void setPwd(String password){
        this.pwd=password;
    }
}

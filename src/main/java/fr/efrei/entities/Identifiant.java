/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author LUCASMasson
 */
@Entity
@Table(name= "IDENTIFIANT")
public class Identifiant implements Serializable {
    
    private String login;
    private String password;
    
    @Id
    @GeneratedValue
    private int id;
    
    public Identifiant(){
        
    }
    

    /**
     * 
     * @return 
     */
    public int getId(){
        return this.id;
    }
    
    /**
     * 
     * @param id 
     */
    public void setId(int id){
        this.id = id;
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
    public String getPassword(){
        return password;
    }
    
    /**
     * 
     * @param password 
     */
    public void setPassword(String password){
        this.password=password;
    }
}

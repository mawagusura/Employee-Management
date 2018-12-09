/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.DAO;

import fr.efrei.entities.Identifiant;
import javax.persistence.Query;

/**
 *
 * @author Amaury
 */
public class IdentifiantDAO extends AbstractDAO{
    
    public IdentifiantDAO(){
        setClass(Identifiant.class);
    }
    
    public Identifiant findByLogin(String login){
        Query q = em.createQuery(
                "SELECT * FROM " + myClass.getName() + " e WHERE login = :login");
        q.setParameter("login", login);
        return (Identifiant) q.getResultList();
    }
}

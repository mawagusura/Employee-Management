/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.DAO;

import fr.efrei.entities.Identifiant;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Amaury
 */
@Stateless
public class IdentifiantDAO extends AbstractDAO{
    
    public IdentifiantDAO(){
        setClass(Identifiant.class);
    }
    
    public Identifiant findByLogin(String login){
        TypedQuery<Identifiant> q = em.createQuery(
                "SELECT e FROM Identifiant e WHERE e.login = :login", this.myClass);
        q.setParameter("login", login);
        return (Identifiant) q.getSingleResult();
    }
}

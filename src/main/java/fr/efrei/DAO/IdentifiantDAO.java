/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.DAO;

import fr.efrei.Controllers.CRUDController;
import fr.efrei.entities.Identifiant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Amaury
 */
@Stateless
public class IdentifiantDAO extends AbstractDAO{
    
    private static final String IDENTIFIANT_LOGIN="login";
    private static final String IDENTIFIANT_PASSWORD="password";
    
    private static final String SQL_IDENTIFIANT="SQL_IDENTIFIANT=SELECT e FROM Identifiant e WHERE e.login = :login AND e.password = :password";
    
    /**
     * Specify the class of this DAO by Identifiant
     */
    public IdentifiantDAO(){
        setClass(Identifiant.class);
    }
    
    public Identifiant findByLogin(String login, String password){
        TypedQuery<Identifiant> q = em.createQuery(SQL_IDENTIFIANT, this.myClass);
        q.setParameter(IDENTIFIANT_LOGIN, login);
        q.setParameter(IDENTIFIANT_PASSWORD,password);
        
        Identifiant id = null;
        try{
            id = (Identifiant) q.getSingleResult();
        }
        catch (NoResultException ex){
            Logger.getLogger(CRUDController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
}

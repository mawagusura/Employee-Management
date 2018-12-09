/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.DAO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Amaury
 * @param <T>
 */
public class AbstractDAO<T> {
    
    protected Class<T> myClass;
    
    @PersistenceContext
    EntityManager em;
    
    public final void setClass (Class<T> classToSet){
        this.myClass = classToSet;
    }
    
    public List<T> findAll(){
        return em.createQuery("FROM " + myClass.getName()).getResultList();
    }
    
    public void create( T entity ){
      em.persist( entity );
   }
 
   public T update( T entity ){
      return em.merge( entity );
   }
 
   public void delete( T entity ){
      em.remove( entity );
   }
   
   public T findOne(int id){
       return em.find(myClass, id);
   }
    
}

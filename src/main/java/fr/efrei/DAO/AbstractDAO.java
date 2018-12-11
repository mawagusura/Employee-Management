/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Amaury
 * @param <T>
 */
public abstract class AbstractDAO<T> {
    
    protected static final String UNIT_NAME="MySQL-local";
    
    protected Class<T> myClass;
    
    @PersistenceContext(unitName = UNIT_NAME )
    EntityManager em;
    
    /**
     * Setup the class
     * @param classToSet 
     */
    public final void setClass (Class<T> classToSet){
        this.myClass = classToSet;
    }
    
    /**
     * Return all entities of this DAO
     * @return 
     */
    public List<T> findAll(){
        String jpql="SELECT e FROM " + myClass.getName() + " e";
        return em.createQuery(jpql).getResultList();
    }
    
    /**
     * Persist the entity given in the database
     * @param entity 
     */
    public void create( T entity ){
      em.persist( entity );
   }
 
    /**
     * Persist the modifications of an entity in the database
     * @param entity
     * @return 
     */
   public T update( T entity ){
      return em.merge( entity );
   }
 
   /**
    * Delete an entity in the database
    * @param entity 
    */
   public void delete( T entity ){
      em.remove( entity );
   }
   
   /**
    * Get an specific entity based on it id in the database
    * @param id
    * @return 
    */
   public T findOne(int id){
       return em.find(myClass, id);
   }
    
}

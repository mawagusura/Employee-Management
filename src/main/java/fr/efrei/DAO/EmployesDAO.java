/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.DAO;

import fr.efrei.entities.Employes;

/**
 *
 * @author Amaury
 */
public class EmployesDAO extends AbstractDAO{
    
    public EmployesDAO(){
        setClass(Employes.class);
    }
    
}

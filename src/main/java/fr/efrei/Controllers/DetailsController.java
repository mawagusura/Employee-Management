/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.Controllers;

import fr.efrei.DAO.EmployesDAO;
import fr.efrei.entities.Employes;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amaury
 */
public class DetailsController extends AbstractController {
        
    @EJB(name="employesDAO")
    EmployesDAO employesDAO;
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * Check if the user is logged in, else redirect to login page.
     * Redirect to the details page of the employee passed in http parameters
     * 
     * On error redirects to list page
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Properties prop = this.initProperty(request);
        
        if(request.getSession().getAttribute(prop.getProperty("ATTRIBUT_IDENTIFIANT"))==null){
            response.sendRedirect(prop.getProperty("URL_LOGIN"));
            return;
        }
        
        try{
            String temp = request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_ID"));
            int id =  Integer.parseInt( temp);
            Employes employe = (Employes) this.employesDAO.findOne(id);
            if(employe!=null){
                request.setAttribute(prop.getProperty("ATTRIBUT_EMPLOYEE"), employe);
                request.setAttribute(prop.getProperty("ATTRIBUT_NEW_EMPLOYEE"), false);
                this.getServletContext().getRequestDispatcher(prop.getProperty("PAGE_DETAILS")).forward(request, response);
                return;
            }
        }
        catch(NumberFormatException ex){
            Logger.getLogger(DetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getSession().setAttribute(prop.getProperty("ATTRIBUT_MESSAGE_ERROR"),prop.getProperty("MESSAGE_DETAILS_ERROR_SELECTION"));
        response.sendRedirect(prop.getProperty("URL_LIST"));
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * Check if the user is logged in, else redirect to login page.
     * Update employee
     * 
     * On error redirect to details page with error message
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Properties prop = this.initProperty(request);
        
        if(request.getSession().getAttribute(prop.getProperty("ATTRIBUT_IDENTIFIANT"))==null){
            response.sendRedirect(prop.getProperty("URL_LOGIN"));
            return;
        }
        Employes employee=new Employes();
        employee.setId(Integer.parseInt(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_ID"))));
        employee.setNom(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_NOM")));
        employee.setPrenom(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_PRENOM")));
        employee.setEmail(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_EMAIL")));
        employee.setTeldom(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_TELDOM")));
        employee.setTelport(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_TELPORT")));
        employee.setTelpro(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_TELPRO")));
        employee.setAdresse(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_ADRESSE")));
        employee.setVille(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_VILLE")));
        employee.setCodepostal(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_CODEPOSTAL")));
        
        try {
            this.employesDAO.update(employee);
            request.setAttribute(prop.getProperty("ATTRIBUT_MESSAGE_INFO"),prop.getProperty("MESSAGE_UPDATE_INFO"));
            request.setAttribute(prop.getProperty("ATTRIBUT_EMPLOYEE"), employee);
            this.getServletContext().getRequestDispatcher(prop.getProperty("PAGE_DETAILS")).forward(request, response);
        }
        catch (EJBException e){
            Logger.getLogger(DetailsController.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute(prop.getProperty("ATTRIBUT_MESSAGE_ERROR"),prop.getProperty("MESSAGE_UPDATE_ERROR"));
            request.setAttribute(prop.getProperty("ATTRIBUT_EMPLOYEE"), employee);
            this.getServletContext().getRequestDispatcher(prop.getProperty("PAGE_DETAILS")).forward(request, response);
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Return the details page on Get request and Update employee on post request";
    }

}

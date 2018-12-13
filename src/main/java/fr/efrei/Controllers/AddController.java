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
public class AddController extends AbstractController {

    @EJB
    EmployesDAO employesDAO;

    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     *Check if the user is logged in, else redirect to login page.
     * 
     * Display details page with blank fields and "Add" button
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        if(request.getSession().getAttribute(prop.getProperty("ATTRIBUT_IDENTIFIANT"))==null){
            response.sendRedirect(prop.getProperty("URL_LOGIN"));
            return;
        }
        request.setAttribute(prop.getProperty("ATTRIBUT_NEW_EMPLOYEE"), true);
        this.getServletContext().getRequestDispatcher(prop.getProperty("PAGE_DETAILS")).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * Check if the user is logged in, else redirect to login page.
     * 
     * Create the user with the form informations
     * On error display error message
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        if(request.getSession().getAttribute(prop.getProperty("ATTRIBUT_IDENTIFIANT"))==null){
            response.sendRedirect(prop.getProperty("URL_LOGIN"));
            return;
        }
        Employes employee=new Employes();
        employee.setNom(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_NOM")));
        employee.setPrenom(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_PRENOM")));
        employee.setEmail(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_EMAIL")));
        employee.setTeldom(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_TELDOM")));
        employee.setTelport(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_TELPORT")));
        employee.setTelpro(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_TELPRO")));
        employee.setAdresse(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_ADRESSE")));
        employee.setVille(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_VILLE")));
        employee.setCodepostal(request.getParameter(prop.getProperty("PARAMETER_EMPLOYES_CODEPOSTAL")));

        try{
            this.employesDAO.create(employee);
            request.getSession().setAttribute(prop.getProperty("ATTRIBUT_MESSAGE_INFO"),prop.getProperty("MESSAGE_ADD_INFO"));
            response.sendRedirect(prop.getProperty("URL_LIST"));
        }
        catch (EJBException ex){
            Logger.getLogger(AddController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute(prop.getProperty("ATTRIBUT_MESSAGE_ERROR"), prop.getProperty("MESSAGE_ADD_ERROR"));
            this.getServletContext().getRequestDispatcher(prop.getProperty("PAGE_NEW_EMPLOYE")).forward(request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Display new employee page on get request and create a new employee on post request.";
    }

}

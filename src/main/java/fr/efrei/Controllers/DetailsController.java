/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.Controllers;

import fr.efrei.DAO.EmployesDAO;
import fr.efrei.entities.Employes;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amaury
 */
public class DetailsController extends HttpServlet {
    
    private static final String PARAMETER_EMPLOYES_NOM="employes-nom";
    private static final String PARAMETER_EMPLOYES_PRENOM="employes-prenom";
    private static final String PARAMETER_EMPLOYES_EMAIL="employes-email";
    private static final String PARAMETER_EMPLOYES_TELDOM="employes-teldom";
    private static final String PARAMETER_EMPLOYES_TELPORT="employes-telport";
    private static final String PARAMETER_EMPLOYES_TELPRO="employes-telpro";
    private static final String PARAMETER_EMPLOYES_ADRESSE="employes-adresse";
    private static final String PARAMETER_EMPLOYES_VILLE="employes-ville";
    private static final String PARAMETER_EMPLOYES_CODEPOSTAL="employes-codepostal";
    private static final String PARAMETER_EMPLOYES_ID="employes-id";
    private static final String PAGE_DETAILS="/WEB-INF/details.jsp";
    private static final String ATTRIBUT_EMPLOYEE="employee";
    private static final String URL_LIST="list";
    private static final String ATTRIBUT_MESSAGE_ERROR = "message_error";
    private static final String ATTRIBUT_MESSAGE_INFO = "message_info";
    private static final String MESSAGE_UPDATE_INFO="Succes de la mise à jour";
    private static final String MESSAGE_UPDATE_ERROR="Erreur dans la mise à jour";  
    
    private static final String URL_LOGIN="login";
    private static final String ATTRIBUT_IDENTIFIANT = "identifiant";
    
    @EJB
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
        
        if(request.getSession().getAttribute(ATTRIBUT_IDENTIFIANT)==null){
            response.sendRedirect(URL_LOGIN);
            return;
        }
        
        try{
            String temp = request.getParameter(PARAMETER_EMPLOYES_ID);
            int id =  Integer.parseInt( temp);
            Employes employe = (Employes) this.employesDAO.findOne(id);
            if(employe!=null){
                request.setAttribute(ATTRIBUT_EMPLOYEE, employe);
                request.setAttribute("new_employee", false);
                this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
                return;
            }
        }
        catch(NumberFormatException ex){
            Logger.getLogger(DetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.getSession().setAttribute(ATTRIBUT_MESSAGE_INFO,MESSAGE_UPDATE_ERROR);
        response.sendRedirect(URL_LIST);
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
        
        if(request.getSession().getAttribute(ATTRIBUT_IDENTIFIANT)==null){
            response.sendRedirect(URL_LOGIN);
            return;
        }
        Employes employee=new Employes();
        employee.setId(Integer.parseInt(request.getParameter(PARAMETER_EMPLOYES_ID)));
        employee.setNom(request.getParameter(PARAMETER_EMPLOYES_NOM));
        employee.setPrenom(request.getParameter(PARAMETER_EMPLOYES_PRENOM));
        employee.setEmail(request.getParameter(PARAMETER_EMPLOYES_EMAIL));
        employee.setTeldom(request.getParameter(PARAMETER_EMPLOYES_TELDOM));
        employee.setTelport(request.getParameter(PARAMETER_EMPLOYES_TELPORT));
        employee.setTelpro(request.getParameter(PARAMETER_EMPLOYES_TELPRO));
        employee.setAdresse(request.getParameter(PARAMETER_EMPLOYES_ADRESSE));
        employee.setVille(request.getParameter(PARAMETER_EMPLOYES_VILLE));
        employee.setCodepostal(request.getParameter(PARAMETER_EMPLOYES_CODEPOSTAL));
        
        try {
            this.employesDAO.update(employee);
            request.setAttribute(ATTRIBUT_MESSAGE_INFO,MESSAGE_UPDATE_INFO);
            request.setAttribute(ATTRIBUT_EMPLOYEE, employee);
            this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
        }
        catch (EJBException e){
            Logger.getLogger(DetailsController.class.getName()).log(Level.SEVERE, null, e);
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,MESSAGE_UPDATE_ERROR);
            request.setAttribute(ATTRIBUT_EMPLOYEE, employee);
            this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
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

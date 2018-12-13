/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.Controllers;

import fr.efrei.DAO.EmployesDAO;
import fr.efrei.entities.Employes;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AddController extends HttpServlet {

    @EJB
    EmployesDAO employesDAO;

    private static final String PARAMETER_EMPLOYES_NOM="employes-nom";
    private static final String PARAMETER_EMPLOYES_PRENOM="employes-prenom";
    private static final String PARAMETER_EMPLOYES_EMAIL="employes-email";
    private static final String PARAMETER_EMPLOYES_TELDOM="employes-teldom";
    private static final String PARAMETER_EMPLOYES_TELPORT="employes-telport";
    private static final String PARAMETER_EMPLOYES_TELPRO="employes-telpro";
    private static final String PARAMETER_EMPLOYES_ADRESSE="employes-adresse";
    private static final String PARAMETER_EMPLOYES_VILLE="employes-ville";
    private static final String PARAMETER_EMPLOYES_CODEPOSTAL="employes-codepostal";
        
    private static final String ATTRIBUT_MESSAGE_ERROR = "message_error";
    private static final String ATTRIBUT_MESSAGE_INFO = "message_info";
    private static final String URL_LIST="list";
    private static final String MESSAGE_ADD_INFO="Succès de l'ajout";
    private static final String MESSAGE_ADD_ERROR="Echec de l'ajout";

    private static final String PAGE_NEW_EMPLOYE="/WEB-INF/details.jsp";  
    
    private static final String URL_LOGIN="login";

    private static final String ATTRIBUT_IDENTIFIANT = "identifiant";

    /**
     * Handles the HTTP <code>GET</code> method.
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
        
        request.setAttribute("new_employee", true);
        this.getServletContext().getRequestDispatcher(PAGE_NEW_EMPLOYE).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
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
        employee.setNom(request.getParameter(PARAMETER_EMPLOYES_NOM));
        employee.setPrenom(request.getParameter(PARAMETER_EMPLOYES_PRENOM));
        employee.setEmail(request.getParameter(PARAMETER_EMPLOYES_EMAIL));
        employee.setTeldom(request.getParameter(PARAMETER_EMPLOYES_TELDOM));
        employee.setTelport(request.getParameter(PARAMETER_EMPLOYES_TELPORT));
        employee.setTelpro(request.getParameter(PARAMETER_EMPLOYES_TELPRO));
        employee.setAdresse(request.getParameter(PARAMETER_EMPLOYES_ADRESSE));
        employee.setVille(request.getParameter(PARAMETER_EMPLOYES_VILLE));
        employee.setCodepostal(request.getParameter(PARAMETER_EMPLOYES_CODEPOSTAL));

        try{
            this.employesDAO.create(employee);
            request.getSession().setAttribute(ATTRIBUT_MESSAGE_INFO,MESSAGE_ADD_INFO);
            response.sendRedirect(URL_LIST);
        }
        catch (EJBException e){
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR, MESSAGE_ADD_ERROR);
            this.getServletContext().getRequestDispatcher(PAGE_NEW_EMPLOYE).forward(request, response);
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

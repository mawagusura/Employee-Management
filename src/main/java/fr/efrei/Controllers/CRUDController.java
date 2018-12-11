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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Amaury
 */
public class CRUDController extends HttpServlet {
    
    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_DETAILS = "details";
    private static final String PARAMETER_DELETE = "delete";
    private static final String PARAMETER_UPDATE = "update";
    private static final String PARAMETER_EMPLOYES_ID="employes-id";
    private static final String PARAMETER_EMPLOYES_NOM="employes-nom";
    private static final String PARAMETER_EMPLOYES_PRENOM="employes-prenom";
    private static final String PARAMETER_EMPLOYES_EMAIL="employes-email";
    private static final String PARAMETER_EMPLOYES_TELDOM="employes-teldom";
    private static final String PARAMETER_EMPLOYES_TELPORT="employes-telport";
    private static final String PARAMETER_EMPLOYES_TELPRO="employes-telpro";
    private static final String PARAMETER_EMPLOYES_ADRESSE="employes-adresse";
    private static final String PARAMETER_EMPLOYES_VILLE="employes-ville";
    private static final String PARAMETER_EMPLOYES_CODEPOSTAL="employes-codepostal";

    private static final String PAGE_DETAILS="/WEB-INF/details.jsp";
    private static final String PAGE_NEW_EMPLOYE="/WEB-INF/new-employee.jsp";
    
    private static final String ATTRIBUT_EMPLOYEE="employee";

    private static final String URL_LIST="list";
    
    private static final String MESSAGE_DELETE_INFO="La suppression a réussi";
    private static final String MESSAGE_DELETE_ERROR="Veuillez selectionner l'employé à supprimer !";
    private static final String MESSAGE_UPDATE_INFO="Succes de la mise à jour";
    private static final String MESSAGE_UPDATE_ERROR="Erreur dans la mise à jour";
    private static final String MESSAGE_ADD_INFO="Succès de l'ajout";
    
    private static final String ATTRIBUT_MESSAGE_ERROR = "message_error";
    private static final String ATTRIBUT_MESSAGE_INFO = "message_info";

    
    
    @EJB
    EmployesDAO employesDAO;
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * Redirect to the details page or creation employee page 
     * base on the parameter action
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action=request.getParameter(PARAMETER_ACTION);
        if(action!=null){
            switch(action){
                case PARAMETER_DETAILS:
                    try{
                        int id =  Integer.parseInt( request.getParameter(PARAMETER_EMPLOYES_ID));
                        Employes employe = (Employes) this.employesDAO.findOne(id);
                        if(employe!=null){
                            request.setAttribute(ATTRIBUT_EMPLOYEE, employe);
                            this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
                        }
                    }
                    catch(NumberFormatException ex){
                        Logger.getLogger(CRUDController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    response.sendRedirect(URL_LIST);
                    break;
                default:
                    this.getServletContext().getRequestDispatcher(PAGE_NEW_EMPLOYE).forward(request, response);
                    break;
            }
        }
        else{
            this.getServletContext().getRequestDispatcher(PAGE_NEW_EMPLOYE).forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * Based on the parameter action
     * 
     * Create or update or delete employee
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action=request.getParameter(PARAMETER_ACTION);
        if(action!=null){
            switch(action){
                case PARAMETER_DELETE:
                    this.doDelete(request, response);
                    break;
                case PARAMETER_UPDATE:
                    this.doPut(request, response);
                    break;
                default:
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

                    request.setAttribute(ATTRIBUT_MESSAGE_INFO,MESSAGE_ADD_INFO);
                    this.employesDAO.create(employee);
                    response.sendRedirect(URL_LIST);
            }
        }
    }
    
    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * Delete an employee based on his id given in the request parameter 
     * And send a message for the cases: success, error and not selected
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id=request.getParameter(PARAMETER_EMPLOYES_ID);
        if(id.equals("")){
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,MESSAGE_DELETE_ERROR);
        }
        else{
            try{
                Employes e = (Employes) this.employesDAO.findOne(Integer.parseInt(id));
                if(e!=null){
                    this.employesDAO.delete(e);
                    request.setAttribute(ATTRIBUT_MESSAGE_INFO,MESSAGE_DELETE_INFO);
                }
            }
            catch(NumberFormatException ex){
                Logger.getLogger(CRUDController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        response.sendRedirect(URL_LIST);
    }
    
    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * Update an employee based on his id and the informations given in the request parameter 
     * And send a message for the case success, error
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        if(this.employesDAO.update(employee)!=null){
            request.setAttribute(ATTRIBUT_MESSAGE_INFO,MESSAGE_UPDATE_INFO);
            this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
        }
        else{
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,MESSAGE_UPDATE_ERROR);
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
        return "Short description";
    }

}

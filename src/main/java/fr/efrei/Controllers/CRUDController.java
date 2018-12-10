/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.Controllers;

import fr.efrei.DAO.EmployesDAO;
import fr.efrei.entities.Employes;
import java.io.IOException;
import java.util.HashSet;
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

    @EJB
    EmployesDAO employesDAO;
    
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
        
        
        String action=request.getParameter("action");
        if(action!=null){
            action=(String)request.getParameter("action");
            
            if(action.equals("details")){        
                try{
                    int id =  Integer.parseInt( request.getParameter("employes-id"));
                    Employes employe = (Employes) this.employesDAO.findOne(id);
                    if(employe!=null){
                        request.setAttribute("employe", employe);
                        this.getServletContext().getRequestDispatcher("/WEB-INF/details.jsp").forward(request, response);
                    }
                }
                catch(NumberFormatException e){
                    e.printStackTrace();
                }
                response.sendRedirect("http://localhost:8080/employee-management/list");
            }
            else{
                this.getServletContext().getRequestDispatcher("/WEB-INF/new-employee.jsp").forward(request, response);
            }
        }
        response.sendRedirect("http://localhost:8080/employee-management/list");
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
        
        Employes newE = new Employes();
        
        newE.setNom(request.getParameter("employes-nom"));
        newE.setPrenom(request.getParameter("employes-prenom"));
        newE.setAdresse(request.getParameter("employes-adresse"));
        newE.setCodepostal(request.getParameter("employes-codepostal"));
        newE.setEmail(request.getParameter("employes-email"));
        newE.setTeldom(request.getParameter("employes-teldom"));
        newE.setTelport(request.getParameter("employes-telperso"));
        newE.setTelpro(request.getParameter("employes-telport"));
        newE.setVille(request.getParameter("employes-ville"));
        
        this.employesDAO.create(newE);
        response.sendRedirect("http://localhost:8080/employee-management/list");
    }
    
    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        try{
            int id =  Integer.parseInt( request.getParameter("employes-id"));
            Employes e = (Employes) this.employesDAO.findOne(id);
            if(e!=null){
                this.employesDAO.delete(e);
                
            }
        }
        catch(NumberFormatException e){
            e.printStackTrace();
        }
        response.sendRedirect("http://localhost:8080/employee-management/list");

    }
    
    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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

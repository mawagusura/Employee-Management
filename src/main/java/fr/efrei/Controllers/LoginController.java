/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.Controllers;

import fr.efrei.DAO.IdentifiantDAO;
import fr.efrei.entities.Identifiant;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LUCASMasson
 */
public class LoginController extends HttpServlet {

    @EJB
    IdentifiantDAO identifiantDAO;
    
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
        
        HttpSession session=request.getSession(true);

        if(session.getAttribute("identifiant")!=null){
            response.sendRedirect("http://localhost:8080/employee-management/list");
        }
        else this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
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
        HttpSession session=request.getSession(true);
        
        String login=request.getParameter("chLogin");
        String password=request.getParameter("chPassword");

        Identifiant id = this.identifiantDAO.findByLogin(login);

        if(id!=null && password.equals(id.getPwd())){
            session.setAttribute("identifiant", id);
            response.sendRedirect("http://localhost:8080/employee-management/list");
        }
        else{
            session.setAttribute("message_erreur", "Identifiant ou mot de passe incorrect");
            doGet(request, response);
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }
}


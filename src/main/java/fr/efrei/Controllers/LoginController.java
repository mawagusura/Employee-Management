/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.Controllers;

import fr.efrei.DAO.IdentifiantDAO;
import fr.efrei.entities.Identifiant;
import java.io.IOException;
import java.util.Properties;
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
public class LoginController extends AbstractController {
    
    @EJB(name="identifiantDAO")
    IdentifiantDAO identifiantDAO;
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * Redirect to the list page if he is connected
     * Redirect to the login page if he isn't
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
        
        HttpSession session=request.getSession(true);

        if(session.getAttribute(prop.getProperty("ATTRIBUT_IDENTIFIANT"))!=null){
            response.sendRedirect(prop.getProperty("URL_LIST"));
        }
        else {
            this.getServletContext().getRequestDispatcher(prop.getProperty("PAGE_LOGIN")).forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * Login to the application based on two request parameters to get login and password
     * Forward to the login page when in on them is empty or parameters are invalid
     * 
     * Forward to the list of employees and save the identifiant in session scope
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
        
        Properties prop = this.initProperty(request);
        
        String login=request.getParameter(prop.getProperty("PARAMETER_CH_LOGIN"));
        String password=request.getParameter(prop.getProperty("PARAMETER_CH_PASSWORD"));
        if(login.equals("")||password.equals("")){
            request.setAttribute(prop.getProperty("ATTRIBUT_MESSAGE_ERROR"),prop.getProperty("MESSAGE_LOGIN_ERROR_EMPTY"));
            this.getServletContext().getRequestDispatcher(prop.getProperty("PAGE_LOGIN")).forward(request, response);
        }
        else{
            Identifiant id = (Identifiant) this.identifiantDAO.findByLogin(login,password);

            if(id!=null){
                session.setAttribute(prop.getProperty("IDENTIFIANT"), id);
                response.sendRedirect(prop.getProperty("URL_LIST"));
            }
            else{
                request.setAttribute(prop.getProperty("ATTRIBUT_MESSAGE_ERROR"), prop.getProperty("MESSAGE_LOGIN_ERROR"));
                doGet(request, response);
            }   
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login Servlet : return the login page on get and log in the user on post (and set him on session attribute).";
    }
}


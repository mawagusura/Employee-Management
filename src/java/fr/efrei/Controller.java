/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LUCASMasson
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        switch(request.getParameter("action")){
            case "login":
                this.login(request,response);
                break;
            case "delete":
                this.delete(request,response);
                break;
            case "add":
                this.add(request,response);
                break;
            case "details":
                this.details(request,response);
                break;
            default:
                this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
                   
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(true);
        
        String login=request.getParameter("chLogin");
        String password=request.getParameter("chPassword");
        Properties prop= new Properties();
        InputStream input= request.getServletContext().getResourceAsStream("/WEB-INF/db.properties");
        prop.load(input);
            
        DataAccess dataAccess=new DataAccess(prop.getProperty("dbUrl"),prop.getProperty("dbUser"),prop.getProperty("dbPassword"));
        
        Identifiant id = dataAccess.getIdentifiant(prop.getProperty("SQL_LOGIN_PREPARED"),login,password);
            
        if(id!=null){
            session.setAttribute("identifiant", id);
            session.setAttribute("employes", dataAccess.getEmployes(prop.getProperty("SQL_ALL_EMPLOYEES")));
            this.getServletContext().getRequestDispatcher("/WEB-INF/bienvenue.jsp").forward(request, response);
        }
        else{
            session.setAttribute("message_erreur", "Identifiant ou mot de passe incorrect");
            this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        }
        dataAccess.closeConnection();
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void add(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void details(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


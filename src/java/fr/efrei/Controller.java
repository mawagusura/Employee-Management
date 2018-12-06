/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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

        String action="";
        if(request.getParameter("action")!=null){
            action=(String)request.getParameter("action");
        }
        
        switch(action){
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
            case "update":
                this.update(request,response);
                break;
            default:
                this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
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

    private Properties initProperty(HttpServletRequest request) throws IOException{
        Properties prop= new Properties();
        InputStream input= request.getServletContext().getResourceAsStream("/WEB-INF/db.properties");
        prop.load(input);
        return prop;
    }
    
    
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(true);
        
        String login=request.getParameter("chLogin");
        String password=request.getParameter("chPassword");
        Properties prop= this.initProperty(request);
            
        DataAccess dataAccess=new DataAccess(prop.getProperty("dbUrl"),prop.getProperty("dbUser"),prop.getProperty("dbPassword"));
        
        Identifiant id = dataAccess.getIdentifiant(prop.getProperty("SQL_LOGIN_PREPARED"),login,password);
            
        if(id!=null){
            List<Employes> listEmployes=dataAccess.getEmployes(prop.getProperty("SQL_ALL_EMPLOYEES"));
            if(listEmployes != null){
                session.setAttribute("identifiant", id);
                session.setAttribute("message_erreur","");
                session.setAttribute("message_info","");
                session.setAttribute("employes", listEmployes);
                this.getServletContext().getRequestDispatcher("/WEB-INF/employees-list.jsp").forward(request, response);
            }
            else{
                session.setAttribute("identifiant", id);
                session.setAttribute("message_erreur","Erreur de la connexion à la base de donnée");
                session.setAttribute("message_info","");
                this.getServletContext().getRequestDispatcher("/WEB-INF/employees-list.jsp").forward(request, response);
            }
        }
        else{
            session.setAttribute("message_info","");
            session.setAttribute("message_erreur", "Identifiant ou mot de passe incorrect");
            this.getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        dataAccess.closeConnection();
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Properties prop= this.initProperty(request);
        HttpSession session=request.getSession(true);
        
        DataAccess dataAccess=new DataAccess(prop.getProperty("dbUrl"),prop.getProperty("dbUser"),prop.getProperty("dbPassword"));
        if(dataAccess.delete(prop.getProperty("SQL_DELETE_EMPLOYEES"),request.getParameter("employes-id"))){
            session.setAttribute("message_erreur","");
            session.setAttribute("message_info","La suppression à réussi");
        }
        else{
            session.setAttribute("message_erreur","La supression à échoué");
            session.setAttribute("message_info","");
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/employees-list.jsp").forward(request, response);
        dataAccess.closeConnection();
        
    }
    
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        HttpSession session=request.getSession(true);
        
        DataAccess dataAccess=new DataAccess(prop.getProperty("dbUrl"),prop.getProperty("dbUser"),prop.getProperty("dbPassword"));
        
        if(dataAccess.delete(prop.getProperty("SQL_DELETE_EMPLOYEES"),request.getParameter("employes-id"))){
            session.setAttribute("message_erreur","");
            session.setAttribute("message_info","Succès de l'ajout");
        }
        else{
            session.setAttribute("message_erreur","Echec de l'ajout");
            session.setAttribute("message_info","");
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/add-employes.jsp").forward(request, response);
        dataAccess.closeConnection();
    }

    private void details(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        HttpSession session=request.getSession(true);
        
        DataAccess dataAccess=new DataAccess(prop.getProperty("dbUrl"),prop.getProperty("dbUser"),prop.getProperty("dbPassword"));
        Employes employes =dataAccess.getEmployes(prop.getProperty("SQL_DETAILS_EMPLOYES"),request.getParameter("employes-id"));
        if(employes !=null){
            session.setAttribute("message_erreur","");
            session.setAttribute("message_info","");
            session.setAttribute("employe",employes);
        this.getServletContext().getRequestDispatcher("/WEB-INF/details.jsp").forward(request, response);
        }
        else{
            session.setAttribute("message_erreur","Erreur de la connexion à la base de donnée");
            session.setAttribute("message_info","");
        this.getServletContext().getRequestDispatcher("/WEB-INF/employees-list.jsp").forward(request, response);
        }
        dataAccess.closeConnection();
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        HttpSession session=request.getSession(true);
        
        DataAccess dataAccess=new DataAccess(prop.getProperty("dbUrl"),prop.getProperty("dbUser"),prop.getProperty("dbPassword"));
        Employes employes=new Employes();
        employes.setId(Integer.parseInt(request.getParameter("employes-id")));
        employes.setNom(request.getParameter("employes-nom"));
        employes.setPrenom(request.getParameter("employes-prenom"));
        employes.setEmail(request.getParameter("employes-email"));
        employes.setTeldom(request.getParameter("employes-teldom"));
        employes.setTelport(request.getParameter("employes-telport"));
        employes.setTelpro(request.getParameter("employes-telperso"));
        employes.setAdresse(request.getParameter("employes-adresse"));
        employes.setVille(request.getParameter("employes-ville"));
        employes.setCodepostal(request.getParameter("employes-codepostal"));
        
        if(dataAccess.updateEmployes(prop.getProperty("SQL_UPDATE_EMPLOYES"),employes)){
            session.setAttribute("message_erreur","Succes de la mise à jour");
            session.setAttribute("message_info","");
        this.getServletContext().getRequestDispatcher("/WEB-INF/details.jsp").forward(request, response);
        }
        else{
            session.setAttribute("message_erreur","Erreur dans la mise à jour");
            session.setAttribute("message_info","");
        this.getServletContext().getRequestDispatcher("/WEB-INF/details.jsp").forward(request, response);
        }
        dataAccess.closeConnection();
    }
}


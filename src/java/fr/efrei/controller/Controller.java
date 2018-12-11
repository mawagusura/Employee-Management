/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.controller;

import fr.efrei.entities.Employes;
import fr.efrei.entities.Identifiant;
import fr.efrei.model.DataAccess;
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

    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_LOGIN = "login";    
    private static final String PARAMETER_DELETE = "delete";
    private static final String PARAMETER_ADD = "add";
    private static final String PARAMETER_DETAILS = "details";
    private static final String PARAMETER_UPDATE = "update";
    private static final String PARAMETER_LOGOUT = "logout";
    private static final String PARAMETER_CH_LOGIN="chLogin";
    private static final String PARAMETER_CH_PASSWORD="chPassword";
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
    
    
    private static final String ATTRIBUT_IDENTIFIANT = "identifiant";
    private static final String ATTRIBUT_MESSAGE_ERROR = "message_error";
    private static final String ATTRIBUT_MESSAGE_INFO = "message_info";
    private static final String ATTRIBUT_EMPLOYEES="employees";
    private static final String ATTRIBUT_EMPLOYEE="employee";
    
    private static final String PAGE_LOGIN="/WEB-INF/login.jsp";
    private static final String PAGE_DETAILS="/WEB-INF/details.jsp";
    private static final String PAGE_EMPLOYEES_LIST="/WEB-INF/employees-list.jsp";
    private static final String PAGE_NEW_EMPLOYE="/WEB-INF/new-employee.jsp";
    private static final String PAGE_PROPERTIES="/WEB-INF/db.properties";
    private static final String PAGE_LOGOUT="/WEB-INF/logout.jsp";
    
    private static final String PROPERTIES_SQL_UPDATE_EMPLOYEES="SQL_UPDATE_EMPLOYES";
    private static final String PROPERTIES_SQL_DETAILS_EMPLOYEES="SQL_DETAILS_EMPLOYES";        
    private static final String PROPERTIES_SQL_ALL_EMPLOYEES="SQL_ALL_EMPLOYEES";
    private static final String PROPERTIES_SQL_DELETE_EMPLOYEES="SQL_DELETE_EMPLOYEES";
    private static final String PROPERTIES_SQL_INSERT_EMPLOYEES="SQL_INSERT_EMPLOYEES";
    private static final String PROPERTIES_SQL_LOGIN="SQL_LOGIN_PREPARED";
    private static final String PROPERTIES_DB_URL="dbUrl";
    private static final String PROPERTIES_DB_USER="dbUser";
    private static final String PROPERTIES_DB_PASSWORD="dbPassword";
    
    
    private static final String MESSAGE_UPDATE_INFO="Succes de la mise à jour";
    private static final String MESSAGE_UPDATE_ERROR="Erreur dans la mise à jour";
    private static final String MESSAGE_DETAILS_ERROR="Erreur de la connexion à la base de donnée";
    private static final String MESSAGE_DETAILS_ERROR_SELECTION="Veuillez selectionner l'employé à  inspecter !";
    private static final String MESSAGE_ADD_INFO="Succès de l'ajout";
    private static final String MESSAGE_ADD_ERROR="Echec de l'ajout";
    private static final String MESSAGE_LOGIN_ERROR="Echec de la connexion! Vérifiez votre login et/ou mot de passe et essayez à nouveau";
    private static final String MESSAGE_LOGIN_ERROR_EMPTY="Vous devez renseigner les deux champs";
    private static final String MESSAGE_DELETE_INFO="La suppression a réussi";
    private static final String MESSAGE_DELETE_ERROR="Veuillez selectionner l'employé à supprimer !";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * Dispatch the process between methods in this class based on a request parameter
     * In case, there is no parameter and no identifiant redirect to the login page
     * In case, there is a identenfiant and no parameter redirect to the list employees
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String action="";
        if(request.getParameter(PARAMETER_ACTION)!=null){
            action=(String)request.getParameter(PARAMETER_ACTION);
        }
        
        if(session.getAttribute(ATTRIBUT_IDENTIFIANT)==null && !action.equals(PARAMETER_LOGIN)){
            this.getServletContext().getRequestDispatcher(PAGE_LOGIN).forward(request, response);
        }
        else{

            switch(action){
                case PARAMETER_LOGIN:
                    this.login(request,response);
                    break;
                case PARAMETER_DELETE:
                    this.delete(request,response);
                    break;
                case PARAMETER_ADD:
                    this.add(request,response);
                    break;
                case PARAMETER_DETAILS:
                    this.details(request,response);
                    break;
                case PARAMETER_UPDATE:
                    this.update(request,response);
                    break;
                case PARAMETER_LOGOUT:
                    this.logout(request,response);
                    break;
                default:
                    this.home(request, response);
            }
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

    
    /**
     * Create and load properties based on a servletContext in the request
     * @param request
     * @return
     * @throws IOException 
     */
    private Properties initProperty(HttpServletRequest request) throws IOException{
        Properties prop= new Properties();
        InputStream input= request.getServletContext().getResourceAsStream(PAGE_PROPERTIES);
        prop.load(input);
        return prop;
    }
    
    /**
     * Create a dataAccess based on properties
     * @param prop
     * @return 
     */
    private DataAccess getDataAccess(Properties prop){
        return new DataAccess(prop.getProperty(PROPERTIES_DB_URL),prop.getProperty(PROPERTIES_DB_USER),prop.getProperty(PROPERTIES_DB_PASSWORD));
    }
    
    /**
     * Remove the session attribut identifiant and forward to the page logout
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session=request.getSession(true);
        session.removeAttribute(ATTRIBUT_IDENTIFIANT);
        this.getServletContext().getRequestDispatcher(PAGE_LOGOUT).forward(request, response);
    }
    
    /**
     * Forward to the list of employees
     * Used when you try to login where you are all ready login
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Properties prop= this.initProperty(request);    
        DataAccess dataAccess=getDataAccess(prop);
        request.setAttribute(ATTRIBUT_EMPLOYEES, dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_ALL_EMPLOYEES)));
        this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
        
        dataAccess.closeConnection();
    }
    
    /**
     * Login to the application based on two request parameters to get login and password
     * Forward to the login page when in on them is empty or parameters are invalid
     * 
     * Forward to the list of employees and save the identifiant in session scope
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(true);
        
        String login=request.getParameter(PARAMETER_CH_LOGIN);
        String password=request.getParameter(PARAMETER_CH_PASSWORD);
        if(login.equals("")||password.equals("")){
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,MESSAGE_LOGIN_ERROR_EMPTY);
            this.getServletContext().getRequestDispatcher(PAGE_LOGIN).forward(request, response);
        }
        else{
            Properties prop= this.initProperty(request);

            DataAccess dataAccess=getDataAccess(prop);
            Identifiant id = dataAccess.getIdentifiant(prop.getProperty(PROPERTIES_SQL_LOGIN),login,password);

            if(id!=null){
                session.setAttribute(ATTRIBUT_IDENTIFIANT, id);
                request.setAttribute(ATTRIBUT_EMPLOYEES, dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_ALL_EMPLOYEES)));
                this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
            }
            else{
                request.setAttribute(ATTRIBUT_MESSAGE_ERROR,MESSAGE_LOGIN_ERROR);
                this.getServletContext().getRequestDispatcher(PAGE_LOGIN).forward(request, response);
            }
            dataAccess.closeConnection();
        }
    }

    /**
     * Delete an employee based on his id given in the request parameter 
     * And send a message for the cases: success, error and not selected
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Properties prop= this.initProperty(request);
        DataAccess dataAccess=getDataAccess(prop);
        
        String id=request.getParameter(PARAMETER_EMPLOYES_ID);
        if(id!=null){
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,MESSAGE_DELETE_ERROR);
        }
        else{

            if(dataAccess.deleteEmployes(prop.getProperty(PROPERTIES_SQL_DELETE_EMPLOYEES),id)){
                request.setAttribute(ATTRIBUT_MESSAGE_INFO,MESSAGE_DELETE_INFO);
            }
        }
        request.setAttribute(ATTRIBUT_EMPLOYEES, dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_ALL_EMPLOYEES)));
        this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
        
        dataAccess.closeConnection();
    }

    /**
     * Add an employee based on the informations given in the request parameter 
     * And send a message for the case success, error 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        DataAccess dataAccess=getDataAccess(prop);
        
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
        dataAccess.insertEmployes(prop.getProperty(PROPERTIES_SQL_INSERT_EMPLOYEES),employee);
        
        
        
        if(dataAccess.insertEmployes(prop.getProperty(PROPERTIES_SQL_INSERT_EMPLOYEES),employee)){
            request.setAttribute(ATTRIBUT_MESSAGE_INFO,MESSAGE_ADD_INFO);
            this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
        }
        else{
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,MESSAGE_ADD_ERROR);
            this.getServletContext().getRequestDispatcher(PAGE_NEW_EMPLOYE).forward(request, response);
        }
        dataAccess.closeConnection();
    }

    /**
     * Show details about an employee based on his id given in the request parameter 
     * And send a message for the cases: error and not selected
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void details(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        DataAccess dataAccess=getDataAccess(prop);
        
        String id=request.getParameter(PARAMETER_EMPLOYES_ID);
        if(id==null){
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,MESSAGE_DETAILS_ERROR_SELECTION);
            request.setAttribute(ATTRIBUT_EMPLOYEES, dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_ALL_EMPLOYEES)));
            this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
        }
        else{
            Employes employee = dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_DETAILS_EMPLOYEES),id);
            if(employee !=null){
                request.setAttribute(ATTRIBUT_EMPLOYEE,employee);
                this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
            }
            else{
                request.setAttribute(ATTRIBUT_MESSAGE_ERROR,MESSAGE_DETAILS_ERROR);
                request.setAttribute(ATTRIBUT_EMPLOYEES, dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_ALL_EMPLOYEES)));
                this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
            }
        }        
        dataAccess.closeConnection();
    }
    
    /**
     * Update an employee based on his id and the informations given in the request parameter 
     * And send a message for the case success, error
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        DataAccess dataAccess=getDataAccess(prop);
        
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
        
        if(dataAccess.updateEmployes(prop.getProperty(PROPERTIES_SQL_UPDATE_EMPLOYEES),employee)){
            request.setAttribute(ATTRIBUT_MESSAGE_INFO,MESSAGE_UPDATE_INFO);
            this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
        }
        else{
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,MESSAGE_UPDATE_ERROR);
            this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
        }
        dataAccess.closeConnection();
    }
}


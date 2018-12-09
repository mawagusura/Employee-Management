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

    private static final String PARAMETER_ACTION = "action";
    private static final String PARAMETER_LOGIN = "login";    
    private static final String PARAMETER_DELETE = "delete";
    private static final String PARAMETER_ADD = "add";
    private static final String PARAMETER_DETAILS = "details";
    private static final String PARAMETER_UPDATE = "update";
    private static final String PARAMETER_LOGOUT = "logout";
    private static final String PARAMETER_CH_LOGIN="chLogin";
    private static final String PARAMETER_CH_PASSWORD="chPassword";
    
    
    
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
    
    private static final String PROPERTIES_SQL_UPDATE_EMPLOYEES="SQL_UPDATE_EMPLOYES";
    private static final String PROPERTIES_SQL_DETAILS_EMPLOYEES="SQL_DETAILS_EMPLOYES";        
    private static final String PROPERTIES_SQL_ALL_EMPLOYEES="SQL_ALL_EMPLOYEES";
    private static final String PROPERTIES_SQL_DELETE_EMPLOYEES="SQL_DELETE_EMPLOYEES";
    private static final String PROPERTIES_SQL_LOGIN="SQL_LOGIN_PREPARED";
    private static final String PROPERTIES_DB_URL="dbUrl";
    private static final String PROPERTIES_DB_USER="dbUser";
    private static final String PROPERTIES_DB_PASSWORD="dbPassword";
    
    
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

    private Properties initProperty(HttpServletRequest request) throws IOException{
        Properties prop= new Properties();
        InputStream input= request.getServletContext().getResourceAsStream(PAGE_PROPERTIES);
        prop.load(input);
        return prop;
    }
    private DataAccess getDataAccess(Properties prop){
        return new DataAccess(prop.getProperty(PROPERTIES_DB_URL),prop.getProperty(PROPERTIES_DB_USER),prop.getProperty(PROPERTIES_DB_PASSWORD));
    }
        
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session=request.getSession(true);
        session.removeAttribute(ATTRIBUT_IDENTIFIANT);
        //TODO
        this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
    }
    
    private void home(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Properties prop= this.initProperty(request);    
        DataAccess dataAccess=getDataAccess(prop);;
        request.setAttribute(ATTRIBUT_EMPLOYEES, dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_ALL_EMPLOYEES)));
        this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
        
        dataAccess.closeConnection();
    }
    
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(true);
        
        String login=request.getParameter(PARAMETER_CH_LOGIN);
        String password=request.getParameter(PARAMETER_CH_PASSWORD);
        Properties prop= this.initProperty(request);
            
        DataAccess dataAccess=getDataAccess(prop);
        Identifiant id = dataAccess.getIdentifiant(prop.getProperty(PROPERTIES_SQL_LOGIN),login,password);
            
        if(id!=null){
            session.setAttribute(ATTRIBUT_IDENTIFIANT, id);
            request.setAttribute(ATTRIBUT_EMPLOYEES, dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_ALL_EMPLOYEES)));
            this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
        }
        else{
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR, "Identifiant ou mot de passe incorrect");
            this.getServletContext().getRequestDispatcher(PAGE_LOGIN).forward(request, response);
        }
        dataAccess.closeConnection();
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Properties prop= this.initProperty(request);
        DataAccess dataAccess=getDataAccess(prop);
        
        if(dataAccess.delete(prop.getProperty(PROPERTIES_SQL_DELETE_EMPLOYEES),request.getParameter("employes-id"))){
            request.setAttribute(ATTRIBUT_MESSAGE_INFO,"La suppression a réussi");
        }
        else{
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,"La supression a échoué");
        }
        request.setAttribute(ATTRIBUT_EMPLOYEES, dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_ALL_EMPLOYEES)));
        this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
        
        dataAccess.closeConnection();
    }
    
    //TODO
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        DataAccess dataAccess=getDataAccess(prop);
        
        if(dataAccess.delete(prop.getProperty(PROPERTIES_SQL_DELETE_EMPLOYEES),request.getParameter("employes-id"))){
            request.setAttribute(ATTRIBUT_MESSAGE_INFO,"Succès de l'ajout");
            request.setAttribute(ATTRIBUT_EMPLOYEES, dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_ALL_EMPLOYEES)));
        }
        else{
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,"Echec de l'ajout");
        }
        this.getServletContext().getRequestDispatcher(PAGE_NEW_EMPLOYE).forward(request, response);
        dataAccess.closeConnection();
    }

    private void details(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        DataAccess dataAccess=getDataAccess(prop);
        
        Employes employee =dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_DETAILS_EMPLOYEES),request.getParameter("employes-id"));
        
        if(employee !=null){
            request.setAttribute(ATTRIBUT_EMPLOYEE,employee);
            this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
        }
        else{
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,"Erreur de la connexion à la base de donnée");
            request.setAttribute(ATTRIBUT_EMPLOYEES, dataAccess.getEmployes(prop.getProperty(PROPERTIES_SQL_ALL_EMPLOYEES)));
            this.getServletContext().getRequestDispatcher(PAGE_EMPLOYEES_LIST).forward(request, response);
        }
        dataAccess.closeConnection();
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties prop= this.initProperty(request);
        DataAccess dataAccess=getDataAccess(prop);
        
        Employes employee=new Employes();
        employee.setId(Integer.parseInt(request.getParameter("employes-id")));
        employee.setNom(request.getParameter("employes-nom"));
        employee.setPrenom(request.getParameter("employes-prenom"));
        employee.setEmail(request.getParameter("employes-email"));
        employee.setTeldom(request.getParameter("employes-teldom"));
        employee.setTelport(request.getParameter("employes-telport"));
        employee.setTelpro(request.getParameter("employes-telperso"));
        employee.setAdresse(request.getParameter("employes-adresse"));
        employee.setVille(request.getParameter("employes-ville"));
        employee.setCodepostal(request.getParameter("employes-codepostal"));
        
        if(dataAccess.updateEmployes(prop.getProperty(PROPERTIES_SQL_UPDATE_EMPLOYEES),employee)){
            request.setAttribute(ATTRIBUT_MESSAGE_INFO,"Succes de la mise à jour");
        this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
        }
        else{
            request.setAttribute(ATTRIBUT_MESSAGE_ERROR,"Erreur dans la mise à jour");
        this.getServletContext().getRequestDispatcher(PAGE_DETAILS).forward(request, response);
        }
        dataAccess.closeConnection();
    }
}


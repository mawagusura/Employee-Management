/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.efrei.Controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Amaury
 */
public abstract class AbstractController extends HttpServlet {

    private static final String PAGE_PROPERTIES = "/WEB-INF/db.properties";

    private Properties initProperty(HttpServletRequest request) throws IOException{
        Properties prop= new Properties();
        InputStream input= request.getServletContext().getResourceAsStream(PAGE_PROPERTIES);
        prop.load(input);
        return prop;
    }




}

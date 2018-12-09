<%-- 
    Document   : bienvenue
    Created on : 8 nov. 2018, 08:25:38
    Author     : LUCASMasson
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bienvenue</title>
    </head>
    <body>
        <h1>Bienvenue</h1>
        Votre login est: <c:out value="${sessionScope.identifiant.login}" default=""/> 
        <br><br>
        Votre mot de passe  est: <c:out value="${sessionScope.identifiant.password}" default=""/> 
        <br><br>
        <table border="1">
                <tbody><tr>
                    <td><b>Detail</b></td>
                    <td><b>Name</b></td>
                    <td><b>First name</b></td>
                    <td><b>Home phone</b></td>
                    <td><b>Mobile phone</b></td>
                    <td><b>Office phone</b></td>
                    <td><b>Adress</b></td>
                    <td><b>Postal code</b></td>
                    <td><b>City</b></td>
                    <td><b>Email</b></td>
                </tr>
                
                <c:forEach items="${ employes }" var="employe" >
                    <tr> 
                        <td><c:out value="${employe.id}"/></td>
                        <td><c:out value="${employe.nom}"/></td>
                        <td><c:out value="${employe.prenom}"/></td>
                        <td><c:out value="${employe.teldom}"/></td>
                        <td><c:out value="${employe.telport}"/></td>
                        <td><c:out value="${employe.telpro}"/></td>
                        <td><c:out value="${employe.adresse}"/></td>
                        <td><c:out value="${employe.codepostal}"/></td>
                        <td><c:out value="${employe.ville}"/></td>
                        <td><c:out value="${employe.email}"/></td>
                    </tr>
                </c:forEach>
            </tbody></table>
    </body>
</html>

<%-- 
    Document   : index
    Created on : 8 nov. 2018, 08:24:25
    Author     : LUCASMasson
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
    </head>
    <body>
        <h1>Index</h1>
      
        <c:if test="${sessionScope.message_erreur != null}"> 
            <c:out value="${message_erreur}"  default=""/> 
            <c:remove var="message_erreur" scope="session"/>
            <br><br>
        </c:if> 
        
        <form action="${pageContext.request.contextPath}/login" method="post">
            Login:<input type="text" name="chLogin"/>
            <br><br>
            
            Mot de passe:<input type="password" name="chPassword"/>
            <br><br>
            <input type="submit" value="Envoyer"/>
        </form>
        
    </body>
</html>

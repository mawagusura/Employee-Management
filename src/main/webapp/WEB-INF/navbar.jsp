<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Gestionnaire d'employés</a>
    <span class="navbar-text ml-auto">
        Vous êtes connecté en tant que <b><c:out value="${identifiant.login}"/></b>
    </span>
    <span class="navbar-text ml-2">
        <form action="${pageContext.request.contextPath}" method="post">
            <button type="submit" name="action" value="logout" class="btn m1-1" > <i class="fa fa-power-off"></i> </button>
        </form>
    </span>
</nav> 
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <!-- title -->
        <title>Liste des employés</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="styles.css">
        <meta charset="utf-8">
    </head>
    <body>
        <form action="${pageContext.request.contextPath}/crud" id="form">
            <legend class="m-2"><b>Liste des employés</b></legend>
            
            <p class="text-danger m-2">${message_erreur}</p>
            <p class="text-primary m-2">${message_info}</p>
            
            <c:choose>
                <c:when test="${ employes.size() == 0 }">
                    <p class="text-danger m-2">Nous devons recruter !</p>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col">Sél</th>
                                <th scope="col">NOM</th>
                                <th scope="col">PRÉNOM</th>
                                <th scope="col">TEL DOMICILE</th>
                                <th scope="col">TEL PORTABLE</th>
                                <th scope="col">TEL PRO</th>
                                <th scope="col">ADRESSE</th>
                                <th scope="col">CODE POSTAL</th>
                                <th scope="col">VILLE</th>
                                <th scope="col">EMAIL</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${ employes }" var="employe" >
                                <tr>
                                    <th scope="row"><input type="radio" name="employes-id" value="${employe.id}" id="employee_${employe.id}"></th>
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
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
            
            <button type="submit" name="action" value="delete" class="btn btn-primary ml-1" formmethod="delete">Supprimer</button>
            <button type="submit" name="action" value="details" class="btn btn-primary ml-1" formmethod="get">Détails</button>
            
            <button type="submit" name="action" value="add" class="btn ml-1" formmethod="get" formaction="${pageContext.request.contextPath}/new">Ajouter</button>
        </form>
    </body>
</html>
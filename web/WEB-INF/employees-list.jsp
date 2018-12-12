<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <!-- title -->
        <title>Liste des employés</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link href="${pageContext.request.contextPath}/resources/styles.css" rel="stylesheet" type="text/css"/>
        <meta charset="utf-8">
    </head>
    <body>
        <form action="${pageContext.request.contextPath}" method="post" class="employees-form">
            <legend class="mt-2"><b>Liste des employés</b></legend>
            
            <p class="text-danger"><c:out value="${message_error}"/><p>
            <c:remove var="message_error" scope="session"/>
            
            <p class="text-primary"><c:out value="${message_info}"/></p>
            <c:remove var="message_info" scope="session"/>
                
            <c:choose>
                <c:when test="${ employees.size() == 0 }">
                    <p class="text-danger m-2">Nous devons recruter !</p>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped table-bordered">
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
                            <!-- Var to ckech by default the first radio button -->
                            <c:set var = "checked" value = "false"/>
                            <c:forEach items="${ employees }" var="employee" >
                                <tr>
                                    <th scope="row"><input type="radio" name="employes-id" value="${employee.id}" id="employee_${employee.id}"></th>
                                    <td><c:out value="${employee.nom}"/></td>
                                    <td><c:out value="${employee.prenom}"/></td>
                                    <td><c:out value="${employee.teldom}"/></td>
                                    <td><c:out value="${employee.telport}"/></td>
                                    <td><c:out value="${employee.telpro}"/></td>
                                    <td><c:out value="${employee.adresse}"/></td>
                                    <td><c:out value="${employee.codepostal}"/></td>
                                    <td><c:out value="${employee.ville}"/></td>
                                    <td><c:out value="${employee.email}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <button type="submit" name="action" value="delete" class="btn btn-primary ml-1">Supprimer</button>
                    <button type="submit" name="action" value="details" class="btn btn-primary ml-1">Détails</button>
                </c:otherwise>
            </c:choose>
            
            <button type="submit" name="action" value="add" class="btn ml-1">Ajouter</button>
            <button type="submit" name="action" value="logout" class="btn ml-1  btn-danger float-right">Déconnexion</button>
        </form>
    </body>
</html>
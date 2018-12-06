<!DOCTYPE html>
<html>
    <head>
        <!-- title -->
        <title>Details</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="styles.css">
        <meta charset="utf-8">
    </head>
    <body>
        <div class="d-block mx-auto details-form">
            <form action="${pageContext.request.contextPath}" method="post">
                <input type="hidden" class="form-control" id="employes-id" name="employes-id" value="${employe.id}">
                
                <legend>Détails du membre ${employe.prenom} ${employe.nom}</legend>
                <hr>
                <div class="form-group row">
                    <label for="name" class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="name" name="employes-nom" value="${employe.nom}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="first-name" class="col-sm-2 col-form-label">Prénom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="first-name" name="employes-prenom" value="${employe.prenom}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="fixed-phone" class="col-sm-2 col-form-label">Tél dom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="fixed-phone" name="employes-teldom" value="${employe.teldom}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="mobile-phone" class="col-sm-2 col-form-label">Tél mob</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="mobile-phone" name="employes-telperso"value="${employe.telport}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="pro-phone" class="col-sm-2 col-form-label">Tél pro</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="pro-phone" name="employes-telport" value="${employe.telpro}">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group row">
                            <label for="address" class="col-sm-4 col-form-label">Adresse</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="address" name="employes-adresse" value="${employe.adresse}">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group row">
                            <label for="postal" class="col-sm-5 col-form-label">Code postal</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="postal" name="employes-codepostal" value="${employe.codepostal}">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group row">
                            <label for="country" class="col-sm-4 col-form-label">Ville</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="country" name="employes-ville" value="${employe.ville}">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group row">
                            <label for="mail" class="col-sm-5 col-form-label">Email</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="mail" name="employes-email" value="${employe.email}">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="float-right">
                    <input type="submit" value="Modifier" class="btn btn-primary ml-1">
                    <a href="employees-list.html"><button class="btn ml-1">Voir liste</button></a>
                </div>
            </form>
        </div>
    </body>
</html>
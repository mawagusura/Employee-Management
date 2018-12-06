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
                <legend>Nouvel utilisateur</legend>
                <hr>
                <div class="form-group row">
                    <label for="name" class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="name" name="employes-nom" placeholder="Nom">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="first-name" class="col-sm-2 col-form-label">Prénom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="first-name" name="employes-prenom" placeholder="Prénom">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="fixed-phone" class="col-sm-2 col-form-label">Tél dom</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="fixed-phone" name="employes-teldom" placeholder="Tél. domicile">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="mobile-phone" class="col-sm-2 col-form-label">Tél mob</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="mobile-phone" name="employes-telperso" placeholder="Tél. mobile">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="pro-phone" class="col-sm-2 col-form-label">Tél pro</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="pro-phone" name="employes-telport" placeholder="Tél. professionnel">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group row">
                            <label for="address" class="col-sm-4 col-form-label">Adresse</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="address" name="employes-adresse" placeholder="Adresse">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group row">
                            <label for="postal" class="col-sm-5 col-form-label">Code postal</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="postal" name="employes-codepostal" placeholder="Code postal">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group row">
                            <label for="country" class="col-sm-4 col-form-label">Ville</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="country" name="employes-ville" placeholder="Adresse">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group row">
                            <label for="mail" class="col-sm-5 col-form-label">Email</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="mail" name="employes-email" placeholder="Email">
                            </div>
                        </div>
                    </div>
                </div>

                <div class="float-right">
                    <input type="submit" value="add" class="btn btn-primary ml-1">
                    <a href="employees-list.html"><button class="btn ml-1">Voir liste</button></a>
                </div>
            </form>
        </div>
    </body>
</html>
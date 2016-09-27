<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Grails"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>
</head>

<body>

<g:layoutBody/>



<asset:javascript src="application.js"/>
<div class="hidden">

</div>


</div>
</div>

<div class="modals">

    <!-- Modal -->
    <div class="modal fade " id="creerGroupeModal" tabindex="-1" role="dialog" aria-labelledby="creerGroupeModalLabel">
        <div class="modal-dialog  modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="creerGroupeModalLabel">Gestion des groupes ✎</h4>
                </div>

                <div class="modal-body">
                    <div>

                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs" role="tablist">
                            <li role="presentation" class="active"><a href="#gCreation" aria-controls="home" role="tab"
                                                                      data-toggle="tab">Création</a></li>
                            <li role="presentation"><a href="#gEdition" aria-controls="profile" role="tab"
                                                       data-toggle="tab">Edition</a></li>
                            <li role="presentation"><a href="#gSupression" aria-controls="messages" role="tab"
                                                       data-toggle="tab">Suppression</a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active" id="gCreation">
                                <form class="container-fluid">
                                    <div class="form-group">
                                        <label>Nom du nouveau groupe</label>
                                        <input type="text" class="form-control"/>
                                    </div>

                                    <div class="form-group">
                                        <label>Logo</label>
                                        <input class="btn btn-default form-control" type="file" accept="image/*"/>
                                    </div>


                                    <input type="submit" class="pull-right btn btn-primary"
                                           value="Enregistrer"/>
                                </form>

                            </div>

                            <div role="tabpanel" class="tab-pane" id="gEdition">

                                <form name="gEdition" class="container-fluid">

                                    <div class="form-group">

                                        <label>Groupe à éditer</label>
                                        <select name="selectGroupe" class="form-control"></select>
                                    </div>

                                    <div class="form-group">
                                        <label>Nom du groupe</label>
                                        <input type="text" class="form-control"/>
                                    </div>

                                    <div class="form-group">
                                        <label>Logo</label>
                                        <input class="btn btn-default form-control" type="file" accept="image/*"/>
                                    </div>

                                    <input type="submit" class="pull-right btn btn-primary"
                                           value="Enregistrer"/>
                                </form>

                            </div>

                            <div role="tabpanel" class="tab-pane" id="gSupression">
                                <form name="gSupression" class="container-fluid">
                                    <ul class="list-group listSupress">
                                        <li class="list-group-item"><label>Groupe 1</label><input type="checkbox"
                                                                                                  class="checkbox"/>

                                        </li>

                                    </ul>

                                    <input type="submit" class="pull-right btn btn-danger"
                                           value="Supprimer les groupes sélectionnés"/>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade " id="gestionUserModal" tabindex="-1" role="dialog" aria-labelledby="gestionUserModalLabel">
    <div class="modal-dialog  modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="gestionUserModalLabel">Gestion des utilisateurs 👤</h4>
            </div>

            <div class="modal-body">
                <div>

                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#uEdition" aria-controls="profile" role="tab"
                                                   data-toggle="tab">Edition</a></li>
                        <li role="presentation"><a href="#uSupression" aria-controls="messages" role="tab"
                                                   data-toggle="tab">Suppression</a></li>
                    </ul>

                    <!-- Tab panes -->
                    <div class="tab-content">


                        <div role="tabpanel" class="tab-pane active" id="uEdition">

                            <form name="uEdition" class="container-fluid">

                                <div class="form-group">

                                    <label>Utilisateur à éditer</label>
                                    <select name="selectUser" class="form-control mainSelect"></select>
                                </div>

                                <div class="form-group">
                                    <label>Pseudo</label>
                                    <input type="text" class="form-control"/>
                                </div>
                                <div class="form-group">
                                    <label>E-Mail</label>
                                    <input type="email" class="form-control"/>
                                </div>

                                <div class="form-group">
                                    <label>Role</label>
                                    <select name="selectRole" class="form-control"></select>
                                </div>

                                <input type="submit" class="pull-right btn btn-primary"
                                       value="Enregistrer"/>
                            </form>

                        </div>

                        <div role="tabpanel" class="tab-pane" id="uSupression">
                            <form name="uSupression" class="container-fluid">
                                <ul class="list-group listSupress">
                                    <li class="list-group-item"><label>User 1</label><input type="checkbox"
                                                                                              class="checkbox"/>

                                    </li>

                                </ul>

                                <input type="submit" class="pull-right btn btn-danger"
                                       value="Supprimer les utilisateurs sélectionnés"/>
                            </form>
                        </div>
                    </div>

                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
            </div>
        </div>
    </div>
</div>
</div>

<div class="modal fade " id="createPOIModal" tabindex="-1" role="dialog" aria-labelledby="createPOIModal">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <div class="container-fluid">
                    <form name="addPOI">
                        <div class="form-group">
                            <label>Nom du POI :</label>
                            <input type="text" class="form-control" placeholder="Nom du nouveau POI">
                        </div>

                        <div class="form-group">

                            <label>Groupe :</label>
                            <select class="form-control"></select>
                        </div>

                        <div class="form-group">
                            <label>Latitude :</label>
                            <input type="text" name="latitude" class="form-control" disabled>
                        </div>

                        <div class="form-group">
                            <label>Longitude :</label>
                            <input type="text" name="longitude" class="form-control" disabled>
                        </div>

                        <div class="form-group">
                            <label>Description :</label>
                            <textarea name="description" class="form-control"></textarea>
                        </div>

                        <div class="form-group">
                            <input type="file" class="btn btn-default form-control" name="photos" multiple="multiple">

                        </div>

                        <div class="form-group">
                            <input type="submit" class="btn btn-primary form-control" value="Envoyer">
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
<script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAOVh1mJ-DvhZOZUsb40ehjooUTUaCa3_M">
</script>

</body>
</html>

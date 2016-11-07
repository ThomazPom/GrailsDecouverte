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





</div>
</div>

<div class="modals">
<sec:ifAnyGranted roles="ROLE_MODO,ROLE_ADMIN">
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

                            <li role="presentation"><a href="#gList" aria-controls="messages" role="tab"
                                                       data-toggle="tab">Liste</a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div role="tabpanel" class="tab-pane active" id="gCreation">
                                <form name="gCreation" callback="gCreationCallBack" method="POST"
                                      enctype="multipart/form-data" action="/A_Groupe/createGroup"
                                      class="container-fluid ajax reset">
                                    <div class="form-group">
                                        <label>Nom du nouveau groupe</label>
                                        <input required name="nom" type="text" class="form-control"/>
                                    </div>

                                    <div class="form-group">
                                        <label>Logo</label>
                                        <input class="btn btn-default form-control" name="logo" type="file"
                                               accept="image/*"/>
                                    </div>


                                    <input type="submit" class="pull-right btn btn-primary"
                                           value="Enregistrer"/>
                                </form>

                            </div>

                            <div role="tabpanel" class="tab-pane" id="gEdition">

                                <form callback="gEditionCallBack" action="/A_Groupe/majGroup" name="gEdition"
                                      class="container-fluid ajax">

                                    <div class="form-group">

                                        <label>Groupe à éditer</label>
                                        <select name="selectGroupe" class="form-control paramId"></select>
                                    </div>

                                    <div class="form-group">
                                        <label>Nom du groupe</label>
                                        <input type="text" name="nom" class="form-control"/>
                                    </div>

                                    <div class="form-group">
                                        <label>Logo</label>
                                        <input class="btn btn-default form-control" name="logo" type="file"
                                               accept="image/*"/>
                                    </div>
                                    <img name="apercu" src="" class="img-responsive"/>
                                    <input type="submit" class="pull-right btn btn-primary"
                                           value="Enregistrer"/>

                                </form>

                            </div>

                            <div role="tabpanel" class="tab-pane" id="gSupression">
                                <form name="gSupression" callback="gSupressionCallBack" action="/A_Groupe/delGroup"
                                      class="container-fluid ajax"><br>

                                    <div class="form-group alert alert-danger">
                                        <label>Supprimer les POI qui n'ont plus de groupe également <input name="force"
                                                                                                           type="checkbox">
                                        </label></div>
                                    <ul class="list-group listSupress">
                                        <li class="list-group-item"><label>Groupe 1</label><input type="checkbox"
                                                                                                  class="checkbox"/>

                                        </li>

                                    </ul>

                                    <input type="submit" class="pull-right btn btn-danger"
                                           value="Supprimer les groupes sélectionnés"/>
                                </form>
                            </div>

                            <div role="tabpanel" class="tab-pane" id="gList">
                                <br>  
                                <ul class="list-group" id="listGroupView"></ul>
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
</sec:ifAnyGranted>
<!-- Modal -->

<sec:ifAnyGranted roles="ROLE_ADMIN">
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
                            <li role="presentation" class="active"><a href="#uEdition" aria-controls="profile"
                                                                      role="tab"
                                                                      data-toggle="tab">Edition</a></li>

                            <li role="presentation"><a href="#uCreation" aria-controls="profile"
                                                       role="tab"
                                                       data-toggle="tab">Creation</a></li>
                            <li role="presentation"><a href="#uSupression" aria-controls="messages" role="tab"
                                                       data-toggle="tab">Suppression</a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div class="tab-content">

                            <div role="tabpanel" class="tab-pane active" id="uEdition">

                                <form callback="uEditionCallBack" action="/A_User/majUser/" name="uEdition"
                                      class="container-fluid ajax">

                                    <div class="form-group">

                                        <label>Utilisateur à éditer</label>
                                        <select name="selectUser" class="form-control mainSelect"></select>
                                    </div>

                                    <div class="form-group">
                                        <label>Pseudo</label>
                                        <input type="text" name="username" class="form-control"/>
                                    </div>

                                    <div class="form-group">
                                        <label>Role</label>
                                        <select name="selectRole" class="form-control"></select>
                                    </div>

                                    <input type="submit" class="pull-right btn btn-primary"
                                           value="Enregistrer"/>
                                </form>

                            </div>

                            <div role="tabpanel" class="tab-pane" id="uCreation">

                                <form callback="uCreationCallBack" action="/A_User/createUser/" name="uCreation"
                                      class="container-fluid ajax reset">
                                    <div class="form-group">
                                        <label>Pseudo</label>
                                        <input type="text" name="username" class="form-control"/>
                                    </div>

                                    <div class="form-group">
                                        <label>Mot de passe</label>
                                        <input type="password" name="password" class="form-control"/>
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
                                <form action="/A_User/delUser/" callback="uSupressionCallback" name="uSupression"
                                      class="container-fluid ajax"><br>

                                    <div class="form-group alert alert-danger">
                                        <label>Supprimer les POI de l'utilisateur également  <input name="force"
                                                                                                    type="checkbox">
                                        </label></div>
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
</sec:ifAnyGranted>
<div class="modal fade " id="infoModal" tabindex="-1" role="dialog" aria-labelledby="infoModal">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <div class="infoContainer container-fluid">

                </div>
            </div>
        </div>
    </div>

</div>

<sec:ifAnyGranted roles="ROLE_USER">
    <div class="modal fade " id="listGroupModal" tabindex="-1" role="dialog" aria-labelledby="listGroupModal">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="listGroupModalLabel">Liste des groupes</h4>
                </div>

                <div class="modal-body">
                    <ul class="list-group" id="listGroupView"></ul>
                </div>
            </div>
        </div>

    </div>
</sec:ifAnyGranted>
<div class="modal fade " id="delPOIModal" tabindex="-1" role="dialog" aria-labelledby="delPOIModal">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="delPOIModalLabel">Confirmation de la suppression</h4>
            </div>

            <div class="modal-body">
                <div class="alert alert-danger">
                    Voulez vous vraiment supprimer le POI : <b id="confirmDeletePOIName"></b>

                </div>

                <div class="modal-footer">

                    <form action="/a_POI/delPOI" name="delPOI" callback="delPOICallback" class="ajax">
                        <input type="hidden" class="paramId">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Non</button>
                        <input value="Oui" type="submit" class="btn btn-danger"/>
                    </form>
                </div>

            </div>
        </div>
    </div>

</div>


<div class="modal fade " id="createPOIModal" tabindex="-1" role="dialog" aria-labelledby="createPOIModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>

                <div class="container-fluid">
                    <form action="/A_POI/createPOI/" callback="addPOICallBack" name="addPOI" class="ajax reset">
                        <div class="form-group">
                            <label>Nom du POI :</label>
                            <input type="text" name="nom" class="form-control" placeholder="Nom du nouveau POI">
                        </div>

                        <div class="form-group">

                            <label>Groupes :</label>
                            <select name="selectGroupe" multiple class="form-control"></select>
                        </div>

                        <div class="form-group">
                            <label>Latitude :</label>
                            <input type="text" name="latitude" class="form-control" readonly>
                        </div>

                        <div class="form-group">
                            <label>Longitude :</label>
                            <input type="text" name="longitude" class="form-control" readonly>
                        </div>

                        <div class="form-group">
                            <label>Description :</label>
                            <textarea name="description" class="form-control"></textarea>
                        </div>

                        <div class="form-group imgContainer">
                        </div>
                        <input type="submit" class="hidden btn btn-primary form-control" value="Envoyer">

                    </form>

                    <form callback="poiPhotoCallBack" name="poiPhoto" class="ajax nomodal reset"
                          action="/A_POI/createImagePOI/">
                        <div class="form-group">
                            <input type="file" class="btn btn-default form-control" name="photos" multiple="multiple">
                        </div>
                    </form>

                    <div class="form-group">
                        <input type="submit" id="addPOISend" class="btn btn-primary form-control" value="Envoyer">
                    </div>

                </div>
            </div>
        </div>
    </div>

</div>

<div class="modal fade " id="viewPOIModal" tabindex="-1" role="dialog" aria-labelledby="viewPOIModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body" name="viewPOI">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>

                <div class="container-fluid">
                    <div class="form-group">
                        <label>Nom du POI :</label>

                        <h2 name="nom"></h2>
                    </div>

                    <div class="form-group">

                        <label>Groupes :</label>
                        <ul name="selectGroupe" class="list-group">
                            <li class="list-group-item">#1</li>
                        </ul>
                    </div>

                    <div class="form-group">
                        <label>Latitude :</label>

                        <h3 name="latitude"></h3>
                    </div>

                    <div class="form-group">
                        <label>Longitude :</label>

                        <h3 name="longitude"></h3>
                    </div>

                    <div class="form-group">
                        <label>Description :</label>

                        <p name="description"></p>
                    </div>

                    <div class="form-group imgContainer">
                    </div>


                    <div class="form-group">
                        <input type="submit" id="editPOIButton" class="btn btn-primary form-control"
                               value="Editer ce POI">
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal fade " id="majPOIModal" tabindex="-1" role="dialog" aria-labelledby="majPOIModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">×</span></button>

                <div class="container-fluid">
                    <form action="/A_POI/majPOI/" name="majPOI" class="ajax">
                        <input type="hidden" class="paramId"/>

                        <div class="form-group">
                            <label>Nom du POI :</label>
                            <input type="text" name="nom" class="form-control" placeholder="Nom du POI">
                        </div>

                        <div class="form-group">

                            <label>Groupes :</label>
                            <select name="selectGroupe" multiple class="form-control"></select>
                        </div>

                        <div class="form-group">
                            <label>Latitude :</label>
                            <input type="text" name="latitude" class="form-control" readonly>
                        </div>

                        <div class="form-group">
                            <label>Longitude :</label>
                            <input type="text" name="longitude" class="form-control" readonly>
                        </div>

                        <div class="form-group">
                            <label>Description :</label>
                            <textarea name="description" class="form-control"></textarea>
                        </div>

                        <div class="form-group imgContainer">
                        </div>
                        <input type="submit" class="hidden btn btn-primary form-control" value="Envoyer">

                    </form>

                    <form callback="majPoiPhotoCallBack" name="majPoiPhoto" class="ajax nomodal reset"
                          action="/A_POI/createImagePOI/">
                        <div class="form-group">
                            <input type="file" class="btn btn-default form-control" name="photos" multiple="multiple">
                        </div>
                    </form>

                    <div class="form-group">
                        <input type="submit" id="majPOISend" class="btn btn-primary form-control" value="Envoyer">
                    </div>


                    <div class="form-group">
                        <input type="submit" id="delPOIButton" class="btn btn-danger form-control"
                               value="Supprimer ce POI">
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAOVh1mJ-DvhZOZUsb40ehjooUTUaCa3_M">
</script>

<asset:javascript src="application.js"/>
</body>
</html>

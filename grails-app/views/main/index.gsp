<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>

    <title>Points of interest manager</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Points of interest</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <!--    <ul class="nav navbar-nav">
                <li>

                    <form class="navbar-form navbar-left" role="search">

                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Rechercher">
                        </div>
                    </form>
                </li>

            </ul>-->

                <div class="navbar-form navbar-left">
                    <div class="form-group">
                        <sec:ifAnyGranted roles="ROLE_ADMIN,ROLE_MODO">
                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    data-target="#creerGroupeModal">
                                Groupes ✎
                            </button>
                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    data-target="#gestionUserModal">
                                Utilisateurs 👤
                            </button>
                        </sec:ifAnyGranted>

                        <sec:ifAnyGranted roles="ROLE_USER">
                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                    data-target="#listGroupModal">
                                Liste des groupes
                            </button>
                        </sec:ifAnyGranted>
                    </div>

                </div>

            <ul class="nav navbar-nav navbar-right">
                <li><a class="navbar-brand" href="#">Devs : Lavoisier / Benhamou</a></li>
            </ul>

            <div class="navbar-form navbar-right">
                <a href="/A_User/logout" class="btn btn-danger">Déconnexion</a>
            </div>

        <ul class="nav navbar-nav navbar-right">
            <li><a class="navbar-brand navbar-right">${sec.loggedInUserInfo(field: 'username')}</a></li>
        </ul>
        </div>
    </div><!-- /.container-fluid -->
</nav>


<div id="map"></div>

</body>
</html>

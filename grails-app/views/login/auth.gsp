<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <meta name="layout" content="main"/>
    <title>Title</title>

    <asset:stylesheet src="login.css"/>
</head>
<body>


<div class="container" style="max-width:400px">

    <form action="/login/authenticate" method="POST" id="loginForm"  class="form-signin" autocomplete="off">

        <h2 class="form-signin-heading">Please sign in</h2>
        <div class="form-group">
    <label class="sr-only" for="username">Nom d'utilisateur:</label>
    <input type="text" class="form-control" name="username" id="username">
        </div>


        <div class="form-group">

    <label class="sr-only" for="password">Mot de passe:</label>
    <input type="password" class="form-control" name="password" id="password">

</div>
    <div style="padding: 10px">
        <input type="checkbox" class="chk" name="remember-me" id="remember_me">
        <label for="remember_me">Rester connecté</label>
    </div>


    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>



    </form>

</div>



</body>
</html>
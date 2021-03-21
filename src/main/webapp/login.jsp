<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
    <title>Car Sales</title>
</head>
<body>
<div>
    <c:import url="_menu.jsp"/>
    <div class="container row">
        <div class="col s6 offset-s3">
            <h4 class="header center-align">Авторизация</h4>
            <div class="card horizontal">
                <div class="card-stacked row">
                    <form class="col s12" action="login" method="post">
                    <div class="card-content">
                        <div class="row">
                            <div class="input-field col s12">
<!--                                <input placeholder="" id="login" type="text" name="login" class="validate" required>-->
                                <input id="login" type="text" name="login" class="validate" required>
                                <label class="active" for="login">Логин</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="password" type="password" name="password" class="validate" required>
                                <label class="active" for="password">Пароль</label>
                            </div>
                            <span class="helper-text js-login-error" data-error="wrong" data-success="right">
                            </span>
                        </div>
                    </div>
                    <div class="card-action right-align">
                        <button type="button" class="waves-effect waves-light btn js-login">
                            войти<i class="material-icons right">send</i>
                        </button>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/login.js"></script>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="ru">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!--Import Google Icon Font-->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!--Import materialize.css-->
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css" media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="css/main.css" media="screen,projection"/>
    <title>Car Sales</title>
</head>
<body>
<div>
    <c:import url="../_menu.jsp"/>
    <div class="container js-main-container main-container">
        <h3 class="center-align page-title"><span class="js-model">Lada</span>, <span class="js-price">300 000 &#x20bd;</span></h3>
        <div class="row card auto-form">
            <div class="col s7 image-container">
                <img src="img/test1.jpeg" width="500">
            </div>
            <div class="col s5 property-container">
                <div class="col s12">
                    <span class="prop-name">Город:</span> <span class="prop-val js-city">Краснодар</span>
                </div>
                <div class="col s12">
                    <span class="prop-name">Пробег:</span> <span class="prop-val js-mileage">188 500</span> км
                </div>
                <div class="col s12">
                    <span class="prop-name">Состояние:</span> <span class="prop-val js-is_broken">не битый</span>
                </div>
                <div class="col s12">
                    <span class="prop-name">Кузов:</span> <span class="prop-val js-body_type">седан</span>
                </div>
                <div class="col s12">
                    <span class="prop-name">Двигатель:</span> <span class="prop-val js-engine">бензин</span>
                </div>
                <div class="col s12">
                    <span class="prop-name">Коробка передач:</span> <span class="prop-val js-transmission_box">механика</span>
                </div>
                <div class="col s12 description js-description">
                    Не бита не крашена, состояние нового авто. Сел и поехал, успей купить!
                </div>
                <div class="col s12 phone-number center-align">
                    <a href="tel:+7000000000" class="waves-effect waves-light btn">
                        позвонить
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/view.js"></script>
</body>
</html>
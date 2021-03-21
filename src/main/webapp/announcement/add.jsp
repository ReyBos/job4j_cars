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
        <h3 class="center-align page-title">разместить объявление</h3>
        <div class="row">
            <div class="col s12 card auto-form js-auto-info-container">
                <div class="auto-info-container">
                    <input type="hidden" value="1" class="js-announcement-type">
                    <p class="center-align auto-form-title">информация о машине</p>
                    <div class="row">
                        <div class="input-field col s4">
                            <select class="js-city">
                                <option value="" selected disabled></option>
                                <option value="1">Москва</option>
                                <option value="2">Санкт-Петербург</option>
                                <option value="3">Краснодар</option>
                            </select>
                            <label>Город</label>
                        </div>
                        <div class="input-field col s4">
                            <select class="js-model">
                                <option value="" selected disabled></option>
                                <option value="1">BMW</option>
                                <option value="2">Honda</option>
                                <option value="2">Lada</option>
                                <option value="2">Toyota</option>
                            </select>
                            <label>Марка</label>
                        </div>
                        <div class="input-field col s4">
                            <input id="price" type="text" class="validate js-price">
                            <label for="price">Цена, &#x20bd;</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s4">
                            <select class="js-is_new">
                                <option value="" selected disabled></option>
                                <option value=false>С пробегом</option>
                                <option value=true>Новый</option>
                            </select>
                            <label>Состояние</label>
                        </div>
                        <div class="input-field col s4">
                            <input id="mileage" type="text" class="validate js-mileage">
                            <label for="mileage">Пробег, км</label>
                        </div>
                        <div class="input-field col s4">
                            <p>
                                <label>
                                    <input type="checkbox" class="filled-in js-is_broken"/>
                                    <span>Битый или не на ходу</span>
                                </label>
                            </p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s4">
                            <select class="js-body_type">
                                <option value="" selected disabled></option>
                                <option value="1">Седан</option>
                                <option value="2">Хетчбэк</option>
                                <option value="2">Универсал</option>
                            </select>
                            <label>Кузов</label>
                        </div>
                        <div class="input-field col s4">
                            <select class="js-engine">
                                <option value="" selected disabled></option>
                                <option value="1">Бензин</option>
                                <option value="2">Дизель</option>
                            </select>
                            <label>Двигатель</label>
                        </div>
                        <div class="input-field col s4">
                            <select class="js-transmission_box">
                                <option value="" selected disabled></option>
                                <option value="1">Механика</option>
                                <option value="2">Автомат</option>
                                <option value="3">Робот</option>
                            </select>
                            <label>Коробка передач</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s8">
                            <textarea id="description" class="materialize-textarea js-description"></textarea>
                            <label for="description">Описание</label>
                        </div>
                        <div class="input-field col s4 center-align">
                            <button type="button" class="waves-effect waves-light btn js-add-next">
                                далее
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col s6 offset-s3 card auto-form js-photo-container" style="display: none">
                <div class="photo-container">
                    <p class="center-align auto-form-title">фотография</p>
                    <div class="file-field input-field choose-photo">
                        <div class="btn">
                            <span>выбрать</span>
                            <input type="file" name="file">
                        </div>
                        <div class="file-path-wrapper">
                            <input class="file-path validate" type="text">
                        </div>
                    </div>
                    <div class="center-align">
                        <button type="button" class="waves-effect waves-light btn">
                            разместить
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="modal1" class="modal js-modal modal-custom">
        <div class="modal-content js-modal-content">
            <p class="js-modal-msg center-align"></p>
        </div>
        <div class="modal-footer">
            <a href="#!" class="modal-close waves-effect waves-green btn-flat">Ok</a>
        </div>
    </div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/add.js"></script>
</body>
</html>
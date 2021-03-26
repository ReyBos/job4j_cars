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
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="css/main.css"  media="screen,projection"/>
    <title>Car Sales</title>
</head>
<body>
<div>
    <c:import url="_menu.jsp"/>
    <div class="container js-main-container main-container">
        <h3 class="center-align page-title">ваши объявления</h3>
        <div class="row">
            <div class="col s6">
                <div class="card auto-card">
                    <p class="image-container">
                        <img src="<c:url value="/img" />/test3.jpeg" width="212">
                    </p>
                    <div class="auto-description">
                        <p class="auto-link"><a href='<c:url value="/announcement?page=view&id=" />'>MINI</a></p>
                        <p class="auto-price">1 100 000 &#x20bd;</p>
                        <p class="auto-properties">
                            23 700 км, хетчбэк, бензин
                        </p>
                        <p class="auto-city">
                            Санкт-Петербург
                        </p>
                    </div>
                </div>
            </div>
            <div class="col s6">
                <div class="card auto-card">
                    <p class="image-container">
                        <img src="<c:url value="/img" />/test2.jpeg" width="212">
                    </p>
                    <div class="auto-description">
                        <p class="auto-link"><a href='<c:url value="/announcement?page=view&id=" />'>Honda</a></p>
                        <p class="auto-price">450 000 &#x20bd;</p>
                        <p class="auto-properties">
                            64 841 км, хетчбэк, бензин
                        </p>
                        <p class="auto-city">
                            Москва
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
</body>
</html>
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
    <link type="text/css" rel="stylesheet" href="<c:url value="/css" />/materialize.min.css"  media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="<c:url value="/css" />/main.css"  media="screen,projection"/>
    <title>Car Sales</title>
</head>
<body>
<div>
    <c:import url="_menu.jsp"/>
    <div class="container js-main-container main-container">
        <h3 class="center-align page-title">купить автомобиль</h3>
        <div class="row">
            <div class="col s6">
                <div class="card auto-card">
                    <p class="image-container">
                        <img src="<c:url value="/img" />/test1.jpeg" width="212">
                    </p>
                    <div class="auto-description">
                        <p class="auto-link"><a href='
                            <c:if test="${empty user}"><c:url value="/login" /></c:if>
                            <c:if test="${not empty user}"><c:url value="/announcement?action=view&id=" /></c:if>
                        '>Lada</a></p>
                        <p class="auto-price">300 000 &#x20bd;</p>
                        <p class="auto-properties">
                            188 500 км, седан, бензин
                        </p>
                        <p class="auto-city">
                            Краснодар
                        </p>
                    </div>
                </div>
            </div>
            <div class="col s6">
                <div class="card auto-card">
                    <p class="image-container">
                        <img src="<c:url value="/img" />/test3.jpeg" width="212">
                    </p>
                    <div class="auto-description">
                        <p class="auto-link"><a href='
                            <c:if test="${empty user}"><c:url value="/login" /></c:if>
                            <c:if test="${not empty user}"><c:url value="/announcement?action=view&id=" /></c:if>
                        '>MINI</a></p>
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
        </div>
        <div class="row">
            <div class="col s6">
                <div class="card auto-card">
                    <p class="image-container">
                        <img src="<c:url value="/img" />/test5.jpeg" width="212">
                    </p>
                    <div class="auto-description">
                        <p class="auto-link"><a href='
                            <c:if test="${empty user}"><c:url value="/login" /></c:if>
                            <c:if test="${not empty user}"><c:url value="/announcement?action=view&id=" /></c:if>
                        '>BMW</a></p>
                        <p class="auto-price">3 550 000 &#x20bd;</p>
                        <p class="auto-properties">
                            12 300 км, седан, бензин
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
                        <img src="<c:url value="/img" />/test4.jpeg" width="212">
                    </p>
                    <div class="auto-description">
                        <p class="auto-link"><a href='
                            <c:if test="${empty user}"><c:url value="/login" /></c:if>
                            <c:if test="${not empty user}"><c:url value="/announcement?action=view&id=" /></c:if>
                        '>Lada</a></p>
                        <p class="auto-price">800 000 &#x20bd;</p>
                        <p class="auto-properties">
                            88 935 км, седан, бензин
                        </p>
                        <p class="auto-city">
                            Москва
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col s6">
                <div class="card auto-card">
                    <p class="image-container">
                        <img src="<c:url value="/img" />/test2.jpeg" width="212">
                    </p>
                    <div class="auto-description">
                        <p class="auto-link"><a href='
                            <c:if test="${empty user}"><c:url value="/login" /></c:if>
                            <c:if test="${not empty user}"><c:url value="/announcement?action=view&id=" /></c:if>
                        '>Honda</a></p>
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
            <div class="col s6">
                <div class="card auto-card">
                    <p class="image-container">
                        <img src="<c:url value="/img" />/test6.jpeg" width="212">
                    </p>
                    <div class="auto-description">
                        <p class="auto-link"><a href='
                            <c:if test="${empty user}"><c:url value="/login" /></c:if>
                            <c:if test="${not empty user}"><c:url value="/announcement?action=view&id=" /></c:if>
                        '>Toyota</a></p>
                        <p class="auto-price">1 940 000 &#x20bd;</p>
                        <p class="auto-properties">
                            188 935 км, седан, бензин
                        </p>
                        <p class="auto-city">
                            Новосибирск
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/js" />/materialize.min.js"></script>
<script type="text/javascript" src="<c:url value="/js" />/index.js"></script>
</body>
</html>
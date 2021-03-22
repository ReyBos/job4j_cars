<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<nav>
    <div class="nav-wrapper container">
        <a class="brand-logo">Car Sales</a>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
            <c:if test="${empty user}">
                <li>
                    <a href='<c:url value="/" />'>Главная</a>
                </li>
                <li>
                    <a href='<c:url value="/registration" />'>Регистрация</a>
                </li>
                <li>
                    <a href='<c:url value="/login" />'>Войти</a>
                </li>
            </c:if>
            <c:if test="${not empty user}">
                <li>
                    <a href="<c:url value="/" />">Главная</a>
                </li>
                <li>
                    <a href="<c:url value="/profile" />">Профиль</a>
                </li>
                <li>
                    <a href="<c:url value="/announcement?action=add" />">Продать</a>
                </li>
                <li>
                    <a class="js-do-logout">Выйти</a>
                </li>
                <script type="text/javascript" src="<c:url value="/js" />/logout.js" defer></script>
            </c:if>
        </ul>
    </div>
</nav>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>The account list</title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
    You are logged as: <sec:authentication property="principal.username"/><br>
    Your role: <sec:authentication property="principal.authorities"/>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
    <form action="/logout" method="post">
        <input class="fa fa-id-badge" type="submit" value="Log out">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</sec:authorize>
<h1>Account list</h1>
<table style="text-align:center">
    <tr>
        <th>No</th>
        <th>Account number</th>
        <th>Currency</th>
        <th>Balance</th>
    </tr>
    <c:forEach items="${accounts}" var="account" varStatus="loop">
        <tr>
            <td>${loop.count}</td>
            <td>${account.accountNumber}</td>
            <td>${account.currency}</td>
            <td>${account.balance}</td>
            <td><a href="/deposit/${account.id}">Make a deposit</a></td>
        </tr>
    </c:forEach>
</table>
<p>
    <a href="/account/add">Add a new account</a>
</p>
<p>
    <a href="/transfer/make">Make a transfer</a>
</p>
<sec:authorize access="hasRole('ADMIN')">
    <p>
        <a href="/user/list">User list</a>
    </p>
    <p>
        <a href="/transfer/history">Transfer history</a>
    </p>
</sec:authorize>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Transfer history</title>
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
<h1>Transfer history</h1>
<table style="text-align:center">
    <tr>
        <th>No</th>
        <th>From Account</th>
        <th>To Account</th>
        <th>Original Currency</th>
        <th>Original Amount</th>
        <th>Final currency</th>
        <th>Final Amount</th>
        <th>Transfer Date</th>
    </tr>
    <c:forEach items="${transfers}" var="transfer" varStatus="loop">
        <tr>
            <td>${loop.count}</td>
            <td>${transfer.fromAccount.accountNumber}</td>
            <td>${transfer.toAccount.accountNumber}</td>
            <td>${transfer.fromAccount.currency}</td>
            <td>${transfer.originalAmount}</td>
            <td>${transfer.toAccount.currency}</td>
            <td>${transfer.finalAmount}</td>
            <td>${transfer.transferDate}</td>
        </tr>
    </c:forEach>
</table>
<p>
    <a href="/user/list">User list</a>
</p>

<p>
    <a href="/account/list">Account list</a>
</p>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>All users</title>
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
<h1>User list</h1>
<table style="text-align:center">
    <tr>
        <th>No</th>
        <th>Username</th>
        <th>Age</th>
        <th>PESEL</th>
        <th>Role</th>
    </tr>
    <c:forEach items="${users}" var="user" varStatus="loop">
        <tr>
            <td>${loop.count}</td>
            <td>${user.username}</td>
            <td>${user.age}</td>
            <td>${user.pesel}</td>
            <td>
                <c:forEach items="${user.roles}" var="role">
                    ${role.name}
                </c:forEach>
            </td>
            <td><a href="edit?id=${user.id}">Edit</a></td>
        </tr>
    </c:forEach>
</table>
<p>
    <a href="/user/add">Add a new user</a>
</p>
<p>
    <a href="/account/list">Account list</a>
</p>
<p>
    <a href="/transfer/history">Transfer history</a>
</p>
</body>
</html>

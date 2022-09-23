<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit the user</title>

    <style>
      .flex-container {
        display: flex;
        flex-direction: column;
      }
    </style>
</head>
<body>
<h1>Edit the user</h1>
<form:form method="post" modelAttribute="user">
    <div class="flex-container">
        <span>Username: <form:input path="username"/></span><form:errors path="username"
                                                                         cssClass="error"/>
        <span>Password: <form:password path="password"/></span><form:errors path="password"
                                                                            cssClass="error"/>
    </div>
    <form:hidden path="id"/>
    <form:hidden path="pesel"/></span>
    <input type="submit" value="Save changes">
</form:form>
<p>
    <a href="/user/list">User list</a>
</p>
</body>
</html>
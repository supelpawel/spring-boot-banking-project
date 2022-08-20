<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add a new user</title>

    <style>
        .flex-container {
            display: flex;
            flex-direction: column;
        }

        .error {
            color: red;
        }
    </style>
</head>
<body>
<h1>Add a new user</h1>
<form:form method="post" modelAttribute="user">
    <div class="flex-container">
        <span>Username: <form:input path="username"/></span><form:errors path="username" cssClass="error"/>
        <span>Age: <form:input path="age"/></span><form:errors path="age" cssClass="error"/>
        <span>PESEL: <form:input path="pesel"/></span><form:errors path="pesel" cssClass="error"/>
        <span>Password: <form:password path="password"/></span><form:errors path="password" cssClass="error"/>
        <span>Enabled: <form:checkbox path="enabled"/></span><form:errors path="enabled" cssClass="error"/>
        <span>Roles: <form:select path="roles" multiple="true" items="${roles}" itemLabel="name"/></span><form:errors path="roles" cssClass="error"/>
    </div>
    <input type="submit" value="Add user">
</form:form>
</body>
</html>

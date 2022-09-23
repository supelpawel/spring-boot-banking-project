<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Add a new account</title>

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
<h1>Add a new account</h1>
<form:form method="post" modelAttribute="account">
    <div class="flex-container">
        <span>Set initial balance: <form:input path="balance"/></span><form:errors path="balance"
                                                                                   cssClass="error"/>
        <span>Choose currency: <form:select path="currency" items="${currencyList}"/></span>
        <form:errors path="currency" cssClass="error"/>
    </div>
    <input type="submit" value="Add account">
</form:form>
<p>
    <a href="/account/list">Account list</a>
</p>
</body>
</html>
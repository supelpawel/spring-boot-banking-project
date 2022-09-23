<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Make a new transfer</title>

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
<h1>Make a new transfer</h1>
<form:form method="post" modelAttribute="transfer">
    <div class="flex-container">
        <span>Amount: <form:input path="originalAmount"/></span><form:errors path="originalAmount"
                                                                             cssClass="error"/>
        <span>From account: <form:select path="fromAccount" items="${accounts}"
                                         itemLabel="accountNumber"/></span><form:errors
            path="fromAccount" cssClass="error"/>
        <span>To account: <form:select path="toAccount" items="${accounts}"
                                       itemLabel="accountNumber"/></span><form:errors
            path="toAccount" cssClass="error"/>
    </div>
    <input type="submit" value="Make a transfer">
</form:form>
<p>
    <a href="/account/list">Account list</a>
</p>
</body>
</html>
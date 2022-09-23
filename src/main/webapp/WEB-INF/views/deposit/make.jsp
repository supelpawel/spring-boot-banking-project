<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Make a deposit</title>

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
<h1>Make a deposit</h1>
<form:form method="post" modelAttribute="deposit">
    <div class="flex-container">
        <span>Amount: <form:input path="amount"/></span><form:errors path="amount"
                                                                     cssClass="error"/>
    </div>
    <input type="submit" value="Make deposit">
</form:form>
<p>
    <a href="/account/list">Account list</a>
</p>
</body>
</html>
<%--suppress JSUnusedGlobalSymbols --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ali
  Date: 12.09.2019
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%--Second BMI calculator that is based on Elm--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
    <%--<link rel="stylesheet" href="whatever-you-want.css">--%>
    <script src="main.js"></script>
</head>
<body>
<div id="elm"></div>
<script>
    var baseUrl = '${requestScope.BASE_URL}';
    var app = Elm.Main.init({
        node: document.getElementById('elm'),
        flags: baseUrl
    });
</script>
</body>
</html>

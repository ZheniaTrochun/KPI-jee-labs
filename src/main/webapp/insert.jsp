<%--
  Created by IntelliJ IDEA.
  User: yetr
  Date: 4/6/2018
  Time: 7:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Movie</title>
</head>
<body>
<form method="post" action="movies">
    <br>
    <input type="text" name="name" placeholder="name">
    <input type="text" name="genre" placeholder="genre">
    <input type="number" name="year" placeholder="year">
    <input type="text" name="author" placeholder="author">
    <input type="number" name="imdbScore" placeholder="imdbScore">

    <br><br>
    <input type="submit">
</form>
</body>
</html>

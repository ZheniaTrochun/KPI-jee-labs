<%--
  Created by IntelliJ IDEA.
  User: yetr
  Date: 4/6/2018
  Time: 7:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import ="java.util.*" %>
<!DOCTYPE html>
<html>
<body>
<center>
    <h1>
        Available Brands
    </h1>
        <%
List result= (List) request.getAttribute("allMovies");
Iterator it = result.iterator();
out.println("<br>We have <br><br>");
while(it.hasNext()){
out.println(it.next().toString()+"<br>");
}
%>

    <form method="delete" action="movies">
        <br>
        <label>
            <input type="number" name="id" placeholder="id">
        </label>
        <br><br>
        <input type="submit" value="delete">
    </form>

    <form method="put" action="movies">
        <br>
        <input type="number" name="id" placeholder="id">
        <input type="text" name="name" placeholder="name">
        <input type="text" name="genre" placeholder="genre">
        <input type="number" name="year" placeholder="year">
        <input type="text" name="author" placeholder="author">
        <input type="number" name="imdbScore" placeholder="imdbScore">

        <br><br>
        <input type="submit" value="update">
    </form>
</center>
</body>
</html>

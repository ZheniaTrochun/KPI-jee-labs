<%--
  Created by IntelliJ IDEA.
  User: yetr
  Date: 4/6/2018
  Time: 7:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import ="java.util.*" %>
<%@ page import ="com.yevhenii.model.Movie" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <script>
        const deleteMovie = (id) => {
            $.ajax({
                url: "/MovieList/movies?" + $.param({"id" : id}),
                type: "DELETE",
                success: (res) => {
                    $("#" + id).hide()
                }
            })
        }
    </script>
</head>
<body>
<div class="container">
    <h1>
        Available Movies
    </h1>
    <table class="table">
        <thead>
          <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Author</th>
            <th>Genre</th>
            <th>Year</th>
            <th>ImdbScore</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
        <%
            List<Movie> result= (List<Movie>) request.getAttribute("allMovies");
            Iterator it = result.iterator();
            while(it.hasNext()){
                Movie movie = (Movie) it.next();
                out.println("<tr id=" + movie.getId() + ">");
                out.println(String.format("<td>%d</td>", movie.getId()));
                out.println(String.format("<td>%s</td>", movie.getName()));
                out.println(String.format("<td>%s</td>", movie.getAuthor()));
                out.println(String.format("<td>%s</td>", movie.getGenre()));
                out.println(String.format("<td>%d</td>", movie.getYear()));
                out.println(String.format("<td>%f</td>", movie.getImdbScore()));
                out.println(String.format("<td><button class=\"btn btn-danger\" onclick=\"deleteMovie(%d)\">Delete</button><button class=\"btn btn-warning\" onclick=update(%d)>Update</button></td>", movie.getId(), movie.getId()));
                out.println("</tr>");
            }
        %>
        </tbody>
    </table>

    <form method="post" action="movies">
        <br>
        <input type="hidden" name="method" value="put">
        <input type="number" name="id" placeholder="id">
        <input type="text" name="name" placeholder="name">
        <input type="text" name="genre" placeholder="genre">
        <input type="number" name="year" placeholder="year">
        <input type="text" name="author" placeholder="author">
        <input type="number" name="imdbScore" placeholder="imdbScore">

        <br><br>
        <input type="submit" value="update">
    </form>
</div>
</body>
</html>

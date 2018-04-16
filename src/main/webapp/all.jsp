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
                    $('#' + id).hide()
                }
            })
        }

        const updateOpen = (id) => {
            $("#update-id").val(id)
        }

        const update = () => {
            $.ajax({
                url: "/MovieList/movies",
                type: "PUT",
                data: JSON.stringify({
                    id: $("#update-id").val(),
                    name: $("#update-name").val(),
                    author: $("#update-author").val(),
                    year: $("#update-year").val(),
                    genre: $("#update-genre").val(),
                    imdbScore: $("#update-rating").val()
                }),
                success: (res) => {
                    location.reload()
                }
            })
        }

        const createMovie = () => {
            $.ajax({
                url: "/MovieList/movies",
                type: "POST",
                data: {
                    name: $("#insert-name").val(),
                    author: $("#insert-author").val(),
                    year: $("#insert-year").val(),
                    genre: $("#insert-genre").val(),
                    imdbScore: $("#insert-rating").val()
                },
                success: (res) => {
                    location.reload()
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
            List<Movie> result= (List) request.getAttribute("allMovies");
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
                out.println(String.format("<td><button class=\"btn btn-danger\" onclick=\"deleteMovie(%d)\">Delete</button>", movie.getId()));
                out.println(String.format("<button class=\"btn btn-warning\" data-toggle=\"modal\" data-target=\"#updateModal\" onclick=updateOpen(%d)>Update</button></td>", movie.getId()));
                out.println("</tr>");
            }
        %>
        </tbody>
    </table>

    <div class="row">
        <div class="col-md-12 text-center">
            <button class="btn btn-success" data-toggle="modal" data-target="#insertModal">Create new</button>
        </div>
    </div>

    <!-- Modal -->
    <div id="updateModal" class="modal fade" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Update Movie</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="update-id">Id: </label>
                        <input type="number" class="form-control" disabled id="update-id" name="id" placeholder="id">
                    </div>
                    <div class="form-group">
                        <label for="update-name">Name: </label>
                        <input type="text" name="name" class="form-control" id="update-name" placeholder="name">
                    </div>
                    <div class="form-group">
                        <label for="update-genre">Genre: </label>
                        <input type="text" name="genre" class="form-control" id="update-genre" placeholder="genre">
                    </div>
                    <div class="form-group">
                        <label for="update-year">Year: </label>
                        <input type="number" name="year" class="form-control" id="update-year" placeholder="year">
                    </div>
                    <div class="form-group">
                        <label for="update-author">Author: </label>
                        <input type="text" name="author" id="update-author" class="form-control" placeholder="author">
                    </div>
                    <div class="form-group">
                        <label for="update-rating">Imdb Rating: </label>
                        <input type="number" name="imdbScore" class="form-control" id="update-rating" placeholder="imdbScore">
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-warning" onclick="update()">Update</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>

    <div id="insertModal" class="modal fade" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Create Movie</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="update-name">Name: </label>
                        <input type="text" name="name" class="form-control" id="insert-name" placeholder="name">
                    </div>
                    <div class="form-group">
                        <label for="update-genre">Genre: </label>
                        <input type="text" name="genre" class="form-control" id="insert-genre" placeholder="genre">
                    </div>
                    <div class="form-group">
                        <label for="update-year">Year: </label>
                        <input type="number" name="year" class="form-control" id="insert-year" placeholder="year">
                    </div>
                    <div class="form-group">
                        <label for="update-author">Author: </label>
                        <input type="text" name="author" id="insert-author" class="form-control" placeholder="author">
                    </div>
                    <div class="form-group">
                        <label for="update-rating">Imdb Rating: </label>
                        <input type="number" name="imdbScore" class="form-control" id="insert-rating" placeholder="imdbScore">
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success" onclick="createMovie()">Create</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>


</div>
</body>
</html>

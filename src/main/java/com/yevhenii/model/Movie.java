package com.yevhenii.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String author;
    private Integer year;
    private String genre;
    private Double imdbScore;

    public Movie() {
    }

    public Movie(Integer id, String name, String author, Integer year, String genre, Double imdbScore) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.imdbScore = imdbScore;
    }

    public Movie(String name, String author, Integer year, String genre, Double imdbScore) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.imdbScore = imdbScore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(Double imdbScore) {
        this.imdbScore = imdbScore;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getName(), movie.getName()) &&
                Objects.equals(getAuthor(), movie.getAuthor()) &&
                Objects.equals(getYear(), movie.getYear()) &&
                Objects.equals(getGenre(), movie.getGenre()) &&
                Objects.equals(getImdbScore(), movie.getImdbScore());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getAuthor(), getYear(), getGenre(), getImdbScore());
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", imdbScore=" + imdbScore +
                '}';
    }
}

package com.example.sam.moviebox.database;

import com.example.sam.moviebox.model.MovieModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import com.example.sam.moviebox.utils.TestUtil;

public class MovieDaoTest extends DatabaseTest {
    @Test
    public void testInsertAndRead() throws InterruptedException{
        List<MovieModel> movieList =new ArrayList<>();
        MovieModel movieModel = TestUtil.createMovie(
                "Bohemian Rhapsody", "\\/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg"
        );
        movieList.add(movieModel);

        movieBoxDatabase.movieDao().insertMovie(movieList);
        List<MovieModel> loadedMovie = getValue(movieBoxDatabase.movieDao()
                .searchMovieByTitle("Bohemian Rhapsody: The Last Jedi"));
        assertThat(loadedMovie, notNullValue());
        assertThat(loadedMovie.get(0).title, is("Bohemian Rhapsody"));
    }

    @Test
    public void testCreatesIfNotExists() throws InterruptedException{
        List<MovieModel> movieList =new ArrayList<>();

        MovieModel movieModel = TestUtil.createMovie(
                "Bohemian Rhapsody", "\\/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg"
        );
        movieList.add(movieModel);

        movieBoxDatabase.movieDao().insertMovie(movieList);

        assertThat(movieBoxDatabase.movieDao()
                .createMovieIfNotExists(movieBoxDatabase), is( -1L));

    }

    @Test void testCreateIfNotExists_doesNotExists() throws InterruptedException{
        MovieModel movieModel = TestUtil.createMovie(
                "Bohemian Rhapsody", "\\/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg"
        );
        assertThat(movieBoxDatabase.movieDao().createMovieIfNotExists(movieModel), is( 1L));
    }
}

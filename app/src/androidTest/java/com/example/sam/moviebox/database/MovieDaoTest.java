package com.example.sam.moviebox.database;

import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.utils.TestUtil;
import static com.example.sam.moviebox.LiveDataTestUtil.getValue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;




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
                .searchMovieByTitle("Bohemian Rhapsody"));
        assertThat(loadedMovie, notNullValue());
        assertThat(loadedMovie.get(0).title, is("Bohemian Rhapsody"));
    }

    @Test
    public void testCreatesIfNotExists() throws InterruptedException{
        List<MovieModel> movieList =new ArrayList<>();

        MovieModel movieModel = TestUtil.createMovie(
                "Bohemian Rhapsody",  "\\/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg"
        );
        movieList.add(movieModel);

        movieBoxDatabase.movieDao().insert(movieModel);

        assertThat(movieBoxDatabase.movieDao()
                .createMovieIfNotExists(movieModel), is( -1L));

    }

    @Test
    public void testCreateIfNotExists_doesNotExists() throws InterruptedException{
        MovieModel movieModel = TestUtil.createMovie(
                "Bohemian Rhapsody", "\\/lHu1wtNaczFPGFDTrjCSzeLPTKN.jpg"
        );
        // why should this be 1L?
        assertThat(movieBoxDatabase.movieDao().createMovieIfNotExists(movieModel), is( 198663L));
    }
}

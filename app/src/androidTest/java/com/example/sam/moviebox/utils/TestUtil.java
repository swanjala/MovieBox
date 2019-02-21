package com.example.sam.moviebox.utils;

import com.example.sam.moviebox.database.MovieBoxDatabase;
import com.example.sam.moviebox.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static MovieModel createMovie(String title, String posterPath) {
        String overView = "Set in a post-apocalyptic world, young Thomas is deposited in a community of boys after his memory is erased, soon learning they're all trapped in a maze that will require him to join forces with fellow “runners” for a shot at escape.";
        String backDropPath = "/lkOZcsXcOLZYeJ2YxJd3vSldvU4.jpg.jpg";
        List<Integer> genreIds = new ArrayList<>();
        genreIds.add(23);

        return new MovieModel(198663, posterPath, 7.8, "2014-09-10", title,
                false, overView, "The Maze Runner", "en", backDropPath,
                732.263205, 6559, false, 7.3, genreIds);
    }

    public static List<MovieModel> getMovieList(){
        List<MovieModel> movieList = new ArrayList<>();
        movieList.add(createMovie("The Maze Runner", "\\/coss7RgL0NH6g4fC2s5atvf3dFO.jpg"));
        movieList.add(createMovie("Jumanji", "\\/47pLZ1gr63WaciDfHCpmoiXJlVr.jpg"));

        return movieList;
    }


}

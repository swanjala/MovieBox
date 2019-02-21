package com.example.sam.moviebox.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;

import com.example.sam.moviebox.model.GenreModel;
import com.example.sam.moviebox.repository.resources.data.Objects;

import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.repository.MovieBoxRepository;
import com.example.sam.moviebox.repository.resources.data.EmptyLiveData;
import com.example.sam.moviebox.repository.resources.utils.Resource;

import javax.inject.Inject;

public class DetailViewModel extends ViewModel {

    @VisibleForTesting
    final MutableLiveData<Integer> movieId = new MutableLiveData<>();

    private final LiveData<Resource<MovieModel>> movie;
    private MovieBoxRepository movieBoxRepository;

    @Inject
    DetailViewModel(MovieBoxRepository movieBoxRepository){
        this.movieBoxRepository = movieBoxRepository;
        movie = Transformations.switchMap(movieId,movieId -> {
            if(movieId == null){
                return EmptyLiveData.create();
            } else{
                return movieBoxRepository.getMovieById(movieId);
            }
        });

    }
    @VisibleForTesting
    public LiveData<Resource<MovieModel>> getMovie(){
        return movie;
    }

    public LiveData<Resource<GenreModel>> getMovieGenresById(int genreId) {
        return movieBoxRepository.getGenresById(genreId);
    }
    @VisibleForTesting
    public void setMovieId(int movieId){
        if(Objects.equals(movieId, this.movieId.getValue())){
            return;
        }

        this.movieId.setValue(movieId);
    }

}

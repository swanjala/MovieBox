package com.example.sam.moviebox.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.sam.moviebox.database.MovieBoxDatabase;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.repository.MovieBoxRepository;
import com.example.sam.moviebox.repository.resources.data.Objects;
import com.example.sam.moviebox.repository.resources.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class MovieViewModel extends ViewModel {

    private final LiveData<Resource<List<MovieModel>>> moviesLiveData;

    @Inject
    MovieViewModel(@NonNull MovieBoxRepository movieBoxRepository){
        moviesLiveData = movieBoxRepository.getAllMovies();

    }
    @VisibleForTesting
    public LiveData<Resource<List<MovieModel>>> getMovies() {
        return moviesLiveData;
    }

    @VisibleForTesting
    static class NextPageHandler implements Observer<Resource<Boolean>> {
        @Nullable
        private LiveData<Resource<Boolean>> nextPageLiveData;
        private final MutableLiveData<LoadMoreState> loadMoreState = new
                MutableLiveData<LoadMoreState>();
        private String query;
        private final MovieBoxRepository movieBoxRepository;

        @VisibleForTesting
        boolean hasMore;

        @VisibleForTesting
        NextPageHandler(MovieBoxRepository repository){
            this.movieBoxRepository = repository;
            reset();
        }
        void queryNextPage(String query){
            if (Objects.equals(this.query, query)){
                return;
            }
            unregister();
            this.query = query;
            nextPageLiveData = movieBoxRepository.searchNextPage(query);
            loadMoreState.setValue(new LoadMoreState(true,null));
            nextPageLiveData.observeForever(this);
        }

        @Override
        public void onChanged(@Nullable Resource<Boolean> result){
            if(result == null){
                reset();
            }else{
                switch(result.status){
                    case SUCCESS:
                        hasMore = Boolean.TRUE.equals(result.data);
                        unregister();
                        loadMoreState.setValue(new LoadMoreState(false, null));
                        break;
                    case ERROR:
                        hasMore = true;
                        unregister();
                        loadMoreState.setValue(new LoadMoreState(false,
                                result.message));
                        break;
                }
            }
        }
        private void unregister(){
            if(nextPageLiveData != null){
                nextPageLiveData.removeObserver(this);
                nextPageLiveData = null;
                if(hasMore){
                    query = null;
                }
            }
        }
        private void reset() {
            unregister();;
            hasMore = true;
            loadMoreState.setValue(new LoadMoreState(false,null));
        }

        MutableLiveData<LoadMoreState> getLoadMoreState(){
            return loadMoreState;
        }

        static class LoadMoreState{
            private final boolean running;
            private final String errorMessage;
            private boolean handledError = false;

            LoadMoreState(boolean running, String errorMessage){
                this.running = running;
                this.errorMessage = errorMessage;
            }
            boolean isRunning(){
                return running;
            }
            String getErrorMessage() {
                return errorMessage;
            }
            String ErrorMessageIfNotHandled() {
                if(handledError){
                    return null;
                }
                handledError = true;
                return errorMessage;
            }
        }

    }

}

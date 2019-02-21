package com.example.sam.moviebox.view;

import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.repository.resources.utils.Resource;
import com.example.sam.moviebox.utils.EspressoTestUtil;
import com.example.sam.moviebox.utils.RecyclerViewMatcher;
import com.example.sam.moviebox.utils.TestUtil;
import com.example.sam.moviebox.utils.ViewModelUtil;
import com.example.sam.moviebox.viewModel.MovieViewModel;
import com.example.sam.moviebox.views.activities.SingleFragmentActivity;
import com.example.sam.moviebox.views.fragments.FragmentDetails;
import com.example.sam.moviebox.views.fragments.FragmentMain;
import com.example.sam.moviebox.views.fragments.FragmentNavigator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.sam.moviebox.utils.MatcherUtil.listMatcher;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class MovieListFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule =
            new ActivityTestRule<SingleFragmentActivity>(SingleFragmentActivity.class,
                    true,true);
    private MovieViewModel movieViewModel;
    private MediatorLiveData<Resource<List<MovieModel>>> result = new MediatorLiveData<>();

    @Before
    public void init() {
        EspressoTestUtil.disableProgressBarAnimation(activityTestRule);
        FragmentDetails fragmentDetails = new FragmentDetails();

        movieViewModel = mock(MovieViewModel.class);
        when(movieViewModel.getMovies()).thenReturn(result);

        fragmentDetails.viewModelFactory = ViewModelUtil.createFor(movieViewModel);

        activityTestRule.getActivity().setFragment(fragmentDetails);
    }
    @Test
    public void testLoadResults() {
        result.postValue(Resource.success(TestUtil.getMovieList()));

        onView(listMatcher().atPosition(0)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));

    }

    @Test
    public void testMovieItemClick() {
        result.postValue(Resource.success(TestUtil.getMovieList()));
        onView(listMatcher().atPosition(0)).check(matches(isDisplayed()));
        onView(withId(R.id.progressbar)).check(matches(not(isDisplayed())));
        onView(listMatcher().atPosition(0)).perform(click());
    }

//    @Test
//    public void testShowError(){
//        result.postValue(Resource.error("Failed to load data", null));
//        onView(withId(R.id.tv_error)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility
//        .VISIBLE)));
//    }

    @NonNull
    private RecyclerViewMatcher listMatcher() {
        return new RecyclerViewMatcher(R.id.rv_main_layout_recyclerView);
    }

}

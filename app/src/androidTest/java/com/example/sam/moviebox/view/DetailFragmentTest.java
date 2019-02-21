package com.example.sam.moviebox.view;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sam.moviebox.R;
import com.example.sam.moviebox.model.MovieModel;
import com.example.sam.moviebox.repository.resources.utils.Resource;
import com.example.sam.moviebox.testing.SingleFragmentActivity;
import com.example.sam.moviebox.utils.EspressoTestUtil;
import com.example.sam.moviebox.utils.ViewModelUtil;
import com.example.sam.moviebox.viewModel.DetailViewModel;
import com.example.sam.moviebox.views.fragments.FragmentDetails;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class DetailFragmentTest {

    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityTestRule =
            new ActivityTestRule<>(SingleFragmentActivity.class,
                    true,true);
    private DetailViewModel detailViewModel;
    private MutableLiveData<Resource<MovieModel>> data = new MutableLiveData<>();

    @Before
    public void init() {
        EspressoTestUtil.disableProgressBarAnimation(activityTestRule);
        FragmentDetails fragmentDetails = FragmentDetails.create(424694);
        detailViewModel = mock(DetailViewModel.class);
        when(detailViewModel.getMovie()).thenReturn(data);
        doNothing().when(detailViewModel).setMovieId(anyInt());

        fragmentDetails.viewModelFactory = ViewModelUtil.createFor(detailViewModel);
        activityTestRule.getActivity().setFragment(fragmentDetails);

    }
    @Test
    public void loading(){
        data.postValue(Resource.loading(null));
        onView(withId(R.id.progress_bar)) .check(matches(isDisplayed()));

    }

//    @Test
//    public void testMovieDetialsShown(){
//        MovieModel movieModel = TestUtil.getMovieList().get(0);
//        data.postValue(Resource.success(movieModel));
//
//        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
//        onView(withId(R.id.tv_title)).check(matches(MatcherUtil.withText(movieModel.title)));
//    }


}

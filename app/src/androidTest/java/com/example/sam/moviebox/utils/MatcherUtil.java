package com.example.sam.moviebox.utils;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class MatcherUtil {

    public static Matcher<View> withText(final String text){

        return new BoundedMatcher<View, TextView>(TextView.class){
            private String resourceName = null;
            private String expectedText = null;

            @Override
            public void describeTo(Description description){
                description.appendText("with string from id:");
                description.appendValue(text);

                if(null != this.expectedText){
                    description.appendText("[");
                    description.appendText(this.resourceName);
                    description.appendText("]");
                }
                if (null != this.expectedText) {
                    description.appendText(" value: ");
                    description.appendText(this.expectedText);
                }
            }
            @Override
            public boolean matchesSafely(TextView textView){
                if(null == this.expectedText){
                    try{
                        this.expectedText = textView.getText()
                                .toString();
                    } catch(Resources.NotFoundException ignored){

                    }
                }
                if(null != this.expectedText){
                    return this.expectedText.equals(textView.getText()
                    .toString());
                }else {
                    return false;
                }
            }
        };
    }
    @NonNull
    public static RecyclerViewMatcher listMatcher(int recyclerId){
        return new RecyclerViewMatcher(recyclerId);
    }
}

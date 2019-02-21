package com.example.sam.moviebox.utils;

import android.arch.core.executor.testing.CountingTaskExecutorRule;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import org.junit.runner.Description;

import java.util.concurrent.CopyOnWriteArrayList;
/*
* Task executor with idling resource rule
* JUnit rule that registers Architecure components background
* threads as an espresso idling*/

public class TaskExecutor extends CountingTaskExecutorRule {
    private CopyOnWriteArrayList<IdlingResource.ResourceCallback>
    callbacks = new CopyOnWriteArrayList<>();

    @Override
    protected void starting(Description description){
        Espresso.registerIdlingResources(new IdlingResource() {
            @Override
            public String getName() {
                return "Components idling resource";
            }

            @Override
            public boolean isIdleNow() {
                return TaskExecutor.this.isIdle();
            }

            @Override
            public void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback) {
                callbacks.add(callback);
            }
        });
        super.starting(description);
    }
    @Override
    protected void onIdle() {
        super.onIdle();
        for(IdlingResource.ResourceCallback callback : callbacks){
            callback.onTransitionToIdle();
        }
    }
}

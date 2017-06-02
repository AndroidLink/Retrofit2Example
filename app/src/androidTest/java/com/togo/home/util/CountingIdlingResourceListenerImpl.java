package com.togo.home.util;

import android.support.test.espresso.idling.CountingIdlingResource;

import com.togo.home.support.util.CountingIdlingResourceListener;

/**
 * Generic implementation of the counting idling resource
 */
public class CountingIdlingResourceListenerImpl implements CountingIdlingResourceListener {

    private final CountingIdlingResource mCountingIdlingResource;

    public CountingIdlingResourceListenerImpl(final String idlingResourceName) {
        mCountingIdlingResource = new CountingIdlingResource(idlingResourceName);
    }

    public CountingIdlingResource getCountingIdlingResource() {
        return mCountingIdlingResource;
    }

    @Override
    public void increment() {
        mCountingIdlingResource.increment();
    }

    @Override
    public void decrement() {
        mCountingIdlingResource.decrement();
    }
}

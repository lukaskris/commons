package com.evodream.app.accountcommons.base;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author Erick Pranata
 * @since 2017/08/02
 */

public abstract class BaseViewModel<S extends Context, T> extends BaseObservable {
    @NonNull
    protected T mNavigator;

    @NonNull
    protected Context mContext;

    @NonNull
    private List<Observable> mObservables;

    // Empty constructor is not allowed
    private BaseViewModel() {}

    protected BaseViewModel(@NonNull S context, @NonNull T navigator) {
        mContext = context;
    	mNavigator = navigator;
        mObservables = new ArrayList<>();
    }

    protected void onPause() {
        unsubscribeObservables();
    }

    final protected void addObservable(Observable observable) {
        mObservables.add(observable);
    }

    private void unsubscribeObservables() {
        for (Observable observable : mObservables) {
            observable.unsubscribeOn(AndroidSchedulers.mainThread());
        }
    }
}

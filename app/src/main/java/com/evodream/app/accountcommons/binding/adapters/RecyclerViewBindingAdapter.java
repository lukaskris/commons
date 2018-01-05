package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.evodream.app.accountcommons.base.BaseSingleLayoutAdapter;
import com.evodream.app.accountcommons.base.BaseViewModel;
import com.evodream.app.accountcommons.util.EndlessRecyclerViewScrollListener;


/**
 * @author Lukaskris
 * @since 2017/08/30
 */

public class RecyclerViewBindingAdapter {

    @SuppressWarnings("unchecked")
    @BindingAdapter({ "items", "itemLayout" })
    public static void setItems(RecyclerView recyclerView, ObservableList items, Integer resItemLayout) {
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(new BaseSingleLayoutAdapter(resItemLayout, items));
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({ "items", "itemLayout", "viewModel" })
    public static void setItems(RecyclerView recyclerView, ObservableList redeems, Integer resItemLayout, BaseViewModel viewModel) {
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(new BaseSingleLayoutAdapter(resItemLayout, redeems, viewModel));
        }
    }

    @SuppressWarnings("WeakerAccess")
    public interface OnScrollListener {
        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    @BindingAdapter("onScroll")
    public static void setScrollListener(RecyclerView view, final OnScrollListener onScrollListener){
        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onScrollListener.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @BindingAdapter("onLoadMore")
    public static void setScrollListener(RecyclerView view, EndlessRecyclerViewScrollListener.OnEndlessScrollListener endlessScrollListener){
        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(view.getLayoutManager(), endlessScrollListener);
        view.addOnScrollListener(endlessRecyclerViewScrollListener);
        view.setTag(endlessRecyclerViewScrollListener);
    }

    @BindingAdapter({ "items", "itemLayout", "viewModel", "layoutManager", "span", "onLoadMore" })
    public static void setItems(RecyclerView recyclerView, ObservableList redeems, Integer resItemLayout, BaseViewModel viewModel, String layoutManager, int span, EndlessRecyclerViewScrollListener.OnEndlessScrollListener endlessScrollListener) {
        if (recyclerView.getAdapter() == null) {
            if(layoutManager.equals("GridLayoutManager")){
                GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), span);
                recyclerView.setLayoutManager(gridLayoutManager);
            }else{
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            }
            recyclerView.setAdapter(new BaseSingleLayoutAdapter(resItemLayout, redeems, viewModel));
            EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(recyclerView.getLayoutManager(), endlessScrollListener);
            recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
            recyclerView.setTag(endlessRecyclerViewScrollListener);
        }
    }

    @BindingAdapter("needRefreshLoadMore")
    public static void setNeedRefreshLoadMore(RecyclerView recyclerView, Boolean needRefreshLoadMore) {
        if (needRefreshLoadMore) {
            Object tag = recyclerView.getTag();
            if (tag != null && tag instanceof EndlessRecyclerViewScrollListener) {
                ((EndlessRecyclerViewScrollListener) tag).resetState();
            }
        }
    }

    @BindingAdapter("enableNestedScrolling")
    public static void setEnableNestedScrolling(RecyclerView recyclerView, boolean isNestedScrollingEnabled) {
        recyclerView.setNestedScrollingEnabled(isNestedScrollingEnabled);
        recyclerView.setHasFixedSize(true);
    }
}

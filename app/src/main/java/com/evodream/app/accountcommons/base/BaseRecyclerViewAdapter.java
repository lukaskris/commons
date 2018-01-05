package com.evodream.app.accountcommons.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * @author Erick Pranata
 * @since 2017/08/03
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    @Override
    public BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        BaseViewModel viewModel = getViewModel();
        if (viewModel == null) {
            return new BaseViewHolder<>(viewDataBinding);
        } else {
            return new BaseViewHolder<>(viewDataBinding, viewModel);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        T obj = getListItemForPosition(position);
        holder.bind(obj);
    }

    @Override
    public int getItemCount() {
        return getListSize();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    protected abstract T getListItemForPosition(int position);
    protected abstract int getLayoutIdForPosition(int position);
    protected abstract int getListSize();
    protected abstract BaseViewModel getViewModel();
}

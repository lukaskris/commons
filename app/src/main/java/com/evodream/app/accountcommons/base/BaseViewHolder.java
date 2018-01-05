package com.evodream.app.accountcommons.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.evodream.app.accountcommons.BR;

/**
 * @author Erick Pranata
 * @since 2017/08/03
 */

@SuppressWarnings("WeakerAccess")
public class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;
    private final BaseViewModel viewModel;

    public BaseViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());
        this.binding = viewDataBinding;
        this.viewModel = null;
    }

    public BaseViewHolder(ViewDataBinding viewDataBinding, BaseViewModel viewModel) {
        super(viewDataBinding.getRoot());
        this.binding = viewDataBinding;
        this.viewModel = viewModel;
    }

    public void bind(T modelItem) {
        binding.setVariable(BR.modelItem, modelItem);
        if (viewModel != null) {
            binding.setVariable(BR.viewModel, viewModel);
        }
        binding.executePendingBindings();
    }
}

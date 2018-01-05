package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * @author Erick Pranata
 * @since 2017/08/31
 */

public class ViewAdapter {

    @BindingAdapter("onLongClick")
    public static void setOnLongClick(View view, View.OnLongClickListener onLongClickListener) {
        view.setOnLongClickListener(onLongClickListener);
    }

}

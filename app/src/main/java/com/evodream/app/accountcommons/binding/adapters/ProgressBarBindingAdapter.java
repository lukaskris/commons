package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.widget.ProgressBar;

/**
 * @author Erick Pranata
 * @since 2017/11/09
 */

public class ProgressBarBindingAdapter {
    @BindingAdapter("color")
    public static void setColor(ProgressBar progressBar, @ColorInt int color) {
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
}

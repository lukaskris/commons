package com.evodream.app.accountcommons.binding.adapters.helper;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * @author Erick Pranata
 * @since 2017/11/07
 */

public class ImageViewBindingAdapter {
    @BindingAdapter("src")
    public static void setSrc(ImageView view, @DrawableRes int drawable) {
        view.setImageResource(drawable);
    }
}

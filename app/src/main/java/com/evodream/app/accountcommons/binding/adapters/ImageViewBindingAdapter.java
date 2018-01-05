package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.evodream.app.accountcommons.R;
import com.evodream.app.accountcommons.util.ImageUtil;
import com.squareup.picasso.Picasso;

/**
 * @author Erick Pranata
 * @since 2017/08/31
 */

public class ImageViewBindingAdapter {

    @BindingAdapter("image")
    public static void setImage(ImageView imageView, String uri) {
        if (uri != null && !uri.isEmpty()) {
            ImageUtil.load(imageView.getContext(), Uri.parse(uri), imageView);
        }
    }

    @BindingAdapter("image")
    public static void setImage(ImageView imageView, @DrawableRes int drawableResId) {
        ImageUtil.load(imageView.getContext(), drawableResId, imageView);
    }

    @BindingAdapter("imageFit")
    public static void setImageFit(ImageView imageView, @DrawableRes int drawableResId) {
        Picasso.get().load(drawableResId).placeholder(R.drawable.placeholder).fit().centerInside().into(imageView);
    }

    @BindingAdapter("imageFit")
    public static void setImageFit(ImageView imageView, String uri) {
        if (uri != null && !uri.isEmpty()) {
            Picasso.get().load(Uri.parse(uri)).placeholder(R.drawable.placeholder).fit().centerInside().into(imageView);
        }
    }

    @BindingAdapter({ "imageFit", "backgroundColor" })
    public static void setImageFit(ImageView imageView, String uri, int color) {
        if (uri != null && !uri.isEmpty()) {
            Picasso.get().load(Uri.parse(uri)).placeholder(R.drawable.placeholder).fit().centerInside().into(imageView);
            imageView.setBackgroundColor(color);
        }
    }

    @BindingAdapter({"imageFit", "default"})
    public static void setImageFit(ImageView imageView, String uri, Drawable drawable) {
        if (uri != null && !uri.isEmpty()) {
            Picasso.get().load(Uri.parse(uri)).placeholder(R.drawable.placeholder).fit().centerInside().into(imageView);
        } else {
            imageView.setImageDrawable(drawable);
        }
    }

    @BindingAdapter({"image", "default"})
    public static void setImage(ImageView imageView, String uri, Drawable drawable) {
        if (uri != null && !uri.isEmpty()) {
            ImageUtil.load(imageView.getContext(), Uri.parse(uri), imageView);
        } else {
            imageView.setImageDrawable(drawable);
        }
    }
}

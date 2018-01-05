package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;

import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * @author Erick Pranata
 * @since 2017/08/31
 */

public class ShimmerLayoutBindingAdapter {

    @BindingAdapter("loading")
    public static void setLoading(ShimmerFrameLayout shimmerFrameLayout, Boolean loading) {
        if (loading) {
            shimmerFrameLayout.startShimmerAnimation();
        } else {
            shimmerFrameLayout.stopShimmerAnimation();
        }
    }

}

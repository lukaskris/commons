package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.widget.TextView;

/**
 * @author Erick Pranata
 * @since 2017/11/13
 */

public class TextViewBindingAdapter {
    @BindingAdapter("strikethrough")
    public static void setStrikethrough(TextView textView, boolean shouldStrikethrough) {
        if (shouldStrikethrough) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}

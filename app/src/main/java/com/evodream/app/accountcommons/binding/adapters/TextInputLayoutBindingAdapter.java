package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputLayout;

/**
 * @author Erick Pranata
 * @since 2017/10/23
 */

public class TextInputLayoutBindingAdapter {
    @BindingAdapter("errorMessage")
    public static void setErrorMessage(TextInputLayout textInputLayout, String message) {
        if (message != null && !message.isEmpty()) textInputLayout.setError(message);
        else textInputLayout.setErrorEnabled(false);
    }
}

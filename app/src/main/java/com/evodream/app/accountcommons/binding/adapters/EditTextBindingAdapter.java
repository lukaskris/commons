package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.EditText;

import com.evodream.app.accountcommons.util.NumberTextWatcherUtil;

import java.math.BigDecimal;

/**
 * @author Erick Pranata
 * @since 2017/10/26
 */

public class EditTextBindingAdapter {
    @BindingAdapter("maxValue")
    public static void setMaxValue(EditText editText, String maxValue) {
        editText.addTextChangedListener(new NumberTextWatcherUtil(editText, maxValue == null || maxValue.isEmpty() ? null : new BigDecimal(maxValue)));
    }

    @BindingAdapter("onBlur")
    public static void setOnBlur(EditText editText, final View.OnFocusChangeListener listener) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) listener.onFocusChange(view, false);
            }
        });
    }
}

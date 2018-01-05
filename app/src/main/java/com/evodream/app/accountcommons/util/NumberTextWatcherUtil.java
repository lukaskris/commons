package com.evodream.app.accountcommons.util;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;

/**
 * @author Erick Pranata
 * @since 2017/07/11
 */

public class NumberTextWatcherUtil implements TextWatcher {

    private EditText editText;
    private String lastText;
    private BigDecimal maxValue;

    public NumberTextWatcherUtil(EditText editText, @Nullable BigDecimal maxValue) {
        this.editText = editText;
        this.maxValue = maxValue;
        lastText = "";
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);

        Integer intValue = null;

        if (s.toString().length() <= 11) {
            String value = DataUtil.getCleanString(editText);
            if (!value.equals("") && lastText.length() <= 10) {
                if (value.startsWith("0")) {
                    editText.setText("");
                } else if (value.equals("-") || value.startsWith("-0")) {
                    editText.setText("-");
                    editText.setSelection(editText.getText().toString().length());
                } else {
                    lastText = value;
                    intValue = Integer.parseInt(stripThousandSeparator(value));
                }

            } else if (!value.isEmpty()) {
                intValue = Integer.parseInt(stripThousandSeparator(value));
            }
        } else {
            intValue = Integer.parseInt(stripThousandSeparator(lastText));
        }

        if (intValue != null) {
            if (maxValue != null) {
                if (intValue > maxValue.intValue()) intValue = maxValue.intValue();
            }
            editText.setText(StringUtil.formatDecimal(new BigDecimal(intValue)));
            editText.setSelection(editText.getText().toString().length());
        }

        editText.addTextChangedListener(this);

    }

    public static String stripThousandSeparator(String string) {
        return string.replaceAll("[,.]", "");
    }
}

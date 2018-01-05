package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * @author Erick Pranata
 * @since 2017/08/30
 */

public class AutoCompleteTextViewBindingAdapter {

    @BindingAdapter("items")
    public static void setItems(AutoCompleteTextView autoCompleteTextView, ObservableList items) {
        //noinspection unchecked
        autoCompleteTextView.setAdapter(new ArrayAdapter(autoCompleteTextView.getContext(), android.R.layout.simple_dropdown_item_1line, items));
    }

    @BindingAdapter("onItemClick")
    public static void setOnItemClickListener(AutoCompleteTextView autoCompleteTextView, AdapterView.OnItemClickListener listener) {
        autoCompleteTextView.setOnItemClickListener(listener);
    }
}

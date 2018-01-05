package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.evodream.app.accountcommons.R;


/**
 * @author Erick Pranata
 * @since 2017/08/30
 */

public class SpinnerBindingAdapter {

    public interface OnItemSelectedListener {
        void onItemSelected(AdapterView<?> parent, View view, int position, long id);
    }

    @BindingAdapter("onItemSelected")
    public static void setItems(Spinner spinner, final OnItemSelectedListener onItemSelectedListener) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onItemSelectedListener.onItemSelected(adapterView, view, i, l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });
    }

    @BindingAdapter("items")
    public static void setItems(Spinner spinner, ObservableList items) {
        //noinspection unchecked
        ArrayAdapter adapter = new ArrayAdapter(spinner.getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @BindingAdapter({ "items", "default" })
    public static void setItems(Spinner spinner, ObservableList items, Object defaultItem) {
        //noinspection unchecked
        ArrayAdapter adapter = new ArrayAdapter(spinner.getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setTag(R.string.binding_tag_spinner_items, items);
        spinner.setTag(R.string.binding_tag_spinner_default_item, defaultItem);
        if (defaultItem != null) {
            int index = items.indexOf(defaultItem);
            if (index >= 0) {
                spinner.setSelection(index + 1);
            }
        }
    }

    @BindingAdapter("selectedItem")
    public static void setSelectedItem(Spinner spinner, Object selectedItem) {
        SpinnerAdapter spinnerAdapter = spinner.getAdapter();
        if (selectedItem != null && spinnerAdapter != null) {
            for (int i = 0; i < spinnerAdapter.getCount(); i++) {
                if (spinnerAdapter.getItem(i).equals(selectedItem)) {
                    spinner.setSelection(i);
                    return;
                }
            }
        }
    }

    @BindingAdapter("needReset")
    public static void setNeedReset(Spinner spinner, boolean needReset) {
        if (needReset) {
            Object defaultItem = spinner.getTag(123);
            ObservableList items = (ObservableList) spinner.getTag(456);
            if (defaultItem != null) {
                int index = items.indexOf(defaultItem);
                if (index >= 0) {
                    spinner.setSelection(index + 1);
                }
            }
        }
    }
}

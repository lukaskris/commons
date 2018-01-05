package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.view.View;

import com.evodream.app.accountcommons.base.BaseBindingHandler;

/**
 * @author Erick Pranata
 * @since 2017/10/18
 */

public class ViewBindingAdapter {
    @BindingAdapter("multiSelectionHandler")
    public static void setMultiSelectionHandler(View view, final MultiSelectionHandler handler) {
        if (handler != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return handler.onLongClick(view);
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handler.onClick(view);
                }
            });
        }else {
            view.setOnLongClickListener(null);
            view.setOnClickListener(null);
        }
    }

    @BindingAdapter("tag")
    public static void setTag(View view, Object object) {
        view.setTag(object);
    }

    public interface MultiSelectionHandler extends BaseBindingHandler {
        boolean onLongClick(View view);
        void onClick(View view);
    }

    @BindingAdapter("selected")
    public static void setSelected(View view, boolean selected) {
        view.setSelected(selected);
    }
}

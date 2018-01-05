package com.evodream.app.accountcommons.base;

import android.databinding.ObservableList;

/**
 * @author Erick Pranata
 * @since 2017/08/03
 */

@SuppressWarnings("WeakerAccess")
public class BaseSingleLayoutAdapter<T> extends BaseRecyclerViewAdapter<T> {

    private final int layoutId;
    private final ObservableList<T> list;
    private final BaseViewModel viewModel;

    public BaseSingleLayoutAdapter(int layoutId, ObservableList<T> list) {
        this(layoutId, list, null);
    }

    public BaseSingleLayoutAdapter(int layoutId, ObservableList<T> list, BaseViewModel viewModel) {
        this.layoutId = layoutId;
        this.list = list;
        this.viewModel = viewModel;

        this.list.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> sender) {
                BaseSingleLayoutAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                BaseSingleLayoutAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                BaseSingleLayoutAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                BaseSingleLayoutAdapter.this.notifyItemMoved(fromPosition, toPosition);

            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                BaseSingleLayoutAdapter.this.notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @Override
    protected T getListItemForPosition(int position) {
        return list.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    @Override
    protected int getListSize() {
        return list.size();
    }

    @Override
    protected BaseViewModel getViewModel() {
        return viewModel;
    }
}

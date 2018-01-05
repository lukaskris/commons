package com.evodream.app.accountcommons.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evodream.app.accountcommons.BR;
import java.util.List;

/**
 * @author Erick Pranata
 * @since 2017/10/23
 */

public class BasePagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final int mLayoutResId;
    private final List mItems;
    private final BaseBindingHandler mHandler;
    private List<String> mTitles;

    public BasePagerAdapter(Context context, int layoutResId, List items) {
        this(context, layoutResId, items, null);
    }

    public BasePagerAdapter(Context context, int layoutResId, List items, BaseBindingHandler handler) {
        mContext = context;
        mLayoutResId = layoutResId;
        mItems = items;
        mHandler = handler;
        mTitles = null;
    }

    public void setTitles(List<String> titles) {
        this.mTitles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? "" : mTitles.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutResId, container, false);
        container.addView(view);
        ViewDataBinding bind = DataBindingUtil.bind(view);
        bind.setVariable(BR.modelItem, mItems.get(position));
        if (mHandler != null) {
            bind.setVariable(BR.handler, mHandler);
        }
        bind.executePendingBindings();
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

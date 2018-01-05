package com.evodream.app.accountcommons.binding.adapters;

import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.evodream.app.accountcommons.base.BaseBindingHandler;
import com.evodream.app.accountcommons.base.BasePagerAdapter;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * @author Erick Pranata
 * @since 2017/10/23
 */

public class ViewPagerBindingAdapter {
    @BindingAdapter({"items", "itemLayout", "indicator"})
    public static void setItems(ViewPager viewPager, List items, int layoutResId, CircleIndicator circleIndicator) {
        BasePagerAdapter basePagerAdapter = new BasePagerAdapter(viewPager.getContext(), layoutResId, items);
        viewPager.setAdapter(basePagerAdapter);
        if (circleIndicator != null) circleIndicator.setViewPager(viewPager);
    }

    @BindingAdapter({"items", "itemLayout", "indicator", "handler"})
    public static void setItems(ViewPager viewPager, List items, int layoutResId, CircleIndicator circleIndicator, BaseBindingHandler handler) {
        BasePagerAdapter basePagerAdapter = new BasePagerAdapter(viewPager.getContext(), layoutResId, items, handler);
        viewPager.setAdapter(basePagerAdapter);
        if (circleIndicator != null) circleIndicator.setViewPager(viewPager);
    }

    @BindingAdapter({ "tabLayout", "titles" })
    public static void setItems(ViewPager viewPager, TabLayout tabLayout, List<String> titles) {
        ((BasePagerAdapter) viewPager.getAdapter()).setTitles(titles);
        tabLayout.setupWithViewPager(viewPager);
    }
}

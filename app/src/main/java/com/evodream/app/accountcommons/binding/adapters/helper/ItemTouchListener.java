package com.evodream.app.accountcommons.binding.adapters.helper;

import com.evodream.app.accountcommons.base.BaseBindingHandler;

/**
 * @author Erick Pranata
 * @since 2017/10/31
 */

public interface ItemTouchListener extends BaseBindingHandler {
    void onMovingItem(int fromPosition, int toPosition);
    void onDismissingItem(int position);
}

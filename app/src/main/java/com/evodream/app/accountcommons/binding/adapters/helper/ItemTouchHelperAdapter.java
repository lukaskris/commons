package com.evodream.app.accountcommons.binding.adapters.helper;

/**
 * @author lukaskris
 * @since 2017/10/30
 */

public interface ItemTouchHelperAdapter {
	boolean onItemMove(int fromPosition, int toPosition);
	void onItemDismiss(int position);
}

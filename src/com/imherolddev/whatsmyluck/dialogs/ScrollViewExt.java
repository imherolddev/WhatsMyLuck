package com.imherolddev.whatsmyluck.dialogs;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 
 * @author imherolddev
 *
 */
public class ScrollViewExt extends ScrollView {

	private ScrollViewListener scrollViewListener = null;

	/**
	 * Explicit constructor
	 * @param context - the context to set
	 */
	public ScrollViewExt(Context context) {
		super(context);
	}

	/**
	 * Explicit constructor
	 * @param context - the context to set
	 * @param attrs - the attrs to set
	 */
	public ScrollViewExt(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollViewExt(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/**
	 * Explicit constructor
	 * @param listener - the listener to set
	 */
	public void setScrollViewListener(ScrollViewListener listener) {
		
		this.scrollViewListener = listener;
		
	}

	/**
	 * Override abstract method
	 * @param l - the l to set
	 * @param t - the t to set
	 * @param oldl - the oldl to set
	 * @param oldt - the oldt to set
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {

		super.onScrollChanged(l, t, oldl, oldt);

		if (scrollViewListener != null) {

			scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);

		}

	}

}

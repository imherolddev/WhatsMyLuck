package com.imherolddev.whatsmyluck.dialogs;

/**
 * 
 * @author imherolddev
 *
 */
public interface ScrollViewListener {
	
	/**
	 * Method to handle onScrollChanged events
	 * @param scrollView - the scrollView to set
	 * @param x - the x to set
	 * @param y - the y to set
	 * @param oldx - the oldx to set
	 * @param oldy - the oldy to set
	 */
	abstract void onScrollChanged(ScrollViewExt scrollView,
			int x, int y, int oldx, int oldy);

}

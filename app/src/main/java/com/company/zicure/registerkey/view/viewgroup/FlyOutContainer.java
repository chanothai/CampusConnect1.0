package com.company.zicure.registerkey.view.viewgroup;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.company.zicure.registerkey.R;

public class FlyOutContainer extends LinearLayout {

	// References to groups contained in this view.
	private View menu;
	private View content;

	//View
	private FrameLayout layoutGhost = null;
	private RelativeLayout layoutMenu = null;
	private FrameLayout controlSlide = null;
	//
	public static int checkMenu = 0;

	// Layout Constants
	protected static final int menuMargin = 150;

	public enum MenuState {
		CLOSED, OPEN, CLOSING, OPENING
	}


	// Position information attributes
	protected int currentContentOffset = 0;
	protected MenuState menuCurrentState = MenuState.CLOSED;

	// Animation objects
	protected Scroller menuAnimationScroller = new Scroller(this.getContext(),
			new DecelerateInterpolator());
	protected Runnable menuAnimationRunnable = new AnimationRunnable();
	protected Handler menuAnimationHandler = new Handler();

	// Animation constants
	private static final int menuAnimationDuration = 400;
	private static final int menuAnimationPollingInterval = 2;

	public FlyOutContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlyOutContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlyOutContainer(Context context) {
		super(context);

	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		this.menu = this.getChildAt(0);
		this.content = this.getChildAt(1);

		//bind view
		controlSlide = (FrameLayout) content.findViewById(R.id.control_slide);
		layoutGhost = (FrameLayout) content.findViewById(R.id.layout_ghost);
		layoutMenu = (RelativeLayout) menu.findViewById(R.id.layout_menu);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
							int bottom) {
		if (changed)
			this.calculateChildDimensions();

		this.menu.layout(left, top, right - menuMargin, bottom);

		this.content.layout(left + this.currentContentOffset, top, right
				+ this.currentContentOffset, bottom);

	}

	private void openning(){
		this.menuCurrentState = MenuState.OPENING; //Content is opening
		checkMenu = 1;
		layoutGhost.setVisibility(View.VISIBLE);
	}

	private void closing(){
		this.menuCurrentState = MenuState.CLOSING;
		checkMenu = 0;
		layoutGhost.setVisibility(View.GONE);
	}

	public void toggleMenu(int width) {
		switch (this.menuCurrentState) {
			case CLOSED:
				controlSlide.setEnabled(true);
				openning();
				if (width > 0){
					this.menuAnimationScroller.startScroll(0, 0, width,
							0, menuAnimationDuration);
				}else{
					this.menuAnimationScroller.startScroll(0, 0, this.getMenuWidth(),
							0, menuAnimationDuration);
				}
				break;
			case OPEN:
				closing();
				if (width > 0){
					this.menuAnimationScroller.startScroll(width,
							0, width, 0, menuAnimationDuration);
				}
				else{
					this.menuAnimationScroller.startScroll(this.currentContentOffset,
							0, -this.currentContentOffset, 0, menuAnimationDuration);
				}

				break;
			default:
				return;
		}

		this.menuAnimationHandler.postDelayed(this.menuAnimationRunnable,
				menuAnimationPollingInterval);

		this.invalidate();
	}

	private int getMenuWidth() {
		return this.menu.getLayoutParams().width;
	}

	private void calculateChildDimensions() {
		this.content.getLayoutParams().height = this.getHeight();
		this.content.getLayoutParams().width = this.getWidth();

		this.menu.getLayoutParams().width = this.getWidth() - menuMargin;
		this.menu.getLayoutParams().height = this.getHeight();
	}

	private void adjustContentPosition(boolean isAnimationOngoing) {
		int scrollerOffset = this.menuAnimationScroller.getCurrX();

		this.content.offsetLeftAndRight(scrollerOffset
				- this.currentContentOffset);

		this.currentContentOffset = scrollerOffset;

		this.invalidate();

		if (isAnimationOngoing)
			this.menuAnimationHandler.postDelayed(this.menuAnimationRunnable,
					menuAnimationPollingInterval);
		else
			this.onMenuTransitionComplete();
	}

	private void onMenuTransitionComplete() {
		switch (this.menuCurrentState) {
			case OPENING:
				this.menuCurrentState = MenuState.OPEN;
				break;
			case CLOSING:
				this.menuCurrentState = MenuState.CLOSED;
				break;
			default:
				return;
		}
	}

	protected class AnimationRunnable implements Runnable {

		@Override
		public void run() {
			FlyOutContainer.this
					.adjustContentPosition(FlyOutContainer.this.menuAnimationScroller
							.computeScrollOffset());
		}

	}


}

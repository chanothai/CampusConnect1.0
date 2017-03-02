package com.company.zicure.registerkey.view.viewgroup;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class FlyOutContainer extends LinearLayout {

	// References to groups contained in this view.
	private View menu;
	private View content;

	//
	public static int checkMenu = 0;

	// Layout Constants
	protected static final int menuMargin = 128;

	public enum MenuState {
		CLOSED, OPEN, CLOSING, OPENING
	};

	// Position information attributes
	protected int currentContentOffset = 0;
	protected MenuState menuCurrentState = MenuState.CLOSED;

	// Animation objects
	protected Scroller menuAnimationScroller = new Scroller(this.getContext(),
			new LinearInterpolator());
	//	protected Scroller menuAnimationScroller = new Scroller(this.getContext(),
//			new SmoothInterpolator());
	protected Runnable menuAnimationRunnable = new AnimationRunnable();
	protected Handler menuAnimationHandler = new Handler();

	// Animation constants
	private static final int menuAnimationDuration = 200;
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
		this.menu.setVisibility(View.INVISIBLE);
		this.content = this.getChildAt(1);
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

	public void scrollContent(){
		switch (this.menuCurrentState){

		}
	}

	public void toggleMenu() {
		switch (this.menuCurrentState) {
			case CLOSED:
				this.menu.setVisibility(View.VISIBLE);
				this.menuCurrentState = MenuState.OPENING; //Content is opening
				this.menuAnimationScroller.startScroll(0, 0, this.getMenuWidth(),
						0, menuAnimationDuration);
				checkMenu = 1;
				break;
			case OPEN:
				this.menuCurrentState = MenuState.CLOSING;
				this.menuAnimationScroller.startScroll(this.currentContentOffset,
						0, -this.currentContentOffset, 0, menuAnimationDuration);
				checkMenu = 0;
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
				this.menu.setVisibility(View.INVISIBLE);
				this.menuCurrentState = MenuState.CLOSED;
				break;
			default:
				return;
		}
	}

	public void setOnClickContent(){
		this.content.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
	}

	protected class SmoothInterpolator implements Interpolator{

		@Override
		public float getInterpolation(float t) {
			return (float)Math.pow(t-1, 5) + 1;
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

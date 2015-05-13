package org.smarty.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.LinkedList;

/**
 * 仿Launcher中的WorkSapce，可以左右滑动切换屏幕的类
 */
public class WorkSpace extends ViewGroup {
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_SCROLLING = 1;
    private static final int SNAP_VELOCITY = 600;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private int mCurScreen;
    private int mTouchState = TOUCH_STATE_REST;
    private int mTouchSlop;
    private float mLastMotionX;
    private LinkedList<OnScrollCompleteListener> listeners;

    public WorkSpace(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        mCurScreen = 0;
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        listeners = new LinkedList<OnScrollCompleteListener>();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        for (int i = 0, len = getChildCount(); i < len; i++) {
            View view = getChildAt(i);
            if (view.getVisibility() == View.GONE) {
                continue;
            }
            int width = view.getMeasuredWidth();
            int right = left + width;
            view.layout(left, 0, right, view.getMeasuredHeight());
            left += width;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        if (widthMode != MeasureSpec.EXACTLY) {
//            throw new IllegalStateException("ScrollLayout only can CurScreen run at EXACTLY mode!");
//        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        if (heightMode != MeasureSpec.EXACTLY) {
//            throw new IllegalStateException("ScrollLayout only can run at EXACTLY mode!");
//        }

        for (int i = 0, len = getChildCount(); i < len; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
        scrollTo(mCurScreen * width, 0);
    }

    /**
     * According to the position of current layout
     * scroll to the destination page.
     */
    public void snapToDestination() {
        int sw = getWidth();
        int ds = (getScrollX() + sw / 2) / sw;
        snapToScreen(ds);
    }

    public void snapToScreen(int whichScreen) {
        // get the valid layout page
        int ws = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        if (getScrollX() != (ws * getWidth())) {
            int delta = ws * getWidth() - getScrollX();
            mScroller.startScroll(getScrollX(), 0,
                    delta, 0, Math.abs(delta) * 2);

            if (mCurScreen != ws) {
                notifyEvent(ws);
            }
            mCurScreen = ws;
            invalidate();
        }
    }

    public void setToScreen(int whichScreen) {
        int ws = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        mCurScreen = ws;
        scrollTo(ws * getWidth(), 0);
        notifyEvent(ws);
        invalidate();
    }

    public int getCurScreen() {
        return mCurScreen;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        touchEventAction(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_MOVE && mTouchState != TOUCH_STATE_REST) {
            return true;
        }
        interceptTouchEventAction(ev);

        return mTouchState != TOUCH_STATE_REST;
    }

    public void setOnScrollCompleteLinstenner(OnScrollCompleteListener listener) {
        listeners.add(listener);
    }

    private void notifyEvent(int curScreen) {
        for (OnScrollCompleteListener l : listeners) {
            l.onScrollComplete(curScreen);
        }
    }

    private void touchEventAction(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (mLastMotionX - x);
                mLastMotionX = x;
                scrollBy(deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                // if (mTouchState == TOUCH_STATE_SCROLLING) {
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                int velocityX = (int) velocityTracker.getXVelocity();
                if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {
                    // Fling enough to move left
                    snapToScreen(mCurScreen - 1);
                } else if (velocityX < -SNAP_VELOCITY
                        && mCurScreen < getChildCount() - 1) {
                    // Fling enough to move right
                    snapToScreen(mCurScreen + 1);
                } else {
                    snapToDestination();

                }

                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                // }
                mTouchState = TOUCH_STATE_REST;
                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
    }

    private void interceptTouchEventAction(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                int xDiff = (int) Math.abs(mLastMotionX - x);
                if (xDiff > mTouchSlop) {
                    mTouchState = TOUCH_STATE_SCROLLING;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                this.postInvalidate();
                mLastMotionX = x;
                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                this.postInvalidate();
                mTouchState = TOUCH_STATE_REST;
                break;
        }
    }

    public interface OnScrollCompleteListener {
        void onScrollComplete(int curScreen);

    }
}

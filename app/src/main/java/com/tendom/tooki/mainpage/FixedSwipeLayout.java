package com.tendom.tooki.mainpage;

import android.content.Context;
import android.util.AttributeSet;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jihaeseong on 15. 12. 14..
 */
public class FixedSwipeLayout extends SwipeLayout {

    private List<SwipeListener> swipeListeners;

    public FixedSwipeLayout(Context context) {
        super(context);
        swipeListeners = new ArrayList<>();
    }

    public FixedSwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        swipeListeners = new ArrayList<>();
    }

    public FixedSwipeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        swipeListeners = new ArrayList<>();
    }

    @Override
    public void addSwipeListener(SwipeListener listener) {
        for (SwipeListener l : swipeListeners) {
            removeSwipeListener(l);
        }
        swipeListeners.clear();

        swipeListeners.add(listener);
        super.addSwipeListener(listener);
    }
}
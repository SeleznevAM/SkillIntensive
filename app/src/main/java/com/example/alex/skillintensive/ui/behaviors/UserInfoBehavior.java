package com.example.alex.skillintensive.ui.behaviors;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.alex.skillintensive.R;
import com.example.alex.skillintensive.util.UiHelper;

/**
 * Created by Alex on 11.09.2016.
 */
public class UserInfoBehavior extends AppBarLayout.ScrollingViewBehavior {
    private final int mMaxAppBarHeight;
    private final int mMinAppBarHeight;
    private final int mMaxUserInfoHeight;
    private final int mMinUserInfoHeight;

    public UserInfoBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UserInfoBehavior);
        mMinUserInfoHeight = a.getDimensionPixelSize(R.styleable.UserInfoBehavior_behavior_min_heigth, 56);
        a.recycle();
        mMinAppBarHeight = UiHelper.getStatusBarHeight() + UiHelper.getActionBarHeight();
        mMaxAppBarHeight = context.getResources().getDimensionPixelSize(R.dimen.profile_image_size_256);
        mMaxUserInfoHeight = context.getResources().getDimensionPixelSize(R.dimen.user_info_wrapper_size_112);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        float currentFriction = UiHelper.currentFriction(mMinAppBarHeight, mMaxAppBarHeight, dependency.getBottom());
        int currentHeight = UiHelper.lerp(mMinUserInfoHeight, mMaxUserInfoHeight, currentFriction);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.height = currentHeight;
        child.setLayoutParams(lp);
        return super.onDependentViewChanged(parent, child, dependency);
    }
}

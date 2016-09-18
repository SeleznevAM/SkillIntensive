package com.example.alex.skillintensive.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.alex.skillintensive.R;

/**
 * Created by Alex on 17.09.2016.
 */
public class AspectRatioImageView extends ImageView {

    private static final float DEFAULT_ASPECT_RATIO = 1.73f;
    private final float mAspectRatio;

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView);
        mAspectRatio = a.getFloat(R.styleable.AspectRatioImageView_aspect_ratio, DEFAULT_ASPECT_RATIO);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int newWith;
        int newHeight;

        newWith = getMeasuredWidth();
        newHeight = (int) (newWith/mAspectRatio);

        setMeasuredDimension(newWith, newHeight);  //Значения устанавливаются во View
    }
}

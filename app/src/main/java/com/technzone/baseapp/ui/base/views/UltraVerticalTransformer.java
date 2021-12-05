package com.technzone.baseapp.ui.base.views;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

public class UltraVerticalTransformer implements ViewPager2.PageTransformer {
    private float yPosition;

    @Override
    public void transformPage(View view, float position) {
        view.setTranslationX(view.getWidth() * -position);
        yPosition = position * view.getHeight();
        view.setTranslationY(yPosition);
    }

    public float getPosition() {
        return yPosition;
    }
}
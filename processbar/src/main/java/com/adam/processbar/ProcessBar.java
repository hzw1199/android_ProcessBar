package com.adam.processbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuzongheng on 16/6/5.
 */
public class ProcessBar extends LinearLayout {
    private Context context;
    private CharSequence[] titles;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mPageChangeListener;

    private int color;
    private Drawable curr, background;
    private int textSizeSelected, textSizeUnSelected;
    private List<ProcessElement> processElementList = new ArrayList<>();

    public ProcessBar(Context context) {
        super(context);
    }

    public ProcessBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProcessBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProcessBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet){
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ProcessBar);
        initAttributes(typedArray);
        typedArray.recycle();

        setContent();
    }

    private void initAttributes(TypedArray typedArray){
        textSizeSelected = context.getResources().getDimensionPixelSize(R.dimen.text_size_selected);
        textSizeUnSelected = context.getResources().getDimensionPixelSize(R.dimen.text_size_unselected);
        color = context.getResources().getColor(R.color.processColor);

        titles = typedArray.getTextArray(R.styleable.ProcessBar_processbar_titles);
        color = typedArray.getColor(R.styleable.ProcessBar_processbar_selected_color, color);
        curr = typedArray.getDrawable(R.styleable.ProcessBar_processbar_current_drawable);
        background = typedArray.getDrawable(R.styleable.ProcessBar_processbar_background);
        textSizeSelected = typedArray.getDimensionPixelSize(R.styleable.ProcessBar_processbar_selected_text_size, textSizeSelected);
        textSizeUnSelected = typedArray.getDimensionPixelSize(R.styleable.ProcessBar_processbar_unselected_text_size, textSizeUnSelected);

        if (titles == null){
            titles = context.getResources().getTextArray(R.array.process_bar_elements);
        }
        if (curr == null){
            curr = context.getResources().getDrawable(R.drawable.plan_arrow);
        }
        if (background == null){
            background = context.getResources().getDrawable(R.drawable.shape_plan_tab_background);
        }
    }

    private void setContent(){
        setOrientation(LinearLayout.HORIZONTAL);

        for(int i = 0; i<titles.length; i++){
            ProcessElement processElement = new ProcessElement(context, titles[i]);
            processElement.setSelectedColor(color);
            processElement.setSelectedDrawable(curr);
            processElement.setTextSize(textSizeSelected, textSizeUnSelected);
            processElement.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            addView(processElement);
            processElementList.add(processElement);
        }

        setBackground(background);


        processElementList.get(0).setStatus(ProcessElement.STATUS_SELECTED);
    }

    /**根据ViewPager来自动控制进度*/
    public void setupWithViewPager(@Nullable final ViewPager viewPager) {
        if (mViewPager != null && mPageChangeListener != null) {
            // If we've already been setup with a ViewPager, remove us from it
            mViewPager.removeOnPageChangeListener(mPageChangeListener);
        }

        if (viewPager != null) {
            final PagerAdapter adapter = viewPager.getAdapter();
            if (adapter == null) {
                throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
            }

            mViewPager = viewPager;

            // Add our custom OnPageChangeListener to the ViewPager
            if (mPageChangeListener == null) {
                mPageChangeListener = new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if(processElementList.size() > position){
                            processElementList.get(position).setStatus(ProcessElement.STATUS_SELECTED);
                            if(position > 0){
                                processElementList.get(position - 1).setStatus(ProcessElement.STATUS_OLD);
                            }
                            if(position < processElementList.size() - 1){
                                processElementList.get(position + 1).setStatus(ProcessElement.STATUS_NEW);
                            }
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                };
            }
            viewPager.addOnPageChangeListener(mPageChangeListener);
        } else {
            // We've been given a null ViewPager so we need to clear out the internal state,
            // listeners and observers
            mViewPager = null;
        }
    }

    /**手动控制进度*/
    public void setPosition(int position){
        if(processElementList.size() > position && position >= 0){
            processElementList.get(position).setStatus(ProcessElement.STATUS_SELECTED);
            if(position > 0){
                for(int i = 0; i< position; i++)
                    processElementList.get(i).setStatus(ProcessElement.STATUS_OLD);
            }
            if(position < processElementList.size() - 1){
                for(int i = position + 1; i<processElementList.size(); i++)
                    processElementList.get(i).setStatus(ProcessElement.STATUS_NEW);
            }
        }
    }

}

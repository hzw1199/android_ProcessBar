package com.adam.processbarsample;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adam.processbar.ProcessBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProcessBar processBar;
    private ViewPager viewPager;
    private List<View> viewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater lf = getLayoutInflater().from(this);

        processBar = (ProcessBar) findViewById(R.id.processbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        for(int i = 0; i<3; i++){
            View view = lf.inflate(R.layout.view_viewpager, null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(String.valueOf(i+1));
            viewList.add(view);
        }
        viewPager.setAdapter(pagerAdapter);
        processBar.setupWithViewPager(viewPager);

    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {

            return arg0 == arg1;
        }

        @Override
        public int getCount() {

            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));

        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {

//            return titleList.get(position);
            return "";
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

    };
}

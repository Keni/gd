package keni.gd_new.flats;

/**
 * Created by Keni on 23.03.2016.
 */
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import keni.gd_new.R;


public class ImagesActivity extends FragmentActivity
{
    static ArrayList<String> image_url = null;

    ViewPager pager;
    PagerAdapter pagerAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        initToolBar();




        Intent flat = getIntent();

        image_url = flat.getStringArrayListExtra("images_url");

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), getData());
        pager.setAdapter(pagerAdapter);


        //CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        //circlePageIndicator.setViewPager(pager);

        pager.setOnPageChangeListener(new OnPageChangeListener() {


            @Override
            public void onPageSelected(int position)
            {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
                circlePageIndicator.setViewPager(pager);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });
    }


    public List<String> getData()
    {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < image_url.size(); i++)
        {
            data.add("Item number " + i);
        }

        return data;
    }




    private class MyFragmentPagerAdapter extends FragmentPagerAdapter
    {
        List<String> data;

        public MyFragmentPagerAdapter(FragmentManager fm, List<String> data)
        {
            super(fm);
            this.data = data;
        }



        @Override
        public Fragment getItem(int position)
        {
            Fragment fragment = new FlatImageActivity();

            Bundle args = new Bundle();
            args.putStringArrayList("image_url", image_url);
            args.putInt("position", position);

            fragment.setArguments(args);
            return fragment;

        }



        @Override
        public int getCount()
        {
            return data.size();
        }

    }

    public void initToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.image_activity);
        toolbar.setTitleTextColor(Color.WHITE);

        //setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

    }

}

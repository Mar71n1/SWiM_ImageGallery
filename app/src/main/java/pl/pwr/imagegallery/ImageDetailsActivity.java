package pl.pwr.imagegallery;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ImageDetailsActivity extends AppCompatActivity {
    private ArrayList<ImageData> images;
    private int imageClickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_image_details);

        images = getIntent().getParcelableArrayListExtra("images");
        imageClickedPosition = getIntent().getIntExtra("imageClickedPosition", 0);

        ViewPager pager = findViewById(R.id.activity_image_details_viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch(i) {
                case 0: return FullImageFragment.newInstance(images.get(imageClickedPosition).getUrl());
                case 1: return HelperFragment.newInstance(images, imageClickedPosition);
                default: return FullImageFragment.newInstance(images.get(imageClickedPosition).getUrl());
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

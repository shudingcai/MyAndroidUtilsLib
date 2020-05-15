package kostal.com.myandroidutilslib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


   // @BindView(R.id.tablayout)
    SlidingTabLayout tabLayout;
    //@BindView(R.id.view_pager)
    ViewPager viewPager;
    //设置适配器
    private MyPagerAdapter mAdapter;
    //Fragment集合
    private ArrayList<Fragment> mFagments = new ArrayList<>();
    //标题
    private String[] mTitles = {"疾病介绍", "病因", "症状", "预防", "检查", "治疗"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = this.findViewById(R.id.tablayout);
        viewPager = this.findViewById(R.id.view_pager);
        initTab();
    }



    private void initTab() {
        //添加Fragment
        mFagments.add(new LeftFragement());
        mFagments.add(new LeftFragement());
        mFagments.add(new LeftFragement());
        mFagments.add(new LeftFragement());
        mFagments.add(new LeftFragement());
        mFagments.add(new LeftFragement());

        //new一个适配器
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        //设置ViewPager与适配器关联
        viewPager.setAdapter(mAdapter);
        //设置Tab与ViewPager关联
        tabLayout.setViewPager(viewPager);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFagments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFagments.get(position);
        }
    }



}

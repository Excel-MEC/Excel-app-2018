package ml.arjunnair.excelapp.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ml.arjunnair.excelapp.R;

import static android.content.Context.MODE_PRIVATE;


public class ScheduleFragment extends Fragment {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private SharedPreferences prefs;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = getActivity().getPreferences(MODE_PRIVATE);

        // Binding view pager and tab layout
        viewPager = view.findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


        ScheduleEventsFragment dayOneFragment = new ScheduleEventsFragment();
        Bundle detailsDayOne = new Bundle();
        detailsDayOne.putString("day_num", "day_one");
        dayOneFragment.setArguments(detailsDayOne);
        adapter.addFragment(dayOneFragment, "Day One");

        ScheduleEventsFragment dayTwoFragment = new ScheduleEventsFragment();
        Bundle detailsDayTwo = new Bundle();
        detailsDayTwo.putString("day_num", "day_two");
        dayTwoFragment.setArguments(detailsDayTwo);
        adapter.addFragment(dayTwoFragment, "Day Two");

        ScheduleEventsFragment dayThreeFragment = new ScheduleEventsFragment();
        Bundle detailsDayThree = new Bundle();
        detailsDayThree.putString("day_num", "day_three");
        dayThreeFragment.setArguments(detailsDayThree);
        adapter.addFragment(dayThreeFragment, "Day Three");



        adapter.notifyDataSetChanged();
    }


    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

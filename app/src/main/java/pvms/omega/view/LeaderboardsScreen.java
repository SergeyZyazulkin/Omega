package pvms.omega.view;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import pvms.omega.R;
import pvms.omega.model.BackgroundMusic;
import pvms.omega.model.Leaderboard;
import pvms.omega.model.Record;


public class LeaderboardsScreen extends Activity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards_screen);

        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        private int sectionNumber;
        private Context context;

        public PlaceholderFragment(Context context, int sectionNumber) {
            if (sectionNumber < 1 || sectionNumber > 3) {
                sectionNumber = 1;
            }

            this.context = context;
            this.sectionNumber = sectionNumber;
        }

        public static PlaceholderFragment newInstance(Context context, int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment(context, sectionNumber);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        private String getStringValue(int score) {
            return score == -1 ? "-----" : Integer.toString(score);
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View fragment;
            Leaderboard leaderboard = Leaderboard.load(context);
            ArrayList<Record> records;

            switch (sectionNumber) {
                case 1:
                    fragment = inflater.inflate(
                            R.layout.fragment_leaderboards_easy, container, false);
                    records = leaderboard.getEasyRecords();
                    break;

                case 2:
                    fragment = inflater.inflate(
                            R.layout.fragment_leaderboards_normal, container, false);
                    records = leaderboard.getNormalRecords();
                    break;

                default:
                    fragment = inflater.inflate(
                            R.layout.fragment_leaderboards_hard, container, false);
                    records = leaderboard.getHardRecords();
            }


            ((TextView) fragment.findViewById(R.id.name1)).setText(records.get(0).getName());
            ((TextView) fragment.findViewById(R.id.name2)).setText(records.get(1).getName());
            ((TextView) fragment.findViewById(R.id.name3)).setText(records.get(2).getName());
            ((TextView) fragment.findViewById(R.id.name4)).setText(records.get(3).getName());
            ((TextView) fragment.findViewById(R.id.name5)).setText(records.get(4).getName());
            ((TextView) fragment.findViewById(R.id.name6)).setText(records.get(5).getName());
            ((TextView) fragment.findViewById(R.id.name7)).setText(records.get(6).getName());
            ((TextView) fragment.findViewById(R.id.name8)).setText(records.get(7).getName());
            ((TextView) fragment.findViewById(R.id.name9)).setText(records.get(8).getName());
            ((TextView) fragment.findViewById(R.id.name10)).setText(records.get(9).getName());
            ((TextView) fragment.findViewById(R.id.name11)).setText(records.get(10).getName());
            ((TextView) fragment.findViewById(R.id.name12)).setText(records.get(11).getName());
            ((TextView) fragment.findViewById(R.id.name13)).setText(records.get(12).getName());
            ((TextView) fragment.findViewById(R.id.name14)).setText(records.get(13).getName());
            ((TextView) fragment.findViewById(R.id.name15)).setText(records.get(14).getName());
            ((TextView) fragment.findViewById(R.id.name16)).setText(records.get(15).getName());
            ((TextView) fragment.findViewById(R.id.name17)).setText(records.get(16).getName());
            ((TextView) fragment.findViewById(R.id.name18)).setText(records.get(17).getName());
            ((TextView) fragment.findViewById(R.id.name19)).setText(records.get(18).getName());
            ((TextView) fragment.findViewById(R.id.name20)).setText(records.get(19).getName());

            ((TextView) fragment.findViewById(R.id.score1)).setText(
                    getStringValue(records.get(0).getScore()));
            ((TextView) fragment.findViewById(R.id.score2)).setText(
                    getStringValue(records.get(1).getScore()));
            ((TextView) fragment.findViewById(R.id.score3)).setText(
                    getStringValue(records.get(2).getScore()));
            ((TextView) fragment.findViewById(R.id.score4)).setText(
                    getStringValue(records.get(3).getScore()));
            ((TextView) fragment.findViewById(R.id.score5)).setText(
                    getStringValue(records.get(4).getScore()));
            ((TextView) fragment.findViewById(R.id.score6)).setText(
                    getStringValue(records.get(5).getScore()));
            ((TextView) fragment.findViewById(R.id.score7)).setText(
                    getStringValue(records.get(6).getScore()));
            ((TextView) fragment.findViewById(R.id.score8)).setText(
                    getStringValue(records.get(7).getScore()));
            ((TextView) fragment.findViewById(R.id.score9)).setText(
                    getStringValue(records.get(8).getScore()));
            ((TextView) fragment.findViewById(R.id.score10)).setText(
                    getStringValue(records.get(9).getScore()));
            ((TextView) fragment.findViewById(R.id.score11)).setText(
                    getStringValue(records.get(10).getScore()));
            ((TextView) fragment.findViewById(R.id.score12)).setText(
                    getStringValue(records.get(11).getScore()));
            ((TextView) fragment.findViewById(R.id.score13)).setText(
                    getStringValue(records.get(12).getScore()));
            ((TextView) fragment.findViewById(R.id.score14)).setText(
                    getStringValue(records.get(13).getScore()));
            ((TextView) fragment.findViewById(R.id.score15)).setText(
                    getStringValue(records.get(14).getScore()));
            ((TextView) fragment.findViewById(R.id.score16)).setText(
                    getStringValue(records.get(15).getScore()));
            ((TextView) fragment.findViewById(R.id.score17)).setText(
                    getStringValue(records.get(16).getScore()));
            ((TextView) fragment.findViewById(R.id.score18)).setText(
                    getStringValue(records.get(17).getScore()));
            ((TextView) fragment.findViewById(R.id.score19)).setText(
                    getStringValue(records.get(18).getScore()));
            ((TextView) fragment.findViewById(R.id.score20)).setText(
                    getStringValue(records.get(19).getScore()));

            return fragment;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Context context;

        public SectionsPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(context, position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();

            switch (position) {
                case 0:
                    return getString(R.string.leaderboards_screen_easy).toUpperCase(l);
                case 1:
                    return getString(R.string.leaderboards_screen_normal).toUpperCase(l);
                case 2:
                    return getString(R.string.leaderboards_screen_hard).toUpperCase(l);
            }

            return null;
        }
    }
}

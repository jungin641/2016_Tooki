package com.tendom.tooki.mainpage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tendom.tooki.R;
import com.tendom.tooki.alarm.AlarmActivity;
import com.tendom.tooki.commentpage.Question;
import com.tendom.tooki.logpage.MyKeyFragment;
import com.tendom.tooki.logpage.MyLogActivity;
import com.tendom.tooki.logpage.MyQuestionFragment;
import com.tendom.tooki.network.ApplicationController;
import com.tendom.tooki.network.ServerInterface;
import com.tendom.tooki.settingpage.SettingActivity;
import com.tendom.tooki.uuid.MyUUID;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ServerInterface api;

    EditText input_value; // Edittext에 입력되는 변수
    Button input_button;
    LinearLayout btn_drawer;
    ImageView alarm_button;
    Button nav_home, nav_messages, nav_setting;
    Adapter adapter;

    // SiteFragment SiteFragment = new SiteFragment();
    ListFragment listFragment = new ListFragment();
    HotFragment hotFragment = new HotFragment();
    MyKeyFragment myKeyFragment = new MyKeyFragment();

    public FragmentRefreshListener getFragmentRefreshListener_List() {
        return fragmentRefreshListener_List;
    }
    public FragmentRefreshListener getFragmentRefreshListener_Hot() {
        return fragmentRefreshListener_Hot;
    }
    public FragmentRefreshListener getFragmentRefreshListener_new() {
        return fragmentRefreshListener_Hot;
    }
    public FragmentRefreshListener getFragmentRefreshListener_Site() {
        return fragmentRefreshListener_Site;
    }

    public void setFragmentRefreshListener_List(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener_List = fragmentRefreshListener;
    }
    public void setFragmentRefreshListener_Hot(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener_Hot = fragmentRefreshListener;
    }
    public void setFragmentRefreshListener_new(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener_Site = fragmentRefreshListener;
    }
    public void setFragmentRefreshListener_Site(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener_Site = fragmentRefreshListener;
    }

    public interface FragmentRefreshListener{
        void onRefresh();
    }

    private FragmentRefreshListener fragmentRefreshListener_List;
    private FragmentRefreshListener fragmentRefreshListener_Hot;
    private FragmentRefreshListener fragmentRefreshListener_new;
    private FragmentRefreshListener fragmentRefreshListener_Site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = ApplicationController.getInstance().getServerInterface();

        input_button = (Button)findViewById(R.id.input_button);
        input_button.setOnClickListener(mClickListener);
        input_value = (EditText)findViewById(R.id.input);

        // onCreate() has these following steps:
        // 1. Setup Toolbar
        // 2. Setup Drawer
        // 3. Setup View Pager
        // 4. Setup Floating Action Button

        // STEP1> Setup Toolbar
        {
            //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            final ActionBar ab = getSupportActionBar();
            //ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            //ab.setCustomView(R.layout.abs_layout);
            //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            //ab.setDisplayHomeAsUpEnabled(true);

            alarm_button = (ImageView)findViewById(R.id.alarm_button);
            alarm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                    startActivity(intent);
                }
            });
            btn_drawer = (LinearLayout)findViewById(R.id.btn_drawer);
            btn_drawer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }

        // STEP2> Setup Drawer
        {
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(

                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            menuItem.setChecked(true);
                            mDrawerLayout.closeDrawers();
                            return true;
                        }
                    });
        }

        nav_home = (Button)findViewById(R.id.nav_home);
        nav_messages= (Button)findViewById(R.id.nav_messages);
        nav_setting=(Button)findViewById(R.id.nav_setting);

        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        nav_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyLogActivity.class);
                startActivity(intent);
                finish();
            }
        });
        nav_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);

            }
        });

        // STEP3> Setup View Pager
        {
            int tabIcons[] = new int[] {R.drawable.ic_new_grey1, R.drawable.ic_hot_grey1, R.drawable.ic_my_choice_grey1};

            final FreezableViewPager viewPager = (FreezableViewPager) findViewById(R.id.viewpager);
            adapter = new Adapter(getSupportFragmentManager());
            //adapter.addFragment(siteFragment, "  내 근처");
            adapter.addFragment(listFragment, "   최신글");//정인 : 간격때문에 띄워놓아야 합니다
            adapter.addFragment(hotFragment, "   인기글");
            adapter.addFragment(myKeyFragment, "   내 선택");
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(0);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
            tabLayout.setupWithViewPager(viewPager);
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setIcon(tabIcons[i]);
            }
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
                @Override
                public void onPageSelected(int position) {
                    viewPager.freeze();
                }
                @Override
                public void onPageScrollStateChanged(int state) {}
            });
        }

        // STEP4> Setup Floating Action Button
        {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    switch (tabLayout.getSelectedTabPosition()){
                        case 0:
                            Log.v("action", String.valueOf(tabLayout.getSelectedTabPosition()));
                            getFragmentRefreshListener_List().onRefresh();
                            break;
                        case 1:
                            Log.v("action", String.valueOf(tabLayout.getSelectedTabPosition()));
                            getFragmentRefreshListener_Hot().onRefresh();
                            break;
                        case 2:
                            //Log.v("action", String.valueOf(tabLayout.getSelectedTabPosition()));
                            //getFragmentRefreshListener_Site().onRefresh();
                            break;
                    }
                }
            });
        }

    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            String uuid = new MyUUID().getUUID(getApplicationContext());
            String question = input_value.getText().toString();
            ArrayList<Question> abList = new ArrayList<Question>(); //질문카드 가져올 ArrayList
            final CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), R.layout.before_item_change,abList);
            int count_yes = 0;
            int count_no = 0;
            int count_comment = 0;
            double site_x = 0;
            double site_y = 0;

            if(!input_value.getText().toString().equals("")) {
                Question input_question = new Question(uuid, question, count_yes, count_no, count_comment, site_x, site_y);
                api.SendQuestion(input_question, new Callback<Question>() {
                    @Override
                    public void success(Question question, Response response) {
                        input_value.setText("");
                        getFragmentRefreshListener_new().onRefresh();
                        Snackbar.make(v, "질문을 등록했습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                        //customAdapter.add_top(question);
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        input_value.setText("");
                        Snackbar.make(v, "질문등록을 실패했습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                    }
                });
            }
            else{
                Snackbar.make(v, "질문을 입력해주세요.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
        @Override
        public int getCount() {
            return mFragments.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
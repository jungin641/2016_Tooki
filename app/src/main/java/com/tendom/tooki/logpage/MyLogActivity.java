package com.tendom.tooki.logpage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.tendom.tooki.mainpage.MainActivity;
import com.tendom.tooki.network.ApplicationController;
import com.tendom.tooki.network.ServerInterface;
import com.tendom.tooki.settingpage.SettingActivity;
import com.tendom.tooki.uuid.MyUUID;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Woong on 2015-07-10.
 */
public class MyLogActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ServerInterface api;

    EditText input_value; // Edittext에 입력되는 변수
    Button input_button;
    LinearLayout btn_drawer;
    ImageView alarm_button;
    Button nav_home, nav_messages, nav_setting;
    Adapter adapter;

    MyCommentFragment myCommentFragment = new MyCommentFragment();
    MyKeyFragment myKeyFragment = new MyKeyFragment();
    MyQuestionFragment myQuestionFragment = new MyQuestionFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        input_button = (Button)findViewById(R.id.input_button);
        input_button.setOnClickListener(mClickListener);
        input_value = (EditText)findViewById(R.id.input);

        btn_drawer = (LinearLayout)findViewById(R.id.btn_drawer);
        btn_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        // onCreate() has these following steps:
        // 1. Setup Toolbar
        // 2. Setup Drawer
        // 3. Setup View Pager
        // 4. Setup Floating Action Button

        // STEP1> Setup Toolbar
        {
          //  setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            final ActionBar ab = getSupportActionBar();
           // ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
          //  ab.setCustomView(R.layout.abs_layout);

            alarm_button = (ImageView)findViewById(R.id.alarm_button);
            alarm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AlarmActivity.class);
                    startActivity(intent);
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

        // STEP3> Setup View Pager
        {
            int tabIcons[] = new int[] {R.drawable.ic_my_question_grey1, R.drawable.ic_my_choice_grey1, R.drawable.ic_my_comment_grey1};

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            adapter = new Adapter(getSupportFragmentManager());
            adapter.addFragment(myQuestionFragment, "  내 질문");
            adapter.addFragment(myKeyFragment, "  내 선택");
            adapter.addFragment(myCommentFragment, "  내 댓글");
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(2);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
            tabLayout.setupWithViewPager(viewPager);
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setIcon(tabIcons[i]);
            }
        }

        nav_home = (Button)findViewById(R.id.nav_home);
        nav_messages= (Button)findViewById(R.id.nav_messages);
        nav_setting=(Button)findViewById(R.id.nav_setting);

        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(final View v) {
            api = ApplicationController.getInstance().getServerInterface();
            String uuid = new MyUUID().getUUID(getApplicationContext());
            String question = input_value.getText().toString();
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
                        Snackbar.make(v, "질문을 등록했습니다.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
    @Override
    public void onBackPressed() {
        return;
    }
}

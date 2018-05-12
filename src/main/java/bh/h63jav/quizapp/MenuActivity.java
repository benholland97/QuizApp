package bh.h63jav.quizapp;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.GridLayout;

public class MenuActivity extends AppCompatActivity {

    private MenuPagerAdapter pagerAdapter;
    private ViewPager viewPagerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Properties p = (Properties)getApplicationContext();
        p.initData();
//        pagerAdapter = new MenuPagerAdapter(getSupportFragmentManager());
        viewPagerMenu = (ViewPager)findViewById(R.id.viewPagerMenu);

        setupViewPager(viewPagerMenu,null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getBoolean("END")) {
                pagerAdapter.getItem(2).setArguments(extras);
                setViewPager(2);
            }
        }
    }



    private void setupViewPager(ViewPager viewPager, Bundle extras) {
        pagerAdapter = new MenuPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new MainMenuFragment(),"Main",null);
        pagerAdapter.addFragment(new CategoryMenuFragment(),"Category",null);
        pagerAdapter.addFragment(new EndQuizFragment(),"End",null);
        if (extras!=null){
            if (extras.getString("Category") != null) {
                pagerAdapter.addFragment(new RankingsFragment(),"Rankings",extras);
            }
        }
        else {
            pagerAdapter.addFragment(new RankingsFragment(),"Rankings",null);
        }
        viewPagerMenu.setAdapter(pagerAdapter);
    }

    public void setViewPager(int fragmentNum) {
        viewPagerMenu.setCurrentItem(fragmentNum);
    }

    public void setViewPagerArgs(int fragmentNum, Bundle extras) {
        setupViewPager(viewPagerMenu,extras);
        viewPagerMenu.setCurrentItem(fragmentNum);

    }

}

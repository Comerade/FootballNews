package com.example.igor.footballnews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener {

    String href;

    private SlidingMenu menu;

    FragmentManager fm;
    ArrayList<Razdel> razdelu = new ArrayList<>();
    RazdelAdapter razdelAdapter;

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        href = "http://football.ua/ukraine.html";

        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        fillData();
        razdelAdapter = new RazdelAdapter(this,razdelu);


        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBackgroundColor(0xFF333333);
        menu.setSelectorDrawable(R.drawable.sidemenu_items_background);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);

        lv = ((ListView) findViewById(R.id.sidemenu));
        lv.setAdapter(razdelAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("myLog", razdelu.get(position).href);
                href = razdelu.get(position).href;
                ResultsFragment resultsFragment = ResultsFragment.newInstance(razdelu.get(position).href);//Передаём ссылку на страницу для чтения
                fm.beginTransaction()
                        .replace(R.id.container, resultsFragment)
                        .commit();
            }
        });

        ActionBar bar = getSupportActionBar();

        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = bar.newTab();
        tab.setText("Результаты");
        tab.setTabListener(this);
        bar.addTab(tab);

        tab = bar.newTab();
        tab.setText("Новости");
        tab.setTabListener(this);
        bar.addTab(tab);

        showFragment(new ResultsFragment());



        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void showFragment(Fragment currentFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ResultsFragment resultsFragment = ResultsFragment.newInstance("http://football.ua/ukraine.html");
        fragmentManager.beginTransaction()
                .replace(R.id.container, resultsFragment)
                .commit();
    }

    private void fillData() {
        // TODO Auto-generated method stub
        razdelu.add(new Razdel("Украина","http://football.ua/ukraine.html"));
        razdelu.add(new Razdel("Англия","http://football.ua/england.html"));
        razdelu.add(new Razdel("Аргентина","http://football.ua/argentina.html"));
        razdelu.add(new Razdel("Бразилия","http://football.ua/brazil.html"));
        razdelu.add(new Razdel("Германия","http://football.ua/germany.html"));
        razdelu.add(new Razdel("Испания","http://football.ua/spain.html"));
        razdelu.add(new Razdel("Италия","http://football.ua/italy.html"));
        razdelu.add(new Razdel("Нидерланды","http://football.ua/netherlands.html"));
        razdelu.add(new Razdel("Португалия","http://football.ua/portugal.html"));
        razdelu.add(new Razdel("Турция","http://football.ua/turkey.html"));
        razdelu.add(new Razdel("Франция","http://football.ua/france.html"));
        razdelu.add(new Razdel("Другие страны","http://football.ua/countrieselse.html"));
        razdelu.add(new Razdel("Лига чемпионов","http://football.ua/champions_league.html"));
        razdelu.add(new Razdel("Лига европы","http://football.ua/uefa.html"));
        razdelu.add(new Razdel("Евро 2016","http://football.ua/euro2016.html"));
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if(tab.getText().equals("Результаты")){
            ResultsFragment resultsFragment = ResultsFragment.newInstance(href);//Передаём ссылку на страницу для чтения
            fm.beginTransaction()
                .replace(R.id.container, resultsFragment)
                .commit();
        }
        else{
            NewsFragment newsFragment = NewsFragment.newInstance(href);//Передаём ссылку на страницу для чтения
        fm.beginTransaction()
                .replace(R.id.container, newsFragment)
                .commit();
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}

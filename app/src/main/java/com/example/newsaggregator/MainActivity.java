package com.example.newsaggregator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private TextView textView;
    private ActionBarDrawerToggle drawerToggle;
    private String[] items;
    private NewsSourceDataDownload newsSourceDataDownload;
    private Menu menu;

    private List<NewsSource> newsSourceList = new ArrayList<>();

    private ArrayList<String> current_items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView1);



        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.news_source_drawer);

        //drawerList.setAdapter(new ArrayAdapter<>(this,
          //      R.layout.news_drawer_list_item, items));

        drawerList.setOnItemClickListener(   // <== Important!
                (parent, view, position, id) -> selectItem(position)
        );

        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        );

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        newsSourceDataDownload = new NewsSourceDataDownload();
        newsSourceDataDownload.downloadNewsSourceData(this);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig); // <== IMPORTANT
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Important!
        if (drawerToggle.onOptionsItemSelected(item)) {
            Log.d(TAG, "onOptionsItemSelected: mDrawerToggle " + item);
            return true;
        }
        updateDrawerListAfterSelection(item.getTitle().toString());
        return super.onOptionsItemSelected(item);
    }

    public void updateDrawerListAfterSelection(String title){
        this.current_items.clear();
        //ArrayList<String> items_current = new ArrayList<>();
        //items_current = new String[this.newsSourceList.size()];
        if(title.equalsIgnoreCase("all")){
            for(int i=0;i<newsSourceList.size();i++){
                current_items.add(newsSourceList.get(i).getName());
            }
        }
        else{
            for(int i = 0;i<this.newsSourceList.size();i++){
                if(this.newsSourceList.get(i).getCategory().equalsIgnoreCase(title)){
                    current_items.add(newsSourceList.get(i).getName());
                }

            }
        }
        drawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.news_drawer_list_item, current_items));
        String app_title = new StringBuilder().append(getString(R.string.app_name)).append(" (").append(current_items.size()).append(")").toString();
        setTitle(app_title);

    }

    private void selectItem(int position) {
        textView.setText(String.format(Locale.getDefault(),
                "You picked %s", this.current_items.get(position).toString()));
        drawerLayout.closeDrawer(drawerList);
    }

    public void updateDrawer(List<NewsSource> newsSourceList){
        this.newsSourceList.addAll(newsSourceList);
        this.current_items.clear();
        //items = new String[newsSourceList.size()];
        for(int i=0;i<newsSourceList.size();i++){
            this.current_items.add(newsSourceList.get(i).getName());
        }
        drawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.news_drawer_list_item, current_items));

        String title = new StringBuilder().append(getString(R.string.app_name)).append(" (").append(current_items.size()).append(")").toString();
        setTitle(title);

        createMenuList(newsSourceList);
    }

    public void createMenuList(List<NewsSource> newsSourceList){
        Map<String, Integer> elements = new LinkedHashMap<String,Integer>();
        //items = new String[newsSourceList.size()];
        for(int i=0;i<newsSourceList.size();i++){
            if(elements.containsKey(newsSourceList.get(i).getCategory())){
                int value = elements.get(newsSourceList.get(i).getCategory());
                value = value+1;
                elements.put(newsSourceList.get(i).getCategory(), value);
            }
            else{
                elements.put(newsSourceList.get(i).getCategory(), 1);
            }
        }
        Set keys = elements.keySet();
        Object[] retArr = keys.toArray();
        menu.clear();
        SpannableString ss = new SpannableString("all");
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF000000")), 0, ss.length(), 0);
        menu.add(ss);
        for(int j = 0;j<retArr.length;j++){
            String s = (String)retArr[j];
            SpannableString spannableString = new SpannableString(s);
            if(s.equalsIgnoreCase("technology")){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#F4A460")), 0, spannableString.length(), 0);
                menu.add(spannableString);
            }
            else if(s.equalsIgnoreCase("business")){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ADFF2F")), 0, spannableString.length(), 0);
                menu.add(spannableString);
            }
            else if(s.equalsIgnoreCase("sports")){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FFFF00")), 0, spannableString.length(), 0);
                menu.add(spannableString);
            }
            else if(s.equalsIgnoreCase("entertainment")){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#2874A6")), 0, spannableString.length(), 0);
                menu.add(spannableString);
            }
            else if(s.equalsIgnoreCase("health")){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#5B2C6F")), 0, spannableString.length(), 0);
                menu.add(spannableString);
            }
            else if(s.equalsIgnoreCase("science")){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#784212")), 0, spannableString.length(), 0);
                menu.add(spannableString);
            }
            else if(s.equalsIgnoreCase("general")){
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#CD5C5C")), 0, spannableString.length(), 0);
                menu.add(spannableString);
            }

        }
        hideKeyboard();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        //this.menu.add("All");

        return super.onCreateOptionsMenu(menu);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;

        //Find the currently focused view
        View view = getCurrentFocus();

        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null)
            view = new View(this);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
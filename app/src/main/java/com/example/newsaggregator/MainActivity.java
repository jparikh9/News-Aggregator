package com.example.newsaggregator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private TextView textView;
    private ActionBarDrawerToggle drawerToggle;
    private String[] items;
    private NewsSourceDataDownload newsSourceDataDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView1);

        // Make sample items for the drawer list
        items = new String[50];
        for (int i = 0; i < items.length; i++)
            items[i] = "Drawer Item #" + (i + 1);
        //

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.news_source_drawer);

        drawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.news_drawer_list_item, items));

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
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(int position) {
        textView.setText(String.format(Locale.getDefault(),
                "You picked %s", items[position]));
        drawerLayout.closeDrawer(drawerList);
    }
}
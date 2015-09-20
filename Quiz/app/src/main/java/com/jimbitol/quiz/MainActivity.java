package com.jimbitol.quiz;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private View drawerView;
    private ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new MainActivityFragment())
                .commit();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, // no toolbar
                R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(R.string.drawer_open); // Drawer open
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(getTitle()); // Drawer closed -> App title
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Cambiar estos elementos del drawer

        drawerView = findViewById(R.id.drawer_view);

        drawerList = (ListView) findViewById(R.id.drawer_list);
        drawerList.setAdapter(new ArrayAdapter<String>( this,
                R.layout.drawer_list_item,
                new String[]{"TODOS","EPICO","ULTIMO"}
        ));
        drawerList.setItemChecked(0, true);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawer(drawerView);
            }
        });

        // FIN Cambiar estos elementos del drawer
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        ((SearchView) menu.findItem(R.id.action_search).getActionView())
                .setSearchableInfo(((SearchManager) getSystemService(Context.SEARCH_SERVICE))
                        .getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if ( drawerToggle.onOptionsItemSelected(item) ) {
            return true;
        }
        switch ( item.getItemId() ) {
            case R.id.action_settings:
                break;
            case R.id.action_search:
                break;
        }

        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if ( intent.getAction() != null ) {
            if ( Intent.ACTION_SEARCH.equals(intent.getAction()) ) {
                // Filtrar adaptador
            }
        }
    }

    @Override
    public void onBackPressed() {

        if( drawerLayout.isDrawerOpen(drawerView) ){
            drawerLayout.closeDrawer(drawerView);
        } else {
            super.onBackPressed();
        }
    }
}

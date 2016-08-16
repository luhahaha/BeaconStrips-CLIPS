/**
 @file MenuActivity.java
 @date 2016-07-22
 @version 1.50
 @author Viviana Alessio
 @description rappresenta il menù dell'applicazione
 **/

package beaconstrips.clips.client.viewcontroller.utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import beaconstrips.clips.R;
import beaconstrips.clips.client.viewcontroller.authentication.AccountActivity;
import beaconstrips.clips.client.viewcontroller.authentication.LoginActivity;
import beaconstrips.clips.client.viewcontroller.authentication.RegistrationActivity;
import beaconstrips.clips.client.viewcontroller.building.BuildingActivity;
import beaconstrips.clips.client.viewcontroller.building.BuildingSearchActivity;
import beaconstrips.clips.client.viewcontroller.building.PathActivity;
import beaconstrips.clips.client.viewcontroller.games.ProofActivity;
import beaconstrips.clips.client.viewcontroller.games.SearchNewStepActivity;
import beaconstrips.clips.client.viewcontroller.savedresults.SavedResultsActivity;

public class MenuActivity extends AppCompatActivity
        //implements NavigationView.OnNavigationItemSelectedListener
{

    private NavigationView navigationView;
    private DrawerLayout fullLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedNavItemId;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_menu, null);

        FrameLayout activityContainer = (FrameLayout) fullLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(fullLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        if (useToolbar())
        {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(0xFFFFFFFF);

        }
        else
        {
            toolbar.setVisibility(View.GONE);
        }

        //setUpNavView();
        setupContent(navigationView);
    }

    protected boolean useToolbar()
    {
        return true;
    }
    protected boolean useDrawerToggle() {return true; }

    protected void setupContent(NavigationView nav) {
        if( useDrawerToggle()) { // use the hamburger menu
        drawerToggle = new ActionBarDrawerToggle(this, fullLayout, toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            drawerToggle.syncState();
        }
        /*else if(useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources()
                    .getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        }*/

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

    }

    protected void selectDrawerItem(MenuItem menuItem) {
        Intent intent = new Intent();
        switch(menuItem.getItemId()) {
            case R.id.nav_registration:
                intent = new Intent(this, RegistrationActivity.class);
                break;
            case R.id.nav_login:
                intent = new Intent(this, LoginActivity.class);
                break;
            case R.id.nav_path:
                // visualizza percorsi disponibili
                intent = new Intent(this, PathActivity.class);
                break;
            case R.id.nav_edifici:
                // visualizza il "cerca edifici"
                intent = new Intent(this, BuildingSearchActivity.class);
                break;
            case R.id.nav_profilo:
                intent = new Intent(this, AccountActivity.class);
                break;
            case R.id.nav_info:
                intent = new Intent(this, AppInfoActivity.class);
                break;
            case R.id.nav_contacts:
                intent = new Intent(this, SavedResultsActivity.class);
                break;
            case R.id.nav_logout:
                intent = new Intent(this, AppInfoActivity.class);
                break;
        }

        startActivity(intent);

        menuItem.setChecked(true);
        // chiusura del drawer
        fullLayout.closeDrawers();
    }

}



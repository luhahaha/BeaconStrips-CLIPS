/**
 * @file MenuActivity.java
 * @date 2016-07-22
 * @version 1.50
 * @author Viviana Alessio
 * Rappresenta il menù dell'applicazione
 */

package beaconstrips.clips.client.viewcontroller.utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import beaconstrips.clips.R;
import beaconstrips.clips.client.data.LoggedUser;
import beaconstrips.clips.client.data.datamanager.AbstractDataManagerListener;
import beaconstrips.clips.client.data.datamanager.LoginManager;
import beaconstrips.clips.client.urlrequest.ServerError;
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
    private View headerView;
    private int selectedNavItemId;
    private boolean logged = false;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {

        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_menu, null);

        FrameLayout activityContainer = (FrameLayout) fullLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(fullLayout);

        toolbar = (Toolbar) findViewById(R.id.toolbar);


        navigationView = (NavigationView) findViewById(R.id.navigationView);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_menu);

        if(LoginManager.sharedManager(getApplicationContext()).isLogged()) {
            updateView();
        }

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

    public void updateView(){
        if(LoginManager.sharedManager(getApplicationContext()).isLogged()) {
            logged = true;
            Log.e("MenuActivity", "loggato");
            LoggedUser u = LoginManager.sharedManager(getApplicationContext()).getLoggedUser();
            Log.e("MenuActivity", u.email);
            Log.e("MenuActivity", u.username);
            //ImageView img = (ImageView) headerView.findViewById(R.id.imageView);
            // setto l'immagine dell'utente (non so se è prevista questa opzione)
            // img.setImageResource(R.drawable.ic_keyboard_arrow_right);
            TextView us = (TextView) headerView.findViewById(R.id.username_utente);
            // setto l'username dell'utente loggato
            us.setText(u.username);
            TextView e = (TextView) headerView.findViewById(R.id.email_utente);
            // setto l'email dell'utente loggato
            e.setText(u.email);
        }
    }


    protected boolean useToolbar()
    {
        return true;
    }
    protected boolean useDrawerToggle() {return true; }

    @Nullable
    @Override
    public ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return super.getDrawerToggleDelegate();
    }

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

    private void logout(){
        if(LoginManager.sharedManager(getApplicationContext()).isLogged()) {
            LoginManager.sharedManager(getApplicationContext()).logout(new AbstractDataManagerListener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    Log.e("MenuActivity", "logout");
                    Toast.makeText(getApplicationContext(), "Ora sei sloggato",
                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(ServerError error) {
                    Log.e("MenuActivity", "logout error");
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Non sei loggato",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean profilo() {
        if(logged){
            return true;
        }
        else {
            Toast.makeText(getApplicationContext(), "Non sei loggato, non hai un profilo",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }



    protected void selectDrawerItem(MenuItem menuItem) {
        boolean startNewActivity = true;
        Intent intent = new Intent();
        switch (menuItem.getItemId()) {
            case R.id.nav_registration:
                intent = new Intent(this, RegistrationActivity.class);
                break;
            case R.id.nav_login:
                intent = new Intent(this, LoginActivity.class);
                break;
            case R.id.nav_edifici:
                // visualizza il "cerca edifici"
                intent = new Intent(this, BuildingSearchActivity.class);
                break;
            case R.id.nav_profilo:
                startNewActivity = profilo();
                intent = new Intent(this, AccountActivity.class);
                break;
            case R.id.nav_info:
                intent = new Intent(this, AppInfoActivity.class);
                break;
            case R.id.nav_logout:
                logout();
                startNewActivity = false;
                break;
        }

        if (startNewActivity) {
            startActivity(intent);
            fullLayout.closeDrawers(); // il drawer si chiude solo se si lancia una nuova activity
        }

        menuItem.setChecked(true);

    }

}



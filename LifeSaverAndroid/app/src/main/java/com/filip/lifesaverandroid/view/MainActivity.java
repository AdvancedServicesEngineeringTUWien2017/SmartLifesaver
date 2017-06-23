package com.filip.lifesaverandroid.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.filip.lifesaverandroid.R;
import com.filip.lifesaverandroid.model.NotificationEntry;
import com.filip.lifesaverandroid.model.PatientData;
import com.filip.lifesaverandroid.service.MyFirebaseMessagingService;
import com.filip.lifesaverandroid.service.RealmService;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private RealmService realmService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view initialization
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);

                drawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.notification_item:
                        showNotificationFragment();
                        return true;
                    case R.id.heart_data_item:
                        showHeartInformationFragment();
                        return true;
                    default:
                        return true;
                }
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        toggle.syncState();

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //setting the drawer header title
        View navHeaderView = navigationView.getHeaderView(0);
        TextView usernameView = (TextView) navHeaderView.findViewById(R.id.drawer_header_title);
        usernameView.setText(PatientData.USERNAME);

        //initializing realm service Database
        realmService = RealmService.instance(getApplicationContext());
        realmService.buildRealm();

        //determining whether the app has been started by a click on notification or by icon tapping
        boolean startedByNotification = checkIfAppWasStartedByNotification();
        if (startedByNotification) {

            String message = (String) getIntent().getExtras().get(MyFirebaseMessagingService.NOTIFICATION_MESSAGE);

            NotificationEntry notificationEntry = new NotificationEntry("Warning", message);

            realmService.getRealm().beginTransaction();
            realmService.getRealm().copyToRealm(notificationEntry);
            realmService.getRealm().commitTransaction();

            navigationView.setCheckedItem(R.id.notification_item);

            showNotificationFragment();

        } else {
            showHeartInformationFragment();

            navigationView.setCheckedItem(R.id.heart_data_item);

        }



    }


    private boolean checkIfAppWasStartedByNotification() {

        //if the app was launched by tapping on a notification
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(MyFirebaseMessagingService.NOTIFICATION_MESSAGE)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Brings NotificationFragment to front
     */
    private void showNotificationFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, NotificationFragment.newInstance(), NotificationFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * Brings HeartDataFragment to front
     */
    private void showHeartInformationFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, HeartDataFragment.newInstance(), HeartDataFragment.TAG);
        fragmentTransaction.commitAllowingStateLoss();
    }

}

package com.example.study_application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MenuScreen extends AppCompatActivity {
    private final DrawerLayout drawer;
    private final Context CONTEXT;
    Activity activity;

    public MenuScreen(Context context) {
        this.CONTEXT = context;
        this.activity = (Activity) CONTEXT;
        drawer = activity.findViewById(R.id.navigation_layout);
    }

    public void toSetDrawer() {
        Toolbar toolBar = activity.findViewById(R.id.toolBar);
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        //checks if the drawer button has been clicked
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle((Activity) CONTEXT, drawer, toolBar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.bringToFront();
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        ContextWrapper contextWrapper = new ContextWrapper(CONTEXT);
        Intent destination;
        switch (item.getItemId()) {

            case R.id.nav_main_page:
                destination = new Intent(CONTEXT, HomeScreen.class);
                break;
            case R.id.nav_message:
                destination = new Intent(CONTEXT, TaskListScreen.class);
                break;
            case R.id.nav_logout:
                destination = new Intent(CONTEXT, MainActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        if(!destination.getComponent().getClassName().equals(CONTEXT.getClass().getName())){
            contextWrapper.startActivity(destination);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
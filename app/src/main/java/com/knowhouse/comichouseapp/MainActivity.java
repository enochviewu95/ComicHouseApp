package com.knowhouse.comichouseapp;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwipeRefreshLayout mySwipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        mySwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavigationView navigationView = findViewById(R.id.nav_view);
                assert navHostFragment != null;
                NavController navController = navHostFragment.getNavController();
                AppBarConfiguration appBarConfiguration =
                        new AppBarConfiguration.Builder(navController.getGraph()).setOpenableLayout(drawerLayout).build();
                NavigationUI.setupWithNavController(navigationView,navController);
                NavigationUI.setupWithNavController(toolbar,navController,appBarConfiguration);
                mySwipeRefreshLayout.setRefreshing(false);
            }
        },2000);


    }

}
package com.vetdevelopers.vetnetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class StartPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button signUpButton, signInButton, BVA, doctorSearchButton;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        signUpButton = (Button) findViewById(R.id.start_signUpButton);
        signInButton = (Button) findViewById(R.id.start_signInButton);
        BVA = (Button) findViewById(R.id.start_bvaButton);
        doctorSearchButton = (Button) findViewById(R.id.start_doctorSearchButton);
        logo = (ImageView) findViewById(R.id.login_logo);

        signUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent registrationIntent = new Intent(StartPageActivity.this, RegistrationActivity.class);
                startActivity(registrationIntent);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent loginIntent = new Intent(StartPageActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        BVA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent BVAIntent = new Intent(StartPageActivity.this, BVAActivity.class);
                startActivity(BVAIntent);
            }
        });

        doctorSearchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent searchIntent = new Intent(StartPageActivity.this, SearchActivity.class);
                startActivity(searchIntent);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reportActivity) {

            Intent reportForBrowsersIntent = new Intent(StartPageActivity.this, ReportForBrowsersActivity.class);
            startActivity(reportForBrowsersIntent);

        } else if (id == R.id.nav_aboutUs) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

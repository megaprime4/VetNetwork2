package com.vetdevelopers.vetnetwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class EditBVAActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button president, sr_vice_president, vice_president, secretary_general, joint_secretary,
            treasurer, org_secretary, office_secretary, press_secretary, intAffair_secretary,
            sports_secretary, social_secretary, members;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bva);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        president = (Button) findViewById(R.id.BVA_PresidentButton);
        sr_vice_president = (Button) findViewById(R.id.BVA_srvpButton);
        vice_president = (Button) findViewById(R.id.BVA_vpButton);
        secretary_general = (Button) findViewById(R.id.BVA_sgButton);
        joint_secretary = (Button) findViewById(R.id.BVA_jsButton);
        treasurer = (Button) findViewById(R.id.BVA_treasurerButton);
        org_secretary = (Button) findViewById(R.id.BVA_orgSecButton);
        office_secretary = (Button) findViewById(R.id.BVA_officeSecButton);
        press_secretary = (Button) findViewById(R.id.BVA_pressSecButton);
        intAffair_secretary = (Button) findViewById(R.id.BVA_intSecButton);
        sports_secretary = (Button) findViewById(R.id.BVA_sportSecButton);
        social_secretary = (Button) findViewById(R.id.BVA_socSecButton);
        members = (Button) findViewById(R.id.BVA_memberButton);

        NavigationView navigationView = (NavigationView) findViewById(R.id.edit_bva_nav_view);
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

        if (id == R.id.nav_adminProfile)
        {
            Intent adminProfileIntent = new Intent(EditBVAActivity.this, AdminProfileActivity.class);
            startActivity(adminProfileIntent);
        }
        else if (id == R.id.nav_editProfile)
        {
            Intent editProfileIntent = new Intent(EditBVAActivity.this, EditProfileAdminActivity.class);
            startActivity(editProfileIntent);
        }
        else if (id == R.id.nav_changePassword)
        {
            Intent changePasswordIntent = new Intent(EditBVAActivity.this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);
        }
        else if (id == R.id.nav_logout)
        {
            //stop auto login
            getApplicationContext().getSharedPreferences(PREF_NAME, 0).edit().clear().commit();
            //exit intent - clear previous flags of intent
            logoutUser();
        }
        else if (id == R.id.nav_deleteAccount)
        {

            Intent deleteAccountAdminIntent = new Intent(EditBVAActivity.this, DeleteAccountAdminActivity.class);
            startActivity(deleteAccountAdminIntent);

        }
        else if (id == R.id.nav_adminPanel)
        {
            Intent adminControlPanel = new Intent(EditBVAActivity.this, AdminPanelActivity.class);
            startActivity(adminControlPanel);
        }
        else if (id == R.id.nav_reportActivity)
        {
            Intent reportForAdminIntent = new Intent(EditBVAActivity.this, ReportForAdminActivity.class);
            startActivity(reportForAdminIntent);
        }
        else if (id == R.id.nav_aboutUs)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser()
    {
        Intent startPageIntent = new Intent(EditBVAActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }
}

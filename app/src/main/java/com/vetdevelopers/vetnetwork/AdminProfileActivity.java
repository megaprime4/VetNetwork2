package com.vetdevelopers.vetnetwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AdminProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    private TextView name, address, email, phone, bvcRegNumber, university, designation, accountStatus, navName, navEmail;

    String Name = "";
    String ID = "";
    String Email = "";
    String Phone = "";
    String BVC_number = "";
    String Password = "";
    String University = "";
    String Designation = "";
    String Posting_Area = "";
    String District = "";
    String Division = "";
    String Email_Confirm = "";
    String Rand_Code = "";
    String User_Request = "";
    String User_Type = "";
    String Admin_Email = "";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        name = (TextView) findViewById(R.id.adminProfile_name);
        address = (TextView) findViewById(R.id.adminProfile_address);
        email = (TextView) findViewById(R.id.adminProfile_email);
        phone = (TextView) findViewById(R.id.adminProfile_phone);
        bvcRegNumber = (TextView) findViewById(R.id.adminProfile_bvc);
        university = (TextView) findViewById(R.id.adminProfile_university);
        designation = (TextView) findViewById(R.id.adminProfile_designation);
        accountStatus = (TextView) findViewById(R.id.adminProfile_accountStatus);

        Bundle bundle = new Bundle();

        try
        {
            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            String value = "";
            value = sharedPreferences.getString("Phone", "");

            if (value.equals(""))
            {
                //first time fetch
                bundle = getIntent().getExtras();

                Name = bundle.getString("Name");
                ID = bundle.getString("ID");
                Email = bundle.getString("Email");
                Phone = bundle.getString("Phone");
                BVC_number = bundle.getString("BVC_Number");
                Password = bundle.getString("Password");
                University = bundle.getString("University");
                Designation = bundle.getString("Designation");
                Posting_Area = bundle.getString("Posting_Area");
                District = bundle.getString("District");
                Division = bundle.getString("Division");
                Email_Confirm = bundle.getString("Email_Confirm");
                Rand_Code = bundle.getString("Rand_Code");
                User_Request = bundle.getString("User_Request");
                User_Type = bundle.getString("User_Type");
                Admin_Email = bundle.getString("Admin_Email");


                editor.putString("Name", Name);
                editor.putString("ID", ID);
                editor.putString("Email", Email);
                editor.putString("Phone", Phone);
                editor.putString("BVC_number", BVC_number);
                editor.putString("Password", Password);
                editor.putString("University", University);
                editor.putString("Designation", Designation);
                editor.putString("Posting_Area", Posting_Area);
                editor.putString("District", District);
                editor.putString("Division", Division);
                editor.putString("Email_Confirm", Email_Confirm);
                editor.putString("Rand_Code", Rand_Code);
                editor.putString("User_Request", User_Request);
                editor.putString("User_Type", User_Type);
                editor.putString("Admin_Email", Admin_Email);

                editor.apply();

            }
            else
            {
                //later fetch - shared preference
                Name = sharedPreferences.getString("Name", "");
                ID = sharedPreferences.getString("ID", "");
                Email = sharedPreferences.getString("Email", "");
                Phone = sharedPreferences.getString("Phone", "");
                BVC_number = sharedPreferences.getString("BVC_number", "");
                Password = sharedPreferences.getString("Password", "");
                University = sharedPreferences.getString("University", "");
                Designation = sharedPreferences.getString("Designation", "");
                Posting_Area = sharedPreferences.getString("Posting_Area", "");
                District = sharedPreferences.getString("District", "");
                Division = sharedPreferences.getString("Division", "");
                Email_Confirm = sharedPreferences.getString("Email_Confirm", "");
                Rand_Code = sharedPreferences.getString("Rand_Code", "");
                User_Request = sharedPreferences.getString("User_Request", "");
                User_Type = sharedPreferences.getString("User_Type", "");
                Admin_Email = sharedPreferences.getString("Admin_Email", "");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        ////debug purpose
        System.out.println(".........................................." + Name);
        System.out.println(".........................................." + ID);
        System.out.println(".........................................." + Email);
        System.out.println(".........................................." + Phone);
        System.out.println(".........................................." + BVC_number);
        System.out.println(".........................................." + Password);
        System.out.println(".........................................." + University);
        System.out.println(".........................................." + Designation);
        System.out.println(".........................................." + Posting_Area);
        System.out.println(".........................................." + District);
        System.out.println(".........................................." + Division);
        System.out.println(".........................................." + Email_Confirm);
        System.out.println(".........................................." + Rand_Code);
        System.out.println(".........................................." + User_Request);
        System.out.println(".........................................." + User_Type);
        System.out.println(".........................................." + Admin_Email);
        //System.out.println(dd);
        ///debug purpose


        //code below

        name.setText(Name);

        address.setText(Posting_Area + ", " + District + ", " + Division);
        email.setText(Email);

        phone.setText(Phone);
        bvcRegNumber.setText(BVC_number);
        university.setText(University);
        designation.setText(Designation);
        accountStatus.setText(User_Request);

        NavigationView navigationView = (NavigationView) findViewById(R.id.adminProfile_nav_view);
        View hView = navigationView.getHeaderView(0);
        navName = (TextView) hView.findViewById(R.id.name_navHeader);
        navEmail = (TextView) hView.findViewById(R.id.email_navHeader);
        navName.setText(Name);
        navEmail.setText(Email);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_profile)
        {
            // Handle the camera action
        }
        else if (id == R.id.nav_editProfile)
        {
            Intent editProfileIntent = new Intent(AdminProfileActivity.this, EditProfileAdminActivity.class);
            startActivity(editProfileIntent);
        }
        else if (id == R.id.nav_changePassword)
        {
            Intent changePasswordIntent = new Intent(AdminProfileActivity.this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);
        }
        else if (id == R.id.nav_logout)
        {
            //firebaseAuth.signOut();
            //stop auto login
            getApplicationContext().getSharedPreferences("prefs", 0).edit().clear().commit();
            //exit intent - clear previous flags of intent
            logoutUser();
        }
        else if (id == R.id.nav_deleteAccount)
        {

            Intent deleteAccountAdminIntent = new Intent(AdminProfileActivity.this, DeleteAccountAdminActivity.class);
            startActivity(deleteAccountAdminIntent);

        }
        else if (id == R.id.nav_adminPanel)
        {
            Intent adminControlPanel = new Intent(AdminProfileActivity.this, AdminPanelActivity.class);
            startActivity(adminControlPanel);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser()
    {
        Intent startPageIntent = new Intent(AdminProfileActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }
}

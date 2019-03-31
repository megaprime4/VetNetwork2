package com.vetdevelopers.vetnetwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private TextView name, address, email, phone, bvcRegNumber, university, designation, bvaNumber, bvaDesignation, accountStatus;

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
    String BVA_Member = "";
    String BVA_Number = "";
    String BVA_Designation = "";
    String Email_Confirm = "";
    String Rand_Code = "";
    String User_Request = "";
    String User_Type = "";
    String Admin_Email = "";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        name = (TextView) findViewById(R.id.profile_name);
        address = (TextView) findViewById(R.id.profile_address);
        email = (TextView) findViewById(R.id.profile_email);
        phone = (TextView) findViewById(R.id.profile_phone);
        bvcRegNumber = (TextView) findViewById(R.id.profile_bvc);
        university = (TextView) findViewById(R.id.profile_university);
        designation = (TextView) findViewById(R.id.profile_designation);
        bvaNumber = (TextView) findViewById(R.id.profile_bvaNumber);
        bvaDesignation = (TextView) findViewById(R.id.profile_bvaDesignation);
        accountStatus = (TextView) findViewById(R.id.profile_accountStatus);

        Bundle bundle = new Bundle();

        try
        {
            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            String value = "";
            String browseSearchedUserProfile = "false";
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
                BVA_Member = bundle.getString("BVA_Member");
                BVA_Number = bundle.getString("BVA_Number");
                BVA_Designation = bundle.getString("BVA_Designation");
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
                editor.putString("BVA_Member", BVA_Member);
                editor.putString("BVA_Number", BVA_Number);
                editor.putString("BVA_Designation", BVA_Designation);
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
                BVA_Member = sharedPreferences.getString("BVA_Member", "");
                BVA_Number = sharedPreferences.getString("BVA_Number", "");
                BVA_Designation = sharedPreferences.getString("BVA_Designation", "");
                Email_Confirm = sharedPreferences.getString("Email_Confirm", "");
                Rand_Code = sharedPreferences.getString("Rand_Code", "");
                User_Request = sharedPreferences.getString("User_Request", "");
                User_Type = sharedPreferences.getString("User_Type", "");
                Admin_Email = sharedPreferences.getString("Admin_Email", "");
            }
            //}
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
        System.out.println(".........................................." + BVA_Member);
        System.out.println(".........................................." + BVA_Number);
        System.out.println(".........................................." + BVA_Designation);
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
        if (BVA_Member.equals("No"))
        {
            bvaNumber.setText("Not available");
            bvaDesignation.setText("Not available");
        }
        else if (BVA_Member.equals("Yes"))
        {
            bvaNumber.setText(BVA_Number);
            bvaDesignation.setText(BVA_Designation);
        }
        accountStatus.setText(User_Request);


        NavigationView navigationView = (NavigationView) findViewById(R.id.profile_nav_view);
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
        else if (id == R.id.nav_search)
        {

        }
        else if (id == R.id.nav_editProfile)
        {
            Intent editProfileIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(editProfileIntent);
        }
        else if (id == R.id.nav_changePassword)
        {

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

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logoutUser()
    {
        Intent startPageIntent = new Intent(ProfileActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }
}

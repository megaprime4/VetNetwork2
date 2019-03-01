package com.vetdevelopers.vetnetwork;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;

    //custom popup
    private Dialog mDialogPass, mDialogMsg;
    private EditText popupGetPassword;
    private Button popupConfirmButton, msgPopupOKButton;
    private TextView msgPopupTextView;

    private EditText name, email, phone, bvcNumber, postingArea, bvaNumber;
    private Spinner university, district, division, bvaMember, bvaDesignation;
    private Button changeButton;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        mToolbar = (Toolbar) findViewById(R.id.editProfile_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Profile Setting");

        //custom popup
        mDialogPass = new Dialog(this);  //custom popup window
        mDialogPass.setContentView(R.layout.custompopup_get_password);
        mDialogPass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupGetPassword = (EditText) mDialogPass.findViewById(R.id.getPassword);
        popupConfirmButton = (Button) mDialogPass.findViewById(R.id.getPassword_confirm_Button);
        popupConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();
            }
        });

        mDialogMsg = new Dialog(this);  //custom popup window
        mDialogMsg.setContentView(R.layout.custompopup_success);
        mDialogMsg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        msgPopupTextView = (TextView) mDialogMsg.findViewById(R.id.popup_textView);
        msgPopupOKButton = (Button) mDialogMsg.findViewById(R.id.popup_OK_Button);
        msgPopupOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogMsg.dismiss();
            }
        });



        changeButton = (Button) findViewById(R.id.editProfile_changeButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupGetPassword.setText("");
                mDialogPass.show();
            }
        });



        NavigationView navigationView = (NavigationView) findViewById(R.id.setting_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        name = (EditText) findViewById(R.id.editProfile_name);
        email = (EditText) findViewById(R.id.editProfile_email);
        phone = (EditText) findViewById(R.id.editProfile_phone);
        bvcNumber = (EditText) findViewById(R.id.editProfile_bvcNumber);
        postingArea = (EditText) findViewById(R.id.editProfile_postingArea);
        bvaNumber = (EditText) findViewById(R.id.editProfile_bva_number);

        university = (Spinner) findViewById(R.id.editProfile_university_spinner);
        district = (Spinner) findViewById(R.id.editProfile_district_spinner);
        division = (Spinner) findViewById(R.id.editProfile_division_spinner);
        bvaMember = (Spinner) findViewById(R.id.editProfile_bva_spinner);
        bvaDesignation = (Spinner) findViewById(R.id.editProfile_bvaDesignation_spinner);


        name.setText(sharedPreferences.getString("Name", ""));
        email.setText(sharedPreferences.getString("Email", ""));
        phone.setText(sharedPreferences.getString("Phone", ""));
        bvcNumber.setText(sharedPreferences.getString("BVC_number", ""));

        //spinner dataset...
        //...code here...


    }

    private void checkPassword()
    {
        String password = popupGetPassword.getText().toString().trim();
        String matchPassword = sharedPreferences.getString("Password", "");

        if(password.equals(matchPassword))
        {
            modifyProfile();
        }
        else
        {
            mDialogPass.dismiss();
            msgPopupTextView.setText("Wrong password!");
            mDialogMsg.show();
        }
    }

    private void modifyProfile()
    {
        ////volley code here
        mDialogPass.dismiss();
        msgPopupTextView.setText("Account modified!");
        mDialogMsg.show();
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
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_profile)
        {
            Intent profileIntent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            startActivity(profileIntent);
        }
        else if (id == R.id.nav_search)
        {

        }
        else if (id == R.id.nav_editProfile)
        {

        }
        else if(id == R.id.nav_changePassword)
        {

        }
        else if (id == R.id.nav_logout)
        {
            getApplicationContext().getSharedPreferences("prefs", 0).edit().clear().commit();
            logoutUser();
        } else if (id == R.id.nav_deleteAccount) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser()
    {
        Intent startPageIntent = new Intent(EditProfileActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }
}

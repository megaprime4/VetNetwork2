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
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AdminPanelActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText getEmail, getUserID;
    private Button emailChangeBtn, userSearchBtn, requestListBtn;

    //custom popup
    private Dialog mDialogPass, mDialogMsg;
    private EditText popupGetPassword;
    private Button popupConfirmButton, msgPopupOKButton;
    private TextView msgPopupTextView;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    private Boolean emailBtn = false, searchBtn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        getEmail = (EditText) findViewById(R.id.adminPanel_getEmail);
        emailChangeBtn = (Button) findViewById(R.id.adminPanel_emailChangeBtn);
        getUserID = (EditText) findViewById(R.id.adminPanel_getUserID);
        userSearchBtn = (Button) findViewById(R.id.adminPanel_userSearchBtn);
        requestListBtn = (Button) findViewById(R.id.adminPanel_requestListBtn);

        //onClickListener
        emailChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = getEmail.getText().toString().trim();
                if(Patterns.EMAIL_ADDRESS.matcher(Email).matches() == false) {
                    Toast.makeText(AdminPanelActivity.this, "Invalid Email Address!", Toast.LENGTH_SHORT).show();
                } else {

                    emailBtn = true;
                    searchBtn = false;

                    popupGetPassword.setText("");
                    mDialogPass.show();
                }
            }
        });

        userSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailBtn = false;
                searchBtn = true;

                popupGetPassword.setText("");
                mDialogPass.show();
            }
        });


        //custom popup
        mDialogPass = new Dialog(this);
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

        mDialogMsg = new Dialog(this);
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkPassword()
    {
        String password = popupGetPassword.getText().toString().trim();
        String matchPassword = sharedPreferences.getString("Password", "");

        if(password.equals(matchPassword))
        {
            mDialogPass.dismiss();
            if(emailBtn == true)
            {
                changeEmail();
            }
            else if(searchBtn == true)
            {
                checkUserID(); //will check if the user is registered or not
            }
        }
        else
        {
            mDialogPass.dismiss();
            msgPopupTextView.setText("Wrong password!");
            mDialogMsg.show();
        }
    }

    private void checkUserID()
    {
        //volley code
        msgPopupTextView.setText("check userID ok!");
        mDialogMsg.show();
    }

    private void changeEmail()
    {
        //volley code
        msgPopupTextView.setText("Email changed!");
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
            // Handle the camera action
        }
        else if (id == R.id.nav_search)
        {

        }
        else if (id == R.id.nav_editProfile)
        {
            Intent editProfileIntent = new Intent(AdminPanelActivity.this, EditProfileActivity.class);
            startActivity(editProfileIntent);
        }
        else if(id == R.id.nav_changePassword)
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
        else if (id == R.id.nav_adminPanel)
        {
            //nothing will happen
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser()
    {
        Intent startPageIntent = new Intent(AdminPanelActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }
}

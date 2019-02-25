package com.vetdevelopers.vetnetwork;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartPageActivity extends AppCompatActivity
{

    private Button signUpButton, signInButton, browseButton;
    private ImageView logo;
    //Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        //mDialog = new Dialog(this);  //custom popup window

        signUpButton = (Button) findViewById(R.id.start_signUpButton);
        signInButton = (Button) findViewById(R.id.start_signInButton);
        browseButton = (Button) findViewById(R.id.start_browseButton);
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
    }
}

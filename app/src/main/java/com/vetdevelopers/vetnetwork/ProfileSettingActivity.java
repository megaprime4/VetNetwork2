package com.vetdevelopers.vetnetwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfileSettingActivity extends AppCompatActivity
{

    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        logoutButton = (Button) findViewById(R.id.setting_logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //code here...
            }
        });
    }
}

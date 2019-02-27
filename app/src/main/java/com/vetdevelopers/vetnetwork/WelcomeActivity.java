package com.vetdevelopers.vetnetwork;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity
{


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        try
        {
            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            String value = "";
            value=sharedPreferences.getString(KEY_USERNAME, "");

            if (value.equals(""))
            {
                //System.out.println("no value");
                Thread thread = new Thread()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            sleep(1500);
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                        finally
                        {
                            Intent mainIntent = new Intent(WelcomeActivity.this, StartPageActivity.class);
                            startActivity(mainIntent);
                        }
                    }
                };
                thread.start();
            }
            else
            {
                //System.out.println("shared preference value : " + value);
                Intent intent = new Intent(WelcomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}

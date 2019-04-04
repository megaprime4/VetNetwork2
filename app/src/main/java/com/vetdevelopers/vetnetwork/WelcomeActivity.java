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
    private static final String KEY_USER_TYPE = "User_Type";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        try
        {
            sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            String currentUser = "";
            currentUser = sharedPreferences.getString("Phone", "");

            if (currentUser.equals(""))
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
                            if (sharedPreferences.getString(KEY_USER_TYPE, "").equals("Doctor"))
                            {
                                Intent intent = new Intent(WelcomeActivity.this, ProfileActivity.class);
                                startActivity(intent);
                            }
                            else if (sharedPreferences.getString(KEY_USER_TYPE, "").equals("Admin"))
                            {
                                Intent intent = new Intent(WelcomeActivity.this, AdminProfileActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                };
                thread.start();
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

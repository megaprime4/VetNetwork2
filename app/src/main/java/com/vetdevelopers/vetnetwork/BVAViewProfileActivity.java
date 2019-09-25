package com.vetdevelopers.vetnetwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class BVAViewProfileActivity extends AppCompatActivity {

    private TextView name, email, phone, designation;
    private String ID, Name, Email, Phone, Designation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bvaview_profile);

        name = (TextView) findViewById(R.id.bva_profile_name);
        email = (TextView) findViewById(R.id.bva_profile_email);
        phone = (TextView) findViewById(R.id.bva_profile_phone);
        designation = (TextView) findViewById(R.id.bva_profile_designation);

        Bundle bundle = new Bundle();

        try {
            String browseBVAProfile = getIntent().getStringExtra("browseBVAProfile");

            if (browseBVAProfile.equals("true"))
            {
                bundle = getIntent().getExtras();

                ID = bundle.getString("ID");
                Name = bundle.getString("Name");
                Email = bundle.getString("Email");
                Phone = bundle.getString("Phone");
                Designation = bundle.getString("Designation");

                /*//debug purpose
                System.out.println(".........................................." + ID);
                System.out.println(".........................................." + Name);
                System.out.println(".........................................." + Email);
                System.out.println(".........................................." + Phone);
                System.out.println(".........................................." + Designation);
                *///debug purpose

            }
            else if (browseBVAProfile.equals("false"))
            {
                System.out.println("Warning : This code is possessed by ghosts! :(");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        name.setText(Name);
        email.setText(Email);
        phone.setText(Phone);
        designation.setText(Designation);
    }
}

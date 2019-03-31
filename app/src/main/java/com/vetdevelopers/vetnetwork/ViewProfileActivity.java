package com.vetdevelopers.vetnetwork;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewProfileActivity extends AppCompatActivity
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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

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
            String browseSearchedUserProfile = "false";
            browseSearchedUserProfile = getIntent().getStringExtra("browseSearchedUserProfile");

            if (browseSearchedUserProfile.equals("true"))
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

            }
            else if (browseSearchedUserProfile.equals("false"))
            {

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

    }
}

package com.vetdevelopers.vetnetwork;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity
{

    private EditText name, email, phone, password, retypePassword, bvc_number, designation, posting_area, bvaNumber;
    private Spinner userType_spinner, university_spinner, district_spinner, division_spinner, bva_member_spinner, bva_designation_spinner;
    private TextView bvaDesignation_textview;
    private LinearLayout linearLayout_bvaDesignation;
    private Button signUpButton;

    private Dialog mDialog;
    private TextView popupTextView;
    private Button popupOKButton;

    ProgressDialog progressDialog;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mToolbar = (Toolbar) findViewById(R.id.registration_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign Up");

        progressDialog = new ProgressDialog(this);

        mDialog = new Dialog(this);  //custom popup window
        mDialog.setContentView(R.layout.custompopup_success);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupTextView = (TextView) mDialog.findViewById(R.id.popup_textView);
        popupOKButton = (Button) mDialog.findViewById(R.id.popup_OK_Button);

        userType_spinner = (Spinner) findViewById(R.id.reg_userType_spinner);
        name = (EditText) findViewById(R.id.reg_name);
        email = (EditText) findViewById(R.id.reg_email);
        phone = (EditText) findViewById(R.id.reg_phone);
        bvc_number = (EditText) findViewById(R.id.reg_bvc);
        password = (EditText) findViewById(R.id.reg_pass1);
        retypePassword = (EditText) findViewById(R.id.reg_pass2);
        university_spinner = (Spinner) findViewById(R.id.reg_university_spinner);
        designation = (EditText) findViewById(R.id.reg_designation);
        posting_area = (EditText) findViewById(R.id.reg_postingArea);
        district_spinner = (Spinner) findViewById(R.id.reg_district_spinner);
        division_spinner = (Spinner) findViewById(R.id.reg_division_spinner);
        bva_member_spinner = (Spinner) findViewById(R.id.reg_bva_spinner);
        bvaNumber = (EditText) findViewById(R.id.reg_bva_number);
        bva_designation_spinner = (Spinner) findViewById(R.id.reg_bvaDesignation_spinner);


        ////////////// object hiding //////////////
        bvaDesignation_textview = (TextView) findViewById(R.id.reg_bvaDesignation_textView);
        linearLayout_bvaDesignation = (LinearLayout) findViewById(R.id.reg_linearLayout_bvaDesignation);
        //////////////////////////////////////////


        signUpButton = (Button) findViewById(R.id.reg_signupButton);

        bva_member_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (bva_member_spinner.getSelectedItem().toString().equals("No") || bva_member_spinner.getSelectedItem().toString().equals("Select"))
                {
                    hideSlot1();
                }
                else
                {
                    showSlot1();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                hideSlot1();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final String UserType = userType_spinner.getSelectedItem().toString().trim();
                final String Name = name.getText().toString().trim();
                final String Email = email.getText().toString().trim();
                final String Phone = phone.getText().toString().trim();
                final String BVC_number = bvc_number.getText().toString().trim();
                final String Password = password.getText().toString().trim();
                String RetypePassword = retypePassword.getText().toString().trim();
                final String University = university_spinner.getSelectedItem().toString().trim();
                final String Designation = designation.getText().toString().trim();
                final String Posting_area = posting_area.getText().toString().trim();
                final String District = district_spinner.getSelectedItem().toString().trim();
                final String Division = division_spinner.getSelectedItem().toString().trim();
                final String BVA_member = bva_member_spinner.getSelectedItem().toString().trim();
                final String BVANumber = bvaNumber.getText().toString().trim();
                final String BVA_designation = bva_designation_spinner.getSelectedItem().toString().trim();


                System.out.println(".........................................." + UserType);
                System.out.println(".........................................." + Name);
                System.out.println(".........................................." + Email);
                System.out.println(".........................................." + Phone);
                System.out.println(".........................................." + BVC_number);
                System.out.println(".........................................." + Password);
                System.out.println(".........................................." + RetypePassword);
                System.out.println(".........................................." + University);
                System.out.println(".........................................." + Designation);
                System.out.println(".........................................." + Posting_area);
                System.out.println(".........................................." + District);
                System.out.println(".........................................." + Division);
                System.out.println(".........................................." + BVA_member);
                System.out.println(".........................................." + BVANumber);
                System.out.println(".........................................." + BVA_designation);
                //debug purpose



                registerAccount(UserType, Name, Email, Phone, BVC_number, Password,
                        RetypePassword, University, Designation, Posting_area,
                        District, Division, BVA_member, BVANumber, BVA_designation);


            } //onClick

        }); //onCLick method

        popupOKButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialog.dismiss();
                Intent startPageActivity = new Intent(RegistrationActivity.this, StartPageActivity.class);
                startActivity(startPageActivity);
            }
        });

    } //onCreate

    private void registerAccount(final String UserType, final String Name, final String Email, final String Phone,
                                 final String BVC_number, final String Password, final String RetypePassword,
                                 final String University, final String Designation, final String Posting_area,
                                 final String District, final String Division, final String BVA_member,
                                 final String BVANumber, final String BVA_designation)
    {
        //data input checking
        if (UserType.equals("Select")) {
            Toast.makeText(RegistrationActivity.this, "Please select user type!", Toast.LENGTH_LONG).show();
        }
        else if (Name.equals("") && Email.equals("") && Phone.equals("") && Password.equals("") && RetypePassword.equals("") && University.equals("Select")) {
            Toast.makeText(RegistrationActivity.this, "Please enter Name, Email, Phone, Password, Re-enter Password & University!", Toast.LENGTH_LONG).show();
        }
        else if (Email.equals("") && Phone.equals("") && Password.equals("") && RetypePassword.equals("") && University.equals("Select")) {
            Toast.makeText(RegistrationActivity.this, "Please enter Email, Phone, Password, Re-enter Password & University!", Toast.LENGTH_LONG).show();
        }
        else if (Phone.equals("") && Password.equals("") && RetypePassword.equals("") && University.equals("Select")) {
            Toast.makeText(RegistrationActivity.this, "Please enter Phone, Password, Re-enter Password & University!", Toast.LENGTH_LONG).show();
        }
        else if (Password.equals("") && RetypePassword.equals("") && University.equals("Select")) {
            Toast.makeText(RegistrationActivity.this, "Please enter Password, Re-enter Password & University!", Toast.LENGTH_LONG).show();
        }
        else if (RetypePassword.equals("") && University.equals("Select")) {
            Toast.makeText(RegistrationActivity.this, "Please Re-enter Password & University!", Toast.LENGTH_LONG).show();
        }
        else if (University.equals("Select")) {
            Toast.makeText(RegistrationActivity.this, "Please select University!", Toast.LENGTH_SHORT).show();
        }
        else if (!Password.equals(RetypePassword)) {
            Toast.makeText(RegistrationActivity.this, "Password didn't match!", Toast.LENGTH_SHORT).show();
        }
        else if (Patterns.EMAIL_ADDRESS.matcher(Email).matches() == false) {
            Toast.makeText(RegistrationActivity.this, "E-mail address is not valid!", Toast.LENGTH_SHORT).show();
        } else
        {

            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Creating a new account");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            //server operation (volley) start
            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.REGISTER_URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            if (response.equals("Registration complete! Request sent to admin!"))
                            {
                                progressDialog.dismiss();
                                showokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            if (response.equals("Please check your e-mail!"))
                            {
                                progressDialog.dismiss();
                                showokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            if (response.equals("Connection failed!"))
                            {
                                progressDialog.dismiss();
                                hideokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            if (response.equals("Account already exists!"))
                            {
                                progressDialog.dismiss();
                                hideokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            if (response.equals("Improper request method!"))
                            {
                                progressDialog.dismiss();
                                hideokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            if (response.equals("Invalid platform!"))
                            {
                                progressDialog.dismiss();
                                hideokbutton();
                                popupTextView.setText(response);
                                mDialog.show();
                            }
                            //progressDialog.dismiss();
                            //mDialog.show();
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    if (error instanceof TimeoutError)
                    {
                        Toast.makeText(RegistrationActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NoConnectionError)
                    {
                        Toast.makeText(RegistrationActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof AuthFailureError)
                    {
                        Toast.makeText(RegistrationActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof NetworkError)
                    {
                        Toast.makeText(RegistrationActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ServerError)
                    {
                        Toast.makeText(RegistrationActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                    }
                    else if (error instanceof ParseError)
                    {
                        Toast.makeText(RegistrationActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                    }
                }
            })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(ServerConstants.KEY_USER_TYPE, UserType);
                    params.put(ServerConstants.KEY_NAME, Name);
                    params.put(ServerConstants.KEY_EMAIL, Email);
                    params.put(ServerConstants.KEY_PHONE, Phone);
                    params.put(ServerConstants.KEY_BVC_REG, BVC_number);
                    params.put(ServerConstants.KEY_PASSWORD, Password);
                    params.put(ServerConstants.KEY_UNIVERSITY, University);
                    params.put(ServerConstants.KEY_DESIGNATION, Designation);
                    params.put(ServerConstants.KEY_POSTING_AREA, Posting_area);
                    params.put(ServerConstants.KEY_DISTRICT, District);
                    params.put(ServerConstants.KEY_DIVISION, Division);
                    params.put(ServerConstants.KEY_BVA_MEMBER, BVA_member);
                    params.put(ServerConstants.KEY_BVA_NUMBER, BVANumber);
                    params.put(ServerConstants.KEY_BVA_DESIGNATION, BVA_designation);

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("User-Agent", "VetNetwork"); ////security purpose
                    return headers;
                }
            };

            MySingleton.getInstance(RegistrationActivity.this).addToRequestQueue(stringRequest);


        }
    }


    public void hideokbutton()
    {
        popupOKButton.setVisibility(View.INVISIBLE);
    }

    public void showokbutton()
    {
        popupOKButton.setVisibility(View.VISIBLE);
    }

    private void hideSlot1()
    {
        bvaNumber.setVisibility(View.INVISIBLE);
        linearLayout_bvaDesignation.setVisibility(View.INVISIBLE);
        bvaDesignation_textview.setVisibility(View.INVISIBLE);
        bva_designation_spinner.setVisibility(View.INVISIBLE);
    }

    private void showSlot1()
    {
        bvaNumber.setVisibility(View.VISIBLE);
        linearLayout_bvaDesignation.setVisibility(View.VISIBLE);
        bvaDesignation_textview.setVisibility(View.VISIBLE);
        bva_designation_spinner.setVisibility(View.VISIBLE);
    }
} //class
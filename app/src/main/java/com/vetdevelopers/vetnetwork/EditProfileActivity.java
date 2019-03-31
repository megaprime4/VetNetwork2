package com.vetdevelopers.vetnetwork;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private Toolbar mToolbar;

    //custom popup
    private Dialog mDialogPass, mDialogMsg;
    private EditText popupGetPassword;
    private Button popupConfirmButton, msgPopupOKButton;
    private TextView msgPopupTextView;

    private EditText Name, Email, Phone, BVCRegNumber, PostingArea, BVANumber;
    private Spinner University, District, Division, BVAMember, BVADesignation;
    private Button changeButton;
    private LinearLayout LinearLayout_BVADesignation;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    //spinner work - 1
    Resources resources;
    String[] universityArray, districtArray, divisionArray, bvaMemberArray, bvaDesignationArray;
    //String prevPhone = sharedPreferences.getString("Phone", "");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressDialog = new ProgressDialog(EditProfileActivity.this);

        sharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);


        mToolbar = (Toolbar) findViewById(R.id.editProfile_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Profile");

        //custom popup
        mDialogPass = new Dialog(this);  //custom popup window
        mDialogPass.setContentView(R.layout.custompopup_get_password);
        mDialogPass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupGetPassword = (EditText) mDialogPass.findViewById(R.id.getPassword);
        popupConfirmButton = (Button) mDialogPass.findViewById(R.id.getPassword_confirm_Button);
        popupConfirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkPassword();
            }
        });

        mDialogMsg = new Dialog(this);  //custom popup window
        mDialogMsg.setContentView(R.layout.custompopup_success);
        mDialogMsg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        msgPopupTextView = (TextView) mDialogMsg.findViewById(R.id.popup_textView);
        msgPopupOKButton = (Button) mDialogMsg.findViewById(R.id.popup_OK_Button);
        msgPopupOKButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mDialogMsg.dismiss();
            }
        });


        changeButton = (Button) findViewById(R.id.editProfile_changeButton);
        changeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popupGetPassword.setText("");
                mDialogPass.show();
            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.setting_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Name = (EditText) findViewById(R.id.editProfile_name);
        Email = (EditText) findViewById(R.id.editProfile_email);
        Phone = (EditText) findViewById(R.id.editProfile_phone);
        BVCRegNumber = (EditText) findViewById(R.id.editProfile_bvcNumber);
        PostingArea = (EditText) findViewById(R.id.editProfile_postingArea);
        BVANumber = (EditText) findViewById(R.id.editProfile_bva_number);

        University = (Spinner) findViewById(R.id.editProfile_university_spinner);
        District = (Spinner) findViewById(R.id.editProfile_district_spinner);
        Division = (Spinner) findViewById(R.id.editProfile_division_spinner);
        BVAMember = (Spinner) findViewById(R.id.editProfile_bva_spinner);
        BVADesignation = (Spinner) findViewById(R.id.editProfile_bvaDesignation_spinner);


        Name.setText(sharedPreferences.getString("Name", ""));
        Email.setText(sharedPreferences.getString("Email", ""));
        Phone.setText(sharedPreferences.getString("Phone", ""));
        BVCRegNumber.setText(sharedPreferences.getString("BVC_number", ""));
        PostingArea.setText(sharedPreferences.getString("Posting_Area", ""));
        BVANumber.setText(sharedPreferences.getString("BVA_Number", ""));

        //------------------object hiding-------------------//
        LinearLayout_BVADesignation = (LinearLayout) findViewById(R.id.editProfile_linearLayout_bvaDesignation);
        //--------------------------------------------------//

        //spinner dataset...
        //...code here...
        //spinner work - 2
        resources = getResources();  ///getting xml string array resource
        universityArray = resources.getStringArray(R.array.university);
        int universityIndex = Integer.parseInt("" + Arrays.asList(universityArray).indexOf(sharedPreferences.getString("University", "0")));
        University.setSelection(universityIndex);

        districtArray = resources.getStringArray(R.array.district);
        int districtIndex = Integer.parseInt("" + Arrays.asList(districtArray).indexOf(sharedPreferences.getString("District", "0")));
        District.setSelection(districtIndex);

        divisionArray = resources.getStringArray(R.array.division);
        int divisionIndex = Integer.parseInt("" + Arrays.asList(divisionArray).indexOf(sharedPreferences.getString("Division", "0")));
        Division.setSelection(divisionIndex);

        bvaMemberArray = resources.getStringArray(R.array.bva);
        int bvaMemberIndex = Integer.parseInt("" + Arrays.asList(bvaMemberArray).indexOf(sharedPreferences.getString("BVA_Member", "0")));
        BVAMember.setSelection(bvaMemberIndex);

        bvaDesignationArray = resources.getStringArray(R.array.bva_designation);
        int bvaDesignationIndex = Integer.parseInt("" + Arrays.asList(bvaDesignationArray).indexOf(sharedPreferences.getString("BVA_Designation", "0")));
        BVADesignation.setSelection(bvaDesignationIndex);

        BVAMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (BVAMember.getSelectedItem().toString().equals("No"))
                {
                    hideBVA();
                }
                else
                {
                    showBVA();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                hideBVA();
            }
        });

    }

    private void checkPassword()
    {
        String password = popupGetPassword.getText().toString().trim();
        String matchPassword = sharedPreferences.getString("Password", "");

        if (password.equals(matchPassword))
        {
            mDialogPass.dismiss();
            checkData();
        }
        else
        {
            mDialogPass.dismiss();
            msgPopupTextView.setText("Wrong password!");
            mDialogMsg.show();
        }
    }

    private void checkData()
    {

        //getting data
        String name = Name.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String phone = Phone.getText().toString().trim();
        String bvcRegNumber = BVCRegNumber.getText().toString().trim();
        String university = University.getSelectedItem().toString().trim();
        String postingArea = PostingArea.getText().toString().trim();
        String district = District.getSelectedItem().toString().trim();
        String division = Division.getSelectedItem().toString().trim();
        String bvaMember = BVAMember.getSelectedItem().toString().trim();
        String bvaNumber = BVANumber.getText().toString().trim();
        String bvaDesignation = BVADesignation.getSelectedItem().toString().trim();


        //checking data
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Email cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Phone cannot be empty!", Toast.LENGTH_SHORT).show();
        }
        else if (university.equals("Select"))
        {
            Toast.makeText(this, "Please select your university!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String prevPhone = sharedPreferences.getString("Phone", "");

            editProfle(name, email, phone, prevPhone, bvcRegNumber, university, postingArea,
                    district, division, bvaMember, bvaNumber, bvaDesignation);
        }
    }

    private void editProfle(final String name, final String email, final String phone, final String prevPhone, final String bvcRegNumber, final String university,
                            final String postingArea, final String district, final String division, final String bvaMember,
                            final String bvaNumber, final String bvaDesignation)
    {
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Changing your profile");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.UPDATE_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (response.contains("Connection failed!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("Invalid platform!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("Improper request method!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("sql (update) query error!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("sql (select) query error!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        else if (response.contains("No row selected! Please debug!"))
                        {
                            msgPopupTextView.setText(response);
                            progressDialog.dismiss();
                            mDialogMsg.show();
                        }
                        //server data  retrieve code below...
                        else
                        {

                            try
                            {
                                progressDialog.dismiss();

                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String u = jsonObject.getString("phone");
                                Boolean r = sharedPreferences.getBoolean("remember", false);


                                System.out.println("---------------------------------editprofile u (jasonobject) : " + u);
                                System.out.println("---------------------------------editprofile r : " + r);

                                sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                                editor = sharedPreferences.edit();

                                BundleFunctions bundleFunctions = new BundleFunctions();
                                getApplicationContext().getSharedPreferences("prefs", 0).edit().clear().commit();


                                //editor.putBoolean(KEY_REMEMBER, r);

                                if (r)
                                {
                                    editor.putString(KEY_USERNAME, u);
                                    editor.putBoolean(KEY_REMEMBER, r);
                                    editor.apply();
                                }

                                Intent profileIntent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                                profileIntent.putExtras(bundleFunctions.MakeBundleFromJSON(response));
                                profileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(profileIntent);
                                finish();
                            }
                            catch (Exception e)
                            {
                                progressDialog.dismiss();
                                e.printStackTrace();
                                System.out.println("-----------EditProfileAcitivity : --------string response error occured ------------!!!");
                            }
                        }
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if (error instanceof TimeoutError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    progressDialog.dismiss();
                    Toast.makeText(EditProfileActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(ServerConstants.KEY_NAME, name);
                params.put(ServerConstants.KEY_EMAIL, email);
                params.put(ServerConstants.KEY_PHONE, phone);
                params.put(ServerConstants.KEY_PREV_PHONE, prevPhone);
                params.put(ServerConstants.KEY_BVC_REG, bvcRegNumber);
                params.put(ServerConstants.KEY_UNIVERSITY, university);
                params.put(ServerConstants.KEY_POSTING_AREA, postingArea);
                params.put(ServerConstants.KEY_DISTRICT, district);
                params.put(ServerConstants.KEY_DIVISION, division);
                params.put(ServerConstants.KEY_BVA_MEMBER, bvaMember);
                params.put(ServerConstants.KEY_BVA_NUMBER, bvaNumber);
                params.put(ServerConstants.KEY_BVA_DESIGNATION, bvaDesignation);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("User-Agent", "VetNetwork");  ////security purpose
                return headers;
            }
        };

        MySingleton.getInstance(EditProfileActivity.this).addToRequestQueue(stringRequest);

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
            Intent profileIntent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            startActivity(profileIntent);
        }
        else if (id == R.id.nav_search)
        {

        }
        else if (id == R.id.nav_editProfile)
        {

        }
        else if (id == R.id.nav_changePassword)
        {

        }
        else if (id == R.id.nav_logout)
        {
            getApplicationContext().getSharedPreferences("prefs", 0).edit().clear().commit();
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
        Intent startPageIntent = new Intent(EditProfileActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }

    private void hideBVA()
    {
        BVANumber.setVisibility(View.INVISIBLE);
        LinearLayout_BVADesignation.setVisibility(View.INVISIBLE);
    }

    private void showBVA()
    {
        BVANumber.setVisibility(View.VISIBLE);
        LinearLayout_BVADesignation.setVisibility(View.VISIBLE);
    }
}

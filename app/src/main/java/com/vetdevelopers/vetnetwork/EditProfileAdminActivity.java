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
import android.widget.Button;
import android.widget.EditText;
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

public class EditProfileAdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private Toolbar mToolbar;

    //custom popup
    private Dialog mDialogPass, mDialogMsg;
    private EditText popupGetPassword;
    private Button popupConfirmButton, msgPopupOKButton;
    private TextView msgPopupTextView;

    private EditText Name, Email, Phone, BVCRegNumber, PostingArea;
    private Spinner University, District, Division;
    private Button changeButton;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";

    //spinner work - 1
    Resources resources;
    private String[] universityArray, districtArray, divisionArray, bvaMemberArray, bvaDesignationArray;
    //String prevPhone = sharedPreferences.getString("Phone", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_admin);

        /*mToolbar = (Toolbar) findViewById(R.id.editProfileAdmin_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Edit Profile");*/

        progressDialog = new ProgressDialog(EditProfileAdminActivity.this);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

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

        changeButton = (Button) findViewById(R.id.editProfileAdmin_changeButton);
        changeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popupGetPassword.setText("");
                mDialogPass.show();
            }
        });

        Name = (EditText) findViewById(R.id.editProfileAdmin_name);
        Email = (EditText) findViewById(R.id.editProfileAdmin_email);
        Phone = (EditText) findViewById(R.id.editProfileAdmin_phone);
        BVCRegNumber = (EditText) findViewById(R.id.editProfileAdmin_bvcNumber);
        PostingArea = (EditText) findViewById(R.id.editProfileAdmin_postingArea);

        University = (Spinner) findViewById(R.id.editProfileAdmin_university_spinner);
        District = (Spinner) findViewById(R.id.editProfileAdmin_district_spinner);
        Division = (Spinner) findViewById(R.id.editProfileAdmin_division_spinner);

        Name.setText(sharedPreferences.getString("Name", ""));
        Email.setText(sharedPreferences.getString("Email", ""));
        Phone.setText(sharedPreferences.getString("Phone", ""));
        BVCRegNumber.setText(sharedPreferences.getString("BVC_number", ""));
        PostingArea.setText(sharedPreferences.getString("Posting_Area", ""));

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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


        //checking data
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Sorry! Name is required!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Sorry! Email is required!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Sorry! Phone number must be given!", Toast.LENGTH_SHORT).show();
        }
        else if (university.equals("Select"))
        {
            Toast.makeText(this, "Please select your university!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String prevPhone = sharedPreferences.getString("Phone", "");

            editProfle(name, email, phone, prevPhone, bvcRegNumber, university, postingArea,
                    district, division);
        }
    }

    private void editProfle(final String name, final String email, final String phone, final String prevPhone, final String bvcRegNumber, final String university,
                            final String postingArea, final String district, final String division)
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

                                Intent profileIntent = new Intent(EditProfileAdminActivity.this, AdminProfileActivity.class);
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
                progressDialog.dismiss();

                if (error instanceof TimeoutError)
                {
                    Toast.makeText(EditProfileAdminActivity.this, "Timeout error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NoConnectionError)
                {
                    Toast.makeText(EditProfileAdminActivity.this, "No connection error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    Toast.makeText(EditProfileAdminActivity.this, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof NetworkError)
                {
                    Toast.makeText(EditProfileAdminActivity.this, "Network error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ServerError)
                {
                    Toast.makeText(EditProfileAdminActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                }
                else if (error instanceof ParseError)
                {
                    Toast.makeText(EditProfileAdminActivity.this, "JSON parse error!", Toast.LENGTH_SHORT).show();
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

        MySingleton.getInstance(EditProfileAdminActivity.this).addToRequestQueue(stringRequest);

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_adminProfile)
        {
            Intent profileIntent = new Intent(EditProfileAdminActivity.this, AdminProfileActivity.class);
            startActivity(profileIntent);
        }
        else if (id == R.id.nav_editProfile)
        {
            //nothing to do
        }
        else if (id == R.id.nav_changePassword)
        {

            Intent changePasswordIntent = new Intent(EditProfileAdminActivity.this, ChangePasswordActivity.class);
            startActivity(changePasswordIntent);

        }
        else if (id == R.id.nav_logout)
        {
            getApplicationContext().getSharedPreferences("prefs", 0).edit().clear().commit();
            logoutUser();
        }
        else if (id == R.id.nav_deleteAccount)
        {

            Intent deleteAccountUserIntent = new Intent(EditProfileAdminActivity.this, DeleteAccountUserActivity.class);
            startActivity(deleteAccountUserIntent);

        }
        else if (id == R.id.nav_adminPanel)
        {

            Intent adminPanelIntent = new Intent(EditProfileAdminActivity.this, AdminPanelActivity.class);
            startActivity(adminPanelIntent);

        }
        else if (id == R.id.nav_reportActivity)
        {
            Intent reportForAdminIntent = new Intent(EditProfileAdminActivity.this, ReportForAdminActivity.class);
            startActivity(reportForAdminIntent);
        }
        else if (id == R.id.nav_aboutUs)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser()
    {
        Intent startPageIntent = new Intent(EditProfileAdminActivity.this, StartPageActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }
}

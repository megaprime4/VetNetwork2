package com.vetdevelopers.vetnetwork;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

public class BundleFunctions
{

    public JSONObject MakeJsonObjectFromBundle(Bundle bundle)
    {
        JSONObject json = new JSONObject();
        Set<String> keys = bundle.keySet();
        for (String key : keys)
        {
            try
            {
                // json.put(key, bundle.get(key)); see edit below
                json.put(key, JSONObject.wrap(bundle.get(key)));
            }
            catch (JSONException e)
            {
                //Handle exception here
            }
        }
        return json;
    }


    public Bundle MakeBundleFromJSON(String response)
    {
        Bundle bundle = new Bundle();
        try
        {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            bundle.putString("Name", jsonObject.getString("name"));
            bundle.putString("ID", jsonObject.getString("id"));
            bundle.putString("Email", jsonObject.getString("email"));
            bundle.putString("Phone", jsonObject.getString("phone"));
            bundle.putString("BVC_Number", jsonObject.getString("bvc_reg"));
            bundle.putString("Password", jsonObject.getString("password"));
            bundle.putString("University", jsonObject.getString("university"));
            bundle.putString("Designation", jsonObject.getString("designation"));
            bundle.putString("Posting_Area", jsonObject.getString("posting_area"));
            bundle.putString("District", jsonObject.getString("district"));
            bundle.putString("Division", jsonObject.getString("division"));
            bundle.putString("BVA_Member", jsonObject.getString("bva_member"));
            bundle.putString("BVA_Number", jsonObject.getString("bva_number"));
            bundle.putString("BVA_Designation", jsonObject.getString("bva_designation"));
            bundle.putString("Email_Confirm", jsonObject.getString("email_confirm"));
            bundle.putString("Rand_Code", jsonObject.getString("rand_code"));
            bundle.putString("User_Request", jsonObject.getString("user_request"));
            bundle.putString("User_Type", jsonObject.getString("user_type"));
            bundle.putString("Admin_Email", jsonObject.getString("admin_email"));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            System.out.println("-----------BundleFunctions : --------string response error occured ------------!!!");

        }
        return bundle;
    }

    public ArrayList<String> MakeArrayListFormJSON(String response)
    {
        ArrayList<String> al = new ArrayList<String>();
        try
        {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            al.add(jsonObject.getString("name"));
            al.add(jsonObject.getString("id"));
            al.add(jsonObject.getString("email"));
            al.add(jsonObject.getString("phone"));
            al.add(jsonObject.getString("bvc_reg"));
            al.add(jsonObject.getString("password"));
            al.add(jsonObject.getString("university"));
            al.add(jsonObject.getString("designation"));
            al.add(jsonObject.getString("posting_area"));
            al.add(jsonObject.getString("district"));
            al.add(jsonObject.getString("division"));
            al.add(jsonObject.getString("bva_member"));
            al.add(jsonObject.getString("bva_number"));
            al.add(jsonObject.getString("bva_designation"));
            al.add(jsonObject.getString("email_confirm"));
            al.add(jsonObject.getString("rand_code"));
            al.add(jsonObject.getString("user_request"));
            al.add(jsonObject.getString("user_type"));
            al.add(jsonObject.getString("admin_email"));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return al;
    }

    public ArrayList<String> MakeArrayListWithNewLineFormJSON(String response)
    {
        ArrayList<String> al = new ArrayList<String>();
        try
        {
            JSONArray jsonArray = new JSONArray(response);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            al.add(jsonObject.getString("name" + "\n"));
            al.add(jsonObject.getString("id" + "\n"));
            al.add(jsonObject.getString("email" + "\n"));
            al.add(jsonObject.getString("phone" + "\n"));
            al.add(jsonObject.getString("bvc_reg" + "\n"));
            al.add(jsonObject.getString("password" + "\n"));
            al.add(jsonObject.getString("university" + "\n"));
            al.add(jsonObject.getString("designation" + "\n"));
            al.add(jsonObject.getString("posting_area" + "\n"));
            al.add(jsonObject.getString("district" + "\n"));
            al.add(jsonObject.getString("division" + "\n"));
            al.add(jsonObject.getString("bva_member" + "\n"));
            al.add(jsonObject.getString("bva_number" + "\n"));
            al.add(jsonObject.getString("bva_designation" + "\n"));
            al.add(jsonObject.getString("email_confirm" + "\n"));
            al.add(jsonObject.getString("rand_code" + "\n"));
            al.add(jsonObject.getString("user_request" + "\n"));
            al.add(jsonObject.getString("rand_code" + "\n"));
            al.add(jsonObject.getString("user_request" + "\n"));
            al.add(jsonObject.getString("user_type" + "\n"));
            al.add(jsonObject.getString("admin_email" + "\n"));
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return al;
    }

    public void setSharedPreference()
    {

    }

}

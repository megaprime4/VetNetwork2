package com.vetdevelopers.vetnetwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyAdapterForRecycleViewForAll extends RecyclerView.Adapter<MyAdapterForRecycleViewForAll.MyAdapterForRecycleView2ViewHolder>
{
    List<ListItemForRecycleView1> listItems;
    OnItemClickListener mListener;
    Context context;


    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public class MyAdapterForRecycleView2ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView mTextView1;
        public TextView mTextView2;


        public MyAdapterForRecycleView2ViewHolder(View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            mTextView1 = itemView.findViewById(R.id.all_users_Name);
            mTextView2 = itemView.findViewById(R.id.all_users_Phone);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);

                            System.out.println(mTextView2.getText().toString());


                            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.SEARCH_FOR_DISPLAY_PROFILE,
                                    new Response.Listener<String>()
                                    {
                                        @Override
                                        public void onResponse(String response)
                                        {
                                            if (response.contains("Connection failed!"))
                                            {
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                            else if (response.contains("Please check your ID & Password!"))
                                            {
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                            else if (response.contains("Improper request method!"))
                                            {
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                            else if (response.contains("Invalid platform!"))
                                            {
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                            else if (response.contains("sql error"))
                                            {
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {

                                                System.out.println("this is response : " + response);

                                                Bundle bundle = new Bundle();
                                                try
                                                {
                                                    JSONArray jsonArray = new JSONArray(response);
                                                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                                                    bundle.putString("Name", jsonObject.getString("name"));
                                                    bundle.putString("ID", "");
                                                    bundle.putString("Email", jsonObject.getString("email"));
                                                    bundle.putString("Phone", jsonObject.getString("phone"));
                                                    bundle.putString("BVC_Number", jsonObject.getString("bvc_reg"));
                                                    bundle.putString("Password", "");
                                                    bundle.putString("University", jsonObject.getString("university"));
                                                    bundle.putString("Designation", jsonObject.getString("designation"));
                                                    bundle.putString("Posting_Area", jsonObject.getString("posting_area"));
                                                    bundle.putString("District", jsonObject.getString("district"));
                                                    bundle.putString("Division", jsonObject.getString("division"));
                                                    bundle.putString("Email_Confirm", "");
                                                    bundle.putString("Rand_Code", "");
                                                    bundle.putString("User_Request", jsonObject.getString("user_request"));
                                                    bundle.putString("User_Type", "");
                                                    bundle.putString("Admin_Email", "");

                                                    Intent profileIntent = new Intent(context, ViewProfileActivity.class);
                                                    profileIntent.putExtra("browseSearchedUserProfile", "true");
                                                    profileIntent.putExtras(bundle);
                                                    context.startActivity(profileIntent);

                                                }
                                                catch (Exception e)
                                                {
                                                    e.printStackTrace();
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
                                        Toast.makeText(context, "Timeout error!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (error instanceof NoConnectionError)
                                    {
                                        Toast.makeText(context, "No connection error!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (error instanceof AuthFailureError)
                                    {
                                        Toast.makeText(context, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (error instanceof NetworkError)
                                    {
                                        Toast.makeText(context, "Network error!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (error instanceof ServerError)
                                    {
                                        Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (error instanceof ParseError)
                                    {
                                        Toast.makeText(context, "JSON parse error!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            {
                                @Override
                                protected Map<String, String> getParams()
                                {
                                    Map<String, String> params = new HashMap<String, String>();

                                    params.put("phone", mTextView2.getText().toString());
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

                            MySingleton.getInstance(context).addToRequestQueue(stringRequest);

                        }
                    }
                }
            });
        }
    }

    public MyAdapterForRecycleViewForAll(List<ListItemForRecycleView1> listItems, Context context)
    {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public MyAdapterForRecycleView2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users_display_layout_for_all, parent, false);
        MyAdapterForRecycleView2ViewHolder evh = new MyAdapterForRecycleView2ViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MyAdapterForRecycleView2ViewHolder holder, int position)
    {
        //MyAdapterForRecycleView2Item currentItem = mMyAdapterForRecycleView2List.get(position);

        ListItemForRecycleView1 listItem = listItems.get(position);

        holder.mTextView1.setText(listItem.getName());
        holder.mTextView2.setText(listItem.getPhone());
    }

    @Override
    public int getItemCount()
    {
        return listItems.size();
    }
}

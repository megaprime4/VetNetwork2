package com.vetdevelopers.vetnetwork;

import android.app.ProgressDialog;
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

public class DisplayBVAmemberModelAdapter extends RecyclerView.Adapter<DisplayBVAmemberModelAdapter.DisplayBVAmemberModelAdapterViewHolder>
{
    public List<DisplayBVAmemberModel> searchResultList;
    DisplayBVAmemberModelAdapter.OnItemClickListener mListener;
    Context context;
    ProgressDialog progressDialog;

    public DisplayBVAmemberModelAdapter(List<DisplayBVAmemberModel> searchResultList,Context context)
    {
        this.searchResultList = searchResultList;
        this.context = context;
    }

    public class DisplayBVAmemberModelAdapterViewHolder extends RecyclerView.ViewHolder
    {
        public TextView allUserName, allUserPhone;

        public DisplayBVAmemberModelAdapterViewHolder(View itemView, final DisplayBVAmemberModelAdapter.OnItemClickListener listener)
        {
            super(itemView);
            //view1=itemView;

            allUserName = itemView.findViewById(R.id.all_users_Name);
            allUserPhone = itemView.findViewById(R.id.all_users_Phone);

            progressDialog =  new ProgressDialog(context);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //allUserPhoneGet = allUserPhone.getText().toString();
                    if (listener != null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);

                            System.out.println(allUserPhone.getText().toString());

                            progressDialog.setTitle("Please Wait");
                            progressDialog.setMessage("Operation in progress");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.VIEW_BVA_PROFILE_URL,
                                    new Response.Listener<String>()
                                    {
                                        @Override
                                        public void onResponse(String response)
                                        {
                                            if (response.contains("Connection failed!"))
                                            {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                            else if (response.contains("Invalid platform!"))
                                            {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                            else if (response.contains("Improper request method!"))
                                            {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                            else if (response.contains("SQL (select) query error!"))
                                            {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                            else if (response.contains("No member found!"))
                                            {
                                                progressDialog.dismiss();
                                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                //System.out.println("this is response : " + response);

                                                Bundle bundle = new Bundle();
                                                try
                                                {
                                                    JSONArray jsonArray = new JSONArray(response);
                                                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                                                    bundle.putString("ID", jsonObject.getString("id"));
                                                    bundle.putString("Name", jsonObject.getString("name"));
                                                    bundle.putString("Phone", jsonObject.getString("phone"));
                                                    bundle.putString("Email", jsonObject.getString("email"));
                                                    bundle.putString("Designation", jsonObject.getString("designation"));

                                                    Intent profileIntent = new Intent(context, BVAViewProfileActivity.class);
                                                    profileIntent.putExtra("browseBVAProfile", "true");
                                                    profileIntent.putExtras(bundle);
                                                    progressDialog.dismiss();
                                                    context.startActivity(profileIntent);

                                                }
                                                catch (Exception e)
                                                {
                                                    progressDialog.dismiss();
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
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Timeout error!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (error instanceof NoConnectionError)
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Internet connection required!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (error instanceof AuthFailureError)
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Authentication failure error!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (error instanceof NetworkError)
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Network error!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (error instanceof ServerError)
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (error instanceof ParseError)
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "JSON parse error!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            {
                                @Override
                                protected Map<String, String> getParams()
                                {
                                    Map<String, String> params = new HashMap<String, String>();

                                    params.put("phone", allUserPhone.getText().toString());
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

    public DisplayBVAmemberModelAdapter.DisplayBVAmemberModelAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_display_bvamember_custom_layout_for_recycler_view, parent, false);
        DisplayBVAmemberModelAdapter.DisplayBVAmemberModelAdapterViewHolder evh = new DisplayBVAmemberModelAdapter.DisplayBVAmemberModelAdapterViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(final DisplayBVAmemberModelAdapter.DisplayBVAmemberModelAdapterViewHolder holder, int position)
    {
        DisplayBVAmemberModel currentItem = searchResultList.get(position);

        final DisplayBVAmemberModel listItem = searchResultList.get(position);

        holder.allUserName.setText(listItem.getName());
        holder.allUserPhone.setText(listItem.getPhone());

    }

    @Override
    public int getItemCount()
    {
        return searchResultList.size();
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(DisplayBVAmemberModelAdapter.OnItemClickListener listener)
    {
        mListener = listener;
    }
}

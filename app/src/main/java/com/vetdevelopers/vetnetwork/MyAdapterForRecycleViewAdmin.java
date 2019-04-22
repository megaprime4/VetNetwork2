package com.vetdevelopers.vetnetwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class MyAdapterForRecycleViewAdmin extends RecyclerView.Adapter<MyAdapterForRecycleViewAdmin.MyAdapterForRecycleView1ViewHolder>
{
    List<ListItemForRecycleView1> listItems;
    OnItemClickListener mListener;
    Context context;

    public MyAdapterForRecycleViewAdmin(List<ListItemForRecycleView1> listItems, Context context)
    {
        this.listItems = listItems;
        this.context = context;
    }

    public class MyAdapterForRecycleView1ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView allUserName, allUserPhone;
        public Button accept, profileView, reject;

        public MyAdapterForRecycleView1ViewHolder(View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            //view1=itemView;

            allUserName = itemView.findViewById(R.id.all_users_Name);
            allUserPhone = itemView.findViewById(R.id.all_users_Phone);
            accept = itemView.findViewById(R.id.userReq_acceptButton);
            profileView = itemView.findViewById(R.id.userReq_viewButton);
            reject = itemView.findViewById(R.id.userReq_rejectButton);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //allUserPhoneGet = allUserPhone.getText().toString();
                }
            });
        }
    }

    @Override
    public MyAdapterForRecycleView1ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users_display_layout_admin, parent, false);
        MyAdapterForRecycleView1ViewHolder evh = new MyAdapterForRecycleView1ViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(final MyAdapterForRecycleView1ViewHolder holder, int position)
    {
        ListItemForRecycleView1 currentItem = listItems.get(position);

        final ListItemForRecycleView1 listItem = listItems.get(position);

        holder.allUserName.setText(listItem.getName());
        holder.allUserPhone.setText(listItem.getPhone());

        //allUserPhoneGet = holder.allUserPhone.getText().toString();

        holder.accept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(context, "accept", Toast.LENGTH_SHORT).show();


                final String allUserPhone = holder.allUserPhone.getText().toString();

                int position = holder.getAdapterPosition();
                System.out.println("holder position : "+position);
                listItems.remove(position);
                notifyItemRemoved(position);


                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.ADMIN_ACCEPT_URL,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                if (response.contains("Connection failed!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No row selected! Please debug!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (update) query error!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("User accepted!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
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

                        params.put(ServerConstants.KEY_ALL_USER_PHONE, allUserPhone);
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
        });
        holder.profileView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(context, holder.allUserPhone.getText().toString(), Toast.LENGTH_SHORT).show();
                final String allUserPhone = holder.allUserPhone.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.ADMIN_VIEW_URL,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                if (response.contains("Connection failed!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No row selected! Please debug!"))
                                {
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

                        params.put(ServerConstants.KEY_ALL_USER_PHONE, allUserPhone);
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
        });
        holder.reject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(context, "reject", Toast.LENGTH_SHORT).show();

                final String allUserPhone = holder.allUserPhone.getText().toString();

                int position = holder.getAdapterPosition();
                System.out.println("holder position : "+position);
                listItems.remove(position);
                notifyItemRemoved(position);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.ADMIN_REJECT_URL,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                if (response.contains("Connection failed!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Invalid platform!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Improper request method!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (select) query error!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("No row selected! Please debug!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("SQL (update) query error!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("User rejected!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
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

                        params.put(ServerConstants.KEY_ALL_USER_PHONE, allUserPhone);
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
        });
    }


    @Override

    public int getItemCount()
    {
        return listItems.size();
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

}

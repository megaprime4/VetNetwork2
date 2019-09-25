package com.vetdevelopers.vetnetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class EditBVAmemberModelAdapter extends  RecyclerView.Adapter<EditBVAmemberModelAdapter.EditBVAmemberModelAdapterViewHolder> {
    public List<EditBVAmemberModel> searchResultList;
    OnItemClickListener mListener;
    Context context;

    ProgressDialog progressDialog;

    public EditBVAmemberModelAdapter(List<EditBVAmemberModel> searchResultList,Context context)
    {
        this.searchResultList = searchResultList;
        this.context = context;
    }

    public class EditBVAmemberModelAdapterViewHolder extends RecyclerView.ViewHolder
    {
        public TextView allUserName, allUserPhone;
        public Button editButton, deleteButton;

        public EditBVAmemberModelAdapterViewHolder(View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            //view1=itemView;

            allUserName = itemView.findViewById(R.id.all_users_Name);
            allUserPhone = itemView.findViewById(R.id.all_users_Phone);
            editButton = itemView.findViewById(R.id.all_users_editButton);
            deleteButton = itemView.findViewById(R.id.all_users_deleteButton);

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

    public EditBVAmemberModelAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_edit_bvamember_custom_layout_for_recycler_view, parent, false);
        EditBVAmemberModelAdapterViewHolder evh = new EditBVAmemberModelAdapterViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(final EditBVAmemberModelAdapterViewHolder holder, int position)
    {
        EditBVAmemberModel currentItem = searchResultList.get(position);

        final EditBVAmemberModel listItem = searchResultList.get(position);

        holder.allUserName.setText(listItem.getName());
        holder.allUserPhone.setText(listItem.getPhone());

        final String Id = listItem.getId();
        final String Name = listItem.getName();
        final String Phone = listItem.getPhone();
        final String Email = listItem.getEmail();
        final String Designation = listItem.getDesignation();

        //allUserPhoneGet = holder.allUserPhone.getText().toString();
        progressDialog = new ProgressDialog(context);

        holder.deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String allUserPhone = holder.allUserPhone.getText().toString();

                progressDialog.setTitle("Please Wait");
                progressDialog.setMessage("Operation in progress");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerConstants.ADMIN_BVA_DELETE_URL,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                progressDialog.dismiss();

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
                                else if (response.contains("SQL (delete) query error!"))
                                {
                                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                }
                                else if (response.contains("Warning : Entry deleted!"))
                                {
                                    int position = holder.getAdapterPosition();
                                    System.out.println("holder position : " + position);
                                    searchResultList.remove(position);
                                    notifyItemRemoved(position);
                                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
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
                            Toast.makeText(context, "Timeout error!", Toast.LENGTH_SHORT).show();
                        }
                        else if (error instanceof NoConnectionError)
                        {
                            Toast.makeText(context, "Internet connection required!", Toast.LENGTH_SHORT).show();
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

        holder.editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Bundle bundle = new Bundle();
                bundle.putString("Id", Id);
                bundle.putString("Name", Name);
                bundle.putString("Phone", Phone);
                bundle.putString("Email", Email);
                bundle.putString("Designation", Designation);
                Intent EditBVAProfileIntent = new Intent(context, EditBVAProfileActivity.class);
                EditBVAProfileIntent.putExtras(bundle);
                context.startActivity(EditBVAProfileIntent);

            }

        });

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

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }
}

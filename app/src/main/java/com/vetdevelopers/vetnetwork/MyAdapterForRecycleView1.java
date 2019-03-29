package com.vetdevelopers.vetnetwork;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapterForRecycleView1 extends RecyclerView.Adapter<MyAdapterForRecycleView1.ViewHolder>
{
    private List<ListItemForRecycleView1> listItems;
    private Context context;
    public MyAdapterForRecycleView1(List<ListItemForRecycleView1> listItems, Context context)
    {
        this.listItems=listItems;
        this.context=context;
    }
    @NonNull
    @Override
    public MyAdapterForRecycleView1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_users_display_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        ListItemForRecycleView1 listItem = listItems.get(position);
        holder.all_users_Name.setText(listItem.getName());
        holder.all_users_Phone.setText(listItem.getPhone());
    }

    public int getItemCount()
    {
        return listItems.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView all_users_Name;
        public TextView all_users_Phone;

        public ViewHolder(View itemView)
        {
            super(itemView);

            all_users_Name = (TextView) itemView.findViewById (R.id.all_users_Name);
            all_users_Phone = (TextView) itemView.findViewById(R.id.all_users_Phone);
        }
    }

}

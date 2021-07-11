package com.example.group14_vaccinationapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserRecycleViewAdapter extends RecyclerView.Adapter<UserRecycleViewAdapter.UserRecyclerViewHolder>{

    Context context;
    String[] NRICs;
    String[] names;

    public UserRecycleViewAdapter(Context context, String[] NRICs, String[] names) {
        this.context = context;
        this.NRICs = NRICs;
        this.names = names;
    }

    @NonNull
    @NotNull

    @Override
    public UserRecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_view_users_layout, parent, false);

        view.findViewById(R.id.cv_adminViewUsers).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                TextView NRIC =  v.findViewById(R.id.txt_adminVIewUsersLayout_ICs);
                String getIC = NRIC.getText().toString();

                Intent intent = new Intent(context, UserDetailInfo.class);
                intent.putExtra("SelectedNRIC", getIC);
                context.startActivity(intent);
                //Toast.makeText(context, getIC,Toast.LENGTH_SHORT).show();
            }
        });

        return new UserRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserRecycleViewAdapter.UserRecyclerViewHolder holder, int position) {
        holder.txt_adminVIewUsersLayout_ICs.setText(NRICs[position]);
        holder.txt_adminVIewUsersLayout_names.setText(names[position]);
    }



    @Override
    public int getItemCount() {
        return NRICs.length;
    }

    public class UserRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txt_adminVIewUsersLayout_ICs;
        TextView txt_adminVIewUsersLayout_names;


        public UserRecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            this.txt_adminVIewUsersLayout_ICs = itemView.findViewById(R.id.txt_adminVIewUsersLayout_ICs);
            this.txt_adminVIewUsersLayout_names = itemView.findViewById(R.id.txt_adminVIewUsersLayout_names);
        }
    }
}

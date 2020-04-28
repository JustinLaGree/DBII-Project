package com.example.db2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.EditStudentAccountActivity;
import com.example.db2.LandingParentActivity;
import com.example.db2.R;

public class MeetingInfoAdapter extends RecyclerView.Adapter<MeetingInfoAdapter.MyViewHolder> {

    String names[];
    String emails[];
    boolean isMentor;
    Context ct;
    boolean isParent;




    public MeetingInfoAdapter(Context ct_, String names_[], String emails_[], boolean isMentor_, boolean isParent_)
    {
        this.ct = ct_;
        names = names_;
        emails = emails_;
        isMentor = isMentor_;
        isParent = isParent_;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.view_participants_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameText.setText(names[position]);
        holder.emailText.setText(emails[position]);

        if(isParent) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent editStudentIntent = new Intent(ct, EditStudentAccountActivity.class);
                    editStudentIntent.putExtra("comesFromParent", true);
                    editStudentIntent.putExtra("name", names[position]);
                    editStudentIntent.putExtra("email", emails[position]);
                    ct.startActivity(editStudentIntent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameText;
        TextView emailText;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textView_studentName);
            emailText = itemView.findViewById(R.id.textView_studentEmail);
        }
    }




}
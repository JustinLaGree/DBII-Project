package com.example.db2.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.R;
import com.example.db2.helpers.EnrollmentType;
import com.example.db2.models.User;

import java.util.List;

public class MeetingAdminEnrollAdapter extends RecyclerView.Adapter<MeetingAdminEnrollAdapter.MeetingViewHolder> {

    private Context context;
    private List<User> users;
    private EnrollmentType enrollmentType;
    private boolean isEnrollable;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout constraintLayout;

        public MeetingViewHolder(ConstraintLayout cl) {
            super(cl);
            constraintLayout = cl;
        }
    }

    public MeetingAdminEnrollAdapter(Context context, List<User> users, EnrollmentType enrollmentType, boolean isEnrollable) {
        this.context = context;
        this.users = users;
        this.enrollmentType = enrollmentType;
        this.isEnrollable = isEnrollable;
    }

    @NonNull
    @Override
    public MeetingAdminEnrollAdapter.MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        ConstraintLayout cl = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partial_admin_student, parent, false);

        MeetingViewHolder mvh = new MeetingViewHolder(cl);
        return mvh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MeetingAdminEnrollAdapter.MeetingViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

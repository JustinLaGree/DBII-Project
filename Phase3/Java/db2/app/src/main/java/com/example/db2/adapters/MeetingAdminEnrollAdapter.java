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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.LandingAdminActivity;
import com.example.db2.R;
import com.example.db2.helpers.EnrollmentType;
import com.example.db2.helpers.QueryExecution;
import com.example.db2.helpers.UserSession;
import com.example.db2.models.Enroll;
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
        ConstraintLayout cl = holder.constraintLayout;

        //get user
        User user = users.get(position);

        //get text and button UI elements
        TextView userText = (TextView) cl.getViewById(R.id.user_textView);
        Button enrollButton = (Button) cl.getViewById(R.id.enroll_button);

        //set text
        userText.setText(user.name);

        if (!this.isEnrollable){
            enrollButton.setEnabled(false);
            enrollButton.setVisibility(View.INVISIBLE);
        }
        else {
            enrollButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    enrollUser(user);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    private String getMentTable(){
        switch (this.enrollmentType){
            case MENTEE:
                return "mentee";
            case MENTOR:
                return "mentor";
            case NONE:
            default:
                return null;
        }
    }

    private String getEnrollTable(){
        switch (this.enrollmentType){
            case MENTEE:
                return "enroll";
            case MENTOR:
                return "enroll2";
            case NONE:
            default:
                return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void enrollUser(User user){
        String mentName = getMentTable();
        String enrollName = getEnrollTable();

        if (mentName == null || enrollName == null){
            Intent landingAdminIntent = new Intent(context, LandingAdminActivity.class);
            context.startActivity(landingAdminIntent);
        }

        String query = String.format("INSERT INTO %ss (%s_id) VALUES (%d)", mentName, mentName, user.id);
        QueryExecution.executeQuery(query);

        query = String.format("INSERT INTO %s (meet_id, %s_id) VALUES (%d, %d)", enrollName, mentName, MeetingAdminAdapter.targetMeeting.meet_id, user.id);
        QueryExecution.executeQuery(query);

        ((AppCompatActivity)context).finish();
        context.startActivity(((AppCompatActivity)context).getIntent());
    }
}

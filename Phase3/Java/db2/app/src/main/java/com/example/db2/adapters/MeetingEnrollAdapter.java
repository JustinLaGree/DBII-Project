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
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.EnrollMenteeStudentActivity;
import com.example.db2.EnrollMentorStudentActivity;
import com.example.db2.MeetingInfoActivity;
import com.example.db2.R;
import com.example.db2.ViewEnrolledMeetingsActivity;
import com.example.db2.helpers.QueryExecution;

public class MeetingEnrollAdapter extends RecyclerView.Adapter<MeetingEnrollAdapter.MyViewHolder> {

    String grades[];
    String meeting_names[];
    String dates[];
    String enrollment[];
    String times[];
    String meeting_ids[];
    Context ct;
    int enroll_state; // 1=Mentee 2=Mentor 3=View
    int userID;


    public MeetingEnrollAdapter(Context ct_, String grades_[], String meeting_names_[], String dates_[], String enrollment_[], String times_[], String meeting_ids_[], int enroll_state_, int userID_)
    {
        ct = ct_;
        grades = grades_;
        meeting_names = meeting_names_;
        dates = dates_;
        enrollment = enrollment_;
        times = times_;
        meeting_ids = meeting_ids_;
        enroll_state = enroll_state_;
        userID = userID_;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.meeting_enroll_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.grademeetingText.setText((Integer.parseInt(grades[position]) + 5) + "th Grade " + meeting_names[position]);
        holder.timeText.setText(times[position] + " PM");
        holder.dateText.setText(dates[position]);
        holder.capacityText.setText(enrollment[position] + "/6");
        if(enroll_state == 1) {
            holder.enrollButton.setText("Enroll");
            holder.vieworbulkMeetingButton.setEnabled(true);
            holder.vieworbulkMeetingButton.setAlpha(1);
            holder.vieworbulkMeetingButton.setText("Bulk\n Enroll");
        }
        else if (enroll_state == 2)
        {
            holder.enrollButton.setText("Enroll");
            holder.vieworbulkMeetingButton.setEnabled(false);
            holder.vieworbulkMeetingButton.setAlpha(0);
        }
        else if (enroll_state == 3) {
            holder.enrollButton.setText("Unenroll");
            holder.vieworbulkMeetingButton.setEnabled(true);
            holder.vieworbulkMeetingButton.setAlpha(1);
            holder.vieworbulkMeetingButton.setText("View");
        }

        holder.enrollButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                verifyAndEnroll(enroll_state, position);
            }
        });

        holder.vieworbulkMeetingButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                vieworbulkMeeting(enroll_state, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meeting_names.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView grademeetingText;
        TextView timeText;
        TextView dateText;
        TextView capacityText;
        Button enrollButton;
        Button vieworbulkMeetingButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            grademeetingText = itemView.findViewById(R.id.textView_grademeetingrow);
            timeText = itemView.findViewById(R.id.textView_timerow);
            dateText = itemView.findViewById(R.id.textView_daterow);
            capacityText = itemView.findViewById(R.id.textView_capacityrow);
            enrollButton = itemView.findViewById(R.id.button_enrollrow);
            vieworbulkMeetingButton = itemView.findViewById(R.id.button_vieworbulkMeeting);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void verifyAndEnroll(int enroll_state, int position)
    {
        String query;
        if(enroll_state == 1) //enroll as mentee
        {
            final Intent enrollMenteeStudentActivity = new Intent(ct, EnrollMenteeStudentActivity.class);
            query = String.format("INSERT INTO mentees VALUES ('%s')", userID);
            QueryExecution.executeQuery(query);
            query = String.format("INSERT INTO enroll VALUES ('%s', '%s')", meeting_ids[position], userID);
            QueryExecution.executeQuery(query);
            ct.startActivity(enrollMenteeStudentActivity);
        }
        else if(enroll_state == 2) //enroll as mentor
        {
            final Intent enrollMentorStudentActivity = new Intent(ct, EnrollMentorStudentActivity.class);
            query = String.format("INSERT INTO mentors VALUES ('%s')", userID);
            QueryExecution.executeQuery(query);
            query = String.format("INSERT INTO enroll2 VALUES ('%s', '%s')", meeting_ids[position], userID);
            QueryExecution.executeQuery(query);
            ct.startActivity(enrollMentorStudentActivity);
        }
        else if(enroll_state == 3) //unenroll
        {
            final Intent viewEnrolledMeetingsActivity = new Intent(ct, ViewEnrolledMeetingsActivity.class);
            query = String.format("DELETE FROM enroll WHERE meet_id = '%s' AND mentee_id = '%s'", meeting_ids[position], userID);
            QueryExecution.executeQuery(query);
            query = String.format("DELETE FROM enroll2 WHERE meet_id = '%s' AND mentor_id = '%s'", meeting_ids[position], userID);
            QueryExecution.executeQuery(query);
            ct.startActivity(viewEnrolledMeetingsActivity);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void vieworbulkMeeting(int enroll_state, int position)
    {
        String query;
        if(enroll_state == 1)
        {
            final Intent enrollMenteeStudentActivity = new Intent(ct, EnrollMenteeStudentActivity.class);
            query = String.format("INSERT INTO enroll SELECT meet_id, '%s' FROM meetings  WHERE group_id = '%s' AND meet_name = '%s'", userID, grades[position], meeting_names[position]);
            QueryExecution.executeQuery(query);
            ct.startActivity(enrollMenteeStudentActivity);
        }
        else if (enroll_state == 3)
        {
            final Intent meetingInfoIntent = new Intent(ct, MeetingInfoActivity.class);
            ct.startActivity(meetingInfoIntent);
        }
    }
}

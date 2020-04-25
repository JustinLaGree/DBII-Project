package com.example.db2.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.db2.helpers.UserSession;
import com.example.db2.models.Enroll2;

import java.util.List;

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
    String query;



    public MeetingEnrollAdapter(Context ct, String grades[], String meeting_names[], String dates[], String enrollment[], String times[], String meeting_ids[], int enroll_state, int userID)
    {
        this.ct = ct;
        this.grades = grades;
        this.meeting_names = meeting_names;
        this.dates = dates;
        this.enrollment = enrollment;
        this.times = times;
        this.meeting_ids = meeting_ids;
        this.enroll_state = enroll_state;
        this.userID = userID;
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
            AlertDialog.Builder builder = new AlertDialog.Builder(ct);
            builder.setTitle("How would you like to unenroll?");

            builder.setPositiveButton("Unenroll in Bulk",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            query = String.format("DELETE FROM enroll WHERE mentee_id = '%s' AND meet_id IN (SELECT meet_id FROM meetings WHERE meet_name = '%s' AND group_id = '%s')", userID, meeting_names[position], grades[position]);
                            QueryExecution.executeQuery(query);
                            ct.startActivity(viewEnrolledMeetingsActivity);
                            dialog.cancel();
                        }
                    });
            builder.setNegativeButton("Unenroll Individually",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {

                            query = String.format("DELETE FROM enroll WHERE meet_id = '%s' AND mentee_id = '%s'", meeting_ids[position], userID);
                            QueryExecution.executeQuery(query);
                            query = String.format("DELETE FROM enroll2 WHERE meet_id = '%s' AND mentor_id = '%s'", meeting_ids[position], userID);
                            QueryExecution.executeQuery(query);
                            ct.startActivity(viewEnrolledMeetingsActivity);
                            dialog.cancel();
                        }
                    });
            builder.create().show();

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

            if(isMentor(meeting_ids[position])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ct);
                builder.setTitle("What would you like to view?");
                builder.setPositiveButton("View Participants",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final Intent meetingInfoIntent = new Intent(ct, MeetingInfoActivity.class);
                                meetingInfoIntent.putExtra("meetingID", meeting_ids[position]);
                                ct.startActivity(meetingInfoIntent);
                                dialog.cancel();
                            }
                        });
                builder.setNegativeButton("View Meeting Materials",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                viewMaterials(position);
                                dialog.cancel();
                            }
                        });
                builder.create().show();
            }
            else
            {
                viewMaterials(position);
            }


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    boolean isMentor(String meetingID)
    {
        String query = String.format("SELECT * FROM enroll2 WHERE mentor_id = '%s' AND meet_id = '%s'", UserSession.getInstance().id, meetingID);
        QueryExecution.executeQuery(query);
        List<Enroll2> enroll2s = QueryExecution.getResponse(Enroll2.class);
        if(enroll2s.size() > 0) return true;
        else return false;
    }

    /*Justin, you can write your code here. I've passed the position and as a parameter
        and sent the meeting ID with the intent. You just have to retrieve it in the materials activity*/
    void viewMaterials(int position)
    {
        /*
        final Intent viewMaterialsIntent = new Intent(ct, viewMaterialsActivity.class);
        viewMaterialsIntent.putExtra("meetingID", meeting_ids[position]);
        */

    }
}

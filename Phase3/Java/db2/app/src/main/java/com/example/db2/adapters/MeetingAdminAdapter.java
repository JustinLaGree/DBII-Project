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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.EnrollAdminActivity;
import com.example.db2.R;
import com.example.db2.helpers.ListHelpers;
import com.example.db2.models.Enroll;
import com.example.db2.models.Enroll2;
import com.example.db2.models.Group;
import com.example.db2.models.Meeting;
import com.example.db2.models.TimeSlot;

import java.text.SimpleDateFormat;
import java.util.List;

public class MeetingAdminAdapter extends RecyclerView.Adapter<MeetingAdminAdapter.MeetingViewHolder> {

    private Context context;
    private List<Meeting> meetings;
    private List<TimeSlot> timeSlots;
    private List<Group> groups;
    private List<Enroll> enrolls;
    private List<Enroll2> enroll2s;

    public static Meeting targetMeeting = null;

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

    public MeetingAdminAdapter(Context context, List<Meeting> meetings, List<TimeSlot> timeSlots, List<Group> groups, List<Enroll> enrolls, List<Enroll2> enroll2s) {
        MeetingAdminAdapter.targetMeeting = null;
        this.context = context;
        this.meetings = meetings;
        this.timeSlots = timeSlots;
        this.groups = groups;
        this.enrolls = enrolls;
        this.enroll2s = enroll2s;
    }

    @NonNull
    @Override
    public MeetingAdminAdapter.MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        ConstraintLayout cl = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partial_meeting_admin_item, parent, false);

        MeetingViewHolder mvh = new MeetingViewHolder(cl);
        return mvh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MeetingAdminAdapter.MeetingViewHolder holder, int position) {
        //get the inner-most Constraint layout-- this is where all textViews are
        ConstraintLayout cl = ((CardView) holder.constraintLayout.getViewById(R.id.inner_cardView))
                .findViewById(R.id.inner_cl);

        //get the meeting and corresponding objects related to that particular meeting
        Meeting meeting = meetings.get(position);
        TimeSlot timeSlot = ListHelpers.single(timeSlots, ts -> ts.time_slot_id == meeting.time_slot_id);
        Group group = ListHelpers.single(groups, g -> g.group_id == meeting.group_id);
        int numMentees = ListHelpers.where(enrolls, e -> e.meet_id == meeting.meet_id).size();
        int numMentors = ListHelpers.where(enroll2s, e -> e.meet_id == meeting.meet_id).size();

        //get all of the relevant textViews
        TextView nameText = (TextView) cl.getViewById(R.id.name_textView);
        TextView dateText = (TextView) cl.getViewById(R.id.date_textView);
        TextView timeText = (TextView) cl.getViewById(R.id.time_textView);
        TextView numMenteesText = (TextView) cl.getViewById(R.id.numMentees_textView);
        TextView numMentorsText = (TextView) cl.getViewById(R.id.numMentors_textView);

        //set the text for all textViews
        String nameStr = String.format("%dth Grade %s", group.description, meeting.meet_name);
        nameText.setText(nameStr);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        dateText.setText(formatter.format(meeting.date));

        timeText.setText(timeSlot.start_time.toString());

        String numMenteesStr = String.format("%d/%d mentees", numMentees, 6);
        numMenteesText.setText(numMenteesStr);

        String numMentorsStr = String.format("%d/%d mentors", numMentors, 3);
        numMentorsText.setText(numMentorsStr);

        //get all of the relevant buttons
        Button enroll_button = (Button) cl.getViewById(R.id.enroll_button);
        Button materials_button = (Button) cl.getViewById(R.id.materials_button);

        //add listeners for all buttons
        enroll_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                viewEnrollAdminActivity(meeting);
            }
        });

        //add listeners for all buttons
        materials_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                viewMaterialsAdminActivity(meeting);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    private void viewEnrollAdminActivity(Meeting meeting) {
        MeetingAdminAdapter.targetMeeting = meeting;

        final Intent enrolAdminIntent = new Intent(context, EnrollAdminActivity.class);
        enrolAdminIntent.putExtra("BACK_ACTIVITY", context.getClass().getName());
        context.startActivity(enrolAdminIntent);
    }

    private void viewMaterialsAdminActivity(Meeting meeting) {
        MeetingAdminAdapter.targetMeeting = meeting;
    }
}

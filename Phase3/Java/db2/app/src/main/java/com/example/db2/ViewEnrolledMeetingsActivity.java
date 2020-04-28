package com.example.db2;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.example.db2.adapters.MeetingEnrollAdapter;
import com.example.db2.helpers.QueryExecution;
import com.example.db2.helpers.UserSession;
import com.example.db2.models.Enroll;
import com.example.db2.models.Meeting;
import com.example.db2.models.TimeSlot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

//view the meetings that a student is enrolled in
public class ViewEnrolledMeetingsActivity extends BaseLogoutBackActivity {

    String grades[];
    String meeting_names[];
    String dates[];
    String enrollment[];
    String times[];
    String meeting_ids[];
    int enroll_state = 3;
    int userID = UserSession.getInstance().id;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_view_enrolled_meetings);
        super.onCreate(savedInstanceState);

        final RecyclerView recyclerview_enrolledMeetings = findViewById(R.id.recyclerview_enrolledMeetings);

        //populate all meeting info
        populateArrays();

        //build adapter and recyclerView using meeting info
        MeetingEnrollAdapter meetingEnrollAdapter = new MeetingEnrollAdapter(this, grades, meeting_names, dates, enrollment, times, meeting_ids, enroll_state, userID);
        recyclerview_enrolledMeetings.setAdapter(meetingEnrollAdapter);
        recyclerview_enrolledMeetings.setLayoutManager(new LinearLayoutManager(this));





    }

    //populate meetings and ancillary info
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void populateArrays()
    {
        String date = "2000-01-01";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            date = dateTimeFormatter.format(now);
        }


        //get meetings that are available for the student
        String query = String.format("SELECT * FROM meetings WHERE meet_id IN " +
                        "               (SELECT meet_id FROM enroll WHERE mentee_id = '%s') " +
                        "                   OR meet_id IN " +
                        "                       (SELECT meet_id FROM enroll2 WHERE mentor_id = '%s')",
                UserSession.getInstance().id, UserSession.getInstance().id);

        QueryExecution.executeQuery(query);
        List<Meeting> meetings = QueryExecution.getResponse(Meeting.class);

        grades = new String[meetings.size()];
        meeting_names = new String[meetings.size()];
        dates = new String[meetings.size()];
        enrollment = new String[meetings.size()];
        times = new String[meetings.size()];
        meeting_ids = new String[meetings.size()];

        for(int i = 0; i < meetings.size(); i++)
        {
            grades[i] = String.valueOf(meetings.get(i).group_id);
            meeting_names[i] = meetings.get(i).meet_name;
            dates[i] = dateFormat.format(meetings.get(i).date);
            meeting_ids[i] = String.valueOf(meetings.get(i).meet_id);
        }

        for(int i = 0; i < meetings.size(); i++)
        {
            query = String.format("SELECT * FROM time_slot WHERE time_slot_id = (SELECT time_slot_id FROM meetings WHERE meet_id = '%s')", meeting_ids[i]);
            QueryExecution.executeQuery(query);
            List<TimeSlot> timeSlots = QueryExecution.getResponse(TimeSlot.class);
            String temp = String.valueOf(timeSlots.get(0).start_time);
            times[i] = temp.substring(0,5);
        }

        for(int i = 0; i < meetings.size(); i++)
        {
            query = String.format("SELECT * FROM enroll WHERE meet_id = '%s'", meeting_ids[i]);
            QueryExecution.executeQuery(query);
            List<Enroll> capacity = QueryExecution.getResponse(Enroll.class);
            enrollment[i] = String.valueOf(capacity.size());
        }


    }
}

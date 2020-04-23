package com.example.db2;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.adapters.MeetingAdminAdapter;
import com.example.db2.helpers.QueryExecution;
import com.example.db2.models.Enroll;
import com.example.db2.models.Enroll2;
import com.example.db2.models.Group;
import com.example.db2.models.Meeting;
import com.example.db2.models.TimeSlot;

import java.util.List;

public class LandingAdminActivity extends BaseLogoutOnlyActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_admin);

        String query = "SELECT * FROM meetings ORDER BY date ASC";
        QueryExecution.executeQuery(query);

        List<Meeting> meetings = QueryExecution.getResponse(Meeting.class);

        query = "SELECT * FROM time_slot";
        QueryExecution.executeQuery(query);

        List<TimeSlot> timeSlots = QueryExecution.getResponse(TimeSlot.class);

        query = "SELECT * FROM groups";
        QueryExecution.executeQuery(query);

        List<Group> groups = QueryExecution.getResponse(Group.class);

        query = "SELECT * FROM enroll";
        QueryExecution.executeQuery(query);

        List<Enroll> enrolls = QueryExecution.getResponse(Enroll.class);

        query = "SELECT * FROM enroll2";
        QueryExecution.executeQuery(query);

        List<Enroll2> enroll2s = QueryExecution.getResponse(Enroll2.class);

        final RecyclerView meetingRecyclerView = findViewById(R.id.meeting_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        meetingRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        meetingRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        MeetingAdminAdapter mAdapter = new MeetingAdminAdapter(meetings, timeSlots, groups, enrolls, enroll2s);
        meetingRecyclerView.setAdapter(mAdapter);
    }
}

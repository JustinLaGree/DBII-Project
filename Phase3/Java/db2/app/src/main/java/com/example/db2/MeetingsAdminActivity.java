package com.example.db2;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.adapters.MeetingAdapter;
import com.example.db2.helpers.QueryExecution;
import com.example.db2.models.Meeting;

import java.util.List;

public class MeetingsAdminActivity extends BaseLogoutActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_meetings_admin);
        super.onCreate(savedInstanceState);

        String query = "SELECT * FROM meetings ORDER BY date ASC";
        QueryExecution.executeQuery(query);

        List<Meeting> meetings = QueryExecution.getResponse(Meeting.class);

        final RecyclerView meetingRecyclerView  = findViewById(R.id.meeting_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        meetingRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        meetingRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        MeetingAdapter mAdapter = new MeetingAdapter(meetings);
        meetingRecyclerView.setAdapter(mAdapter);
    }
}

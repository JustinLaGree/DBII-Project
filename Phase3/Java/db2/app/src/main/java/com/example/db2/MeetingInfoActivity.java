package com.example.db2;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.adapters.MeetingEnrollAdapter;
import com.example.db2.adapters.MeetingInfoAdapter;
import com.example.db2.helpers.QueryExecution;
import com.example.db2.helpers.UserSession;
import com.example.db2.models.Enroll;
import com.example.db2.models.Enroll2;
import com.example.db2.models.TimeSlot;
import com.example.db2.models.User;

import java.util.List;

public class MeetingInfoActivity extends BaseLogoutBackActivity {

    String names[];
    String emails[];

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_meeting_info);
        super.onCreate(savedInstanceState);

        String meetingID = getIntent().getStringExtra("meetingID");
        boolean isMentor = checkIfMentor(meetingID);


        if(isMentor)
        {

            final RecyclerView recyclerview_enrolledParticipants = findViewById(R.id.recyclerview_enrolledParticipants);

            populateArrays(meetingID);

            MeetingInfoAdapter meetingInfoAdapter = new MeetingInfoAdapter(this, names, emails, isMentor);
            recyclerview_enrolledParticipants.setAdapter(meetingInfoAdapter);
            recyclerview_enrolledParticipants.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    boolean checkIfMentor(String meetingID)
    {
        String query = String.format("SELECT * FROM enroll2 WHERE mentor_id = '%s' AND meet_id = '%s'", UserSession.getInstance().id, meetingID);
        QueryExecution.executeQuery(query);
        List<Enroll2> enroll2s = QueryExecution.getResponse(Enroll2.class);
        if(enroll2s.size() > 0) return true;
        else return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void populateArrays(String meetingID)
    {
        String query = String.format("SELECT * FROM users WHERE id IN (SELECT mentee_id FROM enroll WHERE meet_id = '%s') OR id IN (SELECT mentor_id FROM enroll2 WHERE meet_id = '%s')", meetingID, meetingID);
        QueryExecution.executeQuery(query);
        List<User> users = QueryExecution.getResponse(User.class);

        names = new String[users.size()];
        emails = new String[users.size()];

        for(int i = 0; i < users.size(); i++)
        {
            names[i] = users.get(i).name;
            emails[i] = users.get(i).email;
        }
    }


}

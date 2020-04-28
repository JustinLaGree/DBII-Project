package com.example.db2;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.adapters.MeetingInfoAdapter;
import com.example.db2.helpers.QueryExecution;
import com.example.db2.helpers.UserSession;
import com.example.db2.models.Enroll2;
import com.example.db2.models.User;

import java.util.List;

//show info for a meeting
public class MeetingInfoActivity extends BaseLogoutBackActivity {

    String names_of_mentors[];
    String emails_of_mentors[];
    String names_of_mentees[];
    String emails_of_mentees[];

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_meeting_info);
        super.onCreate(savedInstanceState);

        //get the target meetingId
        String meetingID = getIntent().getStringExtra("meetingID");
        boolean isMentor = checkIfMentor(meetingID);


        if(isMentor)
        {

            final RecyclerView recyclerview_enrolledParticipants = findViewById(R.id.recyclerview_menteeParticipants);
            final RecyclerView recyclerview_enrolledMentors = findViewById(R.id.recyclerview_mentorParticipants);

            populateArrays(meetingID);

            MeetingInfoAdapter meetingInfoAdapter = new MeetingInfoAdapter(this, names_of_mentees, emails_of_mentees, isMentor, false);
            recyclerview_enrolledParticipants.setAdapter(meetingInfoAdapter);
            recyclerview_enrolledParticipants.setLayoutManager(new LinearLayoutManager(this));
            MeetingInfoAdapter meetingInfoAdapter_mentors = new MeetingInfoAdapter(this, names_of_mentors, emails_of_mentors, isMentor, false);
            recyclerview_enrolledMentors.setAdapter(meetingInfoAdapter_mentors);
            recyclerview_enrolledMentors.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    //check to see if user is mentor in meeting
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    boolean checkIfMentor(String meetingID)
    {
        String query = String.format("SELECT * FROM enroll2 WHERE mentor_id = '%s' AND meet_id = '%s'", UserSession.getInstance().id, meetingID);
        QueryExecution.executeQuery(query);
        List<Enroll2> enroll2s = QueryExecution.getResponse(Enroll2.class);
        if(enroll2s.size() > 0) return true;
        else return false;
    }

    //populate ancillary info
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void populateArrays(String meetingID)
    {
        String query = String.format("SELECT * FROM users WHERE id IN (SELECT mentee_id FROM enroll WHERE meet_id = '%s')", meetingID);
        QueryExecution.executeQuery(query);
        List<User> mentees = QueryExecution.getResponse(User.class);

        names_of_mentees = new String[mentees.size()];
        emails_of_mentees = new String[mentees.size()];

        for(int i = 0; i < mentees.size(); i++)
        {
            names_of_mentees[i] = mentees.get(i).name;
            emails_of_mentees[i] = mentees.get(i).email;
        }

        query = String.format("SELECT * FROM users WHERE id IN (SELECT mentor_id FROM enroll2 WHERE meet_id = '%s')", meetingID);
        QueryExecution.executeQuery(query);
        List<User> mentors = QueryExecution.getResponse(User.class);

        names_of_mentors = new String[mentors.size()];
        emails_of_mentors = new String[mentors.size()];

        for(int i = 0; i < mentors.size(); i++)
        {
            names_of_mentors[i] = mentors.get(i).name;
            emails_of_mentors[i] = mentors.get(i).email;
        }
    }


}

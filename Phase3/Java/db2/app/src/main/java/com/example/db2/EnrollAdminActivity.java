package com.example.db2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.db2.adapters.MeetingAdminAdapter;
import com.example.db2.adapters.MeetingAdminEnrollAdapter;
import com.example.db2.helpers.EnrollmentType;
import com.example.db2.helpers.ListHelpers;
import com.example.db2.helpers.QueryExecution;
import com.example.db2.models.Enroll;
import com.example.db2.models.Enroll2;
import com.example.db2.models.Group;
import com.example.db2.models.Meeting;
import com.example.db2.models.Student;
import com.example.db2.models.TimeSlot;
import com.example.db2.models.User;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class EnrollAdminActivity extends BaseLogoutBackActivity {

    private Meeting meeting;
    private List<Meeting> meetings;
    private TimeSlot timeSlot;
    private Group group;
    private List<User> users;
    private List<Student> students;
    private List<Enroll> enrolls;
    private List<Enroll2> enroll2s;

    @Override

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_enroll_admin);
        super.onCreate(savedInstanceState);

        this.meeting = MeetingAdminAdapter.targetMeeting;

        runRelevantQueries();

        buildMeetingCard();

        TextView menteeEnrolledText = findViewById(R.id.mentee_enrolled_textView);
        TextView menteeAvailableText = findViewById(R.id.mentee_available_textView);
        TextView mentorEnrolledText = findViewById(R.id.mentor_enrolled_textView);
        TextView mentorAvailableText = findViewById(R.id.mentor_available_textView);

        List<User> enrolledMentees = ListHelpers.where(users, u -> ListHelpers.any(enrolls, e -> e.mentee_id == u.id));
        List<User> enrolledMentors = ListHelpers.where(users, u -> ListHelpers.any(enroll2s, e -> e.mentor_id == u.id));

        List<User> availableMentees;
        if (enrolledMentees.size() < 6) {
            List<Student> availableStudents = ListHelpers.where(students, s -> !ListHelpers.any(enrolls, e -> e.mentee_id == s.student_id) && s.grade == group.description);

            for (int i = 0; i < availableStudents.size(); i++) {
                Student student = availableStudents.get(0);

                List<Enroll> studentEnrolls = ListHelpers.where(enrolls, e -> e.mentee_id == student.student_id);
                List<Meeting> userMeetings = ListHelpers.where(meetings, m -> ListHelpers.any(studentEnrolls, e -> e.meet_id == m.meet_id));

                for (Meeting userMeeting: userMeetings) {
                    long diff = Math.abs(userMeeting.date.getTime() - meeting.date.getTime());
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    if ((diffDays < 2 && meeting.meet_name == userMeeting.meet_name) || (diffDays == 0 && meeting.date == userMeeting.date)){
                        availableStudents.remove(i);
                        i--;
                        continue;
                    }
                }
            }
            availableMentees = ListHelpers.where(users, u -> ListHelpers.any(availableStudents, s -> s.student_id == u.id));
        }
        else {
            availableMentees = new ArrayList();
        }

        List<User> availableMentors;
        if (enrolledMentors.size() < 3) {
            List<Student> availableStudents = ListHelpers.where(students, s -> !ListHelpers.any(enroll2s, e -> e.mentor_id == s.student_id));

            for (int i = 0; i < availableStudents.size(); i++) {
                Student student = availableStudents.get(0);

                List<Enroll2> studentEnrolls = ListHelpers.where(enroll2s, e -> e.mentor_id == student.student_id);
                List<Meeting> userMeetings = ListHelpers.where(meetings, m -> ListHelpers.any(studentEnrolls, e -> e.meet_id == m.meet_id));

                for (Meeting userMeeting: userMeetings) {
                    long diff = Math.abs(userMeeting.date.getTime() - meeting.date.getTime());
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    if (diffDays < 2){
                        availableStudents.remove(i);
                        i--;
                        continue;
                    }
                }
            }
            availableMentors = ListHelpers.where(users, u -> ListHelpers.any(availableStudents, s -> s.student_id == u.id));
        }
        else {
            availableMentors = new ArrayList();
        }

        if (enrolledMentees.isEmpty()){
            menteeEnrolledText.setVisibility(View.INVISIBLE);
        }
        if (availableMentees.isEmpty()){
            menteeAvailableText.setVisibility(View.INVISIBLE);
        }
        if (enrolledMentors.isEmpty()){
            mentorEnrolledText.setVisibility(View.INVISIBLE);
        }
        if (availableMentors.isEmpty()){
            mentorAvailableText.setVisibility(View.INVISIBLE);
        }

        setupUserAdapter(R.id.enrolled_mentee_recyclerView, enrolledMentees, EnrollmentType.MENTEE, false);
        setupUserAdapter(R.id.enrolled_mentor_recyclerView, enrolledMentors, EnrollmentType.MENTOR, false);
        setupUserAdapter(R.id.available_mentee_recyclerView, availableMentees, EnrollmentType.MENTEE, true);
        setupUserAdapter(R.id.available_mentor_recyclerView, availableMentors, EnrollmentType.MENTOR, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void runRelevantQueries() {
        String query = "SELECT * FROM meetings";
        QueryExecution.executeQuery(query);

        this.meetings = QueryExecution.getResponse(Meeting.class);

        query = String.format("SELECT * FROM time_slot WHERE time_slot_id=%d", meeting.time_slot_id);
        QueryExecution.executeQuery(query);

        List<TimeSlot> timeSlots = QueryExecution.getResponse(TimeSlot.class);
        if (timeSlots != null && timeSlots.size() > 0) {
            this.timeSlot = timeSlots.get(0);
        }

        query = String.format("SELECT * FROM groups WHERE group_id=%d", meeting.group_id);
        QueryExecution.executeQuery(query);

        List<Group> groups = QueryExecution.getResponse(Group.class);
        if (groups != null && groups.size() > 0) {
            this.group = groups.get(0);
        }

        query = "SELECT * FROM users";
        QueryExecution.executeQuery(query);

        this.users = QueryExecution.getResponse(User.class);

        query = "SELECT * FROM students";
        QueryExecution.executeQuery(query);

        this.students = QueryExecution.getResponse(Student.class);

        query = String.format("SELECT * FROM enroll WHERE meet_id=%d", meeting.meet_id);
        QueryExecution.executeQuery(query);

        this.enrolls = QueryExecution.getResponse(Enroll.class);

        query = String.format("SELECT * FROM enroll2 WHERE meet_id=%d", meeting.meet_id);
        QueryExecution.executeQuery(query);

        this.enroll2s = QueryExecution.getResponse(Enroll2.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildMeetingCard(){
        //get the meeting and corresponding objects related to that particular meeting
        int numMentees = ListHelpers.where(enrolls, e -> e.meet_id == meeting.meet_id).size();
        int numMentors = ListHelpers.where(enroll2s, e -> e.meet_id == meeting.meet_id).size();

        //get all of the relevant textViews
        TextView nameText = findViewById(R.id.name_textView);
        TextView dateText = findViewById(R.id.date_textView);
        TextView timeText = findViewById(R.id.time_textView);
        TextView numMenteesText = findViewById(R.id.numMentees_textView);
        TextView numMentorsText = findViewById(R.id.numMentors_textView);

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
    }

    private void setupUserAdapter(int recyclerViewId, List<User> targetUsers, EnrollmentType enrollmentType, boolean isEnrollable){
        final RecyclerView meetingRecyclerView = findViewById(recyclerViewId);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        meetingRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        meetingRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        MeetingAdminEnrollAdapter mAdapter = new MeetingAdminEnrollAdapter(this, targetUsers, enrollmentType, isEnrollable);
        meetingRecyclerView.setAdapter(mAdapter);
    }
}

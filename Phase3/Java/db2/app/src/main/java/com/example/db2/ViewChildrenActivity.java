package com.example.db2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.example.db2.adapters.MeetingInfoAdapter;
import com.example.db2.helpers.QueryExecution;
import com.example.db2.helpers.UserSession;
import com.example.db2.models.Enroll2;
import com.example.db2.models.User;

import java.util.List;

//Show all students under the parents
public class ViewChildrenActivity extends BaseLogoutBackActivity {

    String names_of_children[];
    String emails_of_children[];


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_view_children);
        super.onCreate(savedInstanceState);



        final RecyclerView recyclerview_Children = findViewById(R.id.recyclerview_children);
        boolean isMentor = true;
        populateArrays();

        //populate recycler view with all students
        MeetingInfoAdapter meetingInfoAdapter = new MeetingInfoAdapter(this, names_of_children, emails_of_children, isMentor, true);
        recyclerview_Children.setAdapter(meetingInfoAdapter);
        recyclerview_Children.setLayoutManager(new LinearLayoutManager(this));


    }


    //get all students who are under the parent
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void populateArrays()
    {
        String query = String.format("SELECT * FROM users WHERE id IN (SELECT student_id FROM students WHERE parent_id = '%s')", UserSession.getInstance().id);
        QueryExecution.executeQuery(query);
        List<User> children = QueryExecution.getResponse(User.class);

        names_of_children = new String[children.size()];
        emails_of_children = new String[children.size()];

        for(int i = 0; i < children.size(); i++)
        {
            names_of_children[i] = children.get(i).name;
            emails_of_children[i] = children.get(i).email;
        }
    }
}

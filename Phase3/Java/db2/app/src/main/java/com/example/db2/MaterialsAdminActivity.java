package com.example.db2;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.db2.adapters.MaterialAdapter;
import com.example.db2.adapters.MeetingAdminAdapter;
import com.example.db2.helpers.ListHelpers;
import com.example.db2.helpers.QueryExecution;
import com.example.db2.models.Material;
import com.example.db2.models.Meeting;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.List;

//Show all the current materials and allow admin to add new ones
public class MaterialsAdminActivity extends BaseLogoutBackActivity {

    private Meeting meeting;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_materials_admin);
        super.onCreate(savedInstanceState);

        meeting = MeetingAdminAdapter.targetMeeting;

        //setup list of materials in view
        setupMaterialsList();

        //get all relevant controls in view
        EditText titleText = findViewById(R.id.title_editText);
        EditText urlText = findViewById(R.id.url_editText);
        EditText authorText = findViewById(R.id.author_editText);
        EditText notesText = findViewById(R.id.notes_editText);
        EditText typeText = findViewById(R.id.type_editText);
        EditText dateText = findViewById(R.id.date_editText);
        Button submitButton = findViewById(R.id.create_button);

        //add a new material
        submitButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //call register parent function
                String title = titleText.getText().toString();

                String url = null;
                try {
                    url = new URL(urlText.getText().toString()).toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return;
                }
                String author = authorText.getText().toString();
                String notes = notesText.getText().toString();
                String type = typeText.getText().toString();

                String dateStr = dateText.getText().toString();
                Date date = Date.valueOf(dateStr);

                String query = String.format("INSERT INTO material (title, author, type, url, assigned_date, notes) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                        title, author, type, url, date, notes);
                QueryExecution.executeQuery(query);

                query = "SELECT MAX(material_id) as material_id FROM material";
                QueryExecution.executeQuery(query);

                Material insertedMaterial  = QueryExecution.getResponse(Material.class).get(0);

                query = String.format("INSERT INTO assign (meet_id, material_id) VALUES (%d, %d)", meeting.meet_id, insertedMaterial.material_id);
                QueryExecution.executeQuery(query);

                //refresh activity
                finish();
                startActivity(getIntent());
            }
        });
    }

    //build the list of materials that we put in the recyclerView
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupMaterialsList(){
        String query = String.format("SELECT * FROM material WHERE material_id IN (SELECT material_id from assign WHERE meet_id=%d) ORDER BY assigned_date DESC", meeting.meet_id);
        QueryExecution.executeQuery(query);

        List<Material> materials = QueryExecution.getResponse(Material.class);

        final RecyclerView materialsRecyclerView = findViewById(R.id.materials_recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        materialsRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        materialsRecyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        MaterialAdapter mAdapter = new MaterialAdapter(materials);
        materialsRecyclerView.setAdapter(mAdapter);
    }
}

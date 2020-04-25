package com.example.db2;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.example.db2.adapters.MaterialAdapter;
import com.example.db2.adapters.MeetingAdminAdapter;
import com.example.db2.helpers.QueryExecution;
import com.example.db2.models.Material;
import com.example.db2.models.Meeting;

import java.util.List;

public class MaterialsAdminActivity extends BaseLogoutBackActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_materials_admin);
        super.onCreate(savedInstanceState);

        Meeting meeting = MeetingAdminAdapter.targetMeeting;

        String query = String.format("SELECT * FROM material WHERE material_id IN (SELECT material_id from assign WHERE meet_id=%d)", meeting.meet_id);
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

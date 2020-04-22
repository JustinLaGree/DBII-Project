package com.example.db2.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.R;
import com.example.db2.helpers.ListHelpers;
import com.example.db2.models.Meeting;
import com.example.db2.models.TimeSlot;

import java.util.List;

public class MeetingAdminAdapter extends RecyclerView.Adapter<MeetingAdminAdapter.MeetingViewHolder> {

    private List<Meeting> meetings;
    private List<TimeSlot> timeSlots;

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

    public MeetingAdminAdapter(List<Meeting> meetings, List<TimeSlot> timeSlots) {
        this.meetings = meetings;
        this.timeSlots = timeSlots;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MeetingAdminAdapter.MeetingViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        TimeSlot timeSlot = ListHelpers.single(timeSlots, ts -> ts.time_slot_id == meeting.time_slot_id);

        TextView idText = (TextView) holder.constraintLayout.getViewById(R.id.id_textView);
        idText.setText(Integer.toString(meeting.meet_id));
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }
}

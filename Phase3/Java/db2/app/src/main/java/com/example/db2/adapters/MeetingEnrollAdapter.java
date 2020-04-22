package com.example.db2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.R;

public class MeetingEnrollAdapter extends RecyclerView.Adapter<MeetingEnrollAdapter.MyViewHolder> {

    String grades[];
    String meeting_names[];
    String dates[];
    String enrollment[];
    String times[];
    Context ct;

    public MeetingEnrollAdapter(Context ct_, String grades_[], String meeting_names_[], String dates_[], String enrollment_[], String times_[])
    {
        ct = ct_;
        grades = grades_;
        meeting_names = meeting_names_;
        dates = dates_;
        enrollment = enrollment_;
        times = times_;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.meeting_enroll_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.grademeetingText.setText((Integer.parseInt(grades[position]) + 5) + "th Grade " + meeting_names[position]);
        holder.timeText.setText(times[position] + " PM");
        holder.dateText.setText(dates[position]);
        holder.capacityText.setText(enrollment[position] + "/6");
        holder.enrollButton.setText("Enroll");
    }

    @Override
    public int getItemCount() {
        return meeting_names.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView grademeetingText;
        TextView timeText;
        TextView dateText;
        TextView capacityText;
        Button enrollButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            grademeetingText = itemView.findViewById(R.id.textView_grademeetingrow);
            timeText = itemView.findViewById(R.id.textView_timerow);
            dateText = itemView.findViewById(R.id.textView_daterow);
            capacityText = itemView.findViewById(R.id.textView_capacityrow);
            enrollButton = itemView.findViewById(R.id.button_enrollasmenteerow);
        }
    }
}

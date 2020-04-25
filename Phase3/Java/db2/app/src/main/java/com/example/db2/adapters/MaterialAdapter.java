package com.example.db2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.db2.EnrollAdminActivity;
import com.example.db2.MaterialsAdminActivity;
import com.example.db2.R;
import com.example.db2.helpers.ListHelpers;
import com.example.db2.models.Enroll;
import com.example.db2.models.Enroll2;
import com.example.db2.models.Group;
import com.example.db2.models.Material;
import com.example.db2.models.Meeting;
import com.example.db2.models.TimeSlot;

import java.text.SimpleDateFormat;
import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MaterialViewHolder> {

    private List<Material> materials;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MaterialViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout constraintLayout;

        public MaterialViewHolder(ConstraintLayout cl) {
            super(cl);
            constraintLayout = cl;
        }
    }

    public MaterialAdapter(List<Material> materials) {
        this.materials = materials;
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        ConstraintLayout cl = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partial_material, parent, false);

        MaterialViewHolder mvh = new MaterialViewHolder(cl);
        return mvh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        //get the inner-most Constraint layout-- this is where all textViews are
        ConstraintLayout cl = ((CardView) holder.constraintLayout.getViewById(R.id.inner_cardView))
                .findViewById(R.id.inner_cl);

        //get all text views
        TextView titleTextView = cl.findViewById(R.id.title_textView);
        TextView authorTextView = cl.findViewById(R.id.author_textView);
        TextView typeTextView = cl.findViewById(R.id.type_textView);
        TextView dateTextView = cl.findViewById(R.id.date_textView);
        TextView notesTextView = cl.findViewById(R.id.notes_textView);

        //get the instance of the control we are populating
        Material material = materials.get(position);

        //populate all text

        //setup text view so it acts as a hyperlink
        titleTextView.setClickable(true);
        titleTextView.setMovementMethod(LinkMovementMethod.getInstance());
        String titleLinkText = String.format("<a href='%s'>%s</a>", material.url, material.title);
        titleTextView.setText(Html.fromHtml(titleLinkText, Html.FROM_HTML_MODE_COMPACT));

        authorTextView.setText(material.author);
        typeTextView.setText(material.type);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        dateTextView.setText(formatter.format(material.assigned_date));

        notesTextView.setText(material.notes);
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

}

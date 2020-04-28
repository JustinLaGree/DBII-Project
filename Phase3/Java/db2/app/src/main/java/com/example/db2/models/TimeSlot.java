package com.example.db2.models;

import java.sql.Time;

//De-serialized representation of an 'time_slot' table row
public class TimeSlot {
    public int time_slot_id;
    public String day_of_the_week;
    public Time start_time;
    public Time end_time;
}

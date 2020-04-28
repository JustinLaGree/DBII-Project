package com.example.db2.models;

import java.util.Date;

//De-serialized representation of an 'meetings' table row
public class Meeting {
    public int meet_id;
    public String meet_name;
    public Date date;
    public int time_slot_id;
    public int capacity;
    public String announcement;
    public int group_id;
}

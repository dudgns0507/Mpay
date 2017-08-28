package io.github.dudgns0507.mpay.models;

import io.github.dudgns0507.mpay.models.Admin;
import io.github.dudgns0507.mpay.models.Group;

public class Result
{
    private User_info[] user_info;

    private String message;

    private Events[] events;

    private Admin[] admin;

    private String state;

    private Group[] group;

    public Events[] getEvents() {
        return events;
    }

    public void setEvents(Events[] events) {
        this.events = events;
    }

    public User_info[] getUser_info ()
    {
        return user_info;
    }

    public void setUser_info (User_info[] user_info)
    {
        this.user_info = user_info;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Admin[] getAdmin ()
    {
        return admin;
    }

    public void setAdmin (Admin[] admin)
    {
        this.admin = admin;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public Group[] getGroup ()
    {
        return group;
    }

    public void setGroup (Group[] group)
    {
        this.group = group;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user_info = "+user_info+", message = "+message+", admin = "+admin+", state = "+state+", group = "+group+", events = "+events+"]";
    }
}
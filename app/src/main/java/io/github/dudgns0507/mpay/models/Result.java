package io.github.dudgns0507.mpay.models;

public class Result
{
    private String message;

    private String state;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", state = "+state+"]";
    }
}
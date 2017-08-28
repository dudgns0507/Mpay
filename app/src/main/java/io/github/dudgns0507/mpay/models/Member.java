package io.github.dudgns0507.mpay.models;

public class Member
{
    private String paid;

    private String state;

    private String pay;

    private int user_id;

    public String getPaid ()
    {
        return paid;
    }

    public void setPaid (String paid)
    {
        this.paid = paid;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getPay ()
    {
        return pay;
    }

    public void setPay (String pay)
    {
        this.pay = pay;
    }

    public int getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (int user_id)
    {
        this.user_id = user_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [paid = "+paid+", state = "+state+", pay = "+pay+", user_id = "+user_id+"]";
    }
}
package io.github.dudgns0507.mpay.models;

public class Member
{
    private int paid;

    private String name;

    private String state;

    private int pay;

    private int user_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
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
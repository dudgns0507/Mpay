package io.github.dudgns0507.mpay.models;

public class User_info
{
    private String phone;

    private String birth;

    private int _id;

    private String email;

    private String name;

    private String state;

    private int pay;

    private int paid;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getBirth ()
    {
        return birth;
    }

    public void setBirth (String birth)
    {
        this.birth = birth;
    }

    public int get_id ()
    {
        return _id;
    }

    public void set_id (int _id)
    {
        this._id = _id;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [phone = "+phone+", birth = "+birth+", _id = "+_id+", email = "+email+", name = "+name+"]";
    }
}
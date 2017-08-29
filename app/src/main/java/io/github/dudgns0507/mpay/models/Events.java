package io.github.dudgns0507.mpay.models;

public class Events
{
    private int paid;

    private int _id;

    private String name;

    private String state;

    private int group_id;

    private String due_date;

    private int tag_color;

    private int pay;

    private int total;

    private int total_paid;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private User_info[] user;

    public User_info[] getUser() {
        return user;
    }

    public void setUser(User_info[] user) {
        this.user = user;
    }

    public int isPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public int getTag_color() {
        return tag_color;
    }

    public void setTag_color(int tag_color) {
        this.tag_color = tag_color;
    }

    public int getPaid() {
        return paid;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_paid() {
        return total_paid;
    }

    public void setTotal_paid(int total_paid) {
        this.total_paid = total_paid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total = "+total+", paid = "+paid+", _id = "+_id+", name = "+name+", state = "+state+", group_id = "+group_id+", pay = "+pay+", due_date = "+due_date+", tag_color = "+tag_color+"]";
    }
}
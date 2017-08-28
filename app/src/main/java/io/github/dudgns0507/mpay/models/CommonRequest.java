package io.github.dudgns0507.mpay.models;

public class CommonRequest
{
    private Member[] member;

    private int total;

    private int budget;

    private int admin;

    private String name;

    private int group_id;

    private String due_date;

    private String account;

    private String explain;

    private int tag_color;

    public Member[] getMember ()
    {
        return member;
    }

    public void setMember (Member[] member)
    {
        this.member = member;
    }

    public int getTotal ()
    {
        return total;
    }

    public void setTotal (int total)
    {
        this.total = total;
    }

    public int getBudget ()
    {
        return budget;
    }

    public void setBudget (int budget)
    {
        this.budget = budget;
    }

    public int getAdmin ()
    {
        return admin;
    }

    public void setAdmin (int admin)
    {
        this.admin = admin;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public int getGroup_id ()
    {
        return group_id;
    }

    public void setGroup_id (int group_id)
    {
        this.group_id = group_id;
    }

    public String getDue_date ()
    {
        return due_date;
    }

    public void setDue_date (String due_date)
    {
        this.due_date = due_date;
    }

    public String getAccount ()
    {
        return account;
    }

    public void setAccount (String account)
    {
        this.account = account;
    }

    public String getExplain ()
    {
        return explain;
    }

    public void setExplain (String explain)
    {
        this.explain = explain;
    }

    public int getTag_color ()
    {
        return tag_color;
    }

    public void setTag_color (int tag_color)
    {
        this.tag_color = tag_color;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [member = "+member+", total = "+total+", budget = "+budget+", admin = "+admin+", name = "+name+", group_id = "+group_id+", due_date = "+due_date+", account = "+account+", explain = "+explain+", tag_color = "+tag_color+"]";
    }
}
package io.github.dudgns0507.mpay.models;

public class Admin
{
    private int budget;

    private int _id;

    private int admin;

    private String name;

    private String account;

    private String explain;

    public int getBudget ()
    {
        return budget;
    }

    public void setBudget (int budget)
    {
        this.budget = budget;
    }

    public int get_id ()
    {
        return _id;
    }

    public void set_id (int _id)
    {
        this._id = _id;
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

    @Override
    public String toString()
    {
        return "ClassPojo [budget = "+budget+", _id = "+_id+", admin = "+admin+", name = "+name+", account = "+account+", explain = "+explain+"]";
    }
}
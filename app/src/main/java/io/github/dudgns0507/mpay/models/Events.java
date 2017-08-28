package io.github.dudgns0507.mpay.models;

public class Events
{
    private String paid;

    private String _id;

    private String name;

    private String state;

    private String tag_color;

    public String getPaid ()
    {
        return paid;
    }

    public void setPaid (String paid)
    {
        this.paid = paid;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getTag_color ()
    {
        return tag_color;
    }

    public void setTag_color (String tag_color)
    {
        this.tag_color = tag_color;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [paid = "+paid+", _id = "+_id+", name = "+name+", state = "+state+", tag_color = "+tag_color+"]";
    }
}
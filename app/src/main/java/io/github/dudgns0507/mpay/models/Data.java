package io.github.dudgns0507.mpay.models;

public class Data {
    private static Data data = new Data();

    // current user information
    private static int _id;
    private static String name;
    private static String email;
    private static String phone;
    private static String birth;

    // current user group list;
    private Group[] group;
    private Admin[] admin;

    // current user event list;
    private Events[] events;

    private CommonRequest request;

    public CommonRequest getRequest() {
        return request;
    }

    public void setRequest(CommonRequest request) {
        this.request = request;
    }

    public static Data getInstance() {
        return data;
    }

    public static Data getData() {
        return data;
    }

    public static void setData(Data data) {
        Data.data = data;
    }

    public static int get_id() {
        return _id;
    }

    public static void set_id(int _id) {
        Data._id = _id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Data.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Data.email = email;
    }

    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone) {
        Data.phone = phone;
    }

    public static String getBirth() {
        return birth;
    }

    public static void setBirth(String birth) {
        Data.birth = birth;
    }

    public Group[] getGroup() {
        return group;
    }

    public void setGroup(Group[] group) {
        this.group = group;
    }

    public Admin[] getAdmin() {
        return admin;
    }

    public void setAdmin(Admin[] admin) {
        this.admin = admin;
    }

    public Events[] getEvents() {
        return events;
    }

    public void setEvents(Events[] events) {
        this.events = events;
    }
}

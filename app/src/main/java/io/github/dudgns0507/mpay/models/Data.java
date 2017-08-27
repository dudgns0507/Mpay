package io.github.dudgns0507.mpay.models;

/**
 * Created by pyh42 on 2017-08-28.
 */

public class Data {
    private static Data data = new Data();

    private static int _id;
    private static String name;
    private static String email;
    private static String phone;
    private static String birth;

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
}

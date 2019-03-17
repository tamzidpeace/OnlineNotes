package Model;

import android.content.Context;
import android.content.SharedPreferences;

public class Model {

    private String name, password;
    private String id, user_name;

    public Model(String name, String password, String id) {
        this.name = name;
        this.password = password;
        this.id = id;
    }


    public Model() {
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }


}

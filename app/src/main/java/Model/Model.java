package Model;

public class Model {

    private String name, password;
    private String id, user_name;

    public Model(String name, String password, String id, String user_name) {
        this.name = name;
        this.password = password;
        this.id = id;
        this.user_name = user_name;
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

package Model;

public class Model {

    private String name, password;
    private String id;

    public Model(String name, String password, String id) {
        this.name = name;
        this.password = password;
        this.id = id;

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
}

package Objects;

import java.io.Serializable;

public class User implements Serializable {

    public String name;
    public String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
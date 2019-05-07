package macbeth;

public class User {

    private String name;
    private String email;
    private String phone;
    private int age;

    public User(String name, String email, String phone, int age) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
    }

    public String toString() {
        return name + " " + email + " " + phone + " " + age;
    }
}

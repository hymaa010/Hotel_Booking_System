import java.util.*;
public abstract class User {
    private boolean isAdmin;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private static final List<User> users = new ArrayList<>();
    public User(){}
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public static List<User> getUsers() { return users; }
    protected static void addUser(User user) { users.add(user); }

}



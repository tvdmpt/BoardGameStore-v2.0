import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public abstract class User implements Serializable {
    protected String id;
    protected String username;
    protected String password;
    protected String email;
    protected String fullName;
    protected Date registrationDate;

    public User(String username, String password, String email, String fullName) {
        this.id = java.util.UUID.randomUUID().toString().substring(0, 8);
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.registrationDate = new Date();
    }

    public abstract void performRoleSpecificAction();
    public abstract String getRole();
    public abstract void showMenu();

    @Override
    public String toString() {
        return "User[id=" + id + ", login=" + username + ", role=" + getRole() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
package UserAuth.Authentication_Service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User{

    @Id
    int UserID;
    @Email(message ="Email must be valid")
    @NotEmpty(message = "Email is required")
    String email;
    @NotEmpty(message = "please enter userName")
    @Size(min = 6, message = "User name must be at least 6 characters long")
    String Username;
    @NotEmpty(message = "please enter password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[@#$%^&+=!].*", message = "Password must contain at least one special character (@#$%^&+=!)")
    @Pattern(regexp = "\\S+", message = "Password must not contain whitespace")
    String password;

    public User() {
    }

    public User(int userID, String email, String username, String password) {
        UserID = userID;
        this.email = email;
        Username = username;
        this.password = password;
    }

    public int getUserID() {
        return UserID;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return password;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserID=" + UserID +
                ", email='" + email + '\'' +
                ", Username='" + Username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

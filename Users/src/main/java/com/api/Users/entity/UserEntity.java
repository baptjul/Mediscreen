package com.api.Users.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents the UserEntity
 */
@Document(collection = "users")
public class UserEntity {

    @Id
    private String id;
    private String username;
    private String password;

    /**
     * Constructor for UserEntity
     */
    public UserEntity() {}

    /**
     * Constructs a UserEntity with all information
     * @param username the username of the user
     * @param password the password of the user
     */
    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

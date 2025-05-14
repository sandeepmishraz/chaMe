package in.chatMe.entity;

import jakarta.persistence.*;

@Entity
public class MyFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // These field names must match exactly what you use in the repository method
    private String currentUserEmail;
    private String friendEmail;

    public MyFriend() {}

    public MyFriend(String currentUserEmail, String friendEmail) {
        this.currentUserEmail = currentUserEmail;
        this.friendEmail = friendEmail;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public void setCurrentUserEmail(String currentUserEmail) {
        this.currentUserEmail = currentUserEmail;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }
}

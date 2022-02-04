package com.ssanolja.backend.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_nickname")
    private String userNickname;

    @Builder
    public User(String userEmail, String userName, String userNickname) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userNickname = userNickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "usersId=" + usersId +
                ", userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                '}';
    }
}

package com.ssanolja.backend.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name="users_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;


    @Column(name="user_email", nullable = false)
    private String userEmail;


    @Column(name="user_nickname", nullable = false, unique = true)
    private String userNickname;
}

package com.ssafy.backend.domain.room.entity;

import com.ssafy.backend.domain.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "room_users_list")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomUserList {

    @Id
    @Column(name = "room_users_list_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomUserListId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rooms_id", nullable = false)
    private Room room;

    @Column(name = "room_maker", nullable = false)
    private boolean isRoomMaker;
}

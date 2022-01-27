package com.ssafy.backend.domain.fakeartist.entity;

import com.ssafy.backend.domain.room.entity.Room;
import com.ssafy.backend.domain.room.entity.RoomDetail;
import com.ssafy.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FakeArtist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer artistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private RoomDetail roomDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fake_artist_job_id")
    private FakeArtistJob fakeArtistJobId;

    private Integer order;

    @Column(nullable = false)
    private Integer vote;

    @Column(length = 45)
    private String keywordSmall;

    @Column(length = 45)
    private String keywordBig;

    @Column(nullable = false)
    private Integer score;

}

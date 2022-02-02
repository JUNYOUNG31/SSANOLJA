package com.ssafy.backend.domain.telestation.entity;

import com.ssafy.backend.domain.game.entity.Game;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "telestations")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Telestation {

    @Id
    @Column(name = "telestations_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer telestationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "games_id")
    private Game game;

    // self join (둘 중 하나가 before_pk 같음)
    // 부모 정의
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "before_pk")
    private Telestation parent;

    // 자식 정의
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Telestation> children;

    @Column(name = "users_id")
    private Integer userId;

    @Column(name = "data_index")
    private String dataIndex;

    @Column(name = "text_data")
    private String textData;

    @Column(name = "drawing_data")
    private String drawingData;

    @Column(name = "drawing_order")
    private String drawingOrder;

    @Column(name = "bestvote")
    private String bestvote;

    @Column(name = "worstvote")
    private String worstvote;



}

package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class RoomType {
    @Id
    private int roomId;

    private String roomType;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "roomId")
    private Room room;
}

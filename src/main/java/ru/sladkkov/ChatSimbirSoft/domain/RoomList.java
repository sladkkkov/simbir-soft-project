package ru.sladkkov.ChatSimbirSoft.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "room_list")
public class RoomList  {

    @EmbeddedId
    private RoomListId id;

    @Column(name = "ban_time")
    private Timestamp banTime;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "roles")
    private Role role;
}

package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Roles {
    @Id
    private String role;

    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<RoomList> roomListList;
}

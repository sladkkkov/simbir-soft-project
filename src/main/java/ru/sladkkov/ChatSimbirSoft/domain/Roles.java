package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Roles {
    @Id
    private String role;

    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<RoomList> roomListList;
}

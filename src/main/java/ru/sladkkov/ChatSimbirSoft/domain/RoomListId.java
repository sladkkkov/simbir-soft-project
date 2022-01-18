package ru.sladkkov.ChatSimbirSoft.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomListId implements Serializable {
    @Column(name = "room_list_id")
    private Long roomListId;

    @Column(name = "user_list_id")
    private Long userListId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomListId that = (RoomListId) o;
        return Objects.equals(roomListId, that.roomListId) && Objects.equals(userListId, that.userListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomListId, userListId);
    }
}

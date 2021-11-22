package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sladkkov.ChatSimbirSoft.domain.RoomList;

import java.util.List;

public interface RoomListRepo extends JpaRepository<RoomList,Long> {
    RoomList getByUserIdAndRoomRoomId(Long userId, Long roomId);

    List<RoomList> getAllByUserId(Long userId);

}

package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sladkkov.ChatSimbirSoft.domain.RoomList;

public interface RoomListRepo extends JpaRepository<RoomList,Long> {
/*
    RoomList findByUserIdAndRoomId(@Param("userId") Long userId, @Param("roomId") Long roomId);
*/
}

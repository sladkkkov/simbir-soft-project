package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.sladkkov.ChatSimbirSoft.domain.RoomList;

public interface RoomListRepo extends JpaRepository<RoomList,Long> {
    RoomList findByRoom_RoomIdAndUserId(@Param("userId") Long userId, @Param("roomId") Long roomId);
}

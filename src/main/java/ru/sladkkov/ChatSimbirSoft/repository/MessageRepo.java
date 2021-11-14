package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.sladkkov.ChatSimbirSoft.domain.Message;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message,Long> {
    List<Message> findByRoom_RoomId(@Param("roomId")Long roomId);
}

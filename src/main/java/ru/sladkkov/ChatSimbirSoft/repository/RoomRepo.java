package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sladkkov.ChatSimbirSoft.domain.Room;

import java.util.Optional;

public interface RoomRepo extends JpaRepository<Room,Long> {
    Optional<Room> getByRoomName(String name);
}

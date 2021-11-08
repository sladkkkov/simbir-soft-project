package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sladkkov.ChatSimbirSoft.domain.RoomType;

public interface RoomTypeRepo extends JpaRepository<RoomType,Long> {
}

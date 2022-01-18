package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.sladkkov.ChatSimbirSoft.domain.RoomList;
import ru.sladkkov.ChatSimbirSoft.domain.RoomListId;

import java.util.List;
import java.util.Optional;

public interface RoomListRepo extends JpaRepository<RoomList, RoomListId> {
    void deleteAllById_RoomListId(Long roomListId);
    void deleteAllById_UserListId(Long userListId);
}

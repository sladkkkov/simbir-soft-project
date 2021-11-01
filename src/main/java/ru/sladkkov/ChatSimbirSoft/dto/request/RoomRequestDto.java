package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Room;
import ru.sladkkov.ChatSimbirSoft.dto.response.RoomDto;

@Data
public class RoomRequestDto {
    private int roomId;

    public Room ToRoom() {
        Room room = new Room();
        room.setRoomId(roomId);
        return room;
    }

    public static RoomDto fromRoom(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setRoomId(room.getRoomId());
        return roomDto;
    }
}

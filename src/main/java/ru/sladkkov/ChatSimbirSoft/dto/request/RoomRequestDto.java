package ru.sladkkov.ChatSimbirSoft.dto.request;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Room;

@Data
public class RoomRequestDto {
    private long roomId;

    public Room toRoom() {
        Room room = new Room();
        room.setRoomId(roomId);
        return room;
    }

    public static RoomRequestDto fromRoom(Room room) {
        RoomRequestDto roomRequestDto = new RoomRequestDto();
        roomRequestDto.setRoomId(room.getRoomId());
        return roomRequestDto;
    }
}

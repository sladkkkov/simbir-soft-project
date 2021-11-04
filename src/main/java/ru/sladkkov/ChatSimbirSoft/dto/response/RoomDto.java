package ru.sladkkov.ChatSimbirSoft.dto.response;

import lombok.Data;
import ru.sladkkov.ChatSimbirSoft.domain.Room;

@Data
public class RoomDto {
    private long roomId;

    private long ownerId;

    private String roomName;

    public Room ToRoom() {
        Room room = new Room();
        room.setRoomId(roomId);
        room.setOwnerId(ownerId);
        room.setRoomName(roomName);
        return room;
    }

    public static RoomDto fromRoom(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setRoomId(room.getRoomId());
        roomDto.setOwnerId(room.getOwnerId());
        roomDto.setRoomName(room.getRoomName());
        return roomDto;
    }
}

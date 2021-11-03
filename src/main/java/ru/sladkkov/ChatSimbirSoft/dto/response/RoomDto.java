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
        room.setOwner_id(ownerId);
        room.setRoom_name(roomName);
        return room;
    }

    public static RoomDto fromRoom(Room room) {
        RoomDto roomDto = new RoomDto();
        roomDto.setRoomId(room.getRoomId());
        roomDto.setOwnerId(room.getOwner_id());
        roomDto.setRoomName(room.getRoom_name());
        return roomDto;
    }
}

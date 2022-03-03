package com.donamdong.spiritualwar.endpoint.room.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RoomRequest {
    private String roomName;
    private Integer maxCapacity;

}

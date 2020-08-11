package com.arbstudio.easygamesroomserver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.Value;

import java.util.UUID;

@Value
public class RoomHost {
    private String address;
    private String name;
    private String password;
    private UUID token = UUID.randomUUID();
}

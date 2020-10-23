package com.arbstudio.easygamesroomserver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoomStatusUpdate {
    @NonNull private String token;
    private int players;
}

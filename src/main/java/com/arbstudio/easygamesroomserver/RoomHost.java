package com.arbstudio.easygamesroomserver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.UUID;

@Value
@AllArgsConstructor(onConstructor_ = {@JsonCreator})
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RoomHost {
  @NonNull private String address;
  @NonNull private String name;
  @NonNull private String password;
  @NonNull private int players;
  @NonNull private final UUID token = UUID.randomUUID();
}

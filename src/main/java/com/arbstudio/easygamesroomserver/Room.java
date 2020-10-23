package com.arbstudio.easygamesroomserver;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;


@ToString
@Data
@Builder
public class Room {
        private String address;
        private String name;
        private int players;
        @JsonIgnore
        private String password;
        @JsonIgnore
        private UUID token;
        @JsonIgnore
        private OffsetDateTime lastUpdateTime;

        public boolean getPasswordSecured(){
                if(password == null || password.equals("")){
                        return false;
                }
                return true;
        }
}

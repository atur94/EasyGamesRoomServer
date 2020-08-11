package com.arbstudio.easygamesroomserver;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Data
@ToString
@Builder
public class Room {
        private String address;
        private String name;
        private int players;
        @JsonIgnore
        private String password;
        @JsonIgnore
        private UUID token;

        public boolean getPasswordSecured(){
                if(password == null || password.equals("")){
                        return false;
                }
                return true;
        }
}

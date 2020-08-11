package com.arbstudio.easygamesroomserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ServerTester {

    @Bean("development")
    List<Room> getRooms(){
        List<Room> exampleRooms = new ArrayList<>();

    exampleRooms.add(
        Room.builder()
            .name("Serwer Artura oo")
            .players(2)
            .address("192.168.1.1")
            .password("")
            .build());
    exampleRooms.add(
        Room.builder()
            .name("Serwer Michala oo")
            .players(2)
            .address("192.168.1.2")
            .password("")
            .build());
    exampleRooms.add(
        Room.builder()
            .name("Tutaj wbijaj Artura oo")
            .players(3)
            .address("192.168.1.3")
            .password("artur")
            .build());
    exampleRooms.add(
        Room.builder().name("terakotra oo").players(4).address("192.168.1.4").password("").build());

        return exampleRooms;
    }

}

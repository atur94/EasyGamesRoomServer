package com.arbstudio.easygamesroomserver;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServerController {

    List<Room> servers;

    public ServerController(List<Room> servers){
        this.servers = servers;
    }

    @GetMapping("/servers")
    public RoomContainer getServerList(){
        RoomContainer roomContainer = new RoomContainer();
        roomContainer.rooms = servers;
        return roomContainer;
    }

    @PostMapping("/servers/host")
    public RoomHost hostNewServer(@RequestBody RoomHost roomHost){
        RoomHost hostedRoom = new RoomHost(roomHost.getAddress(), roomHost.getName(), roomHost.getPassword());
        servers.add(new Room(roomHost.getAddress(), roomHost.getName(), 0, roomHost.getPassword(), roomHost.getToken()));
        return hostedRoom;
    }





}

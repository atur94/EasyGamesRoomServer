package com.arbstudio.easygamesroomserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class ServerController {

  List<Room> servers;
  ObjectMapper objectMapper;

  public ServerController(List<Room> servers, ObjectMapper objectMapper){
    this.servers = servers;
    this.objectMapper = objectMapper;
    Start();
  }

  public void Start(){
    new Thread(
            () -> {
              while (true) {
                synchronized (servers){
                  servers.removeIf(room -> {
                    var diff =
                            OffsetDateTime.now().toInstant().toEpochMilli()
                                    - room.getLastUpdateTime().toInstant().toEpochMilli();
                    if(diff > 5000) return true;
                    return false;
                  });
                }

                try {
                  Thread.sleep(600);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            })
            .start();
  }

  @GetMapping("/servers")
  public RoomContainer getServerList() {
    RoomContainer roomContainer = new RoomContainer();
    roomContainer.rooms = servers;
    return roomContainer;
  }

  @PutMapping("/servers/host")
  public RoomHost hostNewServer(@RequestBody RoomHost roomHost) throws IOException {
    log.debug(roomHost + " utworzono");

    RoomHost tokenizedRoom =
        new RoomHost(roomHost.getAddress(), roomHost.getName(), roomHost.getPassword());
    synchronized (servers){
      servers.add(
              Room.builder()
                      .address(tokenizedRoom.getAddress())
                      .name(tokenizedRoom.getName())
                      .players(0)
                      .password(tokenizedRoom.getPassword())
                      .token(tokenizedRoom.getToken())
                      .lastUpdateTime(OffsetDateTime.now())
                      .build());
    }

    return tokenizedRoom;
  }

  @PutMapping("/servers/update")
  public RoomStatusUpdate updateServer(@RequestBody RoomStatusUpdate roomStatusUpdate) {
    log.debug(roomStatusUpdate + " zaktualizowano");
    for (Room server : servers) {
      if (server.getToken() != null) {
        if (server.getToken().toString().equals(roomStatusUpdate.getToken())) {
          server.setLastUpdateTime(OffsetDateTime.now());
          server.setPlayers(roomStatusUpdate.getPlayers());
        }
      }
    }

    return roomStatusUpdate;
  }
}

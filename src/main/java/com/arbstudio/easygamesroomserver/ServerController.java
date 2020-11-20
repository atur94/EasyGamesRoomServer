package com.arbstudio.easygamesroomserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
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
                    if(diff > 3000) return true;
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
        new RoomHost(roomHost.getAddress(), roomHost.getName(), roomHost.getPassword(), roomHost.getPlayers());
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
  public void updateServer(@RequestBody RoomHost roomStatusUpdate) {
    log.debug(roomStatusUpdate + " zaktualizowano");
    Room item = servers.stream().filter(room -> room.getToken().equals(roomStatusUpdate.getToken())).findFirst().orElse(null);
    if(item != null){
      // Jezeli serwer istenieje już na liście to zaktualizuj. Jezeli nie to go utwórz
      item.setLastUpdateTime(OffsetDateTime.now());
    }else {
      if(!StringUtils.isEmpty(roomStatusUpdate.getToken())){
        // Jezeli nie istenieje, ale ma już utworzony token to dodaj serwer do listy ponownie
        servers.add(new Room(roomStatusUpdate.getAddress(), roomStatusUpdate.getName(), roomStatusUpdate.getPlayers(), roomStatusUpdate.getPassword(), roomStatusUpdate.getToken(), OffsetDateTime.now()));
      }
    }
  }
}

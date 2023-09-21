package com.example.hello.protocol.request;

import com.example.hello.protocol.Packet;
import lombok.Data;

import java.util.List;

import static com.example.hello.protocol.command.Command.CREATE_GROUP_REQUEST;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}

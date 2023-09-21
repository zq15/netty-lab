package com.example.hello.protocol.request;

import com.example.hello.protocol.Packet;
import lombok.Data;

import static com.example.hello.protocol.command.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}

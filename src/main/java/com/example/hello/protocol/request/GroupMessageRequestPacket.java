package com.example.hello.protocol.request;

import com.example.hello.protocol.Packet;
import lombok.Data;

import static com.example.hello.protocol.command.Command.GROUP_MESSAGE_REQUEST;

@Data
public class GroupMessageRequestPacket extends Packet {

    private String toGroupId;

    private String message;

    public GroupMessageRequestPacket(String toGroupId, String message) {
        this.toGroupId = toGroupId;
        this.message = message;
    }


    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}

package com.example.hello.protocol.response;

import com.example.hello.protocol.Packet;
import com.example.hello.session.Session;
import lombok.Data;

import static com.example.hello.protocol.command.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;
    private Session fromUser;
    private String message;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}

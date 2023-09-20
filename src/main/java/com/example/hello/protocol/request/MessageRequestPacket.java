package com.example.hello.protocol.request;

import com.example.hello.protocol.Packet;
import com.example.hello.protocol.command.Command;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}

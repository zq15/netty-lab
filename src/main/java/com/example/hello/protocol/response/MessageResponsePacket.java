package com.example.hello.protocol.response;

import com.example.hello.protocol.Packet;
import com.example.hello.protocol.command.Command;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {
    private String fromUserId;
    private String fromUserName;
    private String message;
    
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}

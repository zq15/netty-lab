package com.example.hello.protocol.response;


import com.example.hello.protocol.Packet;
import com.example.hello.protocol.command.Command;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}

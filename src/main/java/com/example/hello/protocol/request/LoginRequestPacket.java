package com.example.hello.protocol.request;


import com.example.hello.protocol.command.Command;
import com.example.hello.protocol.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private String userName;

    private String password;


    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}

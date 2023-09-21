package com.example.hello.protocol.response;

import com.example.hello.protocol.Packet;
import com.example.hello.session.Session;
import lombok.Data;

import java.util.List;

import static com.example.hello.protocol.command.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}

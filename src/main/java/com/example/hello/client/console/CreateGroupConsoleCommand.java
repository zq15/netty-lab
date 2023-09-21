package com.example.hello.client.console;

import com.example.hello.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand{

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();

        System.out.print("[拉入群聊]输入 userID 列表，userId 之间英文逗号隔开：");
        String userIds = scanner.next();

        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        System.out.println(createGroupRequestPacket);
        channel.writeAndFlush(createGroupRequestPacket);
    }
}

package com.example.hello.server.handler;

import com.example.hello.protocol.request.CreateGroupRequestPacket;
import com.example.hello.protocol.response.CreateGroupResponsePacket;
import com.example.hello.util.IDUtil;
import com.example.hello.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();
        ArrayList<String> userNameList = new ArrayList<>();

        // 1. 创建一个 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(channelHandlerContext.executor());

        // 2. 筛选出待加入群聊的用户的 channel 和 userName
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (null != channel){
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        // 3. 创建群聊创建结果的响应
        String groupId = IDUtil.randomId();
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNameList(userNameList);

        // 4. 给每个客户端发送拉群消息
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.println("群创建成功，id 为【" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());

        // 5. 保存群组相关的信息
        SessionUtil.bindChannelGroup(groupId, channelGroup);
    }
}

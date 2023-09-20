package com.example.hello.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 指令 在  PacketCodeC 中根据 command 的不同，把 packet 解析成不同的 具体类型的 packet
     * @return
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}

package com.example.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {
    public static void main(String[] args) {
        // capacity 9 maxCapacity 100
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);

        print("allocate ByteBuf(9, 100)", buffer);

        // write 方法写指针，写完之后写指针未到 capacity 时，buffer 仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("write byte(1, 2, 3, 4)", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写, 写完 int 类型之后，写指针增加4
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // write 方法改变写指针, 写完之后写指针等于 capacity 的时候，buffer 不可写
        buffer.writeBytes(new byte[]{5});

        // write 方法改变写指针，写的时候发现 buffer 不可写则开始扩容，扩容之后 capacity 随即改变
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get 方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);

        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);
    }

    private static void print(String action, ByteBuf buffer) {
        System.out.println("after =========== " + action + "============");
        // 底层占用内存字节数
        System.out.println("capacity(): " + buffer.capacity());
        // 最大可用内存
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        // 读指针
        System.out.println("readerIndex(): " + buffer.readerIndex());
        // 当前可读字节数 writerIndex - readerIndex
        System.out.println("readableBytes(): " + buffer.readableBytes());
        // 可读字节数为 0 的时候为 false
        System.out.println("isReadable(): " + buffer.isReadable());
        // 写指针
        System.out.println("writerIndex(): " + buffer.writerIndex());
        // 当前可写的字节数
        System.out.println("writableBytes(): " + buffer.writableBytes());
        // 是否可写 不可写的时候会自动扩容 maxWritableBytes 直到扩容到 maxCapacity
        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }
}

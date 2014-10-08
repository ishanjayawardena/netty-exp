package org.apache.ishan.doc.tutorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import javax.sound.midi.Soundbank;

/**
 * Created by ishan on 10/8/14.
 * Source http://netty.io/wiki/user-guide-for-5.x.html
 */
public class DiscardServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            if (msg instanceof ByteBuf) {
                ByteBuf buffer = (ByteBuf) msg;
                System.out.print("Wire >> ");
                StringBuilder stringBuilder = new StringBuilder();
                while (buffer.isReadable()) {
                    char c = (char) buffer.readByte();
                    if (c == '\n') {
                       stringBuilder.append("[\\n]\n");
                    } else if (c == '\r') {
                        stringBuilder.append("[\\r]");
                    } else if (c == '\t') {
                        stringBuilder.append("[\\t]");
                    } else {
                        stringBuilder.append(c);
                    }
                }
                System.out.print(stringBuilder.toString());
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

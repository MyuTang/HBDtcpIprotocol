package com.hbd.socket.server.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.tomcat.util.buf.ByteBufferUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ren Xunxiao
 * @date 2018/10/12
 * @copyright Hanboard
 * Created by Ren Xunxiao on 2018/10/12
 */
public class ThingFrameDecode extends ByteToMessageDecoder {
    private Logger logger = LoggerFactory.getLogger(getClass());


//    @Override
//    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
//        String result = ByteBufUtil.hexDump(in);
//        if(result.length() == 12132 && result.startsWith("ffeeaa55") && result.endsWith("55aaeeff")) {
//            out.add(result);
//            logger.info("收到数据：{}", result);
//        } else {
//            logger.info("无效数据：{}", result);
//        }
//        in.clear();
//    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String result = ByteBufUtil.hexDump(in);
//        logger.info("收到数据 {}", result);
        if (result.startsWith("242356")) {
            System.out.println("hell");
        }
        if (result.length() == 276){
            logger.info("老传感器数据，忽略,{}",result);
            in.clear();
        }
        else {
            int ffIndex;
            while ((ffIndex = findFF(in)) != -1 && in.readableBytes() >= 4 && in.readableBytes()<=6066) {
                in.readerIndex(ffIndex);
                if (isStartWithHead(in) && isEndingWithEnd(in)) {
                    if (in.readableBytes() >= 6066) {
                        logger.info("新一代传感器数据，接收,{}",result);
                        out.add(readPacketData(in));
                    } else {
                        return;
                    }
                } else {
                    logger.info("继续读取一个完整包");
                    in.readByte();
                }
            }
            in.clear();
        }
    }
//
////        if (in.readableBytes() > 4) {
////            //标记读取位置
////            in.markReaderIndex();
////            //读取数据长度
////            int n = in.readInt();
////            if (n>0){
////                if (in.readableBytes() < n) {
////                    //如果数据长度小于设定的数据，则处于缓存状态
////                    in.resetReaderIndex();
////                    //缓存当前数据，等待数据接入
////                    return ;
////                }
////                byte[] bytes = new byte[n];
////                System.out.println("长度"+bytes.length);
////                in.readBytes(bytes);
////
////                System.out.println(ByteBufUtil.hexDump(bytes));
////                out.add(readPacketData(in));
////            }
////
////        }
//
////        if (null==in){
////            return ;
////        }
////        System.out.println("一共长度+"+in.readableBytes());
////        if (in.readableBytes()<4){
////            return;
////        }
////        int length = in.readInt();
////        System.out.println("in.readableBytes()="+in.readableBytes());
////        System.out.println("decode length"+length);
////        System.out.println("比较:"+(in.readableBytes() < length));
////        if (in.readableBytes() < length){
////            throw  new Exception("实际长度太小");
////        }
////        ByteBuf byteBuf = in.readBytes(length);
////        byte[] bytes = new byte[byteBuf.readableBytes()];
////        byteBuf.readBytes(bytes);
//
////        System.out.println(String.format("ctx.name()=%s", ctx.name()));
////        String result = ByteBufUtil.hexDump(in);
////        if (result.length()<100){
////            logger.info("数据微振数据不合符规范，忽略, length:{}, {}", result.length(),result);
////            in.clear();
////        }
////        else {
////            logger.info("收到数据 {}", result);
////            if(result.startsWith("242356")) {
////                System.out.println("错误信息");
////            }
////            int ffIndex;
////            while ((ffIndex = findFF(in)) != -1 && in.readableBytes() >= 4) {
////                in.readerIndex(ffIndex);
////                if (isStartWithHead(in)) {
////                    if (in.readableBytes() >= 100) {
////                        out.add(readPacketData(in));
////                    } else {
////                        return;
////                    }
////                } else {
////                    in.readByte();
////                }
////            }
////            in.clear();
////        }
//    }

    private ByteBuf readPacketData(ByteBuf in) {
        in.readInt();
        return in.readBytes(6062);
    }


    private int findFF(ByteBuf byteBuf) {
        return byteBuf.forEachByte(value -> value != (byte) 0xFF);
    }

    private void discardBefore(ByteBuf byteBuf, byte index) {
        byteBuf.readerIndex(index);
    }

    private boolean isStartWithHead(ByteBuf byteBuf) {
        byte[] _head = new byte[]{(byte) 0xFF, (byte) 0xEE, (byte) 0xAA, (byte) 0x55};
        byte[] head = new byte[4];
        byteBuf.getBytes(0, head);
        logger.debug("byte string start with {}", Arrays.toString(head));
        return Arrays.equals(head, _head);
    }


    private boolean isEndingWithEnd(ByteBuf byteBuf) {
//        byte[] _head = new byte[]{(byte) 0xFF, (byte) 0xEE, (byte) 0xAA, (byte) 0x55};
        byte[] _end = new byte[]{(byte) 0x55, (byte) 0xAA, (byte) 0xEE, (byte) 0xFF};
        byte[] end = new byte[4];
        byteBuf.getBytes(6062, end);
        logger.debug("byte string end with {}", Arrays.toString(end));
        return Arrays.equals(_end, _end);
    }
}

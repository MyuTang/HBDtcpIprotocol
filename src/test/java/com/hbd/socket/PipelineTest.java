package com.hbd.socket;

import com.hbd.socket.server.netty.handler.ByteArrayToHexStringDecoder;
import com.hbd.socket.server.netty.handler.ThingFrameDecode;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Ren Xunxiao
 * @date 2018/10/12
 * @copyright Hanboard
 * Created by Ren Xunxiao on 2018/10/12
 */
public class PipelineTest {
    @Test
    public void pipelineTest() {
        EmbeddedChannel testChannel = new EmbeddedChannel(new ThingFrameDecode(), new ByteArrayToHexStringDecoder(), new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
//                System.out.println(ctx);
//                System.out.println(msg);
            }
        });

        byte[] data = getData();
        String s = "0000";
        System.out.println("data.length"+data.length);
//        for (int i = 0; i < 10; i++) {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                ByteBuf buf = Unpooled.wrappedBuffer(data);
                testChannel.writeInbound(buf.readBytes(buf));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
//            ByteBuf buf = Unpooled.wrappedBuffer(data);
//            testChannel.writeInbound(buf.readBytes(buf.readableBytes() / 2));
//            testChannel.writeInbound(buf.readBytes(buf));
//            testChannel.writeInbound(Unpooled.wrappedBuffer(getWrongData()));
//        }
    }

    private byte[] getData() {
        String rawData = "FFEEAA5507E30001000B00100028003A19801785000000000000000005200000000000000000000000000000000000000000000000000000005000001770694D53486561640001000400D10086036E0000000000000000000000000000006368300000000000000000000000000000FEFFFFFFFFFFFFDC05000000000000FCA9F1D24D62603F00000000000000000000000000000000000000000000343F0000000000000000000000000000F03F670000000000000000000000000000000000023C0000000000E880427F959E3C00001301190B2A1DA1000000000000000000000000000000000000000000000088F4910660D822000000008068A97F03E04FBF0610D3220001000000A76FCB00000000000000204100000000000000000103000080D32200C03CDE61E84FBF0600000000000000004A0000001800000000000000000000004A000000180000006368310000000000000000000000000000FEFFFFFFFFFFFFDC05000000000000FCA9F1D24D62603F00000000000000000000000000000000000000000000343F0000000000000000000000000000F03F670000000000000000000000000000000040493D0000000000B80842BA78673E00001301190B2A1DA1000000000000000000000000000000000000000000000088F4910660D822000000008068A97F03E04FBF0610D3220001000000A76FCB00000000000000204100000000000000000103000080D32200C03CDE61E84FBF0600000000000000004A0000001800000000000000000000004A000000180000003055000000000000000000000000000000000000000000000000000000000000FCA9F1D24D62603F00000000000000000000000000000000000000000000343F0000000000000000000000000000F03F670000000000000000000000000000000096AA3F0000000000889041E098394100001301190B2A1DA1000000000000000000000000000000000000000000000088F4910660D822000000008068A97F03E04FBF0610D3220001000000A76FCB00000000000000204100000000000000000103000080D32200C03CDE61E84FBF0600000000000000004A0000001800000000000000000000004A000000180000006368330000000000000000000000000000FEFFFFFFFFFFFFDC05000000000000FCA9F1D24D62603F00000000000000000000000000000000000000000000343F0000000000000000000000000000F03F000000000000000000000000000000000000000000000000000000000000000000001301190B2A1DA1000000000000000000000000000000000000000000000088F4910660D822000000008068A97F03E04FBF0610D3220001000000A76FCB00000000000000204100000000000000000103000080D32200C03CDE61E84FBF0600000000000000004A0000001800000000000000000000004A000000180000003CFF7DFF3D0CA10F3AFF7EFF420CA10F36FF82FF410CA10F37FF82FF4F0CA10F3BFF81FF4A0CA10F3BFF81FF460CA10F3CFF80FF4B0CA10F40FF83FF490CA10F3DFF7DFF490CA10F3FFF7BFF470CA10F39FF7CFF440CA10F36FF7EFF4B0CA10F3AFF83FF500CA10F3DFF7CFF490CA10F42FF7EFF470CA10F3DFF7EFF490CA10F3AFF81FF460CA10F39FF82FF440CA10F3AFF7FFF440CA10F3DFF82FF430CA10F3BFF83FF4A0CA10F3BFF82FF490CA10F43FF83FF460CA10F3CFF7EFF470CA10F3BFF7DFF410CA10F36FF7FFF410CA10F37FF83FF490CA10F37FF86FF490CA10F3BFF84FF4B0CA10F43FF83FF480CA10F41FF7DFF450CA10F3EFF7DFF490CA10F3CFF7DFF3F0CA10F36FF81FF420CA10F33FF80FF460CA10F36FF7DFF470CA10F35FF78FF4B0CA10F3AFF80FF4D0CA10F3CFF81FF4C0CA10F3DFF7DFF3F0CA10F35FF7BFF3F0CA10F37FF82FF490CA10F39FF80FF480CA10F37FF82FF490CA10F34FF7FFF4B0CA10F37FF80FF4D0CA10F3DFF82FF4D0CA10F3FFF7EFF4C0CA10F3CFF7CFF420CA10F39FF79FF400CA10F3FFF7EFF450CA10F3BFF80FF4A0CA10F39FF85FF4B0CA10F3CFF81FF480CA10F41FF7DFF4A0CA10F40FF7FFF420CA10F3AFF83FF3F0CA10F39FF83FF460CA10F35FF80FF440CA10F38FF7FFF440CA10F38FF82FF480CA10F3EFF88FF4D0CA10F3EFF82FF470CA10F40FF7FFF440CA10F3FFF7EFF3C0CA10F3EFF7FFF410CA10F38FF7FFF440CA10F34FF7EFF4C0CA10F35FF7FFF500CA10F3CFF83FF4C0CA10F3BFF83FF490CA10F3CFF81FF420CA10F3BFF7EFF420CA10F38FF82FF400CA10F36FF7FFF4D0CA10F38FF7DFF4C0CA10F37FF7FFF480CA10F3AFF80FF4F0CA10F3CFF84FF4E0CA10F42FF85FF520CA10F41FF82FF500CA10F3EFF82FF4B0CA10F3BFF81FF470CA10F3DFF84FF490CA10F39FF81FF4C0CA10F3BFF82FF4C0CA10F39FF83FF450CA10F3CFF84FF440CA10F39FF7FFF410CA10F41FF7CFF460CA10F3EFF7EFF490CA10F3DFF7CFF460CA10F40FF80FF420CA10F39FF83FF3D0CA10F38FF85FF400CA10F3DFF83FF400CA10F3DFF80FF400CA10F3BFF80FF430CA10F40FF7BFF430CA10F3CFF80FF440CA10F39FF83FF440CA10F37FF83FF4E0CA10F37FF83FF4D0CA10F3DFF81FF480CA10F3FFF7CFF480CA10F3CFF7DFF460CA10F3CFF86FF4B0CA10F3DFF82FF4A0CA10F3AFF7DFF500CA10F35FF7DFF4E0CA10F3AFF7CFF500CA10F3DFF85FF4C0CA10F3FFF81FF4A0CA10F44FF82FF430CA10F3FFF7FFF460CA10F3DFF7CFF480CA10F44FF7DFF4A0CA10F3DFF7FFF450CA10F3BFF7BFF460CA10F42FF7DFF430CA10F40FF7CFF410CA10F3CFF7FFF430CA10F3FFF80FF3E0CA10F3FFF7EFF3E0CA10F38FF86FF3C0CA10F37FF84FF430CA10F3AFF80FF460CA10F3AFF80FF450CA10F3AFF82FF450CA10F3EFF83FF4D0CA10F41FF7BFF490CA10F40FF7FFF440CA10F3BFF80FF450CA10F37FF7CFF440CA10F36FF83FF460CA10F33FF83FF450CA10F39FF84FF4D0CA10F3CFF81FF4B0CA10F40FF86FF4B0CA10F3FFF80FF460CA10F41FF78FF480CA10F3FFF80FF480CA10F36FF7FFF4B0CA10F35FF7FFF480CA10F3AFF80FF490CA10F3BFF83FF450CA10F3FFF7FFF480CA10F3FFF84FF460CA10F42FF80FF460CA10F3FFF7DFF450CA10F3FFF7EFF470CA10F39FF80FF400CA10F38FF85FF420CA10F3DFF82FF450CA10F41FF80FF460CA10F40FF82FF430CA10F43FF81FF410CA10F43FF82FF470CA10F3FFF83FF440CA10F3AFF81FF440CA10F41FF81FF460CA10F3BFF7EFF450CA10F36FF82FF430CA10F3DFF7EFF4A0CA10F3FFF82FF450CA10F3BFF83FF450CA10F3FFF80FF460CA10F37FF7FFF4A0CA10F32FF81FF480CA10F3CFF83FF4A0CA10F39FF81FF440CA10F3CFF7FFF480CA10F3BFF7DFF480CA10F3BFF80FF480CA10F3CFF7EFF480CA10F3EFF7FFF430CA10F3CFF7DFF460CA10F3CFF7DFF450CA10F3FFF7FFF480CA10F3AFF82FF4C0CA10F3DFF80FF470CA10F3DFF82FF450CA10F40FF82FF480CA10F41FF82FF430CA10F41FF84FF430CA10F40FF81FF480CA10F3AFF80FF4A0CA10F36FF80FF480CA10F33FF7CFF440CA10F3FFF80FF490CA10F3EFF7FFF490CA10F43FF7FFF4D0CA10F3EFF7FFF480CA10F3AFF82FF480CA10F39FF7DFF470CA10F3BFF7FFF4B0CA10F3BFF7EFF4A0CA10F37FF7FFF480CA10F3FFF80FF400CA10F3CFF7DFF400CA10F40FF7DFF480CA10F41FF7CFF490CA10F37FF7CFF470CA10F3CFF7DFF460CA10F38FF7DFF410CA10F33FF7FFF480CA10F3AFF82FF430CA10F44FF7AFF410CA10F45FF7CFF400CA10F41FF7EFF440CA10F3AFF82FF440CA10F3BFF82FF490CA10F3DFF7CFF4A0CA10F40FF7CFF430CA10F3DFF85FF430CA10F42FF7FFF470CA10F43FF82FF470CA10F43FF81FF450CA10F3FFF82FF440CA10F3BFF81FF4B0CA10F35FF84FF3F0CA10F34FF81FF4A0CA10F3EFF80FF480CA10F3EFF83FF440CA10F42FF81FF430CA10F42FF7CFF3F0CA10F43FF7AFF3F0CA10F3CFF7AFF400CA10F38FF7FFF3A0CA10F39FF83FF440CA10F3AFF81FF470CA10F3EFF82FF440CA10F3AFF81FF4B0CA10F42FF81FF430CA10F3DFF7FFF460CA10F38FF80FF430CA10F3CFF7EFF450CA10F37FF82FF460CA10F2EFF82FF490CA10F39FF86FF480CA10F3DFF82FF4D0CA10F3AFF7FFF490CA10F40FF80FF4D0CA10F3CFF7CFF480CA10F3BFF7CFF4B0CA10F3AFF84FF460CA10F35FF82FF470CA10F37FF7FFF480CA10F39FF81FF480CA10F3CFF7DFF410CA10F42FF7EFF440CA10F42FF80FF490CA10F3FFF80FF490CA10F3FFF7EFF440CA10F41FF7FFF450CA10F3CFF86FF400CA10F37FF83FF440CA10F3BFF7BFF430CA10F43FF7BFF420CA10F43FF7FFF410CA10F46FF7DFF410CA10F3CFF82FF460CA10F3AFF80FF470CA10F35FF81FF420CA10F3AFF7EFF410CA10F38FF7CFF420CA10F38FF80FF470CA10F43FF7BFF4C0CA10F40FF80FF470CA10F3DFF7EFF450CA10F3DFF81FF4A0CA10F39FF7FFF490CA10F36FF81FF4B0CA10F38FF81FF490CA10F37FF83FF4A0CA10F41FF7CFF470CA10F3FFF7FFF480CA10F3EFF7FFF4A0CA10F3FFF80FF450CA10F3CFF7AFF450CA10F37FF7DFF470CA10F3AFF7EFF490CA10F3DFF81FF490CA10F3FFF83FF490CA10F46FF7FFF480CA10F46FF7FFF440CA10F41FF7FFF400CA10F3EFF7EFF420CA10F37FF7BFF430CA10F38FF80FF460CA10F37FF80FF480CA10F39FF80FF440CA10F39FF81FF490CA10F3EFF80FF4A0CA10F3BFF80FF4D0CA10F3EFF81FF4A0CA10F3BFF7FFF4A0CA10F39FF7FFF480CA10F3DFF82FF4B0CA10F3AFF80FF480CA10F39FF81FF460CA10F40FF81FF470CA10F45FF82FF450CA10F41FF7CFF470CA10F37FF80FF420CA10F37FF81FF440CA10F35FF7CFF440CA10F39FF82FF470CA10F3BFF84FF4A0CA10F39FF83FF480CA10F43FF81FF440CA10F3FFF7AFF430CA10F41FF7AFF420CA10F3FFF7DFF460CA10F3DFF7FFF460CA10F35FF7FFF460CA10F3BFF7CFF460CA10F39FF82FF450CA10F42FF82FF480CA10F41FF80FF450CA10F3FFF7DFF480CA10F3FFF81FF4A0CA10F3FFF81FF470CA10F3CFF7FFF4A0CA10F39FF7FFF4B0CA10F3AFF7DFF440CA10F3CFF85FF470CA10F40FF7FFF480CA10F42FF7CFF470CA10F40FF7AFF460CA10F40FF7DFF470CA10F3CFF81FF480CA10F3AFF7FFF4A0CA10F36FF7EFF430CA10F36FF83FF460CA10F3BFF82FF480CA10F3EFF87FF470CA10F3DFF7FFF450CA10F3DFF7CFF440CA10F3FFF7AFF470CA10F3CFF7EFF470CA10F3CFF82FF460CA10F3DFF82FF480CA10F3BFF80FF460CA10F3AFF7FFF4D0CA10F3EFF7FFF430CA10F3CFF7EFF460CA10F39FF7DFF470CA10F38FF83FF490CA10F39FF81FF490CA10F3AFF81FF490CA10F39FF80FF4A0CA10F41FF7EFF470CA10F45FF7EFF470CA10F47FF85FF480CA10F3CFF7DFF4A0CA10F3BFF7CFF410CA10F36FF7CFF430CA10F3BFF80FF430CA10F38FF84FF450CA10F38FF82FF4C0CA10F41FF7DFF490CA10F42FF7AFF470CA10F41FF81FF4A0CA10F3FFF82FF470CA10F3EFF7EFF490CA10F39FF7AFF480CA10F39FF82FF470CA10F3CFF81FF4A0CA10F3EFF7BFF470CA10F3FFF7CFF470CA10F40FF81FF480CA10F3FFF80FF490CA10F3AFF7DFF450CA10F3CFF7FFF460CA10F38FF84FF460CA10F3DFF81FF4A0CA10F3CFF83FF440CA10F38FF7EFF430CA10F41FF7FFF470CA10F3FFF87FF420CA10F39FF81FF420CA10F3BFF7CFF430CA10F3AFF7EFF460CA10F3BFF81FF430CA10F3FFF7EFF4B0CA10F3CFF80FF430CA10F42FF82FF420CA10F46FF81FF420CA10F3FFF81FF410CA10F3EFF7DFF410CA10F3AFF7FFF400CA10F38FF7CFF430CA10F39FF80FF450CA10F3AFF84FF470CA10F3BFF80FF480CA10F3AFF81FF4A0CA10F3DFF81FF4B0CA10F3FFF7FFF440CA10F3EFF7DFF450CA10F3BFF84FF460CA10F3CFF80FF470CA10F3DFF84FF4F0CA10F3CFF80FF4C0CA10F40FF7EFF440CA10F3FFF7EFF460CA10F3FFF7FFF470CA10F40FF7EFF440CA10F3EFF7CFF480CA10F37FF7FFF440CA10F34FF80FF4A0CA10F39FF80FF450CA10F3AFF81FF430CA10F3FFF80FF400CA10F42FF7EFF430CA10F40FF7EFF480CA10F39FF7EFF440CA10F3BFF7FFF470CA10F39FF80FF4A0CA10F3CFF7EFF4D0CA10F3FFF7EFF480CA10F42FF80FF420CA10F40FF7FFF480CA10F42FF7EFF430CA10F40FF7EFF410CA10F40FF7DFF440CA10F41FF81FF480CA10F3AFF82FF470CA10F3BFF81FF4A0CA10F39FF81FF480CA10F3CFF81FF450CA10F3FFF80FF420CA10F3DFF81FF460CA10F41FF7CFF460CA10F39FF7CFF450CA10F38FF7DFF460CA10F3AFF7DFF4A0CA10F3AFF7EFF4B0CA10F37FF7FFF440CA10F37FF7DFF3F0CA10F3FFF7EFF430CA10F43FF80FF460CA10F3FFF7EFF490CA10F3EFF80FF460CA10F3BFF81FF470CA10F3AFF7FFF440CA10F3DFF7CFF460CA10F3DFF7FFF440CA10F3BFF84FF440CA10F3FFF80FF420CA10F45FF7AFF440CA10F3CFF7CFF420CA10F38FF81FF470CA10F36FF86FF4A0CA10F39FF7EFF470CA10F3EFF7BFF480CA10F40FF7EFF420CA10F3FFF81FF490CA10F3EFF7FFF440CA10F40FF7CFF420CA10F3CFF7BFF490CA10F37FF7FFF450CA10F36FF80FF450CA10F3CFF86FF490CA10F3AFF82FF480CA10F3AFF80FF4A0CA10F40FF7DFF460CA10F3FFF81FF460CA10F3CFF7FFF440CA10F3CFF7EFF430CA10F37FF85FF420CA10F36FF87FF4A0CA10F32FF80FF4B0CA10F3BFF81FF480CA10F3FFF7EFF460CA10F3FFF82FF490CA10F3CFF7EFF480CA10F3CFF7CFF420CA10F3BFF7EFF450CA10F3BFF82FF470CA10F3BFF82FF490CA10F35FF82FF470CA10F3BFF7DFF450CA10F3FFF7FFF490CA10F40FF80FF4B0CA10F40FF7FFF470CA10F38FF7BFF440CA10F3AFF7EFF4A0CA10F37FF82FF470CA10F3CFF80FF4F0CA10F3AFF7FFF450CA10F41FF7EFF490CA10F3DFF7FFF420CA10F41FF7FFF430CA10F3DFF81FF460CA10F36FF83FF470CA10F3CFF82FF4A0CA10F3EFF82FF4E0CA10F3BFF85FF460CA10F3FFF80FF460CA10F41FF80FF430CA10F42FF79FF430CA10F40FF80FF420CA10F40FF80FF460CA10F3CFF81FF420CA10F39FF80FF490CA10F3DFF7FFF490CA10F3BFF85FF410CA10F3BFF7CFF450CA10F3EFF7AFF400CA10F43FF7AFF3D0CA10F42FF80FF3D0CA10F38FF71FF350CA10F31FF50FF010CA10F3FFF1AFF370BA10F73FFC9FE7A09A10F0800E6FE5308A10F1A00F1FF240AA10FCAFE89000B0EA10F22FEA100E310A10F25FEEDFF0F11A10F51FF23FF310FA10FFCFF35FF560DA10FE3FE8DFFE50BA10F3BFFC9FF5C0BA10FE5FF29FF690BA10F62FF7AFF600CA10F74FEDCFF930DA10F7BFEF4FFF20DA10FEAFE63FF1D0DA10F51FFF5FE680BA10F5FFF34FFDF0AA10F5EFF71FFCB0AA10F79FF73FF540BA10F5AFF94FFDF0BA10F31FFB3FF110CA10F6BFFB7FFDF0BA10FACFF82FFA20BA10FA7FF65FF9E0BA10F76FF65FFD90BA10F52FF59FF1E0CA10F33FF42FF250CA10F2CFF58FFFF0BA10F2FFF52FFED0BA10F2AFF5CFFE00BA10F11FF62FFF70BA10F14FF65FF060CA10F37FF66FF430CA10F1DFF81FFBC0CA10F00FFBBFF080DA10F18FFD4FF130DA10F42FFB8FFB20CA10F62FFB1FF760CA10F63FF9DFF520CA10F75FF74FF420CA10F77FF48FF400CA10F47FF3AFF390CA10F37FF65FF580CA10F2AFF84FF7B0CA10F23FFA9FFA70CA10F01FFB6FFE80CA10FF4FEBBFFFE0CA10FFEFEA5FFCC0CA10F27FF98FF820CA10F43FF84FF440CA10F53FF7EFF200CA10F5BFF73FF070CA10F62FF71FFFF0BA10F58FF62FFD10BA10F59FF5BFFAD0BA10F4AFF4EFFAF0BA10F36FF3FFFC90BA10F2CFF49FFEE0BA10F21FF62FFF00BA10F35FF6FFFD00BA10F5DFF86FFC10BA10F5FFF9CFFDD0BA10F5AFFAAFF160CA10F61FF9FFF530CA10F56FFA4FF8D0CA10F3DFFAFFFB20CA10F36FFA1FFC50CA10F44FF8BFFDF0CA10F23FF7AFFE30CA10F06FF7BFFD00CA10F13FF6EFFA30CA10F27FF60FF7F0CA10F29FF73FF730CA10F1FFF7EFF870CA10F2DFF8CFF7F0CA10F37FF89FF6C0CA10F36FF8FFF530CA10F40FF87FF360CA10F48FF7BFF180CA10F3EFF7DFFFE0BA10F4CFF76FFDE0BA10F5EFF6CFFBE0BA10F67FF71FFB90BA10F5DFF6CFFCA0BA10F5CFF6BFFE20BA10F54FF6DFFFE0BA10F38FF81FF280CA10F2BFF8DFF490CA10F33FF8FFF640CA10F34FF8FFF830CA10F2BFF8EFF8F0CA10F35FF85FF8A0CA10F3DFF7BFF7E0CA10F37FF7CFF730CA10F3BFF80FF770CA10F3BFF82FF7C0CA10F37FF83FF760CA10F2DFF82FF6E0CA10F34FF7AFF660CA10F42FF74FF4D0CA10F3FFF77FF380CA10F3EFF7CFF2C0CA10F42FF79FF2E0CA10F4AFF79FF330CA10F43FF7DFF360CA10F3CFF80FF3F0CA10F3CFF81FF410CA10F3CFF7EFF3A0CA10F39FF88FF3D0CA10F39FF85FF380CA10F38FF87FF3C0CA10F55AAEEFF";
        String rawData2 = "ffeeaa5507e30002001b000e0035003a19801778000000000000000300220000000003e80000000000000000012a009a07cb0008056c000700000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        return getByteFromHex(rawData2);
    }

    private byte[] getWrongData() {
        char[] digits = "0987654321ABCDEF".toCharArray();
        int length = (int) (Math.random() * 100);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(digits[(int) (Math.random() * digits.length)]);
        }

        return getByteFromHex(sb.toString());
    }

    private byte[] getByteFromHex(String rawData) {
        System.out.println("rawData==========>+"+rawData);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i + 1 < rawData.length(); i += 2) {
            strings.add(rawData.substring(i, i + 2));
        }

        byte[] data = new byte[rawData.length() / 2];
        System.out.println("data.length"+data.length);
        for (int i = 0; i < strings.size(); i++) {
            data[i] = (byte) Integer.parseInt(strings.get(i), 16);
        }

        return data;
    }

    @Test
    public void setupServerAndTest() {

    }
}
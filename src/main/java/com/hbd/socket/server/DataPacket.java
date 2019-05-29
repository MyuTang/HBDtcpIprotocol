package com.hbd.socket.server;

/**
 * @author Ren Xunxiao
 * @date 2018/10/11
 * @copyright Hanboard
 * Created by Ren Xunxiao on 2018/10/11
 */
public class DataPacket {
    private String packet;

    private DataPacket(String packet) {
        this.packet = packet;
    }

    public static DataPacket from(String hexString) {
        return new DataPacket(hexString);
    }

    public static DataPacket from(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = String.format("%02X", bytes[i]);
            sb.append(hex);
        }
        return new DataPacket(sb.toString());
    }

    @Override
    public String toString() {
        return "DataPacket{" +
                "packet='" + packet + '\'' +
                '}';
    }

    public String getPacket() {
        return packet;
    }
}

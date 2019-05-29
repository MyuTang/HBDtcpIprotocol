package com.hbd.socket.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.time.LocalDateTime;

/**
 * @author Ren Xunxiao
 * @date 2018/10/22
 * @copyright Hanboard
 * Created by Ren Xunxiao on 2018/10/22
 */
public class ReplyMessage {
    public static short RESERVE_NORMAL = 0x0000;
    public static short RESERVE_ABNORMAL = 0x0001;
    public static short SWITCH_ON = 0x0055;
    public static short SWITCH_OFF = 0x0000;

    private short year;
    private short month;
    private short day;
    private short hour;
    private short minute;
    private short second;
    private short gpsUploadTime = 0;
    private short cloudPlatformUploadTime = 0;
    private short alarmOnOff = 0;
    private short cameraOnOff = 0;
    private short reserve1 = 0;
    private short reserve2 = 0;
    private short reserve3 = 0;
    private short reserve4 = 0;
    private short reserve5 = 0;

    public ReplyMessage(short year, short month, short day, short hour, short minute, short second, short gpsUploadTime, short cloudPlatformUploadTime, short alarmOnOff, short cameraOnOff, short reserve1, short reserve2, short reserve3, short reserve4, short reserve5) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.gpsUploadTime = gpsUploadTime;
        this.cloudPlatformUploadTime = cloudPlatformUploadTime;
        this.alarmOnOff = alarmOnOff;
        this.cameraOnOff = cameraOnOff;
        this.reserve1 = reserve1;
        this.reserve2 = reserve2;
        this.reserve3 = reserve3;
        this.reserve4 = reserve4;
        this.reserve5 = reserve5;
    }

    public ReplyMessage setGpsUploadTime(short gpsUploadTime) {
        this.gpsUploadTime = gpsUploadTime;
        return this;
    }

    public ReplyMessage setCloudPlatformUploadTime(short cloudPlatformUploadTime) {
        this.cloudPlatformUploadTime = cloudPlatformUploadTime;
        return this;
    }

    public short getYear() {
        return year;
    }

    public short getMonth() {
        return month;
    }

    public short getDay() {
        return day;
    }

    public short getHour() {
        return hour;
    }

    public short getMinute() {
        return minute;
    }

    public short getSecond() {
        return second;
    }

    public short getGpsUploadTime() {
        return gpsUploadTime;
    }

    public short getCloudPlatformUploadTime() {
        return cloudPlatformUploadTime;
    }

    public short getAlarmOnOff() {
        return alarmOnOff;
    }

    public short getCameraOnOff() {
        return cameraOnOff;
    }

    public short getReserve1() {
        return reserve1;
    }

    public short getReserve2() {
        return reserve2;
    }

    public short getReserve3() {
        return reserve3;
    }

    public short getReserve4() {
        return reserve4;
    }

    public short getReserve5() {
        return reserve5;
    }

    public ReplyMessage(LocalDateTime dateTime, short gpsUploadTime, short cloudPlatformUploadTime, short alarmOnOff, short cameraOnOff) {
        year = (short) dateTime.getYear();
        month = (short) dateTime.getMonth().getValue();
        day = (short) dateTime.getDayOfMonth();
        hour = (short) dateTime.getHour();
        minute = (short) dateTime.getMinute();
        second = (short) dateTime.getSecond();

        this.gpsUploadTime = gpsUploadTime;
        this.cloudPlatformUploadTime = cloudPlatformUploadTime;
        this.alarmOnOff = alarmOnOff;
        this.cameraOnOff = cameraOnOff;
    }

    public ReplyMessage(short gpsUploadTime, short cloudPlatformUploadTime, short alarmOnOff, short cameraOnOff) {
        LocalDateTime dateTime = LocalDateTime.now();
        year = (short) dateTime.getYear();
        month = (short) dateTime.getMonth().getValue();
        day = (short) dateTime.getDayOfMonth();
        hour = (short) dateTime.getHour();
        minute = (short) dateTime.getMinute();
        second = (short) dateTime.getSecond();

        this.gpsUploadTime = gpsUploadTime;
        this.cloudPlatformUploadTime = cloudPlatformUploadTime;
        this.alarmOnOff = alarmOnOff;
        this.cameraOnOff = cameraOnOff;
    }

    public ReplyMessage() {
        LocalDateTime dateTime = LocalDateTime.now();
        year = (short) dateTime.getYear();
        month = (short) dateTime.getMonth().getValue();
        day = (short) dateTime.getDayOfMonth();
        hour = (short) dateTime.getHour();
        minute = (short) dateTime.getMinute();
        second = (short) dateTime.getSecond();
    }

    ReplyMessage turnOnAlarm() {
        this.alarmOnOff = SWITCH_ON;
        return this;
    }

    ReplyMessage turnOffAlarm() {
        this.alarmOnOff = SWITCH_OFF;
        return this;
    }

    ReplyMessage turnOffCamera() {
        this.cameraOnOff = SWITCH_OFF;
        return this;
    }

    ReplyMessage turnOnCamera() {
        this.cameraOnOff = SWITCH_ON;
        return this;
    }

    public ByteBuf write(ByteBuf buffer) {
        buffer.writeShort(year);
        buffer.writeShort(month);
        buffer.writeShort(day);
        buffer.writeShort(hour);
        buffer.writeShort(minute);
        buffer.writeShort(second);
        buffer.writeShort(gpsUploadTime);
        buffer.writeShort(cloudPlatformUploadTime);
        buffer.writeShort(alarmOnOff);
        buffer.writeShort(cameraOnOff);
        buffer.writeShort(reserve1);
        buffer.writeShort(reserve2);
        buffer.writeShort(reserve3);
        buffer.writeShort(reserve4);
        buffer.writeShort(reserve5);
        return buffer;
    }
}

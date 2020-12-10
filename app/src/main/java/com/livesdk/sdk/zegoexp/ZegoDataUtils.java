package com.livesdk.sdk.zegoexp;

import java.util.ArrayList;

import im.zego.zegoexpress.constants.ZegoEngineState;
import im.zego.zegoexpress.constants.ZegoNetworkSpeedTestType;
import im.zego.zegoexpress.constants.ZegoPlayerMediaEvent;
import im.zego.zegoexpress.constants.ZegoPlayerState;
import im.zego.zegoexpress.constants.ZegoPublishChannel;
import im.zego.zegoexpress.constants.ZegoPublisherState;
import im.zego.zegoexpress.constants.ZegoRemoteDeviceState;
import im.zego.zegoexpress.constants.ZegoRoomState;
import im.zego.zegoexpress.constants.ZegoStreamQualityLevel;
import im.zego.zegoexpress.constants.ZegoStreamRelayCDNState;
import im.zego.zegoexpress.constants.ZegoStreamRelayCDNUpdateReason;
import im.zego.zegoexpress.constants.ZegoUpdateType;
import im.zego.zegoexpress.constants.ZegoVideoCodecID;
import im.zego.zegoexpress.entity.ZegoBarrageMessageInfo;
import im.zego.zegoexpress.entity.ZegoBroadcastMessageInfo;
import im.zego.zegoexpress.entity.ZegoNetworkSpeedTestQuality;
import im.zego.zegoexpress.entity.ZegoPerformanceStatus;
import im.zego.zegoexpress.entity.ZegoPlayStreamQuality;
import im.zego.zegoexpress.entity.ZegoPublishStreamQuality;
import im.zego.zegoexpress.entity.ZegoRoomExtraInfo;
import im.zego.zegoexpress.entity.ZegoStream;
import im.zego.zegoexpress.entity.ZegoStreamRelayCDNInfo;
import im.zego.zegoexpress.entity.ZegoUser;

public class ZegoDataUtils {
    public static String toString(ZegoEngineState state) {
        return state == null ? "null" : "" + state.name();
    }

    public static String toString(ZegoRoomState state) {
        return state == null ? "null" : "" + state.name();
    }

    public static String toString(ZegoUpdateType state) {
        return state == null ? "null" : "" + state.name();
    }

    public static String userListToString(ArrayList<ZegoUser> userList) {
        if (userList != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            for (ZegoUser user : userList) {
                stringBuilder.append(toString(user));
                stringBuilder.append(",");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        return "null";
    }

    public static String toString(ZegoUser user) {
        if (user != null) {
            return "{ userID=" + user.userID + "," + "userName=" + user.userName + " }";
        }
        return "null";
    }

    public static String streamListToString(ArrayList<ZegoStream> streamList) {
        if (streamList != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            for (ZegoStream stream : streamList) {
                stringBuilder.append(toString(stream));
                stringBuilder.append(",");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        return "null";
    }

    private static String toString(ZegoStream stream) {
        if (stream != null) {
            return "{ streamID=" + stream.streamID + "," + "extraInfo=" + stream.extraInfo + ",user=" + toString(stream.user) + " }";
        }
        return "null";
    }

    public static String roomExtraInfoListToString(ArrayList<ZegoRoomExtraInfo> roomExtraInfoList) {
        if (roomExtraInfoList != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            for (ZegoRoomExtraInfo info : roomExtraInfoList) {
                stringBuilder.append(toString(info));
                stringBuilder.append(",");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        return "null";
    }

    private static String toString(ZegoRoomExtraInfo info) {
        if (info != null) {
            return "{ key=" + info.key + ",value=" + info.value + ",updateTime=" + info.updateTime + ",updateUser=" + toString(info.updateUser) + " }";
        }
        return "null";
    }

    public static String toString(ZegoPublisherState state) {
        return state == null ? "null" : "" + state.name();
    }

    public static String toString(ZegoPublishStreamQuality quality) {
        if (quality != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ videoCaptureFPS=" + quality.videoCaptureFPS + ",");
            stringBuilder.append("videoEncodeFPS=" + quality.videoEncodeFPS + ",");
            stringBuilder.append("videoSendFPS=" + quality.videoSendFPS + ",");
            stringBuilder.append("audioCaptureFPS=" + quality.audioCaptureFPS + ",");
            stringBuilder.append("audioSendFPS=" + quality.audioSendFPS + ",");
            stringBuilder.append("audioKBPS=" + quality.audioKBPS + ",");

            stringBuilder.append("rtt=" + quality.rtt + ",");
            stringBuilder.append("packetLostRate=" + quality.packetLostRate + ",");

            stringBuilder.append("level=" +  toString(quality.level) + ",");
            stringBuilder.append("isHardwareEncode=" + quality.isHardwareEncode + ",");

            stringBuilder.append("videoCodecID=" + toString(quality.videoCodecID) + ",");
            stringBuilder.append("totalSendBytes=" + quality.totalSendBytes + ",");

            stringBuilder.append("audioSendBytes=" + quality.audioSendBytes + ",");
            stringBuilder.append("videoSendBytes=" + quality.videoSendBytes + " }");
            return stringBuilder.toString();
        }
        return "null";
    }

    private static String toString(ZegoVideoCodecID videoCodecID) {
        if (videoCodecID != null) {
            return "" + videoCodecID.name();
        }
        return "null";
    }

    private static String toString(ZegoStreamQualityLevel level) {
        if (level != null) {
            return "" + level.name();
        }
        return "null";
    }

    public static String toString(ZegoPublishChannel channel) {
        if (channel != null) {
            return "" + channel.name();
        }
        return "null";
    }

    public static String cdnInfoListToString(ArrayList<ZegoStreamRelayCDNInfo> infoList) {
        if (infoList != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            for (ZegoStreamRelayCDNInfo info : infoList) {
                stringBuilder.append(toString(info));
                stringBuilder.append(",");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        return "null";
    }

    private static String toString(ZegoStreamRelayCDNInfo info) {
        if (info != null) {
            return "{ url=" + info.url + ",stateTime=" + info.stateTime + ",state=" + toString(info.state) + ",updateReason=" + toString(info.updateReason) + " }";
        }
        return "null";
    }

    private static String toString(ZegoStreamRelayCDNUpdateReason updateReason) {
        if (updateReason != null) {
            return "" + updateReason.name();
        }
        return "null";
    }

    private static String toString(ZegoStreamRelayCDNState state) {
        if (state != null) {
            return "" + state.name();
        }
        return "null";
    }

    public static String toString(ZegoPlayerState state) {
        if (state != null) {
            return "" + state.name();
        }
        return "null";
    }

    public static String toString(ZegoPlayStreamQuality quality) {
        if (quality != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ videoRecvFPS=" + quality.videoRecvFPS + ",");
            stringBuilder.append("videoDejitterFPS=" + quality.videoDejitterFPS + ",");
            stringBuilder.append("videoDecodeFPS=" + quality.videoDecodeFPS + ",");
            stringBuilder.append("videoRenderFPS=" + quality.videoRenderFPS + ",");
            stringBuilder.append("videoKBPS=" + quality.videoKBPS + ",");
            stringBuilder.append("videoBreakRate=" + quality.videoBreakRate + ",");
            stringBuilder.append("audioRecvFPS=" + quality.audioRecvFPS + ",");
            stringBuilder.append("audioDejitterFPS=" + quality.audioDejitterFPS + ",");
            stringBuilder.append("audioDecodeFPS=" + quality.audioDecodeFPS + ",");
            stringBuilder.append("audioRenderFPS=" + quality.audioRenderFPS + ",");
            stringBuilder.append("audioKBPS=" + quality.audioKBPS + ",");
            stringBuilder.append("audioBreakRate=" + quality.audioBreakRate + ",");
            stringBuilder.append("rtt=" + quality.rtt + ",");
            stringBuilder.append("packetLostRate=" + quality.packetLostRate + ",");
            stringBuilder.append("peerToPeerDelay=" + quality.peerToPeerDelay + ",");
            stringBuilder.append("peerToPeerPacketLostRate=" + quality.peerToPeerPacketLostRate + ",");
            stringBuilder.append("level=" +  toString(quality.level) + ",");
            stringBuilder.append("delay=" + quality.delay + ",");
            stringBuilder.append("avTimestampDiff=" + quality.avTimestampDiff + ",");
            stringBuilder.append("isHardwareDecode=" + quality.isHardwareDecode + ",");
            stringBuilder.append("videoCodecID=" + toString(quality.videoCodecID) + ",");
            stringBuilder.append("totalRecvBytes=" + quality.totalRecvBytes + ",");
            stringBuilder.append("audioRecvBytes=" + quality.audioRecvBytes + ",");
            stringBuilder.append("videoRecvBytes=" + quality.videoRecvBytes + " }");
            return stringBuilder.toString();
        }
        return "null";
    }

    public static String toString(ZegoPlayerMediaEvent event) {
        if (event != null) {
            return "" + event.name();
        }
        return "null";
    }

    public static String toString(ZegoPerformanceStatus status) {
        if (status != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ cpuUsageApp=" + status.cpuUsageApp + ",");
            stringBuilder.append("cpuUsageSystem=" + status.cpuUsageSystem + ",");
            stringBuilder.append("memoryUsageApp=" + status.memoryUsageApp + ",");
            stringBuilder.append("memoryUsageSystem=" + status.memoryUsageSystem + ",");
            stringBuilder.append("memoryUsedApp=" + status.memoryUsedApp + " }");
            return stringBuilder.toString();
        }
        return "null";
    }

    public static String toString(ZegoRemoteDeviceState state) {
        if (state != null) {
            return "" + state.name();
        }
        return "null";
    }

    public static String messageListToString(ArrayList<ZegoBroadcastMessageInfo> messageList) {
        if (messageList != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            for (ZegoBroadcastMessageInfo info : messageList) {
                stringBuilder.append(toString(info));
                stringBuilder.append(",");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        return "null";

    }

    private static String toString(ZegoBroadcastMessageInfo info) {
        if (info != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ message=" + info.message + ",");
            stringBuilder.append("messageID=" + info.messageID + ",");
            stringBuilder.append("sendTime=" + info.sendTime + ",");
            stringBuilder.append("fromUser=" + toString(info.fromUser) + " }");
            return stringBuilder.toString();
        }
        return "null";
    }

    public static String zbMessageListToString(ArrayList<ZegoBarrageMessageInfo> messageList) {
        if (messageList != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            for (ZegoBarrageMessageInfo info : messageList) {
                stringBuilder.append(toString(info));
                stringBuilder.append(",");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        return "null";
    }

    private static String toString(ZegoBarrageMessageInfo info) {
        if (info != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ message=" + info.message + ",");
            stringBuilder.append("messageID=" + info.messageID + ",");
            stringBuilder.append("sendTime=" + info.sendTime + ",");
            stringBuilder.append("fromUser=" + toString(info.fromUser) + " }");
            return stringBuilder.toString();
        }
        return "null";
    }

    public static String toString(ZegoNetworkSpeedTestType type) {
        if (type != null) {
            return "" + type.name();
        }
        return "null";
    }

    public static String toString(ZegoNetworkSpeedTestQuality quality) {
        if (quality != null) {

            return "{ connectCost=" + quality.connectCost + ",rtt=" + quality.rtt + ",packetLostRate=" + quality.packetLostRate + " }";
        }
        return "null";
    }
}

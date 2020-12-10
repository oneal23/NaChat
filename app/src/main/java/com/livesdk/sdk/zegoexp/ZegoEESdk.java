package com.livesdk.sdk.zegoexp;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.livesdk.ILSCallback;
import com.livesdk.ILSSelfView;
import com.livesdk.ILiveSdk;
import com.livesdk.utils.Logger;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import im.zego.zegoexpress.ZegoExpressEngine;
import im.zego.zegoexpress.callback.IZegoEventHandler;
import im.zego.zegoexpress.constants.ZegoEngineState;
import im.zego.zegoexpress.constants.ZegoNetworkSpeedTestType;
import im.zego.zegoexpress.constants.ZegoPlayerMediaEvent;
import im.zego.zegoexpress.constants.ZegoPlayerState;
import im.zego.zegoexpress.constants.ZegoPublishChannel;
import im.zego.zegoexpress.constants.ZegoPublisherState;
import im.zego.zegoexpress.constants.ZegoRemoteDeviceState;
import im.zego.zegoexpress.constants.ZegoRoomState;
import im.zego.zegoexpress.constants.ZegoScenario;
import im.zego.zegoexpress.constants.ZegoUpdateType;
import im.zego.zegoexpress.entity.ZegoBarrageMessageInfo;
import im.zego.zegoexpress.entity.ZegoBroadcastMessageInfo;
import im.zego.zegoexpress.entity.ZegoCanvas;
import im.zego.zegoexpress.entity.ZegoNetworkSpeedTestQuality;
import im.zego.zegoexpress.entity.ZegoPerformanceStatus;
import im.zego.zegoexpress.entity.ZegoPlayStreamQuality;
import im.zego.zegoexpress.entity.ZegoPublishStreamQuality;
import im.zego.zegoexpress.entity.ZegoRoomExtraInfo;
import im.zego.zegoexpress.entity.ZegoStream;
import im.zego.zegoexpress.entity.ZegoStreamRelayCDNInfo;
import im.zego.zegoexpress.entity.ZegoUser;

public class ZegoEESdk extends IZegoEventHandler implements ILiveSdk {

    private final static String TAG = "ZegoLiveSdk";

    private final static long AppId = 0L; //your zego appId
    private final static String AppSign = ""; //your zego appsign
    private ZegoExpressEngine engine;
    private ZegoScenario scenario = ZegoScenario.GENERAL;
    private boolean isTextEvn = true;
    private String roomId;
    private ZegoUser user;
    private ILSCallback lsCallback;
    private ILSSelfView selfView;
    public ZegoExpressEngine getEngine() {
        return engine;
    }

    public String getRoomId() {
        return roomId;
    }

    public ZegoUser getUser() {
        return user;
    }

    public ILSCallback getLsCallback() {
        return lsCallback;
    }

    public ILSSelfView getSelfView() {
        return selfView;
    }

    @Override
    public void onDebugError(int errorCode, String funcName, String info) {
        super.onDebugError(errorCode, funcName, info);
        Logger.d(TAG, "onDebugError errorCode=" + errorCode + ",funcName=" + funcName + ",info=" + info);
    }

    @Override
    public void onEngineStateUpdate(ZegoEngineState state) {
        super.onEngineStateUpdate(state);
        Logger.d(TAG, "onEngineStateUpdate state=" + ZegoDataUtils.toString(state));
    }

    @Override
    public void onRoomStateUpdate(String roomID, ZegoRoomState state, int errorCode, JSONObject extendedData) {
        super.onRoomStateUpdate(roomID, state, errorCode, extendedData);
        Logger.d(TAG, "onRoomStateUpdate roomID=" + roomID + ",state=" + ZegoDataUtils.toString(state) + ",errorCode=" + errorCode + ",extendedData" + extendedData);
        switch (state) {
            case CONNECTING: {
                if (getLsCallback() != null) {
                    getLsCallback().onLSLoginRoom(true);
                }
                break;
            }
            case CONNECTED:{
                if (getLsCallback() != null) {
                    getLsCallback().onLSLoginRoomResult(true);
                }
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public void onRoomUserUpdate(String roomID, ZegoUpdateType updateType, ArrayList<ZegoUser> userList) {
        super.onRoomUserUpdate(roomID, updateType, userList);
        Logger.d(TAG, "onRoomUserUpdate roomID=" + roomID + ",updateType=" + ZegoDataUtils.toString(updateType) + ",userList=" + ZegoDataUtils.userListToString(userList));
    }

    @Override
    public void onRoomOnlineUserCountUpdate(String roomID, int count) {
        super.onRoomOnlineUserCountUpdate(roomID, count);
        Logger.d(TAG, "onRoomOnlineUserCountUpdate roomID=" + roomID + ",count=" + count);
    }

    @Override
    public void onRoomStreamUpdate(String roomID, ZegoUpdateType updateType, ArrayList<ZegoStream> streamList, JSONObject extendedData) {
        super.onRoomStreamUpdate(roomID, updateType, streamList, extendedData);
        Logger.d(TAG, "onRoomStreamUpdate roomID=" + roomID + ",updateType=" + ZegoDataUtils.toString(updateType) + ",streamList=" + ZegoDataUtils.streamListToString(streamList) + ",extendedData" + extendedData);
        switch (updateType) {
            case ADD:{
                for (ZegoStream info: streamList) {
                    if (getLsCallback() != null && info != null) {
                        getLsCallback().onLSAddUser(info.user.userID, info.user.userName, info.streamID);
                    }
                }
                break;
            }
            case DELETE:{
                for (ZegoStream info: streamList) {
                    if (getLsCallback() != null && info != null) {
                        getLsCallback().onLSDeleteUser(info.user.userID, info.user.userName, info.streamID);
                    }
                }
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public void onRoomStreamExtraInfoUpdate(String roomID, ArrayList<ZegoStream> streamList) {
        super.onRoomStreamExtraInfoUpdate(roomID, streamList);
        Logger.d(TAG, "onRoomStreamExtraInfoUpdate roomID=" + roomID + ",streamList=" + ZegoDataUtils.streamListToString(streamList));
    }

    @Override
    public void onRoomExtraInfoUpdate(String roomID, ArrayList<ZegoRoomExtraInfo> roomExtraInfoList) {
        super.onRoomExtraInfoUpdate(roomID, roomExtraInfoList);
        Logger.d(TAG, "onRoomStreamExtraInfoUpdate roomID=" + roomID + ",roomExtraInfoList=" + ZegoDataUtils.roomExtraInfoListToString(roomExtraInfoList));
    }

    @Override
    public void onPublisherStateUpdate(String streamID, ZegoPublisherState state, int errorCode, JSONObject extendedData) {
        super.onPublisherStateUpdate(streamID, state, errorCode, extendedData);
        Logger.d(TAG, "onPublisherStateUpdate streamID=" + streamID + ",state=" + ZegoDataUtils.toString(state) + ",extendedData=" + extendedData);
        if (getLsCallback() != null) {
            getLsCallback().onLSPublishState(state == ZegoPublisherState.PUBLISHING);
        }
    }

    @Override
    public void onPublisherQualityUpdate(String streamID, ZegoPublishStreamQuality quality) {
        super.onPublisherQualityUpdate(streamID, quality);
        Logger.d(TAG, "onPublisherQualityUpdate streamID=" + streamID + ",quality=" + ZegoDataUtils.toString(quality));
    }

    @Override
    public void onPublisherCapturedAudioFirstFrame() {
        super.onPublisherCapturedAudioFirstFrame();
        Logger.d(TAG, "onPublisherCapturedAudioFirstFrame");
    }

    @Override
    public void onPublisherCapturedVideoFirstFrame(ZegoPublishChannel channel) {
        super.onPublisherCapturedVideoFirstFrame(channel);
        Logger.d(TAG, "onPublisherCapturedVideoFirstFrame channel=" + ZegoDataUtils.toString(channel));
    }

    @Override
    public void onPublisherVideoSizeChanged(int width, int height, ZegoPublishChannel channel) {
        super.onPublisherVideoSizeChanged(width, height, channel);
        Logger.d(TAG, "onPublisherVideoSizeChanged channel=" + ZegoDataUtils.toString(channel) + ",width=" + width + ",height=" + height);
    }

    @Override
    public void onPublisherRelayCDNStateUpdate(String streamID, ArrayList<ZegoStreamRelayCDNInfo> infoList) {
        super.onPublisherRelayCDNStateUpdate(streamID, infoList);
        Logger.d(TAG, "onPublisherRelayCDNStateUpdate streamID=" + streamID + ",infoList=" + ZegoDataUtils.cdnInfoListToString(infoList));
    }

    @Override
    public void onPlayerStateUpdate(String streamID, ZegoPlayerState state, int errorCode, JSONObject extendedData) {
        super.onPlayerStateUpdate(streamID, state, errorCode, extendedData);
        Logger.d(TAG, "onPlayerStateUpdate streamID=" + streamID + ",state=" + ZegoDataUtils.toString(state) + ",errorCode=" + errorCode + ",extendedData=" + extendedData);
    }

    @Override
    public void onPlayerQualityUpdate(String streamID, ZegoPlayStreamQuality quality) {
        super.onPlayerQualityUpdate(streamID, quality);
        Logger.d(TAG, "onPlayerQualityUpdate streamID=" + streamID + ",quality=" + ZegoDataUtils.toString(quality));
    }

    @Override
    public void onPlayerMediaEvent(String streamID, ZegoPlayerMediaEvent event) {
        super.onPlayerMediaEvent(streamID, event);
        Logger.d(TAG, "onPlayerMediaEvent streamID=" + streamID + ",event=" + ZegoDataUtils.toString(event));
    }

    @Override
    public void onPlayerRecvAudioFirstFrame(String streamID) {
        super.onPlayerRecvAudioFirstFrame(streamID);
        Logger.d(TAG, "onPlayerRecvAudioFirstFrame streamID=" + streamID);
    }

    @Override
    public void onPlayerRecvVideoFirstFrame(String streamID) {
        super.onPlayerRecvVideoFirstFrame(streamID);
        Logger.d(TAG, "onPlayerRecvVideoFirstFrame streamID=" + streamID);
    }

    @Override
    public void onPlayerRenderVideoFirstFrame(String streamID) {
        super.onPlayerRenderVideoFirstFrame(streamID);
        Logger.d(TAG, "onPlayerRenderVideoFirstFrame streamID=" + streamID);
    }

    @Override
    public void onPlayerVideoSizeChanged(String streamID, int width, int height) {
        super.onPlayerVideoSizeChanged(streamID, width, height);
        Logger.d(TAG, "onPlayerVideoSizeChanged streamID=" + streamID + ",width=" + width + ",height=" + height);
    }

    @Override
    public void onPlayerRecvSEI(String streamID, byte[] data) {
        super.onPlayerRecvSEI(streamID, data);
        Logger.d(TAG, "onPlayerRecvSEI streamID=" + streamID + ",data=" + (data == null ? "null" : data.toString()));
    }

    @Override
    public void onMixerRelayCDNStateUpdate(String taskID, ArrayList<ZegoStreamRelayCDNInfo> infoList) {
        super.onMixerRelayCDNStateUpdate(taskID, infoList);
        Logger.d(TAG, "onMixerRelayCDNStateUpdate taskID=" + taskID + ",infoList=" + ZegoDataUtils.cdnInfoListToString(infoList));
    }

    @Override
    public void onMixerSoundLevelUpdate(HashMap<Integer, Float> soundLevels) {
        super.onMixerSoundLevelUpdate(soundLevels);
        Logger.d(TAG, "onMixerSoundLevelUpdate soundLevels=" + (soundLevels == null ? "null" : soundLevels.toString()));
    }

    @Override
    public void onCapturedSoundLevelUpdate(float soundLevel) {
        super.onCapturedSoundLevelUpdate(soundLevel);
        Logger.d(TAG, "onCapturedSoundLevelUpdate soundLevels=" + soundLevel);
    }

    @Override
    public void onRemoteSoundLevelUpdate(HashMap<String, Float> soundLevels) {
        super.onRemoteSoundLevelUpdate(soundLevels);
        Logger.d(TAG, "onRemoteSoundLevelUpdate soundLevels=" + (soundLevels == null ? "null" : soundLevels.toString()));
    }

    @Override
    public void onCapturedAudioSpectrumUpdate(float[] audioSpectrum) {
        super.onCapturedAudioSpectrumUpdate(audioSpectrum);
        Logger.d(TAG, "onCapturedAudioSpectrumUpdate audioSpectrum=" + (audioSpectrum == null ? "null" : audioSpectrum.toString()));
    }

    @Override
    public void onRemoteAudioSpectrumUpdate(HashMap<String, float[]> audioSpectrums) {
        super.onRemoteAudioSpectrumUpdate(audioSpectrums);
        Logger.d(TAG, "onRemoteAudioSpectrumUpdate audioSpectrums=" + (audioSpectrums == null ? "null" : audioSpectrums.toString()));
    }

    @Override
    public void onPerformanceStatusUpdate(ZegoPerformanceStatus status) {
        super.onPerformanceStatusUpdate(status);
        Logger.d(TAG, "onPerformanceStatusUpdate status=" + ZegoDataUtils.toString(status));
    }

    @Override
    public void onDeviceError(int errorCode, String deviceName) {
        super.onDeviceError(errorCode, deviceName);
        Logger.d(TAG, "onDeviceError errorCode=" + errorCode + ",deviceName=" + deviceName);
    }

    @Override
    public void onRemoteCameraStateUpdate(String streamID, ZegoRemoteDeviceState state) {
        super.onRemoteCameraStateUpdate(streamID, state);
        Logger.d(TAG, "onRemoteCameraStateUpdate streamID=" + streamID + ",state=" + ZegoDataUtils.toString(state));
    }

    @Override
    public void onRemoteMicStateUpdate(String streamID, ZegoRemoteDeviceState state) {
        super.onRemoteMicStateUpdate(streamID, state);
        Logger.d(TAG, "onRemoteMicStateUpdate streamID=" + streamID + ",state=" + ZegoDataUtils.toString(state));
    }

    @Override
    public void onIMRecvBroadcastMessage(String roomID, ArrayList<ZegoBroadcastMessageInfo> messageList) {
        super.onIMRecvBroadcastMessage(roomID, messageList);
        Logger.d(TAG, "onIMRecvBroadcastMessage roomID=" + roomID + ",messageList=" + ZegoDataUtils.messageListToString(messageList));
    }

    @Override
    public void onIMRecvBarrageMessage(String roomID, ArrayList<ZegoBarrageMessageInfo> messageList) {
        super.onIMRecvBarrageMessage(roomID, messageList);
        Logger.d(TAG, "onIMRecvBarrageMessage roomID=" + roomID + ",messageList=" + ZegoDataUtils.zbMessageListToString(messageList));
    }

    @Override
    public void onIMRecvCustomCommand(String roomID, ZegoUser fromUser, String command) {
        super.onIMRecvCustomCommand(roomID, fromUser, command);
        Logger.d(TAG, "onIMRecvCustomCommand roomID=" + roomID + ",fromUser=" + ZegoDataUtils.toString(fromUser) + ",command=" + command);
    }

    @Override
    public void onNetworkSpeedTestError(int errorCode, ZegoNetworkSpeedTestType type) {
        super.onNetworkSpeedTestError(errorCode, type);
        Logger.d(TAG, "onNetworkSpeedTestError errorCode=" + errorCode + ",type=" + ZegoDataUtils.toString(type));
    }

    @Override
    public void onNetworkSpeedTestQualityUpdate(ZegoNetworkSpeedTestQuality quality, ZegoNetworkSpeedTestType type) {
        super.onNetworkSpeedTestQualityUpdate(quality, type);
        Logger.d(TAG, "onNetworkSpeedTestQualityUpdate quality=" + ZegoDataUtils.toString(quality) + ",type=" + ZegoDataUtils.toString(type));
    }

    @Override
    public void init(Context context, String userId, String userName) {
        if (engine == null) {
            Application application = (Application) context.getApplicationContext();
            engine = ZegoExpressEngine.createEngine(AppId, AppSign, isTextEvn, scenario, application, this);
        }
        ZegoUser user = new ZegoUser(userId, userName);
        this.user = user;
    }

    @Override
    public void destory() {
        ZegoExpressEngine.destroyEngine(null);
        engine = null;
    }

    @Override
    public void setLSCallback(ILSCallback lsCallback) {
        this.lsCallback = lsCallback;
    }

    @Override
    public void loginRoom(String roomId) {
        if (getEngine() != null) {
            this.roomId = roomId;
            getEngine().loginRoom(roomId, getUser());
        }
    }

    @Override
    public void logoutRoom() {
        if (getEngine() != null && getRoomId() != null) {
            if (getLsCallback() != null) {
                getLsCallback().onLSStopLivePublisher();
                getLsCallback().onLSStopLivePlayer();
            }
            getEngine().logoutRoom(getRoomId());
            this.roomId = null;
            this.user = null;
        }
    }

    @Override
    public boolean startPreview(ILSSelfView view) {
        if (getEngine() != null && view != null) {
            selfView = view;
            getEngine().startPreview(new ZegoCanvas(view.getView()));
            return true;
        }
        return false;
    }

    @Override
    public void stopPreview() {
        if (getEngine() != null) {
            getEngine().stopPreview();
        }
    }

    @Override
    public void resetPreviewView() {
        if (getEngine() != null && getSelfView() != null) {
            getEngine().startPreview(null);
            getEngine().startPreview(new ZegoCanvas(getSelfView().getView()));
        }
    }

    @Override
    public void startPublish(String streamId) {
        if (getEngine() != null) {
            getEngine().startPublishingStream(streamId);
        }
    }

    @Override
    public void stopPublish() {
        if (getEngine() != null) {
            getEngine().stopPublishingStream();
        }
    }

    @Override
    public void startPlayStream(String streamId, View view) {
        if (getEngine() != null) {
            getEngine().startPlayingStream(streamId, new ZegoCanvas(view));
        }
    }

    @Override
    public void stopPlayStream(String streamId) {
        if (getEngine() != null) {
            getEngine().stopPlayingStream(streamId);
        }
    }
}

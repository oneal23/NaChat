package com.livesdk.client;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.livesdk.ILSCallback;
import com.livesdk.ILSRoom;
import com.livesdk.ILSRoomClient;
import com.livesdk.ILSSelfView;
import com.livesdk.ILSUser;
import com.livesdk.ILSView;
import com.livesdk.ILSViewHelper;
import com.livesdk.data.LSRoom;
import com.livesdk.data.LSSelfUser;
import com.livesdk.data.LSUser;
import com.livesdk.sdk.LiveSdk;
import com.livesdk.view.LSSelfView;
import com.livesdk.view.LSUserView;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LSRoomClient implements ILSRoomClient, ILSCallback {

    private final static int MaxUserViewNum = 5;
    private static volatile LSRoomClient singleton;
    private WeakReference<Activity> activityWeakReference;
    //房间信息
    private ILSRoom room;
    //自己信息
    private LSSelfUser user;

    private ILSSelfView selfView;

    private List<ILSView> userViews;

    private Map<String, ILSView> onlineUserViewMap;

    private ILSViewHelper lsViewHelper;

    public Activity getActivity() {
        if (activityWeakReference != null) {
            return activityWeakReference.get();
        }
        return null;
    }

    public ILSRoom getRoom() {
        return room;
    }

    public ILSUser getUser() {
        return user;
    }


    public ILSSelfView getSelfView() {
        if (getLSViewHelper() != null) {
            selfView.setView(getLSViewHelper().addView(selfView.getIndex(), getViewNums()));
        }
        return selfView;
    }

    private ILSViewHelper getLSViewHelper() {
        return lsViewHelper;
    }

    public void setLSViewHelper(ILSViewHelper lsViewHelper) {
        this.lsViewHelper = lsViewHelper;
    }

    private int getViewNums() {
        int num = 1;
        if (onlineUserViewMap != null) {
            num += onlineUserViewMap.size();
        }
        return num;
    }

    private LSRoomClient() {

    }

    public static LSRoomClient getInstance() {
        if (singleton == null) {
            synchronized (LSRoomClient.class) {
                if (singleton == null) {
                    singleton = new LSRoomClient();
                }
            }
        }
        return singleton;
    }

    @Override
    public boolean init(Context context, String userId, String userName) {
        this.activityWeakReference = new WeakReference<Activity>((Activity) context);
        if (context != null && !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(userName)) {
            user = new LSSelfUser(userId, userName);
            initViews();
            LiveSdk.setLSCallBack(this);
            LiveSdk.init(context, userId, userName);
            return true;
        }
        return false;
    }

    private void initViews() {
        if (selfView == null) {
            selfView = new LSSelfView();
        } else {
            selfView.reset();
        }
        selfView.setUserId(user.getId());

        if (userViews == null) {
            userViews = new LinkedList<>();
            for (int i = 0; i < MaxUserViewNum; ++i) {
                ILSView view = new LSUserView();
                view.setIndex(i + 1);
                userViews.add(view);
            }
        } else {
            for (ILSView view : userViews) {
                view.reset();
            }
        }

        if (onlineUserViewMap == null) {
            onlineUserViewMap = new LinkedHashMap<>();
        } else {
            onlineUserViewMap.clear();
        }
    }

    private View addUserView(String uid) {
        if (!TextUtils.isEmpty(uid)) {
            ILSView ilsView = onlineUserViewMap.get(uid);
            if (ilsView == null) {
                for (ILSView iv : userViews) {
                    if (!TextUtils.isEmpty(uid)) {
                        iv.setUserId(uid);
                        ilsView = iv;
                        break;
                    }
                }
                onlineUserViewMap.put(uid, ilsView);
            }

            if (ilsView != null && getLSViewHelper() != null) {
                return getLSViewHelper().addView(ilsView.getIndex(), getViewNums());
            }
        }
        return null;
    }

    private void deleteUserView(String uid) {
        if (!TextUtils.isEmpty(uid)) {
            ILSView ilsView = onlineUserViewMap.remove(uid);
            if (ilsView != null) {
                ilsView.reset();
                if (getLSViewHelper() != null) {
                    getLSViewHelper().removeView(ilsView.getIndex(), getViewNums());
                }
            }
        }
    }

    private void createLSRoom(String roomId) {
        if (getRoom() != null) {
            if (!roomId.equals(getRoom().getId())) {
                logout();
            }
        } else {
            room = new LSRoom(roomId);
        }
        if (user != null) {
            user.setRoom(room);
        }
    }

    public void login(String roomId) {
        login(roomId, ILSUser.Audience);
    }

    public void login(String roomId, int role) {
        if (!TextUtils.isEmpty(roomId)) {
            createLSRoom(roomId);
            if (getRoom().needLoginRoom()) {
                LiveSdk.loginRoom(roomId);
            } else {
                startPreview();
            }
        }
    }

    public void changeRole(int role) {
        if (getUser() != null && getUser().getRole() != role) {
            getUser().setRole(role);
            if (getUser().isCanPublish()) {
                startPreview();
                startPublish();
            } else {
                stopPreview();
                stopPublish();
            }
        }
    }

    private boolean startPreview() {
        if (getUser() != null && getUser().isCanPublish()) {
            if (getRoom().isStartPreviewed()) {
                LiveSdk.resetPreviewView();
            } else {
                LiveSdk.startPreview(getSelfView());
            }
            return true;
        }
        return false;
    }

    private void stopPreview() {
        LiveSdk.stopPreview();
        if (getRoom() != null) {
            getRoom().setStartPreviewed(false);
        }
    }

    private void startPublish() {
        if (getUser() != null && getUser().isCanPublish()) {
            LiveSdk.startPublish(getUser().getStreamId());
        }
    }

    private void stopPublish() {
        LiveSdk.stopPublish();
    }

    private void startPlayStream(String streamID, String userID) {
        if (getActivity() == null) {
            return;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LiveSdk.startPlayStream(streamID, addUserView(userID));
            }
        });
    }

    private void stopPlayStream(String streamID, final String userID) {
        LiveSdk.stopPlayStream(streamID);
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                deleteUserView(userID);
            }
        });
    }

    public void logout() {
        LiveSdk.logoutRoom();
        if (getRoom() != null) {
            getRoom().reset();
        }
    }

    /*
     * ILSCallback start
     * */
    @Override
    public void onLSLoginRoom(boolean success) {
        if (getRoom() != null) {
            getRoom().setCallLoginRoomOK(success);
            if (success) {
                boolean isStartPreviewed = startPreview();
                getRoom().setStartPreviewed(isStartPreviewed);
            }
        }
    }

    @Override
    public void onLSLoginRoomResult(boolean success) {
        if (getRoom() != null) {
            getRoom().setLogined(success);
            startPublish();
        }
    }

    @Override
    public void onLSStopLivePublisher() {
        stopPreview();
        stopPublish();
    }

    @Override
    public void onLSStopLivePlayer() {
        if (getRoom() != null && getRoom().getUsers() != null) {
            for (ILSUser user : getRoom().getUsers()) {
                LiveSdk.stopPlayStream(user.getStreamId());
            }
        }
    }

    @Override
    public void onLSAddUser(String userID, String userName, String streamID) {
        if (getRoom() != null) {
            ILSUser user = (new LSUser.Builder()).setId(userID).setName(userName).setStreamId(streamID).build();
            getRoom().addUser(user);
            startPlayStream(streamID, userID);
        }
    }

    @Override
    public void onLSDeleteUser(String userID, String userName, String streamID) {
        if (getRoom() != null) {
            getRoom().removeUser(userID);
            stopPlayStream(streamID, userID);
        }
    }

    @Override
    public void onLSPublishState(boolean isPublishOK) {

    }
}

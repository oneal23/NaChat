package com.livesdk;

import android.content.Context;

public interface ILSRoomClient {
    boolean init(Context context, String userId, String userName);
}

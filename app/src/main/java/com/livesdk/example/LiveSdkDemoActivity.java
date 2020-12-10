package com.livesdk.example;


import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.livesdk.ILSUser;
import com.livesdk.ILSViewHelper;
import com.livesdk.client.LSRoomClient;
import com.yyt.nachat.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class LiveSdkDemoActivity extends AppCompatActivity implements ILSViewHelper {


    private final static String RoomID = "100081";
    @BindView(R.id.flVideo0)
    FrameLayout flVideo0;
    @BindView(R.id.flVideo1)
    FrameLayout flVideo1;
    @BindView(R.id.flVideo2)
    FrameLayout flVideo2;
    @BindView(R.id.llVideoLayout1)
    LinearLayout llVideoLayout1;
    @BindView(R.id.flVideo3)
    FrameLayout flVideo3;
    @BindView(R.id.flVideo4)
    FrameLayout flVideo4;
    @BindView(R.id.flVideo5)
    FrameLayout flVideo5;
    @BindView(R.id.ivHeader)
    CircleImageView ivHeader;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvTip)
    TextView tvTip;
    @BindView(R.id.llVideoLayout2)
    LinearLayout llVideoLayout2;
    @BindView(R.id.llLayout)
    LinearLayout llLayout;

    private List<FrameLayout> layoutList;

    //test
    private int num = 1;
    private int role = ILSUser.Audience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_sdk_demo);
        ButterKnife.bind(this);
        layoutList = new ArrayList<>();
        layoutList.add(flVideo0);
        layoutList.add(flVideo1);
        layoutList.add(flVideo2);
        layoutList.add(flVideo3);
        layoutList.add(flVideo4);
        layoutList.add(flVideo5);
        String uid = "81000002";
        LSRoomClient.getInstance().init(this, uid, "n-" + uid);
        LSRoomClient.getInstance().setLSViewHelper(this);
        LSRoomClient.getInstance().login(RoomID, role);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LSRoomClient.getInstance().logout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name = "";
        String avatarUrl = null;
        tvName.setText(name);
//        GlideUtils.getInstance().showAvatar(ivHeader, avatarUrl);
    }

    @Override
    public View addView(int index, int viewNum) {
        TextureView textureView = null;
        if (index >= 0 && index < layoutList.size()) {
            changeLayout(viewNum);
            FrameLayout layout = layoutList.get(index);
            if (layout.getChildCount() <= 0) {
                textureView = new TextureView(layout.getContext());
                layout.addView(textureView);
            } else {
                textureView = (TextureView) layout.getChildAt(0);
            }
        }
        return textureView;
    }

    private void changeLayout(int viewNum) {

//        if (viewNum > 4) {
//            llVideoLayout2.setVisibility(View.VISIBLE);
//            flVideo1.setVisibility(View.VISIBLE);
//            flVideo2.setVisibility(View.VISIBLE);
//            flVideo5.setVisibility(View.VISIBLE);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llVideoLayout1.getLayoutParams();
//            params.height = DensityUtils.dip2px(175);
//            params.topMargin = DensityUtils.dip2px(150);
//            llLayout.updateViewLayout(llVideoLayout1, params);
//            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) llVideoLayout2.getLayoutParams();
//            params2.height = DensityUtils.dip2px(175);
//            params2.topMargin = DensityUtils.dip2px(150);
//            llLayout.updateViewLayout(llVideoLayout2, params2);
//        } else if (viewNum > 2) {
//            llVideoLayout2.setVisibility(View.VISIBLE);
//            flVideo1.setVisibility(View.VISIBLE);
//            flVideo2.setVisibility(View.GONE);
//            flVideo5.setVisibility(View.GONE);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llVideoLayout1.getLayoutParams();
//            params.height = DensityUtils.dip2px(175);
//            params.topMargin = DensityUtils.dip2px(150);
//            llLayout.updateViewLayout(llVideoLayout1, params);
//            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) llVideoLayout2.getLayoutParams();
//            params2.height = DensityUtils.dip2px(175);
//            params2.topMargin = DensityUtils.dip2px(150);
//            llLayout.updateViewLayout(llVideoLayout2, params2);
//        } else if (viewNum > 1) {
//            llVideoLayout2.setVisibility(View.GONE);
//            flVideo1.setVisibility(View.VISIBLE);
//            flVideo2.setVisibility(View.GONE);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llVideoLayout1.getLayoutParams();
//            params.height = DensityUtils.dip2px(300);
//            params.topMargin = DensityUtils.dip2px(150);
//            llLayout.updateViewLayout(llVideoLayout1, params);
//        } else {
//            llVideoLayout2.setVisibility(View.GONE);
//            flVideo1.setVisibility(View.GONE);
//            flVideo2.setVisibility(View.GONE);
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llVideoLayout1.getLayoutParams();
//            params.height = LinearLayout.LayoutParams.MATCH_PARENT;
//            params.topMargin = 0;
//            llLayout.updateViewLayout(llVideoLayout1, params);
//        }
    }

    @Override
    public void removeView(int index, int viewNum) {
        changeLayout(viewNum);
        if (index >= 0 && index < layoutList.size()) {
            FrameLayout layout = layoutList.get(index);
            if (layout.getChildCount() > 0) {
                layout.removeAllViews();
            }
        }
    }

    @OnClick({R.id.ivHeader, R.id.tvTip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivHeader:
                changeNum();
                break;
            case R.id.tvTip:
                changeRole();
                break;
        }
    }


    private void changeRole() {
        if (role == ILSUser.Audience) {
            role = ILSUser.Anchor;
        } else {
            role = ILSUser.Audience;
        }
        LSRoomClient.getInstance().changeRole(role);
    }

    private void changeNum() {
        if (num > 4) {
            num = 1;
        } else if (num > 2) {
            num = 5;
        } else if (num > 1) {
            num = 3;
        } else {
            num = 2;
        }
        changeLayout(num);
    }
}

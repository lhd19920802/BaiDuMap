package com.lhd.baidumap;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.lhd.baidumap.utils.Constants;

public class HelloBaiduMapActivity extends Activity
{

    private BMapManager manager;
    private MapView mapview_main;
    private MapController mapController;
    private int latitude= (int) (40.051*1E6);
    private int longitude= (int) (116.303*1E6);
    private com.baidu.platform.comapi.basestruct.GeoPoint geoPoint=new GeoPoint(latitude, longitude);;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        checkKey();//验证appkey,如果成功会自动把返回的数据加载到布局中的MapView中 并显示出来
        setContentView(R.layout.activity_main);

        init();
    }

    private void init()
    {
        mapview_main = (MapView)findViewById(R.id.mapview_main);
        //设置是否启用内置的缩放控件
        mapview_main.setBuiltInZoomControls(true);
        mapController = mapview_main.getController();

        //设置地图的缩放级别
        mapController.setZoom(12);

    }

    private void checkKey()
    {
        manager = new BMapManager(this);
        manager.init(Constants.KEY, new MKGeneralListener()
        {
            @Override
            public void onGetNetworkState(int i)
            {
                if (i == MKEvent.ERROR_NETWORK_CONNECT)
                {
                    //key验证是否通过
                    Toast.makeText(HelloBaiduMapActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetPermissionState(int i)
            {
                if (i == MKEvent.ERROR_PERMISSION_DENIED)
                {
                    //是否有网络
                    Toast.makeText(HelloBaiduMapActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mapview_main.onResume();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mapview_main.onPause();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mapview_main.destroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)

        {
            case KeyEvent.KEYCODE_1:
                //平移
                mapController.animateTo(geoPoint);
                break;
            case KeyEvent.KEYCODE_2:
//                //获取旋转之前的角度
//                int mapRotation = mapview_main.getMapRotation();
//                //在该角度值之上增加30度
//                mapRotation+=30;
//                //将新设置好的值给地图
//                mapController.setRotation(mapRotation);//转动范围[0,360]

                //旋转 平面内旋转
                float rotation = mapview_main.getMapRotation();
                rotation+=30;
                mapController.setRotation((int) rotation);
                break;
            case KeyEvent.KEYCODE_3:

                //旋转 俯视角旋转
                int mapOverlooking = mapview_main.getMapOverlooking();
                mapOverlooking-=5;
                mapController.setOverlooking(mapOverlooking);
                break;
            case KeyEvent.KEYCODE_4:
                //放大
                //放大
                mapController.zoomIn();//放大一级
                break;
            case KeyEvent.KEYCODE_5:
                //缩小
                //缩小
                mapController.zoomOut();//缩小一级
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}

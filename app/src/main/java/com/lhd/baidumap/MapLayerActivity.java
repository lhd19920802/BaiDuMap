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

/**
 * 地图图层
 * ①底图
     ②时时交通图
     ③卫星图
 */

public class MapLayerActivity extends Activity
{


    private BMapManager manager;
    private MapView mapview_map_layer;
    private MapController mapController;
//    private int latitude= (int) (40.051*1E6);
//    private int longitude= (int) (116.303*1E6);
    private int longitude= (int) (104.089*1E6);
    private int latitude= (int) (30.638*1E6);
    private com.baidu.platform.comapi.basestruct.GeoPoint geoPoint=new GeoPoint(latitude, longitude);;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        checkKey();//验证appkey,如果成功会自动把返回的数据加载到布局中的MapView中 并显示出来
        setContentView(R.layout.activity_map_layer);

        init();
    }

    private void init()
    {
        mapview_map_layer = (MapView)findViewById(R.id.mapview_map_layer);
        //设置是否启用内置的缩放控件
        mapview_map_layer.setBuiltInZoomControls(true);
        mapController = mapview_map_layer.getController();

        //设置地图的缩放级别
        mapController.setZoom(12);
        mapController.setCenter(geoPoint);

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
                    Toast.makeText(MapLayerActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetPermissionState(int i)
            {
                if (i == MKEvent.ERROR_PERMISSION_DENIED)
                {
                    //是否有网络
                    Toast.makeText(MapLayerActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mapview_map_layer.onResume();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mapview_map_layer.onPause();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mapview_map_layer.destroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)

        {
            case KeyEvent.KEYCODE_1:
                //使用底图
                mapview_map_layer.setTraffic(false);
                mapview_map_layer.setSatellite(false);
                break;
            case KeyEvent.KEYCODE_2:
                //实时交通图
                mapview_map_layer.setTraffic(true);
                mapview_map_layer.setSatellite(false);

                break;
            case KeyEvent.KEYCODE_3:
                //卫星图
                mapview_map_layer.setSatellite(true);
                mapview_map_layer.setTraffic(false);
                break;

        }
        return super.onKeyDown(keyCode, event);
    }
}

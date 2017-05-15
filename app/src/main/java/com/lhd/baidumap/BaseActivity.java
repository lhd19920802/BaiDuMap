package com.lhd.baidumap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.lhd.baidumap.utils.Constants;

public class BaseActivity extends Activity
{

    protected BMapManager manager;
    protected MapView mapview_main;
    protected MapController mapController;

    //    private int latitude = (int) (40.051 * 1E6);
    //    private int longitude = (int) (116.303 * 1E6);
    protected int longitude = (int) (104.089 * 1E6);
    protected int latitude = (int) (30.638 * 1E6);
    protected com.baidu.platform.comapi.basestruct.GeoPoint geoPoint = new GeoPoint(latitude,
            longitude);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        checkKey();//验证appkey,如果成功会自动把返回的数据加载到布局中的MapView中 并显示出来
        setContentView(R.layout.activity_base);


        setContentView(R.layout.activity_main);

        init();

    }

    private void init()
    {
        mapview_main = (MapView) findViewById(R.id.mapview_main);
        //设置是否启用内置的缩放控件
        mapview_main.setBuiltInZoomControls(true);

        //卫星图
        mapview_main.setSatellite(true);
        mapview_main.setTraffic(false);
        mapController = mapview_main.getController();

        //设置地图的缩放级别
        mapController.setZoom(19);

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
                    Toast.makeText(BaseActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGetPermissionState(int i)
            {
                if (i == MKEvent.ERROR_PERMISSION_DENIED)
                {
                    //是否有网络
                    Toast.makeText(BaseActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
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

    class MySearchListener implements MKSearchListener

    {
        @Override
        public void onGetPoiResult(MKPoiResult result, int type, int iError)
        {

        }

        @Override
        public void onGetTransitRouteResult(MKTransitRouteResult result, int iError)
        {

        }

        @Override
        public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError)
        {

        }

        @Override
        public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError)
        {

        }

        @Override
        public void onGetAddrResult(MKAddrInfo result, int iError)
        {

        }

        @Override
        public void onGetBusDetailResult(MKBusLineResult result, int iError)
        {

        }

        @Override
        public void onGetSuggestionResult(MKSuggestionResult result, int iError)
        {

        }

        @Override
        public void onGetPoiDetailSearchResult(int type, int iError)
        {

        }
    }

}

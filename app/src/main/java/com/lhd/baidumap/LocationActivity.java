package com.lhd.baidumap;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class LocationActivity extends BaseActivity
{


    private LocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_location);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        location();
        locationClient.start();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        locationClient.stop();
    }

    private void location()
    {
        locationClient = new LocationClient(this);
        //设置参数
        LocationClientOption option = new LocationClientOption();

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的


        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果


        locationClient.setLocOption(option);

        locationClient.registerLocationListener(new MyBDLocationListener());
    }

    class MyBDLocationListener implements BDLocationListener

    {
        @Override
        public void onReceiveLocation(BDLocation bdLocation)
        {
            if (bdLocation == null)
            {
                return;
            }
            //定义覆盖物
            MyLocationOverlay overlay = new MyLocationOverlay(mapview_main);
            LocationData locationData=new LocationData();
            locationData.latitude = bdLocation.getLatitude();
            locationData.longitude = bdLocation.getLongitude();

            overlay.setData(locationData);

            mapview_main.getOverlays().add(overlay);
            mapview_main.refresh();


            GeoPoint geoPoint = new GeoPoint((int)(locationData.longitude * 1E6),(int)(locationData.latitude * 1E6));

            //模拟器定位
            mapController.animateTo(geoPoint);
        }

        @Override
        public void onReceivePoi(BDLocation bdLocation)
        {

        }
    }
}

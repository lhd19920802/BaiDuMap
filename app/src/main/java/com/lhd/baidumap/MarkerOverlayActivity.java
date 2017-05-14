package com.lhd.baidumap;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * 标志覆盖物
 */
public class MarkerOverlayActivity extends BaseActivity
{

    private View view;
    private TextView popTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initPop();
        draw();
    }

    private void initPop()
    {
        view = View.inflate(this, R.layout.pop, null);
        popTextView = (TextView) view.findViewById(R.id.tv_title);
        view.setVisibility(View.GONE);

        //添加到mapView容器中
        mapview_main.addView(view);
    }

    private void draw()
    {
        //装overlayItem的集合
        final ItemizedOverlay<OverlayItem> itemizedOverlay = new ItemizedOverlay<OverlayItem>
                (getResources().getDrawable(R.drawable.icon_eat), mapview_main)
        {
            //重写此方法 点击事件

            @Override
            protected boolean onTap(int index)
            {
                                Toast.makeText(MarkerOverlayActivity.this, this.getItem(index)
                                        .getTitle(), Toast.LENGTH_SHORT).show();


                //                MapView.LayoutParams(int width, int height, GeoPoint point, int
                // alignment)
                popTextView.setText(this.getItem(index).getTitle());
                //                创建自定义布局参数，按地理坐标布局
                //通过params与地理坐标建立一个关系
                MapView.LayoutParams params = new MapView.LayoutParams(ViewGroup.LayoutParams
                        .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, this.getItem(index)
                        .getPoint(), MapView.LayoutParams.BOTTOM_CENTER);
                //更新泡泡的位置
                view.setVisibility(View.VISIBLE);
                mapview_main.updateViewLayout(view, params);
                return super.onTap(index);

            }
        };

        setData(itemizedOverlay);
        mapview_main.getOverlays().add(itemizedOverlay);
        mapview_main.refresh();
    }

    private void setData(ItemizedOverlay<OverlayItem> itemizedOverlay)
    {
        OverlayItem overlayItem = new OverlayItem(geoPoint, "四川大学", "最牛叉的大学");
        itemizedOverlay.addItem(overlayItem);

        overlayItem = new OverlayItem(new GeoPoint(latitude + 1000, longitude), "向北", "增加纬度");
        itemizedOverlay.addItem(overlayItem);

        overlayItem = new OverlayItem(new GeoPoint(latitude, longitude + 1000), "向东", "增加经度");
        itemizedOverlay.addItem(overlayItem);

        overlayItem = new OverlayItem(new GeoPoint(latitude - 1000, longitude - 1000), "向西南",
                "减少经纬度");
        itemizedOverlay.addItem(overlayItem);


    }
}

package com.lhd.baidumap;

import android.os.Bundle;

import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.Symbol;

/**
 * 圆形覆盖物
 */
public class GraphicsOverlayActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_graphics_overlay);
        //画圆
        draw();
    }

    private void draw()
    {
        //        ①定义一个覆盖物
        //        ②设置数据（大小，颜色等属性）
        //        ③获取MapView装覆盖物的集合（MapView身上的覆盖物集合），往里面一塞（添加）
        //        ④刷新（手动）
        GraphicsOverlay overlay = new GraphicsOverlay(mapview_main);
        setData(overlay);
        mapview_main.getOverlays().add(overlay);
        mapview_main.refresh();

    }

    private void setData(GraphicsOverlay overlay)
    {
        //定义几何图形的形状
        Geometry geometry = new Geometry();//几何图形类
        geometry.setCircle(geoPoint, 1000);


        //定义几何图形的样式
        Symbol symbol = new Symbol();//样式类

        Symbol.Color color = symbol.new Color();
        color.red = 255;
        color.green = 0;
        color.blue = 0;
        color.alpha = 100;
        //设置面样式
        symbol.setSurface(color, 1, 0);


        Graphic graphic = new Graphic(geometry, symbol);

        //设置数据
        overlay.setData(graphic);

    }
}

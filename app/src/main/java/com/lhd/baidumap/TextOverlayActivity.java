package com.lhd.baidumap;

import android.graphics.Typeface;
import android.os.Bundle;

import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.map.TextItem;
import com.baidu.mapapi.map.TextOverlay;

/**
 * 文字覆盖物
 */
public class TextOverlayActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        draw();
    }

    private void draw()
    {
        //绘制文本
        TextOverlay overlay = new TextOverlay(mapview_main);
        setData(overlay);
        mapview_main.getOverlays().add(overlay);
        mapview_main.refresh();
    }

    private void setData(TextOverlay overlay)
    {
        TextItem textItem=new TextItem();
//        int	align
//        文字对齐方式 ，为 ALIGN_TOP,ALIGN_CENTER, ALIGN_BOTTOM中的一个值
//        static int	ALIGN_BOTTOM
//        文字对齐参数，下边中点对齐
//        static int	ALIGN_CENTER
//        文字对齐参数，中心对齐
//        static int	ALIGN_TOP
//        文字对齐参数，上边中点对齐
//        Symbol.Color	bgColor
//        文字背景色, 默认为透明
//        Symbol.Color	fontColor
//        文字颜色
//        int	fontSize
//        字号大小
//        GeoPoint pt
//        文字显示的位置，用经纬度坐标表示
//        String	text
//        要显示的文字内容
//        Typeface typeface
//        文字字体， android 字体表示，为空则用系统默认字体.
        textItem.align=TextItem.ALIGN_CENTER;
//        textItem.bgColor = getColor();
        textItem.fontColor=getColor();
        textItem.fontSize=20;
        textItem.pt=geoPoint;
        textItem.text="四川大学望江校区";
        textItem.typeface= Typeface.DEFAULT_BOLD;
        overlay.addText(textItem);
    }

    private Symbol.Color getColor()
    {
        Symbol symbol=new Symbol();
        Symbol.Color color = symbol.new Color();
        color.alpha=100;
        color.red=255;
        color.green=0;
        color.blue=0;
        return color;

    }
}

package com.lhd.baidumap;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;

import java.util.ArrayList;

public class SearchInCityActivity extends BaseActivity
{

    private MKSearch mkSearch;
    private MKSearchListener mkSearchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_search_in_city);
        search();
    }

    private void search()
    {
        mkSearch = new MKSearch();
        mkSearchListener = new MySearchListener()
        {
            @Override
            public void onGetPoiResult(MKPoiResult result, int type, int iError)
            {
                super.onGetPoiResult(result, type, iError);
                if (iError == 0)
                {
                    //返回数据成功
                    if (result != null)
                    {
                        PoiOverlay overlay = new PoiOverlay(SearchInCityActivity.this,
                                mapview_main);
                        setData(overlay, result);
                        //查询下一页之前清除之前的数据
                        mapview_main.getOverlays().clear();
                        mapview_main.getOverlays().add(overlay);
                        mapview_main.refresh();
                    }
                }

            }
        };
        mkSearch.init(manager, mkSearchListener);

        //        public int poiSearchInCity(String city,
        //            String key)
        //        城市poi检索.
        //                异步函数，返回结果在MKSearchListener里的onGetPoiResult方法通知
        //
        //        参数：
        //        city - 城市名
        //        key - 关键词
        mkSearch.poiSearchInCity("成都", "学校");
    }

    private void setData(PoiOverlay overlay, MKPoiResult result)
    {
        ArrayList<MKPoiInfo> allPoi = result.getAllPoi();//当前页的所有条目 默认为10条
        overlay.setData(allPoi);

        //当前页/总共页  当前条目/总条目
        String info = "当前页:" + result.getPageIndex() + "/总页数:" + result.getNumPages() + " 当前页条目:"
                      + result.getCurrentNumPois() + "/总条目:" + result.getNumPois();
        Log.e("TAG", "info===="+info);

        Toast.makeText(SearchInCityActivity.this, info, Toast.LENGTH_SHORT).show();
    }

    private int current=0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_1:
                current++;
                mkSearch.goToPoiPage(current);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}

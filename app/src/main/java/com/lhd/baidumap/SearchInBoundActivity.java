package com.lhd.baidumap;

import android.os.Bundle;

import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;

import java.util.ArrayList;

/**
 * 在范围内搜索
 */
public class SearchInBoundActivity extends BaseActivity
{
    private MKSearch mkSearch;
    private MKSearchListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_search_in_bound);
        search();
    }

    private void search()
    {
        mkSearch = new MKSearch();
        listener = new MySearchListener()
        {
            @Override
            public void onGetPoiResult(MKPoiResult result, int type, int iError)
            {
                super.onGetPoiResult(result, type, iError);
                if (iError == 0)
                {
                    //正确返回
                    if (result != null)
                    {
                        PoiOverlay overlay = new PoiOverlay(SearchInBoundActivity.this,
                                mapview_main);
                        setData(overlay,result);
                        mapview_main.getOverlays().add(overlay);
                        mapview_main.refresh();
                    }
                }
            }
        };

        mkSearch.init(manager, listener);

        mkSearch.poiSearchNearBy("游泳池", geoPoint, 2000);
    }

    private void setData(PoiOverlay overlay, MKPoiResult result)
    {
        ArrayList<MKPoiInfo> arrayList=result.getAllPoi();
        overlay.setData(arrayList);
    }
}

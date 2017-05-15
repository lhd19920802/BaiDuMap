package com.lhd.baidumap;

import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKTransitRoutePlan;
import com.baidu.mapapi.search.MKTransitRouteResult;

/**
 * 公交换乘查询
 */
public class TransitSearchActivity extends BaseActivity
{

    private MKSearch mkSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_transit_search);
        search();
    }

    private void search()
    {
        mkSearch = new MKSearch();
        mkSearch.init(manager, new MySearchListener()
                {
                    @Override
                    public void onGetTransitRouteResult(MKTransitRouteResult result, int iError)
                    {
                        super.onGetTransitRouteResult(result, iError);
                        if (iError == 0)
                        {
                            //返回数据成功
                            if (result != null)
                            {
                                //驾车或步行用RouteOverlay，公交换乘用另一个
                                TransitOverlay overlay = new TransitOverlay(TransitSearchActivity
                                        .this, mapview_main);


                                setData(overlay, result);
                                //查询下一页之前清除之前的数据
                                //                        mapview_main.getOverlays().clear();
                                mapview_main.getOverlays().add(overlay);
                                mapview_main.refresh();
                            }
                        }
                        else
                        {
                            Toast.makeText(TransitSearchActivity.this, "未搜索到指定方案", Toast
                                    .LENGTH_SHORT).show();
                        }
                    }

                }


        );

        //        public int transitSearch(String city,
        //            MKPlanNode start,
        //            MKPlanNode end)
        //        公交路线搜索.
        //                异步函数，返回结果在MKSearchListener里的onGetTransitRouteResult方法通知
        //
        //        参数：
        //        city - 城市名，用于在哪个城市内进行检索(必须填写)
        //        start - 检索的起点，可通过关键字，坐标，两种方式指定
        //        end - 检索的终点，可通过关键字，坐标，两种方式指定
        //        返回：
        //        成功返回0，否则返回-1
        MKPlanNode start = new MKPlanNode();
        start.pt = geoPoint;

        MKPlanNode end = new MKPlanNode();
        end.name = "天府广场";

        //搜索之前设置策略
//        public int setTransitPolicy(int policy)
//        设置路线规划策略.
//                参数为策略常量。对下次搜索有效
//
//        参数：
//        policy - EBUS_TIME_FIRST:时间优先；EBUS_TRANSFER_FIRST:少换乘；EBUS_WALK_FIRST:少步行；EBUS_NO_SUBWAY: 非地铁
//        返回：
//        成功返回0，否则返回-1
        mkSearch.setTransitPolicy(MKSearch.EBUS_TIME_FIRST);
        mkSearch.transitSearch("成都", start, end);
    }

    private void setData(TransitOverlay overlay, MKTransitRouteResult result)
    {
        //判断回复的结果集中 至少有一条
        if (result.getNumPlan() > 0)
        {

            MKTransitRoutePlan plan = result.getPlan(0);
            overlay.setData(plan);
        }
    }
}

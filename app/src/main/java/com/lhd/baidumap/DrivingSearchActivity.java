package com.lhd.baidumap;

import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;

/**
 * 驾车路线搜索
 */
public class DrivingSearchActivity extends BaseActivity
{
    private MKSearch mkSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.activity_driving_search);
        search();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        search();
    }

    private void search()
    {
        mkSearch = new MKSearch();

        mkSearch.init(manager, new MySearchListener()
        {
            @Override
            public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError)
            {
                super.onGetDrivingRouteResult(result, iError);
                if (iError == 0)
                {
                    //返回数据成功
                    if (result != null)
                    {
                        //驾车或步行用RouteOverlay，公交换乘用另一个
                        RouteOverlay overlay = new RouteOverlay(DrivingSearchActivity.this,
                                mapview_main);
                        setData(overlay, result);
                        //查询下一页之前清除之前的数据
//                        mapview_main.getOverlays().clear();
                        mapview_main.getOverlays().add(overlay);
                        mapview_main.refresh();
                    }
                }
                else
                {
                    Toast.makeText(DrivingSearchActivity.this, "未搜索到指定方案", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        //        public int drivingSearch(String startCity,
        //            MKPlanNode start,
        //            String endCity,
        //            MKPlanNode end)
        //        驾乘路线搜索.
        //                异步函数，返回结果在MKSearchListener里的onGetDrivingRouteResult方法通知
        //
//        startCity - 起点所在城市，起点为坐标时可不填
//        start - 搜索的起点，可以为坐标，名称任一种
//        endCity - 终点所在城市，终点为坐标时可不填
//        end - 搜索的终点，可以为坐标，名称任一种
//        wpNodes - 途经点数据
        MKPlanNode start = new MKPlanNode();
        start.pt = geoPoint;

        MKPlanNode end = new MKPlanNode();
        end.name = "天府广场";

//        public int setDrivingPolicy(int policy)
//        设置驾车路线规划策略. 参数为策略常量。对下次搜索有效
//        参数：
//        policy - ECAR_TIME_FIRST:时间优先；ECAR_DIS_FIRST:距离最短；ECAR_FEE_FIRST:费用最少
//        返回：
//        成功返回0，否则返回-1
//        mkSearch.setDrivingPolicy()

        mkSearch.setDrivingPolicy(MKSearch.ECAR_DIS_FIRST);
        //返回数据给监听器
        mkSearch.drivingSearch("成都", start, "成都", end);

//        ArrayList<MKWpNode> nodes=new ArrayList<>();
//        MKWpNode node = new MKWpNode();
//        node.city = "成都";
//        node.name = "磨子桥";
//        nodes.add(node);
//        mkSearch.drivingSearch("成都", start, "成都", end, nodes);
    }

    private void setData(RouteOverlay overlay, MKDrivingRouteResult result)
    {
        if(result.getNumPlan()>0) {

            MKRoute mkRoute=result.getPlan(0).getRoute(0);
            overlay.setData(mkRoute);
        }
    }
}

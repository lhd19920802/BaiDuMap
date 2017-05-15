package com.lhd.baidumap;

import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKWalkingRouteResult;

public class WalkingSearchActivity extends BaseActivity
{

    private MKSearch mkSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_walking_search);

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
                    public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError)
                    {
                        super.onGetWalkingRouteResult(result, iError);
                        if (iError == 0)
                        {
                            //返回数据成功
                            if (result != null)
                            {
                                //驾车或步行用RouteOverlay，公交换乘用另一个
                                RouteOverlay overlay = new RouteOverlay(WalkingSearchActivity.this, mapview_main);


                                setData(overlay, result);
                                //查询下一页之前清除之前的数据
                                //                        mapview_main.getOverlays().clear();
                                mapview_main.getOverlays().add(overlay);
                                mapview_main.refresh();
                            }
                        }
                        else
                        {
                            Toast.makeText(WalkingSearchActivity.this, "未搜索到指定方案", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            );




//        walkingSearch
//
//        public int walkingSearch(String startCity,
//            MKPlanNode start,
//            String endCity,
//            MKPlanNode end)
//        步行路线搜索.
//                异步函数，返回结果在MKSearchListener里的onGetWalkingRouteResult方法通知
//
//        参数：
//        startCity - 起点所在城市，起点为坐标时可不填
//        start - 搜索的起点，可以为坐标，名称任一种
//        endCity - 终点所在城市，终点为坐标时可不填
//        end - 搜索的终点，可以为坐标，名称任一种
        MKPlanNode start = new MKPlanNode();
        start.pt = geoPoint;

        MKPlanNode end = new MKPlanNode();
        end.name = "天府广场";
        mkSearch.walkingSearch("成都", start, "成都", end);


    }

    private void setData(RouteOverlay overlay, MKWalkingRouteResult result)
    {
        MKRoute mkRoute = result.getPlan(0).getRoute(0);
        overlay.setData(mkRoute);
    }

}

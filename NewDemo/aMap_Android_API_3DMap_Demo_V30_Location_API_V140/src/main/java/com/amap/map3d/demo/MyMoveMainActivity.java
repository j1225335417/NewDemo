package com.amap.map3d.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niuhome_vip on 15/12/9.
 */
public class MyMoveMainActivity extends Activity {
    private AMap aMap;
    private MapView mapview;
    private Button line;
    private Button move;
    private int index = -1;
    private Polyline mVirtureRoad;
    LatLng latLng1;
    LatLng latLng2;
    private Marker mMoveMarker;
    private MarkerOptions markerOptions;
    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 10;
    private static final double DISTANCE = 0.0001;
    private PolylineOptions polylineOptions;
    boolean flag = true;
    private MarkerOptions startMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_move_activity_main);
        line = (Button) findViewById(R.id.line);
        move = (Button) findViewById(R.id.move);
        mapview = (MapView) findViewById(R.id.map);
        mapview.onCreate(savedInstanceState);
        aMap = mapview.getMap();
        polylineOptions = new PolylineOptions();
        polylineOptions.width(10);
        polylineOptions.color(Color.RED);
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                index ++;
//                if(index%2==1){
//                    latLng1 = latLng;
//                }else {
//                    latLng2 = latLng;
//                }
                aMap.getMapScreenMarkers().clear();
                if(startMarker==null){
                    startMarker = new MarkerOptions();
                    startMarker.setFlat(true);
                    startMarker.anchor(0.5f, 0.5f);
                    startMarker.icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.start));
                    startMarker.position(latLng);
                    aMap.addMarker(startMarker);
                }
                polylineOptions.add(latLng);
                mVirtureRoad = aMap.addPolyline(polylineOptions);  //两点一线


//                MarkerOptions markerOptions1 = new MarkerOptions();
//                markerOptions1.setFlat(true);
//                markerOptions1.anchor(0.5f, 0.5f);
//                markerOptions1.icon(BitmapDescriptorFactory
//                        .fromResource(R.drawable.end));
//                markerOptions1.title("终点");
//                markerOptions1.position(latLng);
//                aMap.addMarker(markerOptions1);

            }
        });
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aMap.removecache();
                if (latLng1 == null || latLng1 == null) {
                    Toast.makeText(MyMoveMainActivity.this, "请点击", Toast.LENGTH_SHORT).show();
                    return;
                }
                aMap.clear();
//                PolylineOptions polylineOptions = new PolylineOptions();
//                polylineOptions.add(latLng1);
//                polylineOptions.add(latLng2);
//                polylineOptions.width(10);
//                polylineOptions.color(Color.RED);
//                mVirtureRoad = aMap.addPolyline(polylineOptions);  //两点一线

//                MarkerOptions markerOptions1 = new MarkerOptions();
//                markerOptions1.setFlat(true);
//                markerOptions1.anchor(0.5f, 0.5f);
//                markerOptions1.icon(BitmapDescriptorFactory
//                        .fromResource(R.drawable.end));
//                markerOptions1.title("终点");
//                markerOptions1.position(latLng2);
//                mMoveMarker = aMap.addMarker(markerOptions1);


//                markerOptions = new MarkerOptions();
//                markerOptions.setFlat(true);
//                markerOptions.anchor(0.5f, 0.5f);
//                markerOptions.icon(BitmapDescriptorFactory
//                        .fromResource(R.drawable.marker));
//                markerOptions.title("我是小牛");
//                markerOptions.position(latLng1);
//                mMoveMarker = aMap.addMarker(markerOptions);
            }
        });
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MyMoveMainActivity.this,""+ aMap.getMapScreenMarkers().size(),Toast.LENGTH_SHORT).show();
                markerOptions = new MarkerOptions();
                markerOptions.setFlat(true);
                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.marker));
                markerOptions.position(mVirtureRoad.getPoints().get(0));
                mMoveMarker = aMap.addMarker(markerOptions);
                markerOptions.title("小牛正在想你奔跑");
                mMoveMarker.setRotateAngle((float) getAngle(0)); //设置角度
                mMoveMarker.showInfoWindow();
                moveLooper();
            }
        });

    }

    /**
     * 循环进行移动逻辑
     */
    public void moveLooper() {
        new Thread() {

            public void run() {
                while (true) {
                    for (int i = 0; i < mVirtureRoad.getPoints().size() - 1; i++) {


                        LatLng startPoint = mVirtureRoad.getPoints().get(i);
                        LatLng endPoint = mVirtureRoad.getPoints().get(i + 1);
                        mMoveMarker
                                .setPosition(startPoint);

                        mMoveMarker.setRotateAngle((float) getAngle(startPoint,  //根据开始 和结束 改变图标方向
                                endPoint));

                        double slope = getSlope(startPoint, endPoint);
                        //是不是正向的标示（向上设为正向）
                        boolean isReverse = (startPoint.latitude > endPoint.latitude);

                        double intercept = getInterception(slope, startPoint);

                        double xMoveDistance = isReverse ? getXMoveDistance(slope)
                                : -1 * getXMoveDistance(slope);


                        for (double j = startPoint.latitude;
                             !((j > endPoint.latitude) ^ isReverse);

                             j = j
                                     - xMoveDistance) {
                            LatLng latLng = null;
                            if (slope != Double.MAX_VALUE) {
                                latLng = new LatLng(j, (j - intercept) / slope);
                            } else {
                                latLng = new LatLng(j, startPoint.longitude);
                            }
                            mMoveMarker.setPosition(latLng);
                            try {
                                Thread.sleep(TIME_INTERVAL);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }

        }.start();
    }

    /**
     * 计算x方向每次移动的距离
     */
    private double getXMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
    }

    /**
     * 根据点和斜率算取截距
     */
    private double getInterception(double slope, LatLng point) {

        double interception = point.latitude - slope * point.longitude;
        return interception;
    }

    /**
     * 根据点获取图标转的角度
     */
    private double getAngle(int startIndex) {
//        if ((startIndex + 1) >= mVirtureRoad.getPoints().size()) {
//            throw new RuntimeException("index out of bonds");
//        }
        LatLng startPoint = mVirtureRoad.getPoints().get(startIndex);
        LatLng endPoint = mVirtureRoad.getPoints().get(startIndex + 1);
        return getAngle(startPoint, endPoint);
    }

    /**
     * 根据两点算取图标转的角度
     */
    private double getAngle(LatLng fromPoint, LatLng toPoint) {
        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            if (toPoint.latitude > fromPoint.latitude) {
                return 0;
            } else {
                return 180;
            }
        }
        float deltAngle = 0;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
            deltAngle = 180;
        }
        double radio = Math.atan(slope);
        double angle = 180 * (radio / Math.PI) + deltAngle - 90;
        return angle;
    }

    /**
     * 算斜率
     */
    private double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
        return slope;

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapview.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }
}

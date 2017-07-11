package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import com.baidu.trace.model.PushMessage;
import com.google.gson.Gson;

import Utils.FileUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

/* Baidu Trace classes */
import com.baidu.trace.LBSTraceClient;
import com.google.gson.reflect.TypeToken;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TraceActivity extends AppCompatActivity {
    private static final String TAG = "TraceActivity";
    private LBSTraceClient mTraceClient;

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private Polyline mVirtureRoad;
    private Marker mMoveMarker;
    private Handler mHandler;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new TraceActivity.MyLocationListener();
    private BitmapDescriptor bdA;
    private boolean isFirstLoc = true;
    private BDLocation myloc = new BDLocation();

    private OverlayOptions polylineOptions;
    private List<LatLng> polylines = new ArrayList<LatLng>();
    boolean triggerTrace = false;

    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 50;
    private static final double DISTANCE = 0.00005;

    @Bind(R.id.btn_beginTrace) Button _beginTrace;
    @Bind(R.id.btn_endTrace) TextView _endTrace;
    @Bind(R.id.btn_queryTrace) TextView _saveTrace;
    @Bind(R.id.btn_loadTrace) TextView _loadTrace;

    private FileUtil fu = new FileUtil();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.traceview);
        ButterKnife.bind(this);

        mMapView = (TextureMapView) findViewById(R.id.cmapView);
        mMapView.onCreate(this, savedInstanceState);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        bdA = BitmapDescriptorFactory.fromResource(R.drawable.huaji);

        _endTrace.setEnabled(false);
        _saveTrace.setEnabled(false);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        //注册监听函数
        initLocation();
        initRoadData();
        mLocationClient.start();

        mHandler = new Handler(Looper.getMainLooper());

        _beginTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _beginTrace.setEnabled(false);
                _endTrace.setEnabled(true);
                _saveTrace.setEnabled(false);
                backToMyLocation();
               /* mTraceClient.startTrace(mTrace, mTraceListener);
                mTraceClient.startGather(mTraceListener);*/
                triggerTrace = true;

            }
        });

        _endTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _beginTrace.setEnabled(true);
                _endTrace.setEnabled(false);
                _saveTrace.setEnabled(true);
                backToMyLocation();
              /*  mTraceClient.stopGather(mTraceListener);
                mTraceClient.stopTrace(mTrace, mTraceListener);*/
                triggerTrace = false;
            }
        });

        _saveTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _saveTrace.setEnabled(false);
                saveTrace();
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                _saveTrace.setEnabled(true);
                            }
                        }, 3000);

            }
        });
        _loadTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTrace();
            }
        });
    }
    private void loadTrace() {
        polylines = null;           //empty the polylines
        String jsonString = fu.fileToJson("2017-07-11/16:23:43");
        jsonString.replace("\\", "");
        jsonString = jsonString.substring(1, jsonString.length()-1);
        Gson gson = new Gson();
        polylines = gson.fromJson(jsonString ,new TypeToken<ArrayList<LatLng>>() {}.getType());
        polylineOptions = new PolylineOptions().points(polylines).width(4).color(Color.DKGRAY);
        mVirtureRoad = (Polyline) mBaiduMap.addOverlay(polylineOptions);
    }

    private void saveTrace() {
        String path = null;
        String fordername = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String forderpath = fu.createFolder(fordername);
        path = forderpath  + new SimpleDateFormat("HH:mm:ss").format(new Date());
        Gson gson = new Gson();
        String jsonstr = gson.toJson(polylines);
        fu.jsonToFile(path, jsonstr);
    }

    private void backToMyLocation() {
        if(myloc == null || myloc.getLongitude() == 0)  return;
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(new LatLng(myloc.getLatitude(), myloc.getLongitude()))
                .zoom(18)
                .build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
    }

    private void initRoadData() {
        // init latlng data
        double centerLatitude = 39.916049;
        double centerLontitude = 116.399792;
        double deltaAngle = Math.PI / 180 * 5;
        double radius = 0.02;
        OverlayOptions polylineOptions;

        List<LatLng> polylines = new ArrayList<LatLng>();
        for (double i = 0; i < Math.PI * 2; i = i + deltaAngle) {
            float latitude = (float) (-Math.cos(i) * radius + centerLatitude);
            float longtitude = (float) (Math.sin(i) * radius + centerLontitude);
            polylines.add(new LatLng(latitude, longtitude));
            if (i > Math.PI) {
                deltaAngle = Math.PI / 180 * 30;
            }
        }

        float latitude = (float) (-Math.cos(0) * radius + centerLatitude);
        float longtitude = (float) (Math.sin(0) * radius + centerLontitude);
        polylines.add(new LatLng(latitude, longtitude));

        polylineOptions = new PolylineOptions().points(polylines).width(10).color(Color.RED);

        mVirtureRoad = (Polyline) mBaiduMap.addOverlay(polylineOptions);
        OverlayOptions markerOptions;
        markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.huaji)).position(polylines.get(0)).rotate((float) getAngle(0));
        mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(false);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(true);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);


        new Thread() {
            public void run() {
                while (true) {
                    mLocationClient.requestLocation();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mBaiduMap == null) {
                return;
            }
            mBaiduMap.clear();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            myloc.setLatitude(location.getLatitude());
            myloc.setLongitude(location.getLongitude());
            LatLng llA=new LatLng(location.getLatitude(),location.getLongitude());
            Log.d(Double.toString(location.getLatitude()),Double.toString(location.getLatitude()));
            if (triggerTrace == true) {
                int size = polylines.size();
                if (size > 0 &&
                    polylines.get(size-1).latitude == llA.latitude &&
                    polylines.get(size-1).longitude == llA.longitude)
                    polylines.remove(size - 1);
                polylines.add(llA);
                do{ size = polylines.size();}
                while (size > 2 &&
                        clearNoise(polylines.get(size-3), polylines.get(size-2), polylines.get(size-1), size-2));
                    ;

            }
                if (polylines.size() >= 2) {
                    polylineOptions = new PolylineOptions().points(polylines).width(4).color(Color.DKGRAY);
                    mVirtureRoad = (Polyline) mBaiduMap.addOverlay(polylineOptions);
                }

            MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA).zIndex(9).draggable(true);
            mBaiduMap.addOverlay(ooA);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
            if (triggerTrace == true) {
                new Thread() {
                    public void run() {
                        if (mVirtureRoad.getPoints().size() < 2) return;
                        int firstindex = mVirtureRoad.getPoints().size() - 2;
                        final LatLng startPoint = mVirtureRoad.getPoints().get(firstindex);
                        final LatLng endPoint = mVirtureRoad.getPoints().get(firstindex + 1);
                        mMoveMarker.setPosition(startPoint);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // refresh marker's rotate
                                if (mMapView == null) {
                                    return;
                                }
                                mMoveMarker.setRotate((float) getAngle(startPoint, endPoint));
                            }
                        });
                        double slope = getSlope(startPoint, endPoint);
                        //是不是正向的标示（向上设为正向）
                        boolean isReverse = (startPoint.latitude > endPoint.latitude);

                        double intercept = getInterception(slope, startPoint);

                        double xMoveDistance = isReverse ? getXMoveDistance(slope) : -1 * getXMoveDistance(slope);

                        for (double j = startPoint.latitude; j > endPoint.latitude==isReverse; j = j - xMoveDistance) {
                            LatLng latLng;
                            if (slope != Double.MAX_VALUE) {
                                latLng = new LatLng(j, (j - intercept) / slope);
                            } else {
                                latLng = new LatLng(j, startPoint.longitude);
                            }

                            final LatLng finalLatLng = latLng;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (mMapView == null) {
                                        return;
                                    }
                                    // refresh marker's position
                                    mMoveMarker.setPosition(finalLatLng);
                                }
                            });
                            try {
                                Thread.sleep(TIME_INTERVAL);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }.start();
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            Log.d(TAG, "MyLocationListener.BDLocationListener.onConnectHotSpotMessage");
        }
    }

    private boolean clearNoise(LatLng p1, LatLng p2, LatLng p3, int midI) {
        boolean needNext = false;
        double a =   Math.sqrt(  (p3.latitude - p2.latitude) * (p3.latitude - p2.latitude)
                    +  (p3.longitude - p2.longitude) * (p3.longitude - p2.longitude) );
        double b =   Math.sqrt(  (p1.latitude - p3.latitude) * (p1.latitude - p3.latitude)
                +  (p1.longitude - p3.longitude) * (p1.longitude - p3.longitude) );
        double c =   Math.sqrt(  (p1.latitude - p2.latitude) * (p1.latitude - p2.latitude)
                +  (p1.longitude - p2.longitude) * (p1.longitude - p2.longitude) );
        double angle = Math.acos((a * a + c * c - b * b )/(2 * a * c));
        double decAngle = 180 * (angle / Math.PI);
        if ( decAngle > 30) return needNext;
        else    needNext = true;
        if (polylines.get(midI).latitude == p2.latitude )
            polylines.remove(midI);
        return needNext;
    }

    /**
     * 根据点获取图标转的角度
     */
    private double getAngle(int startIndex) {
        if ((startIndex + 1) >= mVirtureRoad.getPoints().size()) {
            throw new RuntimeException("index out of bonds");
        }
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
     * 根据点和斜率算取截距
     */
    private double getInterception(double slope, LatLng point) {

        double interception = point.latitude - slope * point.longitude;
        return interception;
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
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mBaiduMap.clear();
        bdA.recycle();
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



}
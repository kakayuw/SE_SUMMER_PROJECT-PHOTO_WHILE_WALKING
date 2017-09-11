package com.photowalking.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
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
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.linroid.filtermenu.library.FilterMenu;
import com.photowalking.FragmentsActity;
import com.photowalking.R;
import com.photowalking.adapter.MainAdapter;
import com.photowalking.fragment.MainFragment;
import com.photowalking.model.Photo;
import com.photowalking.model.RowModel;
import com.photowalking.model.Trace;
import com.photowalking.model.TraceInfo;
import com.photowalking.utils.FileUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/* Baidu Trace classes */

public class NewTraceActivity extends AppCompatActivity {
    private static final String TAG = "NewTraceActivity";
    private TraceInfo ti = new TraceInfo();
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private Polyline mVirtureRoad;
    private Marker mMoveMarker;
    private Handler mHandler;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private BitmapDescriptor bdA;
    private boolean isFirstLoc = true;
    private BDLocation myloc = new BDLocation();

    private OverlayOptions polylineOptions;
    private List<LatLng> polylines = new ArrayList<LatLng>();
    boolean triggerTrace = false;

    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 50;
    private static final double DISTANCE = 0.00005;

    @Bind(R.id.main_new_fab_menu)
    FloatingActionMenu fab;
    @Bind(R.id.btn_beginTrace)
    FloatingActionButton _beginTrace;
    @Bind(R.id.btn_endTrace)
    FloatingActionButton _endTrace;
    @Bind(R.id.btn_saveTrace)
    FloatingActionButton _saveTrace;
    @Bind(R.id.btn_StartCamera)
    FloatingActionButton _beginCamera;
    @Bind(R.id.btn_locate)
    FloatingActionButton _locate;

    private FileUtil fu = new FileUtil();
    private Trace trace = new Trace();

    private String uid;
    private String uname;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.main_add_new);
        ButterKnife.bind(this);
        fab.setClosedOnTouchOutside(true);

        uid = getIntent().getStringExtra("me");
        uname = getIntent().getStringExtra("uname");

        mMapView = (TextureMapView) findViewById(R.id.cmapView);
        mMapView.onCreate(this, savedInstanceState);

        checkPerssion();

        mHandler = new Handler(Looper.getMainLooper());
    }

    private void checkPerssion(){
        String[] pers = {ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION, ACCESS_LOCATION_EXTRA_COMMANDS};
        if(Build.VERSION.SDK_INT>=23){
            int FINE = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
            int COARSE = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
            int EXTRA = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_LOCATION_EXTRA_COMMANDS);
            if(FINE != PERMISSION_GRANTED || COARSE != PERMISSION_GRANTED || EXTRA != PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,pers,123);
                return;
            }
        }
        initGPS();
        initButton();
        initRoadData();
        initLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==123){
            if(grantResults[0]!=PERMISSION_GRANTED || grantResults[1]!=PERMISSION_GRANTED
                    || grantResults[2]!=PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"定位功能将受到限制",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void initGPS(){
        LocationManager locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)){
            new AlertDialog.Builder(this,R.style.AppTheme_Light_Dialog)
                    .setMessage("请打开GPS定位")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    private void  photoPro() {
        Intent photoIntent = new Intent(NewTraceActivity.this, SysCameraActivity.class);
        photoIntent.putExtra("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        photoIntent.putExtra("time", new SimpleDateFormat("HH:mm:ss").format(new Date()));
        photoIntent.putExtra("lat", myloc.getLatitude());
        photoIntent.putExtra("lon", myloc.getLongitude());
        photoIntent.putExtra("ti", new Gson().toJson(ti));
        startActivity(photoIntent);
    }

    private void beginTrace() {
        FloatingActionButton fb = (FloatingActionButton)findViewById(R.id.btn_beginTrace);
        fb.setImageResource(R.drawable.started);
        _beginTrace.setEnabled(false);
        _endTrace.setEnabled(true);
        _saveTrace.setEnabled(false);
        backToMyLocation();
        triggerTrace = true;
        /* Log trace infomation */
        ti = new TraceInfo();
        ti.setTraceDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        ti.setStartTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        ti.setUserid(uid);
        turnToLocation(new LatLng(myloc.getLatitude(), myloc.getLongitude()));
    }
    private void endTrace() {
        FloatingActionButton fb = (FloatingActionButton)findViewById(R.id.btn_beginTrace);
        fb.setImageResource(R.drawable.start);
        _beginTrace.setEnabled(true);
        _endTrace.setEnabled(false);
        _saveTrace.setEnabled(true);
        backToMyLocation();
        triggerTrace = false;
        ti.setEndTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }
    private void saveTrace() {
        _saveTrace.setEnabled(false);

        ti.setTraceId(ti.getTraceDate()+ti.getStartTime()+uid);
        ti.setFileName(ti.getStartTime());
        ti.setInfoFileName("info_" + ti.getFileName());
        int photonumb = new Select().from(Photo.class).where("traceid = ?", ti.getTraceId()).execute().size();
        LogTraceFile();

        trace.setUserid(Integer.parseInt(uid));
        trace.setTraceid(ti.getTraceDate()+ti.getStartTime()+uid);
        trace.setUsername(uname);
        trace.setStarttime(ti.getStartTime());
        trace.setEndtime(ti.getEndTime());
        trace.setPhotonumb(photonumb);
        trace.setTracename(ti.getTraceName());
        trace.setTracedate(ti.getTraceDate());
        trace.setPolylines(new Gson().toJson(polylines));
        trace.setMiles(parseMiles());
        trace.save();

        RowModel rm = new RowModel();
        rm.setMile(Double.toString(trace.getMiles()));
        rm.setBeginTime(trace.getStarttime());
        rm.setEndTime(trace.getEndtime());
        rm.setDate(trace.getTracedate());
        rm.setPhotoNumb(Integer.toString(trace.getPhotonumb()));
        rm.setTraceName(trace.getTracename());
        rm.setTraceId(trace.getTraceid());
        ((MainAdapter)MainFragment.mAdapter).addItem(rm);

        MainFragment.mAdapter.notifyDataSetChanged();

        LogTraceFile();
        finish();
    }
    private void LogTraceFile() {
        if (polylines.size() < 3) {
            return;
        }
        String fordername = ti.getTraceDate();
        String forderpath = fu.createFolder(fordername);
        Gson gson = new Gson();
        String polyStr = gson.toJson(polylines);
        fu.jsonToFile( forderpath  + ti.getFileName() , polyStr);
        String infoStr = gson.toJson(ti);
        fu.jsonToFile(forderpath  + ti.getInfoFileName(), infoStr);
    }

    private void backToMyLocation() {
        if(myloc == null || myloc.getLongitude() == 0)  return;
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(new LatLng(myloc.getLatitude(), myloc.getLongitude()))
                .zoom(56)
                .build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
    }

    private void turnToLocation(LatLng llA) {
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(llA)
                .zoom(18)
                .build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
    }

    private void initButton() {
        _endTrace.setEnabled(false);
        _saveTrace.setEnabled(false);
        _saveTrace.setEnabled(false);

        _beginTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginTrace();
            }
        });

        _endTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTrace();
            }
        });

        _saveTrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTitleDialog();
            }
        });

        _locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMyLocation();
            }
        });

        _beginCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPro();
            }
        });
    }

    private void initRoadData() {
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        bdA = BitmapDescriptorFactory.fromResource(R.drawable.huaji);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        //注册监听函数
        mLocationClient.start();

        // init latlng data
        double centerLatitude = 39.916049;
        double centerLontitude = 116.399792;
        double deltaAngle = Math.PI / 180 * 5;
        double radius = 0.02;
        float latitude = (float) (-Math.cos(0) * radius + centerLatitude);
        float longtitude = (float) (Math.sin(0) * radius + centerLontitude);
        LatLng llA = new LatLng(latitude, longtitude);
/*        OverlayOptions polylineOptions = new PolylineOptions().points(polylines).width(10).color(Color.BLUE);
        mVirtureRoad = (Polyline) mBaiduMap.addOverlay(polylineOptions);*/
        OverlayOptions markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory
                .fromResource(R.drawable.huaji)).position(llA);
        mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);

    }


    private void inputTitleDialog() {

        final EditText inputServer = new EditText(this);
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入路线名").setView(inputServer).setNegativeButton(
                "取消", null);
        builder.setPositiveButton("保存",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String inputName = inputServer.getText().toString();
                        ti.setTraceName(inputName);
                        saveTrace();
                    }
                });
        builder.show();
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
                /*if (size > 0 &&
                    polylines.get(size-1).latitude == llA.latitude &&
                    polylines.get(size-1).longitude == llA.longitude)
                    polylines.remove(size - 1);*/
                polylines.add(llA);
/*                do{ size = polylines.size();}
                while (size > 2 &&
                        clearNoise(polylines.get(size-3), polylines.get(size-2), polylines.get(size-1), size-2));*/

            }
                if (polylines.size() >= 2) {
                    polylineOptions = new PolylineOptions().points(polylines).width(4).color(Color.DKGRAY);
                    mVirtureRoad = (Polyline) mBaiduMap.addOverlay(polylineOptions);
                }

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
                        if (mVirtureRoad == null || mVirtureRoad.getPoints().size() < 2) return;
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


    private double parseMiles() {
        double miles = 0;
        int i, size = polylines.size();
        double radLat1,radLat2,radLon1,radLon2, x1,x2,y1,y2,z1,z2;
        int EARTH_RADIUS = 6371393;
        if (size < 2)   return 0;
        for (i = 0; i < size - 1; i++) {

            radLat1 = polylines.get(i).latitude * Math.PI / 180.0;
            radLat2 = polylines.get(i).longitude * Math.PI / 180.0;

            radLon1 = polylines.get(i+1).latitude * Math.PI / 180.0;
            radLon2 = polylines.get(i+1).longitude * Math.PI / 180.0;

            if (radLat1 < 0)
                radLat1 = Math.PI / 2 + Math.abs(radLat1);// south
            if (radLat1 > 0)
                radLat1 = Math.PI / 2 - Math.abs(radLat1);// north
            if (radLon1 < 0)
                radLon1 = Math.PI * 2 - Math.abs(radLon1);// west
            if (radLat2 < 0)
                radLat2 = Math.PI / 2 + Math.abs(radLat2);// south
            if (radLat2 > 0)
                radLat2 = Math.PI / 2 - Math.abs(radLat2);// north
            if (radLon2 < 0)
                radLon2 = Math.PI * 2 - Math.abs(radLon2);// west
            x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);
            y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);
            z1 = EARTH_RADIUS * Math.cos(radLat1);

            x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);
            y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);
            z2 = EARTH_RADIUS * Math.cos(radLat2);

            double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));
            //余弦定理求夹角
            double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));
            double dist = theta * EARTH_RADIUS;
            miles += dist;
        }

        return miles;
    }

}
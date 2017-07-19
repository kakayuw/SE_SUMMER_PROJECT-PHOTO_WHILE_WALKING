package com.photowalking.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.photowalking.R;
import com.photowalking.adapter.PhotoAdapter;
import com.photowalking.model.PhotoInfo;
import com.photowalking.model.TraceInfo;
import com.photowalking.utils.FileUtil;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.HorizontalListView;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewOthersDetailActivity extends AppCompatActivity {

    private static final String TAG = "viewOthersDetailActivity";
    private TraceInfo ti = new TraceInfo();
    private PhotoInfo pi = new PhotoInfo();

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private Polyline mVirtureRoad;
    private Marker mMoveMarker;
    public LocationClient mLocationClient = null;
    private BitmapDescriptor bdA;
    private BitmapDescriptor bdB;

    private OverlayOptions polylineOptions;
    private List<LatLng> polylines = new ArrayList<LatLng>();

    private FileUtil fu = new FileUtil();

    int depth = 0;
    String path = null;
    List<String> list_all = new ArrayList<String>();

    List<String> photos = new ArrayList<String>();
    List<String> infos = new ArrayList<String>();

    @Bind(R.id.btn_share) Button _btnShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.main_show_detail);
        ButterKnife.bind(this);
        Intent intent1 = this.getIntent();
/*        final String date = intent1.getStringExtra("date");
        final String time = intent1.getStringExtra("time");*/
        mMapView = (TextureMapView) findViewById(R.id.bmapView);
        mMapView.onCreate(this, savedInstanceState);
        mBaiduMap = mMapView.getMap();

        _btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                compressTrace(date , time);
                Intent intent = new Intent(ViewOthersDetailActivity.this, EditTextActivity.class);
                startActivity(intent);*/
            }
        });

        initPaths("11718");
        for( int i = 0; i < photos.size(); i++) {
            Log.e("photos:", photos.get(i));
        }
        for(int j = 0 ;j < infos.size(); j++) {
            Log.e("infos", infos.get(j));
        }
        initMarker();
        loadTrace();
        new LoadPictureTask().execute("?");


    }

    private void initPaths(String traceid) {
        String traceidPath = UrlPath.downloadPath + "/" + traceid;
        File fileList[] = new File(traceidPath).listFiles();
        for ( int i = 0; i < fileList.length; i++ ) {
            String filename = fileList[i].getName();
            if (filename.contains(".jpg")) {
                photos.add(traceidPath + "/" + filename);
            } else if (filename.contains("p_")) {
                infos.add(traceidPath + "/" + filename);
            } else if (filename.contains("info_")) {
                ti.setInfoFileName(traceidPath + "/" + filename);
            } else {
                ti.setFileName(traceidPath + "/" + filename);
            }
        }
    }

    private void initMarker() {
        bdA = BitmapDescriptorFactory.fromResource(R.drawable.huaji);
        bdB = BitmapDescriptorFactory.fromResource(R.drawable.arrow_down);
        OverlayOptions markerOptions;
        markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(bdA).
                position(new LatLng(118.87,42.28)).rotate(0).animateType(MarkerOptions.MarkerAnimateType.drop);
        mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);
    }

    private void loadTrace() {
        polylines = null;           //empty the polylines
        String polyStr = fu.AfileToJson(ti.getFileName());
        Gson gson = new Gson();
        List<LatLng> loadPoly = gson.fromJson(polyStr ,new TypeToken<List<LatLng>>() {}.getType());
        polylines = loadPoly;
        polylineOptions = new PolylineOptions().points(polylines).width(4).color(Color.GREEN);
        mVirtureRoad = (Polyline) mBaiduMap.addOverlay(polylineOptions);
        String infoStr = fu.AfileToJson(ti.getInfoFileName());
        ti = gson.fromJson(infoStr, TraceInfo.class);
        if (polylines != null) {
            turnToLocation(polylines.get(0));
        }
    }
    private void turnToLocation(LatLng llA) {
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(llA)
                .zoom(21)
                .build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
    }

    private class LoadPictureTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            List<String> photos = null;
            try {
                photos = getPicturesFromTI();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //  List<String> photos = getPictures(UrlPath.APP_PATH,".jpg");
            return photos;
        }

        @Override
        protected void onPostExecute(List<String> photos) {
            if (photos != null) {
                final PhotoAdapter adapter = new PhotoAdapter(ViewOthersDetailActivity.this);
                for (String s : photos) {
                    adapter.addItem(s);
                }
                HorizontalListView flist = (HorizontalListView) findViewById(R.id.main_detail_photo_list);
                flist.setAdapter(adapter);
                flist.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String photoFileName = adapter.getItem(position);
                        LatLng llA = getPositionFromPI(photoFileName);
                       /* mMoveMarker.setPosition(llA);*/
                        turnToLocation(llA);

                        mMoveMarker.remove();
                        OverlayOptions markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(bdA).
                                position(llA).rotate(0).animateType(MarkerOptions.MarkerAnimateType.grow);
                        mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);
                    }
                });
            }
        }
    }

    private LatLng getPositionFromPI(String photoFile) {
        String time = photoFile.substring(photoFile.lastIndexOf("/") + 1).substring(0,8);
        File fileList[] = new File(UrlPath.downloadPath + "/" + ti.getTraceId()).listFiles();
        Log.e("timeInPI",time);
        for ( int i = 0; i < fileList.length; i++ ) {
            String filename = fileList[i].getName();
            if ( filename.contains("p_") && filename.contains(time) ) {
                Gson gson = new Gson();
                String infoStr = fu.AfileToJson(UrlPath.downloadPath + "/" + ti.getTraceId() + "/" + filename);
                pi = gson.fromJson(infoStr, PhotoInfo.class);
                break;
            }
        }
        LatLng llA = new LatLng(pi.getLat(), pi.getLon());
        return llA;
    }

    private List<String> getPicturesFromTI() throws ParseException {
        return photos;
    }

    private List<String> getPicturesInfoFromTI() throws ParseException {
        return infos;
    }
}



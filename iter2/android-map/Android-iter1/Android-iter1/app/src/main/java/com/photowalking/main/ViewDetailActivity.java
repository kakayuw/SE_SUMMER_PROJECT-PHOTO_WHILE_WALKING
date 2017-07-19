package com.photowalking.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;

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
import com.photowalking.EditTextActivity;
import com.photowalking.R;
import com.photowalking.adapter.PhotoAdapter;
import com.photowalking.model.PhotoInfo;
import com.photowalking.model.ShareItem;
import com.photowalking.model.TraceInfo;
import com.photowalking.utils.FileUtil;
import com.photowalking.utils.UrlPath;
import com.photowalking.utils.ZipUtil;
import com.photowalking.viewUtils.HorizontalListView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewDetailActivity extends AppCompatActivity {

    private static final String TAG = "NewTraceActivity";
    private TraceInfo ti = new TraceInfo();
    private PhotoInfo pi = new PhotoInfo();
    private int photoNumb;

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

    @Bind(R.id.btn_share) Button _btnShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.main_show_detail);
        ButterKnife.bind(this);
        Intent intent1 = this.getIntent();
        final int userid = Integer.parseInt(intent1.getStringExtra("me"));
        final String username = intent1.getStringExtra("myname");
        final String date = intent1.getStringExtra("date");
        final String time = intent1.getStringExtra("time");
        mMapView = (TextureMapView) findViewById(R.id.bmapView);
        mMapView.onCreate(this, savedInstanceState);
        mBaiduMap = mMapView.getMap();

        _btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compressTrace(date , time);
                sendInfo(userid, username);
            }
        });

        initMarker();
        loadTrace(date, time);
        new LoadPictureTask().execute("?");


    }

    private void sendInfo(int userid, String username) {

        String pathname = UrlPath.uploadTmpPath + "/" + ti.getTraceId() + ".zip";
        ShareItem si = new ShareItem();
        si.setSid(ti.getTraceId());
        si.setPicnum(photoNumb);
        si.setStarttime(ti.getTraceDate() + " " + ti.getStartTime());
        si.setStarttime(ti.getTraceDate() + " " + ti.getEndTime());
        si.setTitle("ffff");
        si.setUid(userid);
        si.setUsername(username);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(si);
        Log.e("jsonStr", jsonStr);
        Log.e("pathname", pathname);
        Intent sendItem = new Intent(ViewDetailActivity.this, EditTextActivity.class);
        sendItem.putExtra("jsonStr",jsonStr);
        sendItem.putExtra("pathname", pathname);
        startActivity(sendItem);
    }

    private void initMarker() {
        bdA = BitmapDescriptorFactory.fromResource(R.drawable.huaji);
        bdB = BitmapDescriptorFactory.fromResource(R.drawable.arrow_down);
        OverlayOptions markerOptions;
        markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(bdA).
                position(new LatLng(118.87,42.28)).rotate(0).animateType(MarkerOptions.MarkerAnimateType.drop);
        mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);
    }

    private void compressTrace(String date, String time) {
        List<String> files = new ArrayList<String>();
        files.add(UrlPath.APP_PATH + date + "/" + time);
        files.add(UrlPath.APP_PATH + date + "/info_" + time);
        List<String> photos = null;
        List<String> infos = null;
        try {
            photos = getPicturesFromTI();
            infos = getPicturesInfoFromTI();
            for (int i = 0; i < photos.size(); i++) {
                files.add(UrlPath.APP_PATH + photos.get(i));
            }
            for (int i = 0; i < infos.size(); i++) {
                files.add(UrlPath.APP_PATH + infos.get(i));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for ( int i = 0 ; i < files.size(); i++) {
            Log.e("fff:",files.get(i));
        }
        ZipUtil.compress(ti.getTraceId(), files);
    };

    private void loadTrace(String date, String time) {
        polylines = null;           //empty the polylines
        String polyStr = fu.fileToJson(date + "/" + time);
        Gson gson = new Gson();
        List<LatLng> loadPoly = gson.fromJson(polyStr ,new TypeToken<List<LatLng>>() {}.getType());
        polylines = loadPoly;
        polylineOptions = new PolylineOptions().points(polylines).width(4).color(Color.DKGRAY);
        mVirtureRoad = (Polyline) mBaiduMap.addOverlay(polylineOptions);
        String infoStr = fu.fileToJson(date + "/info_" + time);
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
                photoNumb = photos.size();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //  List<String> photos = getPictures(UrlPath.APP_PATH,".jpg");
            return photos;
        }

        @Override
        protected void onPostExecute(List<String> photos) {
            if (photos != null) {
                final PhotoAdapter adapter = new PhotoAdapter(ViewDetailActivity.this);
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
        int len = UrlPath.APP_PATH.length();
        String date = photoFile.substring(0 + len,10 + len);
        String time = photoFile.substring(11 + len,19 + len);
        File fileList[] = new File(UrlPath.APP_PATH+date).listFiles();
        for ( int i = 0; i < fileList.length; i++ ) {
            String filename = fileList[i].getName();
            if ( filename.contains("p_") && filename.contains(time) ) {
                Gson gson = new Gson();
                String infoStr = fu.fileToJson(date + "/p_" + time);
                pi = gson.fromJson(infoStr, PhotoInfo.class);
                break;
            }
        }
        LatLng llA = new LatLng(pi.getLat(), pi.getLon());
        return llA;
    }

    private List<String> getPicturesFromTI() throws ParseException {
        List<String> pictureFiles = new ArrayList<String>();
        String path = UrlPath.APP_PATH + ti.getTraceDate();
        File dateFolder = new File(path);
        File fileList[] = dateFolder.listFiles();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        Date start = sdf.parse(ti.getTraceDate() + " " + ti.getStartTime());
        Date end = sdf.parse(ti.getTraceDate() + " " + ti.getEndTime());
        for (int i = 0; i < fileList.length; i++) {
            File filei = fileList[i];

            if (filei.getName().contains(".jpg"))  {
                String timei = filei.getName().substring(0,8);
                Date time = sdf.parse(  ti.getTraceDate() + " " + timei);
                if (time.after(start) && time.before(end)) {
                    pictureFiles.add(UrlPath.APP_PATH +ti.getTraceDate() + "/" + filei.getName());
                }

            }
        }
        return pictureFiles;
    }

    private List<String> getPicturesInfoFromTI() throws ParseException {
        List<String> pictureInfoFiles = new ArrayList<String>();
        String path = UrlPath.APP_PATH + ti.getTraceDate();
        File dateFolder = new File(path);
        File fileList[] = dateFolder.listFiles();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
        Date start = sdf.parse(ti.getTraceDate() + " " + ti.getStartTime());
        Date end = sdf.parse(ti.getTraceDate() + " " + ti.getEndTime());
        for (int i = 0; i < fileList.length; i++) {
            File filei = fileList[i];
            if (filei.getName().contains("p_")) {
                String timei = filei.getName().substring(2,10);
                Date time = sdf.parse(  ti.getTraceDate() + " " + timei);
                if (time.after(start) && time.before(end)) {
                    pictureInfoFiles.add(UrlPath.APP_PATH +ti.getTraceDate() + "/" + filei.getName());
                }
            }
        }
        return pictureInfoFiles;
    }
}



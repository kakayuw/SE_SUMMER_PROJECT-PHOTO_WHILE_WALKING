package com.photowalking.main;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.activeandroid.query.Select;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nantaphop.hovertouchview.HoverTouchHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.photowalking.R;

import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.photowalking.adapter.PhotoAdapter;
import com.photowalking.model.Photo;
import com.photowalking.model.PhotoInfo;
import com.photowalking.model.Trace;
import com.photowalking.model.TraceInfo;
import com.photowalking.utils.FileUtil;
import com.photowalking.utils.UrlPath;
import com.photowalking.utils.geoDecodedUtil;
import com.photowalking.viewUtils.StatusBarUtil;
import com.photowalking.widget.MyThumbnail;

public class ViewDetailActivity extends Activity {

    private static final String TAG = "ViewDetailActivity";
    private TraceInfo ti = new TraceInfo();
    private PhotoInfo pi = new PhotoInfo();
    private int photoNum;

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private Polyline mVirtureRoad;
    private Marker mMoveMarker;
    private HorizontalScrollView mSameLocPic;
    private HorizontalScrollView mPicListScroll;

    public LocationClient mLocationClient = null;
    private BitmapDescriptor bdA;
    private BitmapDescriptor bdB;

    private OverlayOptions polylineOptions;
    private List<LatLng> polylines = new ArrayList<LatLng>();

    private FileUtil fu = new FileUtil();
    private String uid;
    private String uname;
    private String traceid;
    private double miles;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.main_show_detail);
        final Intent intent1 = this.getIntent();
        final String date = intent1.getStringExtra("date");
        final String time = intent1.getStringExtra("time");
        miles = intent1.getDoubleExtra("miles",0);
        uid = intent1.getStringExtra("me");
        uname = intent1.getStringExtra("uname");
        traceid = intent1.getStringExtra("traceid");

        Trace trace = (Trace) new Select().from(Trace.class).where("traceid = ?", traceid).execute().get(0);
        ti.setTraceName(trace.getTracename());
        ti.setTraceDate(trace.getTracedate());
        ti.setStartTime(trace.getStarttime());
        ti.setEndTime(trace.getEndtime());
        ti.setTraceId(trace.getTraceid());


        mMapView = (TextureMapView) findViewById(R.id.bmapView);
        mMapView.onCreate(this, savedInstanceState);
        mBaiduMap = mMapView.getMap();

        FloatingActionButton btnShare = (FloatingActionButton)findViewById(R.id.main_detail_fab_share);
        FloatingActionButton btnWechat = (FloatingActionButton)findViewById(R.id.main_detail_fab_wechat);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String polyStr = gson.toJson(polylines);
                String traceInfo = gson.toJson(ti);
                Intent intent = new Intent(ViewDetailActivity.this, EditTextActivity.class);
                intent.putExtra("me",uid);
                intent.putExtra("uname",uname);
                intent.putExtra("ti",traceInfo);
                intent.putExtra("date",date);
                intent.putExtra("time",time);
                intent.putExtra("miles", miles);
                startActivity(intent);
            }
        });

        btnWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                ComponentName componentName = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareImgUI");
                shareIntent.setComponent(componentName);
//                shareIntent.setPackage("com.tencent.mm");
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "快来看我分享的路线与照片\n"+
                        UrlPath.wechatShareUrl+ti.getTraceId());
//                startActivity(Intent.createChooser(shareIntent, "分享到"));
                startActivity(shareIntent);
            }
        });

        initMarker();
        loadTrace(date,time);
        new LoadPictureTask().execute();
    }

    private void initMarker() {
        bdA = BitmapDescriptorFactory.fromResource(R.drawable.huaji);
        bdB = BitmapDescriptorFactory.fromResource(R.drawable.arrow_down);
        OverlayOptions markerOptions;
        markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(bdA).
                position(new LatLng(118.87,42.28)).rotate(0).animateType(MarkerOptions.MarkerAnimateType.drop);
        mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);
        mSameLocPic = (HorizontalScrollView) findViewById(R.id.main_detail_photo_samelocpic_scroll);
        mSameLocPic.setVisibility(View.GONE);
        mPicListScroll = (HorizontalScrollView) findViewById(R.id.main_detail_photo_list_scroll);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "长按图片显示详情，轻点地图退出", Toast.LENGTH_LONG).show();
                LatLng mPosi = marker.getPosition();
                String geoMarker = geoDecodedUtil.getDecodedGeoInfo(mPosi.latitude, mPosi.longitude );
                mSameLocPic.setVisibility(View.VISIBLE);
                LinearLayout llGroup = (LinearLayout) findViewById(R.id.main_detail_samelocpic);
                List<Photo> sameLocPics = new Select().from(Photo.class).where("geo = ?", geoMarker).execute();
                List<String> fileNameList = new ArrayList<String>();
                for ( Photo p : sameLocPics) {  fileNameList.add(p.getFilename());  }
                llGroup.removeAllViews();  //clear linearlayout
                TextView desc = new TextView(getApplicationContext());
                desc.setText("在不同的时间，您曾在这里拍摄过这些照片：");
                desc.setLayoutParams(new ActionBar.LayoutParams(300,200));
                llGroup.addView(desc);
                generatePicView(llGroup, fileNameList, mSameLocPic);
                return true;
            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }
            @Override
            public void onMapClick(LatLng arg0) {
                mSameLocPic.setVisibility(View.GONE);
            }
        });
    }

    private void generatePicView(LinearLayout llGroup, List<String> photos, final HorizontalScrollView hsview) {
        final FrameLayout root = (FrameLayout) findViewById(R.id.main_detail_photo_root);
        for (final String s : photos) {
            final MyThumbnail imageView = new MyThumbnail(getApplicationContext());
            imageView.setLayoutParams(new ActionBar.LayoutParams(170, 190));
            ImageLoader.getInstance().displayImage("file://"+s,  imageView);
            llGroup.addView(imageView); //动态添加图片

            LatLng llA = getPositionFromPI(s);
            String geoInfo = geoDecodedUtil.getDecodedGeoInfo( llA.latitude, llA.longitude);
            String printInfo = getTimeFromPI(s) + "  \n 拍摄于 " + geoInfo;
            imageView.setText(printInfo);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HoverTouchHelper.make(root, imageView);
                }
            });

            imageView.setMi(new MyThumbnail.MyInterface() {
                @Override
                public void targetMethod() {
                    /*mSameLocPic.setVisibility(View.GONE);*/
                    LatLng llA = getPositionFromPI(s);
                    MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdB).zIndex(9).draggable(false);
                    mBaiduMap.clear();
                    mBaiduMap.addOverlay(ooA);
                    mBaiduMap.addOverlay(polylineOptions);
                    turnToLocation(llA);
                    hsview.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                }
                @Override
                public void overMethod() {
                    hsview.setOnTouchListener(new View.OnTouchListener(){
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            return false;
                        }
                    });
                }
            });

        }
    }



    private void loadTrace(String date, String time) {
        polylines = null;           //empty the polylines
        //String polyStr = fu.fileToJson(date + "/" + time);
        Gson g = new Gson();
        List<Trace> allTrace =   new Select()
                .from(Trace.class)
                .orderBy("traceid ASC")
                .execute();
        Log.e("size of table:", Integer.toString(allTrace.size()));
        for(Trace test:allTrace) {
            Log.e("++++++++++++++++","------------------");
            Log.e("getTraceid:", test.getTraceid());
            Log.e("getUsername:", test.getUsername());
            Log.e("getMiles:", g.toJson(test.getMiles()));
            Log.e("getPhotonumb:", g.toJson(test.getPhotonumb()));
            Log.e("getStarttime:", g.toJson(test.getStarttime()));
            Log.e("getEndtime:", g.toJson(test.getEndtime()));
            Log.e("getUserid:", g.toJson(test.getUserid()));
            Log.e("getTracedate:", g.toJson(test.getTracedate()));
            Log.e("items:", g.toJson(test.items()));
            Log.e("getPolylines:", test.getPolylines());
        }
        Log.e("???????????????????   ", traceid);
        Trace t = new Select().from(Trace.class).where("traceid = ?", traceid).orderBy("traceid ASC").executeSingle();
        String polyStr = t.getPolylines();
        Gson gson = new Gson();
        List<LatLng> loadPoly = gson.fromJson(polyStr ,new TypeToken<List<LatLng>>() {}.getType());
        polylines = loadPoly;
        if (polylines.size() < 2) return ;
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
                .zoom(19)
                .build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
    }


    private class LoadPictureTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> photos = null;
            try {
                photos = getPicturesFromTI();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return photos;
        }

        @Override
        protected void onPostExecute(List<String> photos) {
            if (photos != null) {
                final PhotoAdapter adapter = new PhotoAdapter(ViewDetailActivity.this);
                for (String s : photos) {
                    adapter.addItem(s);
                }
                LinearLayout llGroup = (LinearLayout) findViewById(R.id.main_detail_photo_list);
                llGroup.removeAllViews();  //clear linearlayout
                generatePicView(llGroup, photos, mPicListScroll);
            }
        }
    }

    private String getTimeFromPI(String photoFile) {
        Photo tmpPhoto = (Photo) new Select().from(Photo.class).where("filename = ?", photoFile).execute().get(0);
        return tmpPhoto.getPhotoid();
    }


    private LatLng getPositionFromPI(String photoFile) {
        Log.e(photoFile, ((Photo) new Select().from(Photo.class).execute().get(0)).getFilename());
        Photo tmpPhoto = (Photo) new Select().from(Photo.class).where("filename = ?", photoFile).execute().get(0);
        LatLng llA = new LatLng(tmpPhoto.getLat(), tmpPhoto.getLon());
        return llA;
    }

    private List<String> getPicturesFromTI() throws ParseException {
        List<Photo> picModels = new Select().from(Photo.class).where("traceid = ?", ti.getTraceId()).execute();
        List<String> picFiles = new ArrayList<String>();
        for (Photo p : picModels) {
            picFiles.add(p.getFilename());
        }
        return picFiles;
    }

}



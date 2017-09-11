package com.photowalking.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.photowalking.adapter.SharePhotoAdapter;
import com.photowalking.model.PhotoInfo;
import com.photowalking.model.TraceInfo;
import com.photowalking.utils.FileUtil;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;
import com.photowalking.utils.ZipUtil;
import com.photowalking.viewUtils.HorizontalListView;
import com.photowalking.viewUtils.StatusBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViewOthersDetailActivity extends Activity {
    private WebView webView;
    private ProgressBar pb;

    private String sid;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.share_show_detail);

        sid = getIntent().getStringExtra("sid");

        pb = (ProgressBar) findViewById(R.id.share_detail_pb);
        webView = (WebView) findViewById(R.id.share_detail_webview);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    pb.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    pb.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pb.setProgress(newProgress);//设置进度值
                }

            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(UrlPath.wechatShareUrl+sid);

        LinearLayout ll_back = (LinearLayout)findViewById(R.id.share_detail_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}



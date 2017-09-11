package com.photowalking.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.photowalking.R;
import com.photowalking.viewUtils.StatusBarUtil;

/**
 * Created by lionel on 2017/8/24.
 */

public class BonusActivity extends Activity{
    private WebView webView;
    private ProgressBar pb;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.bonus_scene);
        pb = (ProgressBar) findViewById(R.id.bonus_pb);
        webView = (WebView) findViewById(R.id.bonus_website);
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
        webView.loadUrl("https://tusenpo.github.io/FlappyFrog/?from=singlemessage&isappinstalled=0");

        LinearLayout ll_back = (LinearLayout)findViewById(R.id.bonus_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

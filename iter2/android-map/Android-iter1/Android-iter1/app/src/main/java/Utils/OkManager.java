package Utils;
import android.util.Log;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by liujinxu on 17/6/30.
 */

public class OkManager {
    private OkHttpClient client;
    private Handler handler;
    static final String TAG = "okmanagerActicity:";

    //提交json数据
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    //提交字符串数据
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    public OkManager() {
        client = new OkHttpClient();
        handler = new Handler(Looper.getMainLooper());

    }

    /**
     * 向服务器提交String请求
     *
     * @param url
     * @param content
     */
    public String sendStringByPost(String url, String content) {
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSON, content))
                .build();
        Log.d(TAG, "after build");
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            //Log.d(TAG, response.body().string());
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "internal error";
        }
    }
}

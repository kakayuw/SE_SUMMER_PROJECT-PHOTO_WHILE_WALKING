package com.photowalking.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.photowalking.model.PhotoInfo;
import com.photowalking.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SysCameraActivity extends Activity {
	private Button btn_StartCamera, btn_StartCameraInGallery;
	private ImageView iv_CameraImg;

	private static final String TAG = "SysCameraActivity";
    FileUtil fu = new FileUtil();
	int depth = 0;
	String path = null;
	List<String> list_all = new ArrayList<String>();
    PhotoInfo pi = new PhotoInfo();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Intent intent1 = this.getIntent();
        String date = intent1.getStringExtra("date");
        String time = intent1.getStringExtra("time");
        double lat = intent1.getDoubleExtra("lat", 0.0);
        double lon = intent1.getDoubleExtra("lon", 0.0);
		Intent intent = new Intent();

		// 指定开启系统相机的Action
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		// create folder
        String fordername = date;
        String forderpath = fu.createFolder(fordername);
        pi.setLat(lat);
        pi.setLon(lon);
        pi.setInfoFileName("p_" + time);
        Gson gson = new Gson();
        String infoStr = gson.toJson(pi);
        fu.jsonToFile( forderpath  + pi.getInfoFileName() , infoStr);

		File file = new File(forderpath+"/"+time+".jpg");
		// 把文件地址转换成Uri格式
		Uri uri = Uri.fromFile(file);
		// 设置系统相机拍摄照片完成后图片文件的存放地址
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, 0);
		finish();
	}


	private List<String> getPictures(final String strPath, String format) {
		List<String> list_last = new ArrayList<String>();
		List<String> list = new ArrayList<String>();
		File file = new File(strPath);
		list = printDirectory(file, depth);
		list.size();
		/**
		 * 在循环判断之前，就必须完成树的遍历
		 */
		for (int k = 0; k < list.size(); k++) {

			int idx = list.get(k).lastIndexOf(".");
			Log.v("idx:", String.valueOf(idx));
			if (idx <= 0) {
				continue;
			}
			String suffix = list.get(k).substring(idx);
            /*
             * format可以是".jpg"、".jpeg"等等，例如suffix.toLowerCase().equals(".jpeg")
             */
			if (suffix.toLowerCase(Locale.PRC).equals(format)
					) {
				list_last.add(list.get(k));
			}
		}
		/**
		 * 如果没有这个，因为List<String> list_all = new
		 * ArrayList<String>();作为GetEachDir类构造函数的成员变量
		 * ，可以不被清楚，再再次使用GetEachDir的printDirectory,之前的list_all依然存在
		 */
		list_all.clear();
		return list_last;
	}

	private List<String> printDirectory(File f, int depth) {
		if (!f.isDirectory()) {
			// 如果不是目录，则打印输出
			Log.i("1", "I am not Dir");
		} else {
			File[] fs = f.listFiles();
			depth++;
			for (int i = 0; i < fs.length; ++i) {
				File file = fs[i];
				path = file.getPath();
				list_all.add(path);
				printDirectory(file, depth);
			}
		}
		return list_all;
	}

}
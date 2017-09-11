package com.photowalking.main;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.photowalking.R;
import com.photowalking.model.Photo;
import com.photowalking.model.PhotoInfo;
import com.photowalking.model.TraceInfo;
import com.photowalking.utils.FileUtil;
import com.photowalking.utils.geoDecodedUtil;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SysCameraActivity extends Activity {

	private static final String TAG = "SysCameraActivity";
    FileUtil fu = new FileUtil();
	int depth = 0;
	String path = null;
	List<String> list_all = new ArrayList<String>();
    PhotoInfo pi = new PhotoInfo();
	Uri uri;
	Photo photo;
	String destUri;

	@Bind(R.id.btn_crop) Button _crop;
	@Bind(R.id.btn_lv) Button _lv;
	@Bind(R.id.result_image) ImageView resultView;

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_syscamera);
		ButterKnife.bind(this);

        Intent intent1 = this.getIntent();
        String date = intent1.getStringExtra("date");
        String time = intent1.getStringExtra("time");
        double lat = intent1.getDoubleExtra("lat", 0.0);
        double lon = intent1.getDoubleExtra("lon", 0.0);
		String tiStr = intent1.getStringExtra("ti");
		TraceInfo ti = new Gson().fromJson(tiStr, TraceInfo.class);
		Intent intent = new Intent();

		_crop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				beginCrop(uri);
			}
		});
		_lv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

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
		// 设置系统相机拍摄照片完成后图片文件的存放地址
		photo = new Photo();
		photo.setTraceid(ti.getTraceDate()+ti.getStartTime()+ti.getUserid());
		photo.setLat(lat);
		photo.setLon(lon);
		photo.setPhotoid("P" + date + time);
		photo.setTime(date + " " + time);
		photo.setGeo(geoDecodedUtil.getDecodedGeoInfo( lat, lon));
		photo.setFilename(forderpath+time+".jpg");
		photo.save();

		destUri = forderpath + "/c"+time+".jpg";
		File file = new File(forderpath+"/"+time+".jpg");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(Build.VERSION.SDK_INT<24){
			uri = Uri.fromFile(file);
		}else{
			ContentValues contentValues = new ContentValues(1);
			contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
			uri = getApplicationContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(intent, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_CANCELED){
			FileUtil.deleteImage(SysCameraActivity.this,photo.getFilename());
			finish();
		}else if (resultCode == RESULT_OK){
			ImageLoader.getInstance().displayImage("file://"+photo.getFilename(),  resultView);
			if (requestCode == 0){
			} else if (requestCode == Crop.REQUEST_PICK){
				beginCrop(data.getData());
			}
		}else if (requestCode == Crop.REQUEST_CROP){
			handleCrop(resultCode, data);
		}
	}

	private void beginCrop(Uri source){
		Uri destination = Uri.fromFile(new File(destUri));
		// start() 方法根据其的需求选择不同的重载方法
		Crop.of(source, destination).asSquare().start(SysCameraActivity.this);
	}

	// 将裁剪回来的数据进行处理
	private void handleCrop(int resultCode, Intent result){
		if (resultCode == RESULT_OK){
			resultView.setImageURI(Crop.getOutput(result));
			Photo cropped_photo = new Photo();
			cropped_photo.setTraceid(photo.getTraceid());
			cropped_photo.setLat(photo.getLat());
			cropped_photo.setLon(photo.getLon());
			cropped_photo.setPhotoid("c" + photo.getPhotoid());
			cropped_photo.setTime(photo.getTime());
			cropped_photo.setGeo(photo.getGeo());
			cropped_photo.setFilename(Crop.getOutput(result).toString().replaceAll("%3A",":").substring(5));
			cropped_photo.save();
			new Delete().from(Photo.class).where("filename = ?", photo.getFilename()).execute();
			new File(photo.getFilename()).delete();
			finish();
		} else if (resultCode == Crop.RESULT_ERROR){
			Toast.makeText(this ,Crop.getError(result).getMessage(),	Toast.LENGTH_SHORT).show();
		}
	}

}
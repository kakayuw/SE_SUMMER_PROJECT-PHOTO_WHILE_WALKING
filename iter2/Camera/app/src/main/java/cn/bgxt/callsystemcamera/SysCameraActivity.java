package cn.bgxt.callsystemcamera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.lang.String;

public class SysCameraActivity extends Activity {
	private Button btn_StartCamera, btn_StartCameraInGallery;
	private ImageView iv_CameraImg;

	private static final String TAG = "main";
	String path = Environment.getExternalStorageDirectory().toString() + "/"
			+ "abzbp";

	private static final String FILE_PATH = Environment.getExternalStorageDirectory().toString() +"/abzbp/syscamera.jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_syscamera);

		btn_StartCamera = (Button) findViewById(R.id.btn_StartCamera);
		btn_StartCameraInGallery = (Button) findViewById(R.id.btn_StartCameraInGallery);
		iv_CameraImg = (ImageView) findViewById(R.id.iv_CameraImg);

		btn_StartCamera.setOnClickListener(click);
		btn_StartCameraInGallery.setOnClickListener(click);
	}

	private View.OnClickListener click = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			Intent intent = null;
			switch (v.getId()) {
				// 指定相机拍摄照片保存地址
				case R.id.btn_StartCamera:
					intent = new Intent();
					// 指定开启系统相机的Action
					intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					// 根据文件地址创建文件
					File file = new File(FILE_PATH);
					File dirFirstFolder = new File(path);
					if(!dirFirstFolder.exists()) {
						dirFirstFolder.mkdirs();//创建文件夹
					}
					if (file.exists()) {
						file.delete();
					}
					// 把文件地址转换成Uri格式
					Uri uri = Uri.fromFile(file);
					// 设置系统相机拍摄照片完成后图片文件的存放地址
					intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 0);
					break;
				// 不指定相机拍摄照片保存地址
				case R.id.btn_StartCameraInGallery:
					intent = new Intent();
					// 指定开启系统相机的Action
					intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					startActivityForResult(intent, 1);
					break;
				default:
					break;
			}

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "系统相机拍照完成，resultCode="+resultCode);

		if (requestCode == 0) {
		//	File file = new File(FILE_PATH);
		//	Uri uri = Uri.fromFile(file);
		//	iv_CameraImg.setImageURI(uri);
			File file = new File(FILE_PATH);
			Bitmap bm = BitmapFactory.decodeFile(FILE_PATH);
			//将图片显示到ImageView中
			iv_CameraImg.setImageBitmap(bm);

//			File localFile;
//			FileInputStream localStream = null;
//			Bitmap bitmap = null;
//			localFile = new File(FILE_PATH);
//			if (!localFile.exists()) {
//				Log.d(TAG, FILE_PATH + " is null.");
//			}
//			else {
//				try {
//					localStream = new FileInputStream(localFile);
//					bitmap = BitmapFactory.decodeStream(localStream);
//					iv_CameraImg.setImageBitmap(bitmap);
//					if (localStream != null) {
//						localStream.close();
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			}
			else if (requestCode == 1) {
				if (data.getData() != null){
					Log.i(TAG, "默认content地址："+data.getData());
					iv_CameraImg.setImageURI(data.getData());
				}

		}
	}


}
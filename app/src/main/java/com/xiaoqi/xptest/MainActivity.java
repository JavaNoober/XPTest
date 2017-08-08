package com.xiaoqi.xptest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


	private JigsawView mJv;

	private Button mBtn;

	private int curmode = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initView() {
		mJv = (JigsawView) findViewById(R.id.jv);
		RotateImageViewAdapter adapter1 = new RotateImageViewAdapter();
		RotateImageViewAdapter2 adapter2 = new RotateImageViewAdapter2();
		mJv.addAdapter(adapter1);
		mJv.addAdapter(adapter2);
		mJv.setMould(0);
		mJv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(View view) {
				Toast.makeText(MainActivity.this, "点击事件", Toast.LENGTH_SHORT).show();
			}
		});
		mBtn = (Button) findViewById(R.id.btn);
		mBtn.setOnClickListener(this);
		Button mBtn2 = (Button) findViewById(R.id.btn_save);
		mBtn2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn:
				if(curmode == 0){
					curmode = 1;
					mJv.setMould(1);
				}else {
					curmode = 0;
					mJv.setMould(0);
				}
				break;
			case R.id.btn_save:
					Bitmap bitmap = getViewBitmap(mJv, mJv.getWidth(), mJv.getHeight());
					if(bitmap != null){
						saveImageToGallery(bitmap);
						Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

					}
				break;
		}
	}

	public Bitmap getViewBitmap(View comBitmap, int width, int height) {
		Bitmap bitmap = null;
		if (comBitmap != null) {
			comBitmap.clearFocus();
			comBitmap.setPressed(false);

			boolean willNotCache = comBitmap.willNotCacheDrawing();
			comBitmap.setWillNotCacheDrawing(false);
			int color = comBitmap.getDrawingCacheBackgroundColor();
			comBitmap.setDrawingCacheBackgroundColor(0);
			float alpha = comBitmap.getAlpha();
			comBitmap.setAlpha(1.0f);

			if (color != 0) {
				comBitmap.destroyDrawingCache();
			}

			int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
			int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
			comBitmap.measure(widthSpec, heightSpec);
			comBitmap.layout(0, 0, width, height);

			comBitmap.buildDrawingCache();
			Bitmap cacheBitmap = comBitmap.getDrawingCache();
			if (cacheBitmap == null) {
				Log.e("view.ProcessImageToBlur", "failed getViewBitmap(" + comBitmap + ")",
						new RuntimeException());
				return null;
			}
			bitmap = Bitmap.createBitmap(cacheBitmap);
			// Restore the view
			comBitmap.setAlpha(alpha);
			comBitmap.destroyDrawingCache();
			comBitmap.setWillNotCacheDrawing(willNotCache);
			comBitmap.setDrawingCacheBackgroundColor(color);
		}
		return bitmap;
	}

	public void saveImageToGallery(Bitmap bmp) {
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(), "image");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
	}
}

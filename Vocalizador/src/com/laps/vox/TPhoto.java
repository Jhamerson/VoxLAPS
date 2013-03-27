package com.laps.vox;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import com.laps.Constants;

import android.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

/*A kind of magic happens here*/

public class TPhoto extends Activity implements SurfaceHolder.Callback {
	private LayoutInflater mInflater = null;
	Camera mCamera;
	byte[] tempdata;
	boolean mPreviewRunning = false;
	private SurfaceHolder mSurfaceHolder;
	private SurfaceView mSurfaceView;
	Button takepicture;
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(com.laps.vox.R.layout.tphoto);
        
        mSurfaceView = (SurfaceView)findViewById(com.laps.vox.R.id.image);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        mInflater = LayoutInflater.from(this);
        View overView = mInflater.inflate(com.laps.vox.R.layout.tphotoverlay, null);
        this.addContentView(overView,
	        new LayoutParams(LayoutParams.FILL_PARENT,
	        		LayoutParams.FILL_PARENT));
        takepicture = (Button) findViewById(com.laps.vox.R.id.take);
        takepicture.setOnClickListener(new OnClickListener(){
	        public void onClick(View view){
		        mCamera.takePicture(mShutterCallback,
		        		mPictureCallback, mjpeg);
	        }
        });
    }
    ShutterCallback mShutterCallback = new ShutterCallback(){
    	@Override
    	public void onShutter() {}
    };
    PictureCallback mPictureCallback = new PictureCallback() {
    	public void onPictureTaken(byte[] data, Camera c) {}
    };
    PictureCallback mjpeg = new PictureCallback() {
    	public void onPictureTaken(byte[] data, Camera c) {
    		if(data !=null) {
    			tempdata=data;
    			done();
    		}
    	}
	};
	void done() {
		Bitmap bm = BitmapFactory.decodeByteArray(tempdata,
				0, tempdata.length);
		
		/*Gambiarra!*/
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/saved_images"); 
		myDir.mkdirs();
		Random gen = new Random();
		int n = 1000;
		n = gen.nextInt(n);
		String filename = "image-"+n+".jpg";
		File file = new File (myDir, filename);
		if (file.exists ()) file.delete (); 
		try{
			FileOutputStream out = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		Constants.URI = file.getPath();
		/*Fim da gambiarra*/
		
		String url = Images.Media.insertImage(getContentResolver(),
				bm, null, null);
		//Constants.URI = url;
		//bm.recycle();
		Bundle bundle = new Bundle();
		if(url!=null) {
			bundle.putString("url", url);
			Intent mIntent = new Intent();
			mIntent.putExtras(bundle);
			setResult(RESULT_OK, mIntent);
			Toast.makeText(this, url.split("/")[url.split("/").length - 1],
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Picture can not be saved",
					Toast.LENGTH_SHORT).show();
		}
		finish();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.laps.vox.R.menu.tphoto, menu);
        return true;
    }

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int w,
			int h) {
		try {
			if (mPreviewRunning) {
				mCamera.stopPreview();
				mPreviewRunning = false;
			}
			Camera.Parameters p = mCamera.getParameters();
			p.setPreviewSize(w, h);
			mCamera.setParameters(p);
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			mPreviewRunning = true;
		} catch(Exception e) {
			Log.d("",e.toString());
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mCamera = Camera.open();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mCamera.stopPreview();
		mPreviewRunning = false;
		mCamera.release();
		mCamera=null;	
	}
}

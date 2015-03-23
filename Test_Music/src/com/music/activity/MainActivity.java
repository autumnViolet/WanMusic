package com.music.activity;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.example.test_music.R;
import com.music.dao.MusicDao;
import com.music.tools.MusicTools;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView content;
	private ImageView image;
	private MusicDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        content = (TextView)findViewById(R.id.content);
        image = (ImageView)findViewById(R.id.image);
        
        dao = new MusicDao(this);
        
        Intent intent = getIntent();  
        String action = intent.getAction();  
        String type = intent.getType();  
  
        if (Intent.ACTION_SEND.equals(action) && type != null) {  
            if (type.equals("audio")) {  
//                handleSendAudio(intent); // Handle text being sent  
            }  if (type.startsWith("text/")) {  
            	handleSendText(intent); // Handle single image being sent  
            }  if (type.startsWith("image/")) {  
                handleSendImage(intent); // Handle single image being sent  
            }  if (type.startsWith("multipart/")) {  
                handleMultipart(intent); // Handle single image being sent  
            }  
        }
        
        content.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent incontent = new Intent();
				incontent.setClass(MainActivity.this, MusicList.class);
				startActivity(incontent);
			}
		});
    }
    
    void handleSendText(Intent intent) {  
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);  
        String sharedTitle = intent.getStringExtra(Intent.EXTRA_TITLE);  
        if (sharedText != null) {  
        	Toast.makeText(getApplication(), "sharedText="+sharedText+" sharedTitle="+sharedTitle, Toast.LENGTH_LONG).show();
        }  
    }  
    
    /**
     * 分享图片消息
     * @param intent
     * @param incontent
     */
    void handleSendImage(Intent intent) {  
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);  
        if (imageUri != null) {  
        	Bitmap mBitmap = null;
        	ContentResolver contentProvider = getApplicationContext().getContentResolver();
        	try {
                Bitmap bmp = BitmapFactory.decodeStream(contentProvider.openInputStream(imageUri));
                mBitmap = Bitmap.createScaledBitmap(bmp, 300, 400, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }  
//        	dao.addContent(sharedText,MusicTools.getRealPathFromURI(imageUri, getApplication())); //添加数据到数据库中
        	dao.addContent(sharedText,MusicTools.img(mBitmap));
        	content.setText(sharedText);
        	image.setImageBitmap(mBitmap);
//        	Toast.makeText(getApplication(), "ok="+mBitmap, Toast.LENGTH_LONG).show();
        }  
    } 
    
    void handleMultipart(Intent intent) {  
    	 ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);  
         if (imageUris != null) {  
//        	 int i;
        	 Toast.makeText(getApplication(), "imageUris.size="+imageUris.size(), Toast.LENGTH_LONG).show();
//        	 for(i=0;i<imageUris.size();i++){
//        		 
//        	 }
         }   
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

package com.music.activity;

import com.example.test_music.R;
import com.google.zxing.WriterException;
import com.music.tools.MusicTools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
/**
 * ÏÔÊ¾¶þÎ¬Âë
 * @author Administrator
 *
 */
public class CodeActivity extends Activity{
	private ImageView code;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code);
        
        code = (ImageView)findViewById(R.id.code);
        
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        try {
			code.setImageBitmap(MusicTools.create2DCode(content));
		} catch (WriterException e) {
			e.printStackTrace();
		}
        
	}
	
}

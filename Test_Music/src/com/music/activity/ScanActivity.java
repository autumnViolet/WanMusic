package com.music.activity;


import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.test_music.R;
import com.music.bean.MusicBean;
import com.music.dao.MusicDao;
import com.music.tools.MusicTools;

public class ScanActivity extends Activity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	/**
	 * 显示扫描结果
	 */
	private TextView mTextView ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_scan);
		
		mTextView = (TextView) findViewById(R.id.result); 
		
		//点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
		//扫描完了之后调到该界面
		Button mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ScanActivity.this, MipcaCaptureActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
		
		Button mButton2 = (Button) findViewById(R.id.button2);
		mButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ScanActivity.this, MusicList.class);
				startActivity(intent);
			}
		});
	}
	
	

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				final String content = bundle.getString("result");
				
				if("accept".equals(content)){
					new RetrieveFeedTask().execute(content);
				}
				
				//显示扫描到的内容
				mTextView.setText(content);
				//显示
			}
			break;
		}
    }	
	
//	/**
//	 * 向服务器发送消息
//	 */
//	public void sendMessage(String content){
//		try {  
//            OutputStream is=new Socket("10.0.0.27",6665).getOutputStream();  
////            byte[] by=new byte[1024];
//            String by = "aaaaaaaaa";
//            is.write(by.getBytes());  
//            System.out.println(by);  
//            is.close();  
//        } catch (UnknownHostException e) {  
//           e.printStackTrace();  
//        } catch (IOException e) {  
//           e.printStackTrace();  
//        }  
//	}
	
	
	/**
	 * 从队列中获得内容数据并存入数组
	 */
	public String[] getContent(ArrayList<MusicBean> list){
		String[] data = new String[list.size()];
		for(int i=0;i<list.size();i++){
			data[i] = list.get(i).getContent();
		}
		return data;
	}
	
	class RetrieveFeedTask extends AsyncTask<String, Void, String> {

	    private Exception exception;

	    protected String doInBackground(String... urls) {
	        try {
	        	InetAddress serverAddr = InetAddress.getByName("192.168.23.1");//TCPServer.SERVERIP 
	        	Log.d("TCP", "C: Connecting..."); 
	        	Socket socket = new Socket(serverAddr, 6665); 
	        	Log.d("TCP", "C: Connecting2222..."+socket); 
//	        	OutputStream is=socket.getOutputStream(); 
	        	
	        	DataOutputStream dataOS = new DataOutputStream(socket.getOutputStream());
				OutputStreamWriter outSW = new OutputStreamWriter(dataOS, "UTF-8");
				BufferedWriter is = new BufferedWriter(outSW);
	        	
	        	MusicDao dao = new MusicDao(getApplication());
	            String by = "accept@@@"+MusicTools.asString(getContent(dao.getData()));
	            is.write(by);  
	            is.flush();
	            System.out.println(by);  
	            is.close();  
	            return null;
	        } catch (Exception e) {
	            this.exception = e;
	            return null;
	        }
	    }

	    protected void onPostExecute(String feed) {
	    }
	}
	

}

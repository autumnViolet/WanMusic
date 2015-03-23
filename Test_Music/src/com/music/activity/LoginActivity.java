package com.music.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test_music.R;
import com.music.dao.UserDao;

public class LoginActivity extends Activity{
	private EditText username;
	private EditText password;
	private Button login;
	private Button register;
	private UserDao dao;
	
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login);
	        
	        init();
	        
	        //设置监听器
	        LoginListener lis = new LoginListener();
	        login.setOnClickListener(lis);
	        register.setOnClickListener(lis);
	 }
	 
	 /**
	  * 初始化
	  */
	 public void init(){
		 dao = new UserDao(this);
		 login = (Button)findViewById(R.id.login);
		 register = (Button)findViewById(R.id.register);
		 username = (EditText)findViewById(R.id.username);
		 password = (EditText)findViewById(R.id.pwd);
	 }
	 
	 /**
		 * 未输入提醒的方法
		 */
		public boolean loginNotify(String name,String pwd){
			boolean check = true;
			if("".equals(name)){
				check = false;
				Toast.makeText(getApplication(), "用户名不能为空！", Toast.LENGTH_LONG).show();
			}else if("".equals(pwd)){
				check = false;
				Toast.makeText(getApplication(), "密码不能为空！", Toast.LENGTH_LONG).show();
			}
			return check;
		}
	 
	 /**
	  * 监听器类
	  * @author Administrator
	  *
	  */
	 private class LoginListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.login:
//				String name = username.getText().toString().trim();
//				String pwd = password.getText().toString().trim();
//				if(loginNotify(name,pwd)){   //判断输入是否为空
//					if (dao.isLogin(name, pwd)) {    //判断登陆是否成功
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, ScanActivity.class);
						startActivity(intent);
						LoginActivity.this.finish();
//					} else {
//						Toast.makeText(getApplication(), "用户名或密码错误，请重新输入！",
//								Toast.LENGTH_LONG).show();
//						username.setText("");
//						password.setText("");
//					}
//				}
				break;
			case R.id.register:
				Intent intent1 = new Intent();
				intent1.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent1);
				LoginActivity.this.finish();
				break;
			}
		}
		 
	 }
	 
}

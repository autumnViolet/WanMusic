package com.music.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.test_music.R;
import com.music.dao.UserDao;
/**
 * 用户注册
 * @author Administrator
 *
 */
public class RegisterActivity extends Activity{
	private Button register;
	private EditText username;
	private EditText password;
	private EditText age;
	private RadioButton boy;
	private RadioButton girl;
	private UserDao user;

	@Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		init();
		
		//设置监听器
		RegisterListener lis = new RegisterListener();
		register.setOnClickListener(lis);
	}
	
	/**
	 * 初始化控件
	 */
	public void init(){
		username = (EditText)findViewById(R.id.username);
		password = (EditText)findViewById(R.id.pwd);
		age = (EditText)findViewById(R.id.age);
		boy = (RadioButton)findViewById(R.id.radioMale);
		girl = (RadioButton)findViewById(R.id.radioFemale);
		register = (Button)findViewById(R.id.regi);
		
		user = new UserDao(getApplication());
	}
	
	/**
	 * 注册的方法
	 * @author Administrator
	 *
	 */
	public void regist(){
		String name = username.getText().toString().trim();
		String pwd = password.getText().toString().trim();
		String sex = "";
		if(boy.isChecked()){
			sex = "男";
		}else if(girl.isChecked()){
			sex = "女";
		}
		if(notify(name,pwd,age.getText().toString())){
			if(!user.isExist(name, pwd)){
				int uage = Integer.parseInt(age.getText().toString().trim());
				user.addUser(name, pwd, uage, sex);
				Toast.makeText(getApplication(), "注册成功！", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this, LoginActivity.class);
			    startActivity(intent);
			    RegisterActivity.this.finish();
			}
		}
	}
	
	/**
	 * 未输入提醒的方法
	 */
	public boolean notify(String name,String pwd,String age){
		boolean check = true;
		if("".equals(name)){
			check = false;
			Toast.makeText(getApplication(), "用户名不能为空！", Toast.LENGTH_LONG).show();
		}else if("".equals(pwd)){
			check = false;
			Toast.makeText(getApplication(), "密码不能为空！", Toast.LENGTH_LONG).show();
		}else if("".equals(age)){
			check = false;
			Toast.makeText(getApplication(), "年龄不能为空！", Toast.LENGTH_LONG).show();
		}
		return check;
	}
	
	/**
	 * 监听器类
	 * @author Administrator
	 *
	 */
	private class RegisterListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.regi:
				regist();
				break;
			case R.id.reset:
				Toast.makeText(getApplication(), "重置了！", Toast.LENGTH_LONG).show();
				break;
			}
		}
		
	}

}

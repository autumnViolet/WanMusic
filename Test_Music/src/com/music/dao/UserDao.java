package com.music.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.music.bean.UserBean;
import com.music.database.DbOpenHelper;

/**
 * 注册用户信息数据库工具类
 * @author Administrator
 *
 */
public class UserDao {
	public static final String TABLE_NAME = "user_table";
	public static final String COLUMN_NAME_ID = "id";
	public static final String COLUMN_NAME_USERNAME = "username";
	public static final String COLUMN_NAME_PASSWORD = "password";
	public static final String COLUMN_NAME_AGE = "age";
	public static final String COLUMN_NAME_SEX = "sex";
	
	private DbOpenHelper dbHelper;
	
	public UserDao(Context context){
		dbHelper = DbOpenHelper.getInstance(context);
	}
	
	/**
	 * 添加用户
	 * @param name
	 * @param pwd
	 * @param age
	 * @param sex
	 */
	public void addUser(String name,String pwd,int age,String sex){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			ContentValues values = new ContentValues();
			values.put(COLUMN_NAME_USERNAME, name);
			values.put(COLUMN_NAME_PASSWORD, pwd);
			values.put(COLUMN_NAME_AGE, age);
			values.put(COLUMN_NAME_SEX, sex);
			db.insert(TABLE_NAME, null, values);
		}
	}
	
	/**
	 * 查询数据
	 */
	public ArrayList<UserBean> getUser(){
		ArrayList<UserBean> contents = new ArrayList<UserBean>();
		UserBean user = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select username,password from " + TABLE_NAME,null);
			while (cursor.moveToNext()) {
				user = new UserBean();
				user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USERNAME)));
				user.setPwd(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_PASSWORD))) ;
				contents.add(user);
			}
			cursor.close();
		}
		return contents;
	}
	
	/**
	 * 判断数据库中是否存在该用户
	 */
	public boolean isExist(String name,String value){
		boolean exist = false;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select username from " + TABLE_NAME + " where " + name + 
					" =? ",new String[]{value});
			System.out.println("cursor=="+cursor.getCount());
			if(cursor.getCount() != 0){
				exist = true;
			}
		}
		return exist;
	}
	
	/**
	 * 判断登陆用户是否正确
	 */
	public boolean isLogin(String name,String pwd){
		boolean login = false;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select sex from " + TABLE_NAME + " where username =? " + 
		                           "and password =? ",new String[]{name,pwd});
			System.out.println("cursor=="+cursor.getCount());
			if(cursor.getCount() != 0){
				login = true;
			}
		}
		return login;
	}

}

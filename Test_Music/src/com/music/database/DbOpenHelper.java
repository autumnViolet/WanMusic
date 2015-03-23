package com.music.database;

import com.music.dao.MusicDao;
import com.music.dao.UserDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 数据库操作类
 * @author Administrator
 *
 */
public class DbOpenHelper extends SQLiteOpenHelper{
	
	private static final String name = "music_db";
	private static final int version = 10;
	private static DbOpenHelper instance;
	
	private static final String MUSIC_TABLE_CREATE = "CREATE TABLE "
			+ MusicDao.TABLE_NAME + " ( "
			+ MusicDao.COLUMN_NAME_ID +" INTEGER primary key AUTOINCREMENT, "
			+ MusicDao.COLUMN_NAME_CONTENT +" TEXT, "
			+ MusicDao.COLUMN_NAME_IMAGE +" BLOB);";
	
	private static final String USER_TABLE_CREATE = "CREATE TABLE "
			+ UserDao.TABLE_NAME + " ( "
			+ UserDao.COLUMN_NAME_ID +" INTEGER primary key AUTOINCREMENT, "
			+ UserDao.COLUMN_NAME_USERNAME +" TEXT, "
			+ UserDao.COLUMN_NAME_PASSWORD +" TEXT, "
			+ UserDao.COLUMN_NAME_AGE +" INTEGER, "
			+ UserDao.COLUMN_NAME_SEX +" TEXT);";


	public DbOpenHelper(Context context) {
		super(context, name, null, version);
	}
	
	/**
	 * 获取对象实例
	 * @param context
	 * @return
	 */
	public static DbOpenHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DbOpenHelper(context.getApplicationContext());
		}
		return instance;
	}

    /**
     * 创建时调用建表语句
     */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(MUSIC_TABLE_CREATE);
		db.execSQL(USER_TABLE_CREATE);
	}

	/**
	 * 更新操作
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(!tableIsExist(db, MusicDao.TABLE_NAME)){
			db.execSQL(MUSIC_TABLE_CREATE);
		} else if(!tableIsExist(db, UserDao.TABLE_NAME)){
			db.execSQL(USER_TABLE_CREATE);
		} else{
			db.execSQL("drop table " + MusicDao.TABLE_NAME);
			db.execSQL("drop table " + UserDao.TABLE_NAME);
		}
	}
	
	/**
	 * 判断数据库表是否存在
	 * @param db
	 * @param tabName
	 * @return
	 */
	public boolean tableIsExist(SQLiteDatabase db, String tabName) {
		boolean result = false;
		if (tabName == null) {
			return false;
		}

		if (instance != null) {
			try {
				Cursor cursor = null;
				String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
						+ tabName.trim() + "' ";
				cursor = db.rawQuery(sql, null);
				if (cursor.moveToNext()) {
					int count = cursor.getInt(0);
					if (count > 0) {
						result = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}


}

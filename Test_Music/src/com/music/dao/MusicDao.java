package com.music.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.music.bean.MusicBean;
import com.music.database.DbOpenHelper;

/**
 * �����б����ݿ⹤����
 * @author Administrator
 *
 */
public class MusicDao {
	public static final String TABLE_NAME = "music_table";
	public static final String COLUMN_NAME_ID = "id";
	public static final String COLUMN_NAME_CONTENT = "content";
	public static final String COLUMN_NAME_IMAGE = "image";
	
	private DbOpenHelper dbHelper;
	
	public MusicDao(Context context){
		dbHelper = DbOpenHelper.getInstance(context);
	}
	
	/**
	 * �������
	 */
	public void addContent(String content,byte[] image) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values = new ContentValues();
			values.put(COLUMN_NAME_CONTENT, content);
			values.put(COLUMN_NAME_IMAGE, image);
			db.insert(TABLE_NAME, null, values);
		}
    }
	
	/**
	 * ��ѯ����
	 */
	public ArrayList<MusicBean> getData(){
		ArrayList<MusicBean> contents = new ArrayList<MusicBean>();
		MusicBean music = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select content,image from " + TABLE_NAME,null);
			while (cursor.moveToNext()) {
				music = new MusicBean();
				music.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CONTENT)));
				music.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_IMAGE))) ;
				contents.add(music);
			}
			cursor.close();
		}
		return contents;
	}
	
	/**
	 * ɾ������
	 */
	public void deleteItem(String content) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			db.delete(TABLE_NAME, COLUMN_NAME_CONTENT + "=?", new String[] { content });
		}
	}

	/**
	 * ������ݿ��
	 */
	public void clear() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(TABLE_NAME, null, null);
		}
	}

}

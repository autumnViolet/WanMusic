package com.music.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.test_music.R;
import com.music.adapter.MusicListAdapter;
import com.music.bean.MusicBean;
import com.music.dao.MusicDao;
import com.music.tools.MusicTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 显示音乐分享列表Activity
 * @author Administrator
 *
 */
public class MusicList extends Activity {
	private ListView lv;
	private Button share;
	private MusicListAdapter madapter;
	private MusicDao dao;
	private String[] content;
	private Bitmap[] image;
	private List<Map<String, Object>> item_lists ;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_main);
		
		lv = (ListView)findViewById(R.id.list);
		share = (Button)findViewById(R.id.share);
		
		// 从数据库中获取分享数据列表
		dao = new MusicDao(this);
		ArrayList<MusicBean> list = dao.getData();

		content = getContent(list);
		image = getImage(list);

		item_lists = getData(content, image);
		madapter = new MusicListAdapter(this, item_lists);

		lv.setAdapter(madapter);
		
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("content", MusicTools.asString(content));
				intent.setClass(MusicList.this, CodeActivity.class);
				startActivity(intent);
			}
		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, final View view,final int position, long id) {
				new AlertDialog.Builder(MusicList.this)
				               .setTitle("删除")
				               .setPositiveButton("您确定要删除该项分享吗", new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
						            TextView contentview = (TextView) view.findViewById(R.id.content_item);    //获取显示分享内容的控件
						            String text = contentview.getText().toString();  
									
						            dao.deleteItem(text);
						            item_lists.remove(madapter.getItem(position));
						    		madapter.notifyDataSetChanged();
								}
							}).create().show();
				return false;
			}
		});
	}
	
	/**
	 * 获得列表数据
	 */
	public List<Map<String,Object>> getData(String[] content,Object[] image){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=0;i<content.length;i++){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("content", content[i]);
			map.put("image", image[i]);
			list.add(map);
		}
		return list;
	}
	
	
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
	
	/**
	 * 从队列中获得图片数据并存入数组
	 * @param list
	 * @return
	 */
	public Bitmap[] getImage(ArrayList<MusicBean> list){
		Bitmap[] data = new Bitmap[list.size()];
		for(int i=0;i<list.size();i++){
			byte[] photo = list.get(i).getImage();
			Bitmap bmpout = BitmapFactory.decodeByteArray(photo, 0, photo.length);
			data[i] = bmpout;
		}
		return data;
	}
}

package com.music.adapter;

import java.util.List;
import java.util.Map;

import com.example.test_music.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 音乐列表信息适配器类
 * @author Administrator
 *
 */
public class MusicListAdapter extends BaseAdapter{
	
	private List<Map<String, Object>> data;
	private LayoutInflater layoutInflater;
	@SuppressWarnings("unused")
	private Context context;
	private ImageView image_item;
	private TextView content_item;

	public MusicListAdapter(Context context, List<Map<String, Object>> data) {
		this.context = context;
		this.data = data;
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	/**
	 * 获取单列数据
	 */
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.music_item, null);
		}
		image_item = (ImageView) convertView.findViewById(R.id.image_item);
		content_item = (TextView) convertView.findViewById(R.id.content_item);
		image_item.setImageBitmap((Bitmap)data.get(position).get("image"));
		content_item.setText((String) data.get(position).get("content"));
		return convertView;
	}
	
	

}

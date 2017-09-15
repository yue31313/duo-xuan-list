package com.example.checklistdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	private Context ct;
	private ListView lvContact;
	private ArrayList<HashMap<String, Object>> data;
	public Map<Integer, Boolean> checkedMap;

	public ArrayList<HashMap<String, Object>> getData() {
		return data;
	}
	private Drawable getImage(String touxiang) {
		switch (Integer.parseInt(touxiang)) {
		case 0:
			return ct.getResources().getDrawable(R.drawable.img0);
		case 1:
			return ct.getResources().getDrawable(R.drawable.img1);
		case 2:
			return ct.getResources().getDrawable(R.drawable.img2);
		case 3:
			return ct.getResources().getDrawable(R.drawable.img3);
		case 4:
			return ct.getResources().getDrawable(R.drawable.img4);
		case 5:
			return ct.getResources().getDrawable(R.drawable.img5);
		case 6:
			return ct.getResources().getDrawable(R.drawable.img6);
		case 7:
			return ct.getResources().getDrawable(R.drawable.img7);
		case 8:
			return ct.getResources().getDrawable(R.drawable.img8);
		case 9:
			return ct.getResources().getDrawable(R.drawable.img9);
		default:
			throw new IllegalArgumentException("´íÎóµÄÍ¼Æ¬Ë÷Òý£¡");
		}
	}
	public static ArrayList<HashMap<String, Object>> queryContact(Context ct,
			String where, String[] args, String orderColumn) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (int i = 0; i < 100; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", i + 1);
			map.put("name", "name" + i);
			map.put("touxiang", (i % 10));
			data.add(map);
		}
		return data;
	}

	private void setData() {
		data = queryContact(ct, null, null, null);
		for (HashMap<String, Object> m : data) {
			checkedMap.put(Integer.parseInt(m.get("id") + ""), false);
		}
	}

	public ContactAdapter(Context ct) {
		this.ct = ct;
		checkedMap = new HashMap<Integer, Boolean>();

		setData();
	}

	public Map<Integer, Boolean> getCheckedMap() {
		return checkedMap;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HashMap<String, Object> map = data.get(position);
		final String name = map.get("name") + "";
		final String touxiang = map.get("touxiang") + "";
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(ct).inflate(
					R.layout.contact_item, null);

			viewHolder = new ViewHolder();
			viewHolder.checkBox = (CheckBox) convertView
					.findViewById(R.id.contactitem_select_cb);
			// ·Ö¸îÀ¸
			viewHolder.tvCatalog = (TextView) convertView
					.findViewById(R.id.contactitem_catalog);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.item_name);
			viewHolder.touxiang = (ImageView) convertView
					.findViewById(R.id.contactitem_touxiang);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		boolean isShowTitle = false;

		viewHolder.checkBox.setChecked(checkedMap.get(Integer.parseInt(""
				+ map.get("id"))));
		if (isShowTitle) {
			viewHolder.tvCatalog.setVisibility(View.VISIBLE);
		} else {
			viewHolder.tvCatalog.setVisibility(View.GONE);
		}
		viewHolder.touxiang.setImageDrawable(getImage(touxiang)); 
		viewHolder.name.setText(name);
		return convertView;
	}

	static class ViewHolder {
		TextView tvCatalog;
		CheckBox checkBox;
		ImageView touxiang;
		TextView name;

	}

}
package com.example.checklistdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnItemClickListener {
	private ListView lvContact;
	private HorizontalListView checkedContack;
	private ContactAdapter adapter;
	private CheckedImgAdapter checkedAdapter;
	private Button makesureBtn;
	private ArrayList<CheckedImg> checkedList;

	public static class CheckedImg {
		public String touxiang;
		public String name;
		public String id;
	}

	private void initCheckedList() {
		CheckedImg img = new CheckedImg();
		img.id = "default";
		img.name = "default";
		img.touxiang = "none";
		checkedList.add(img);
	}

	/**
	 * 移除水平图片.
	 * 
	 * @param _name
	 */
	private void removeByName(String _name) {
		checkedList.remove(findCheckedPositionByName(_name));
		if (checkedList.size() == 4) {
			if (findCheckedPositionByName("default") == -1) {
				CheckedImg img = new CheckedImg();
				img.id = "default";
				img.name = "default";
				img.touxiang = "none";
				checkedList.add(img);
			}
		}
		makesureBtn.setText("确定(" + --countChecked + ")");
	}

	/**
	 * 选择checkbox，添加水平图片.
	 * 
	 * @param _name
	 * @param _id
	 * @param _touxiang
	 */
	private void addToCheckedList(String _name, String _id, String _touxiang) {
		CheckedImg map2 = new CheckedImg();
		map2.name = _name;
		map2.id = _id;
		map2.touxiang = _touxiang;
		if (checkedList.size() < maxCount)
			checkedList.add(checkedList.size() - 1, map2);
		else
			checkedList.add(map2);

		if (checkedList.size() == maxCount + 1) {
			if (findCheckedPositionByName("default") != -1)
				checkedList.remove(findCheckedPositionByName("default"));
		} else if (checkedList.size() == maxCount - 1) {
			if (findCheckedPositionByName("default") == -1) {
				CheckedImg img = new CheckedImg();
				img.id = "default";
				img.name = "default";
				img.touxiang = "none";
				checkedList.add(img);
			}
		}
		makesureBtn.setText("确定(" + ++countChecked + ")");
	}

	private boolean hasMeasured = false;
	private int maxCount = 5;// 这里要是可以根据屏幕计算出来最多容纳多少item就好了.没有找到合适的方法.
	private int countChecked = 0;// 得到选中的数量.

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.contactlist);
		lvContact = (ListView) this.findViewById(R.id.ListView_catalog);
		adapter = new ContactAdapter(MainActivity.this);
		lvContact.setAdapter(adapter);
		lvContact.setOnItemClickListener(this);

		checkedContack = (HorizontalListView) this.findViewById(R.id.imgList);
		checkedList = new ArrayList<CheckedImg>();
		initCheckedList();
		checkedAdapter = new CheckedImgAdapter(MainActivity.this, checkedList);
		makesureBtn = (Button) findViewById(R.id.makesureBtn);
		checkedContack.setAdapter(checkedAdapter); 
		checkedContack.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CheckedImg img = checkedList.get(position);
				Map<Integer, Boolean> checkedMap = adapter.getCheckedMap();
				if (!img.id.equals("default")) {
					checkedMap.put(Integer.parseInt(img.id), false);
					adapter.notifyDataSetChanged();
					removeByName(img.name);
				}
				checkedAdapter.notifyDataSetChanged();
			}
			
		});
		
	}

	/**
	 * 根据name查询对应的索引.
	 * 
	 * @param name
	 * @return
	 */
	private int findCheckedPositionByName(String name) {
		int i = -1;
		for (CheckedImg m : checkedList) {
			i++;
			if (name.equals(m.name)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ArrayList<HashMap<String, Object>> list = adapter.getData();
		Map<Integer, Boolean> checkedMap = adapter.getCheckedMap();
		HashMap<String, Object> map = list.get(position);
		RelativeLayout layout = (RelativeLayout) view;
		LinearLayout layout2 = (LinearLayout) layout.getChildAt(1);
		String _id = "" + map.get("id");
		String _name = "" + map.get("name");
		String _touxiang = "" + map.get("touxiang");

		CheckBox ckb = (CheckBox) layout2.getChildAt(0);
		if (ckb.isChecked()) {
			checkedMap.put(Integer.parseInt(_id), false);
			removeByName(_name);
			checkedAdapter.notifyDataSetChanged();
		} else {
			checkedContack.setSelection(adapter.getCount()-1);
			checkedMap.put(Integer.parseInt(_id), true);
			addToCheckedList(_name, _id, _touxiang);
			checkedAdapter.notifyDataSetChanged();
			checkedContack.setSelection(checkedAdapter.getCount()-1);
		}
		adapter.notifyDataSetChanged();
	}
}
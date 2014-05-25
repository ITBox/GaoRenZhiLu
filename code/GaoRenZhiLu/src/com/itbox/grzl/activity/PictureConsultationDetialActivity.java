package com.itbox.grzl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.grzl.R;

public class PictureConsultationDetialActivity extends BaseActivity {
	@InjectView(R.id.list)
	ListView mListView;

	private View mHeaderView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_picture_consultation_detial);
		ButterKnife.inject(this);
		mHeaderView = View.inflate(this, R.layout.layout_comment_list_header,
				null);
		mListView.addHeaderView(mHeaderView);
		CommentAdapter adapter = new CommentAdapter();
		mListView.setAdapter(adapter);
	}

	@OnClick(R.id.btn_buy)
	public void buy() {
		Intent intent = new Intent(this, PayActivity.class);
		startActivity(intent);
	}

	private class CommentAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 100;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			return View.inflate(PictureConsultationDetialActivity.this,
					R.layout.layout_comment_item, null);
		}

	}

}

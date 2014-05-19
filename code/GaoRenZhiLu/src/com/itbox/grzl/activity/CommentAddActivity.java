package com.itbox.grzl.activity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.net.GsonResponseHandler;
import com.itbox.grzl.AppContext;
import com.itbox.grzl.R;
import com.itbox.grzl.bean.CommentAdd;
import com.itbox.grzl.bean.RespResult;
import com.itbox.grzl.engine.CommentEngine;

/**
 * 添加论坛界面
 * 
 * @author byz
 * @date 2014-5-11下午4:26:37
 */
public class CommentAddActivity extends BaseActivity {

	@InjectView(R.id.text_medium)
	protected TextView mTitleTv;
	@InjectView(R.id.et_title)
	protected EditText mTitleEt;
	@InjectView(R.id.et_content)
	protected EditText mContentEt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_add);

		ButterKnife.inject(this);

		initView();
	}

	private void initView() {
		mTitleTv.setText("发布论坛");
	}

	@OnClick(R.id.bt_add)
	public void onClick(View v) {
		// 发布论坛
		CommentAdd bean = new CommentAdd();
		String title = mTitleEt.getText().toString();
		String content = mContentEt.getText().toString();
		bean.setCommentcontent(content);
		bean.setTitle(title);
		bean.setPhoto("http://img.dofay.com/2014/03/20140324qc10.jpeg");
		bean.setUserid(AppContext.getInstance().getAccount().getUserid()
				.toString());
		showProgressDialog("正在发布...");
		CommentEngine.addComment(bean, new GsonResponseHandler<RespResult>(
				RespResult.class) {
			@Override
			public void onFinish() {
				super.onFinish();
				dismissProgressDialog();
			}

			@Override
			public void onSuccess(RespResult result) {
				if (result.isSuccess()) {
					showToast("发布成功");
					finish();
				} else {
					showToast("发布失败");
				}
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				showToast(content);
			}
		});
	}
}

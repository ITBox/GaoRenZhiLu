package com.itbox.grzl.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.itbox.fx.util.ImageUtils;
import com.itbox.grzl.AppContext;
import com.zhaoliewang.grzl.R;
import com.itbox.grzl.api.ConsultationApi;
import com.itbox.grzl.api.ConsultationApi.AskQuestionListener;

public class PublishConsultationActivity extends Activity implements
		AskQuestionListener {

	private final String DIR = Environment.getExternalStorageDirectory()
			+ "/gaorenzhilu";

	private final String TEMP_FEED_IMAGE_PATH = DIR + "TEMP_FEED_IMAGE.jpg";

	private Uri imageFileUri;
	@InjectView(R.id.et_title)
	EditText titleEditText;

	@InjectView(R.id.et_content)
	EditText contentEditText;

	@InjectView(R.id.iv)
	ImageView mImageView;

	@InjectView(R.id.tv_add_picture)
	TextView addPicture;
	@InjectView(R.id.tv_select_jobtype)
	TextView selectJobtype;

	@InjectView(R.id.btn_add)
	Button btnAdd;

	private String[] jobNames;

	private int jobType = -1;

	private File file;

	private ConsultationApi api;

	private ProgressDialog dialog;

	@InjectView(R.id.text_medium)
	TextView mediumTextView;
	@InjectView(R.id.text_left)
	TextView leftTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_pic_consultation);
		ButterKnife.inject(this);
		mediumTextView.setText("图文咨询");
		leftTextView.setVisibility(View.VISIBLE);

		jobNames = AppContext.getJobNameArray();
		File file = new File(DIR);
		file.mkdir();
		dialog = new ProgressDialog(this);
		dialog.setMessage("正在发表提问...");
	}

	@OnClick(R.id.btn_add)
	public void addConsultation() {
		String title = titleEditText.getText().toString();
		String content = contentEditText.getText().toString();
		file = new File(TEMP_FEED_IMAGE_PATH);
		if (TextUtils.isEmpty(title)) {
			Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(content)) {
			Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (jobType == -1) {
			Toast.makeText(this, "请选择工作类型", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!file.exists()) {
			Toast.makeText(this, "请添加图片", Toast.LENGTH_SHORT).show();
			return;
		}
		api = new ConsultationApi();
		api.setmAskQuestionListener(this);
		api.freeAskQuestion(title, jobType + "", file, content, "14");
	}

	@OnClick(R.id.text_left)
	public void back() {
		finish();
	}

	@OnClick(R.id.tv_select_jobtype)
	public void selectJobType() {
		AlertDialog.Builder builder = new Builder(this);

		builder.setItems(jobNames, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				jobType = arg1;
				selectJobtype.setText(jobNames[0]);
			}
		}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 1) {
				imageFileUri = data.getData();
			}
			Bitmap bitmap = ImageUtils
					.getBitmap(this, null, null, imageFileUri, 800, 600);
			mImageView.setVisibility(View.VISIBLE);
			mImageView.setImageBitmap(bitmap);
			ImageUtils.Bitmap2File(bitmap, TEMP_FEED_IMAGE_PATH);

		}
	}

	@OnClick(R.id.tv_add_picture)
	public void getPic() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setItems(new String[] { "拍照", "从图库选择" },
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							imageFileUri = getContentResolver()
									.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											new ContentValues());
							if (imageFileUri != null) {
								Intent intent = new Intent(
										android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								intent.putExtra(
										android.provider.MediaStore.EXTRA_OUTPUT,
										imageFileUri);
								// 开启系统拍照的Activity
								startActivityForResult(intent, 0);
							}
							break;
						case 1:
							Intent intent = new Intent(
									"android.intent.action.PICK");
							intent.setType("image/*");
							startActivityForResult(intent, 1);
							break;
						}

					}
				}).show();

	}

	@Override
	public void onStartAsk() {
		dialog.show();
	}

	@Override
	public void onSuccess() {
		dialog.dismiss();
		Toast.makeText(this, "提问成功", Toast.LENGTH_SHORT).show();
		finish();
	}

	@Override
	public void onFail() {
		dialog.dismiss();
		Toast.makeText(this, "提问失败", Toast.LENGTH_SHORT).show();
	}
}

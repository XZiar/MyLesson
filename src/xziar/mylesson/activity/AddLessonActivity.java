package xziar.mylesson.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import xziar.mylesson.R;
import xziar.mylesson.data.LessonBean;
import xziar.mylesson.view.FromToPicker;

public class AddLessonActivity extends Activity
{
	private EditText txtLN, txtTN, txtAddr;
	private FromToPicker npTime;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_lesson);
		txtLN = (EditText) findViewById(R.id.lname);
		txtTN = (EditText) findViewById(R.id.tname);
		txtAddr = (EditText) findViewById(R.id.laddr);
		npTime = (FromToPicker) findViewById(R.id.npTime);
	}
	
	public void onBtnYes(View view)
	{
		LessonBean lb = new LessonBean();
		lb.lessonName = txtLN.getText().toString();
		lb.teacher = txtTN.getText().toString();
		lb.place = txtAddr.getText().toString();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("confirm").setPositiveButton("确定",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface arg0, int arg1)
					{
					}
				});
		// 透明
		final AlertDialog dlg = builder.create();
		Window window = dlg.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.9f;
		window.setAttributes(lp);
		dlg.show();
	}

	public void onBtnClose(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Abort").setPositiveButton("确定",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface arg0, int arg1)
					{
						finish();
					}
				});
		// 透明
		final AlertDialog dlg = builder.create();
		Window window = dlg.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.9f;
		window.setAttributes(lp);
		dlg.show();
	}
	
}

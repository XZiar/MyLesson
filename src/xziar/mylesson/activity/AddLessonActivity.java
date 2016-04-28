package xziar.mylesson.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import xziar.mylesson.R;

public class AddLessonActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_lesson);
	}
	
	public void onBtnYes(View view)
	{
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

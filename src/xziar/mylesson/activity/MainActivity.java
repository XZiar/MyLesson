package xziar.mylesson.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import xziar.mylesson.R;
import xziar.mylesson.data.DBUtil;
import xziar.mylesson.data.LessonBean;
import xziar.mylesson.view.lessonview.LessonBlock;
import xziar.mylesson.view.lessonview.LessonView;
import xziar.mylesson.view.lessonview.LessonView.OnChooseItemListener;

public class MainActivity extends Activity
{
	private final static int REQUESTCODE_Add = 1;
	private final static int REQUESTCODE_Mod = 2;
	private static Context context = null;
	private LessonView lview = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.v("tester", "Context create");
		context = getApplicationContext();

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		lview = (LessonView) findViewById(R.id.lv);

		DBUtil.onInit(getFilesDir());
		
		lview.setData(DBUtil.query());
		lview.SetOnChooseItemListener(new OnChooseItemListener()
		{
			@Override
			public void onChoose(LessonBlock lb)
			{
				Intent it = new Intent();
				it.setClass(MainActivity.this, ModLessonActivity.class);
				it.putExtra("LessonBean", (LessonBean) lb);
				startActivityForResult(it, REQUESTCODE_Mod);
			}
		});
	}

	@Override
	protected void onDestroy()
	{
		DBUtil.onExit();
		super.onDestroy();
	}

	public void onBtnAdd(View view)
	{
		Intent it = new Intent();
		it.setClass(this, AddLessonActivity.class);
		startActivityForResult(it, REQUESTCODE_Add);
	}

	public void onBtnSetting(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(
				MainActivity.this);
		builder.setMessage("System Setting").setPositiveButton("È·¶¨",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface arg0, int arg1)
					{
					}
				});
		// Í¸Ã÷
		final AlertDialog dlg = builder.create();
		Window window = dlg.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 0.9f;
		window.setAttributes(lp);
		dlg.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK)
		{
			LessonBean lb;
			switch(requestCode)
			{
			case REQUESTCODE_Add:
				lb = (LessonBean) data.getSerializableExtra("LessonBean");
				DBUtil.add(lb);
				lview.setData(DBUtil.query());
				break;
			case REQUESTCODE_Mod:
				lb = (LessonBean) data.getSerializableExtra("LessonBean");
				DBUtil.delete(lb);
				DBUtil.add(lb);
				lview.setData(DBUtil.query());
				break;
			}
		}
	}

	public static Context getContext()
	{
		return context;
	}
}

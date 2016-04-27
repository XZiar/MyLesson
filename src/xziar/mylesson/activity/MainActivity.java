package xziar.mylesson.activity;

import xziar.mylesson.R;
import xziar.mylesson.data.DatabaseUtil;
import xziar.mylesson.data.LessonBean;
import xziar.mylesson.lessonview.LessonBlock;
import xziar.mylesson.lessonview.LessonView;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class MainActivity extends Activity
{
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
		
		DatabaseUtil.onInit(getFilesDir());
		
		for (int a = 0; a < 7; a++)
		{
			for (int b = 0; b < 12; b += 4)
			{
				LessonBean lb = new LessonBean();
				lb.timeWeek = a;
				lb.timeFrom = b;
				lb.timeLast = 3;
				lb.lessonName = "手机软件开发";
				lb.place = a + "楼" + b + "室";
				lb.color = 0xff40b060;
				DatabaseUtil.add(lb);
			}
		}
		lview = (LessonView) findViewById(R.id.lv);
		
		lview.setData(DatabaseUtil.query());
	}
	
	@Override
	protected void onDestroy()
	{
		DatabaseUtil.onExit();
		super.onDestroy();
	}
	
	public static Context getContext()
	{
		return context;
	}
}

package xziar.mylesson.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import xziar.mylesson.R;
import xziar.mylesson.data.DBUtil;
import xziar.mylesson.data.LessonBean;
import xziar.mylesson.view.lessonview.LessonBlock;
import xziar.mylesson.view.lessonview.LessonView;
import xziar.mylesson.view.lessonview.LessonView.OnChooseItemListener;

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
		lview = (LessonView) findViewById(R.id.lv);
		
		DBUtil.onInit(getFilesDir());
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
				DBUtil.add(lb);
			}
		}
		lview.setData(DBUtil.query());
		
		lview.SetOnChooseItemListener(new OnChooseItemListener(){
			@Override
			public void onChoose(LessonBlock lb)
			{
				DBUtil.delete((LessonBean)lb);
				lview.setData(DBUtil.query());
			}
		});
	}
	
	@Override
	protected void onDestroy()
	{
		DBUtil.onExit();
		super.onDestroy();
	}
	
	public static Context getContext()
	{
		return context;
	}
}

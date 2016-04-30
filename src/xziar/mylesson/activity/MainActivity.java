package xziar.mylesson.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import xziar.mylesson.R;
import xziar.mylesson.data.DBUtil;
import xziar.mylesson.data.LessonBean;
import xziar.mylesson.util.SizeUtil;
import xziar.mylesson.view.lessonview.LessonBlock;
import xziar.mylesson.view.lessonview.LessonView;
import xziar.mylesson.view.lessonview.LessonView.OnChooseItemListener;

public class MainActivity extends Activity
{
	private final static int REQUESTCODE_Add = 1;
	private final static int REQUESTCODE_Mod = 2;
	public final static int RETCODE_Del = 2;
	private static Context context = null;
	private LessonView lview = null;
	private PopupWindow pop = null;
	private TextView popTxtLN, popTxtTN, popTxtWeek, popTxtTP;
	private Button popBtnMod, popBtnDel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		context = getApplicationContext();

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		lview = (LessonView) findViewById(R.id.lv);
		{
			View cont = LayoutInflater.from(context)
					.inflate(R.layout.popup_lesson_detail, null);
			Rect rect = new Rect();
			getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
			int w = rect.right - rect.left - 2 * SizeUtil.dp2px(32);
			pop = new PopupWindow(cont, w, LayoutParams.WRAP_CONTENT);
			pop.setOutsideTouchable(true);
			pop.setBackgroundDrawable(
					getResources().getDrawable(R.drawable.popupwindow));
			popTxtLN = (TextView) cont.findViewById(R.id.lname);
			popTxtTN = (TextView) cont.findViewById(R.id.tname);
			popTxtWeek = (TextView) cont.findViewById(R.id.week);
			popTxtTP = (TextView) cont.findViewById(R.id.timeplace);
			popBtnMod = (Button) cont.findViewById(R.id.btn_mod);
			popBtnDel = (Button) cont.findViewById(R.id.btn_del);
		}
		DBUtil.onInit(getFilesDir());

		lview.setData(DBUtil.query());
		lview.SetOnChooseItemListener(new OnChooseItemListener()
		{
			@Override
			public void onChoose(LessonBlock lb)
			{
				LessonDetail((LessonBean) lb);
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

	}

	private void LessonDetail(final LessonBean lb)
	{
		popTxtLN.setText(lb.lessonName);
		popTxtLN.setTextColor(lb.color);
		popTxtTN.setText(lb.teacher);
		popTxtWeek
				.setText(lb.weekFrom + "-" + lb.weekTo + "÷‹£¨√ø÷‹" + lb.timeWeek);
		popTxtTP.setText(lb.timeFrom + "-" + lb.timeLast + "," + lb.place);
		popBtnMod.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				modLesson(lb);
				pop.dismiss();
			}
		});
		popBtnDel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				delLesson(lb);
				pop.dismiss();
			}
		});
		pop.showAtLocation((View) lview.getParent(), Gravity.CENTER, 0, 0);
	}

	private void modLesson(LessonBean lb)
	{
		Intent it = new Intent();
		it.setClass(MainActivity.this, ModLessonActivity.class);
		it.putExtra("LessonBean", (LessonBean) lb);
		startActivityForResult(it, REQUESTCODE_Mod);
	}

	private void delLesson(LessonBean lb)
	{
		DBUtil.delete(lb);
		lview.setData(DBUtil.query());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK)
		{
			LessonBean lb;
			switch (requestCode)
			{
			case REQUESTCODE_Add:
				lb = (LessonBean) data.getSerializableExtra("LessonBean");
				DBUtil.add(lb);
				break;
			case REQUESTCODE_Mod:
				lb = (LessonBean) data.getSerializableExtra("LessonBean");
				DBUtil.delete(lb);
				DBUtil.add(lb);
				break;
			}
			lview.setData(DBUtil.query());
		}
		else if (resultCode == RETCODE_Del)
		{
			delLesson((LessonBean) data.getSerializableExtra("LessonBean"));
		}
	}

	public static Context getContext()
	{
		return context;
	}
}

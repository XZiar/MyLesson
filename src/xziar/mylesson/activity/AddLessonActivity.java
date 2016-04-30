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
import xziar.mylesson.view.NumberPickerEx;

public class AddLessonActivity extends Activity
{
	private EditText txtLN, txtTN, txtAddr;
	private FromToPicker npWeek, npTime;
	private NumberPickerEx npWeekTime;
	private String[] days = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_lesson);
		txtLN = (EditText) findViewById(R.id.lname);
		txtTN = (EditText) findViewById(R.id.tname);
		txtAddr = (EditText) findViewById(R.id.laddr);
		npWeek = (FromToPicker) findViewById(R.id.npWeek);
		npWeekTime = (NumberPickerEx) findViewById(R.id.npWeekTime);
		npTime = (FromToPicker) findViewById(R.id.npTime);
		
		npWeekTime.setDisplayedValues(days);
	}

	public void onBtnYes(View view)
	{
		LessonBean lb = new LessonBean();
		lb.lessonName = txtLN.getText().toString();
		lb.teacher = txtTN.getText().toString();
		lb.place = txtAddr.getText().toString();
		lb.weekFrom = npWeek.getFromVal();
		lb.weekTo = npWeek.getToVal();
		lb.timeFrom = npTime.getFromVal();
		lb.timeLast = npTime.getToVal() - lb.timeFrom;
		lb.timeWeek = npWeekTime.getValue() + 1;
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
		finish();
	}

}

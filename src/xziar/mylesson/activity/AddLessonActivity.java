package xziar.mylesson.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import xziar.mylesson.R;
import xziar.mylesson.data.LessonBean;
import xziar.mylesson.util.RandomUtil;
import xziar.mylesson.view.FromToPicker;
import xziar.mylesson.view.NumberPickerEx;

public class AddLessonActivity extends Activity
{
	private EditText txtLN, txtTN, txtAddr;
	private FromToPicker npWeek, npTime;
	private NumberPickerEx npWeekTime;
	private String[] days;

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
		
		days = getResources().getStringArray(R.array.weekdays);
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
		lb.timeLast = npTime.getToVal() - lb.timeFrom + 1;
		lb.timeWeek = npWeekTime.getValue() + 1;
		lb.color = RandomUtil.getColor();
		Intent intent = new Intent();
		intent.putExtra("LessonBean", lb);
		setResult(RESULT_OK, intent);
		finish();
	}

	public void onBtnClose(View view)
	{
		setResult(RESULT_CANCELED, null);
		finish();
	}

}

package xziar.mylesson.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import xziar.mylesson.R;
import xziar.mylesson.data.LessonBean;
import xziar.mylesson.view.FromToPicker;
import xziar.mylesson.view.NumberPickerEx;
import xziar.mylesson.view.actionbar.SimpleActionBar;

public class ModLessonActivity extends Activity
{
	private SimpleActionBar actBar;
	private EditText txtLN, txtTN, txtAddr;
	private FromToPicker npWeek, npTime;
	private NumberPickerEx npWeekTime;
	private LessonBean lb;
	private String[] days = { "周一", "周二", "周三", "周四", "周五", "周六", "周日" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_lesson);
		actBar = (SimpleActionBar) findViewById(R.id.actbar);
		txtLN = (EditText) findViewById(R.id.lname);
		txtTN = (EditText) findViewById(R.id.tname);
		txtAddr = (EditText) findViewById(R.id.laddr);
		npWeek = (FromToPicker) findViewById(R.id.npWeek);
		npWeekTime = (NumberPickerEx) findViewById(R.id.npWeekTime);
		npTime = (FromToPicker) findViewById(R.id.npTime);
		
		actBar.setTitle("修改课程");
		npWeekTime.setDisplayedValues(days);
		
		lb = (LessonBean) getIntent().getSerializableExtra("LessonBean");
		txtLN.setText(lb.lessonName);
		txtTN.setText(lb.teacher);
		txtAddr.setText(lb.place);
		npWeek.setFromVal(lb.weekFrom);
		npWeek.setToVal(lb.weekTo);
		npTime.setFromVal(lb.timeFrom);
		npTime.setToVal(lb.timeLast + lb.timeFrom);
		npWeekTime.setValue(lb.timeWeek - 1);
	}

	public void onBtnYes(View view)
	{
		lb.lessonName = txtLN.getText().toString();
		lb.teacher = txtTN.getText().toString();
		lb.place = txtAddr.getText().toString();
		lb.weekFrom = npWeek.getFromVal();
		lb.weekTo = npWeek.getToVal();
		lb.timeFrom = npTime.getFromVal();
		lb.timeLast = npTime.getToVal() - lb.timeFrom;
		lb.timeWeek = npWeekTime.getValue() + 1;
		lb.color = 0xff40b060;
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

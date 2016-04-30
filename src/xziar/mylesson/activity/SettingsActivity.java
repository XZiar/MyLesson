package xziar.mylesson.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import xziar.mylesson.R;

public class SettingsActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);
	}
	
	public void onBtnClose(View view)
	{
		setResult(RESULT_CANCELED, null);
		finish();
	}
	
	public void onBtnYes(View view)
	{
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}
}

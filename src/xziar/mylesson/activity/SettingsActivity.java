package xziar.mylesson.activity;

import android.app.Activity;
import android.os.Bundle;
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
}

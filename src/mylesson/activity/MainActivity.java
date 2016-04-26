package mylesson.activity;

import com.example.mylesson.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class MainActivity extends Activity
{
	private static Context context = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.v("tester", "Context create");
		context = getApplicationContext();
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
	}
	
	public static Context getContext()
	{
		return context;
	}
}

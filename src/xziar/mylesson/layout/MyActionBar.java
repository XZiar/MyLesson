package xziar.mylesson.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class MyActionBar extends LinearLayout
{

	public MyActionBar(Context context)
	{
		super(context);
		init();
	}

	public MyActionBar(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init();
	}

	public MyActionBar(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	private void init()
	{
		
	}
}

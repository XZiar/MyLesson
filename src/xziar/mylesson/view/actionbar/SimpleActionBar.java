package xziar.mylesson.view.actionbar;

import java.lang.reflect.Array;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class SimpleActionBar extends LinearLayout
{

	public SimpleActionBar(Context context)
	{
		super(context);
		init(context);
	}

	public SimpleActionBar(Context context, AttributeSet attrs,
			int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public SimpleActionBar(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	private void init(Context context)
	{
		Log.v("tester", "ActBar from ctx:" + context.getClass().getName());
	}

	@Override
	public void addView(View child, int index,
			android.view.ViewGroup.LayoutParams params)
	{
		Log.v("tester", "----- addView,view,idx,param");
		Class<?>[] viewifs = child.getClass().getInterfaces();
		boolean isAdd = false;
		for(Class<?> viewif : viewifs)
		{
			if(viewif == xziar.mylesson.view.lessonview.LessonBlock.class)
				isAdd = true;//can add
		}
		if(isAdd)
			super.addView(child, index, params);
	}

}

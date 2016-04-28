package xziar.mylesson.view.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class SimpleActionBar extends RelativeLayout
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
		for (Class<?> viewif : viewifs)
		{
			if (viewif == xziar.mylesson.view.actionbar.ActionBarElement.class)
			{
				isAdd = true;// can add
				break;
			}
		}
		if (!isAdd)
			return;
		ActionBarElement abe = (ActionBarElement)child;
		RelativeLayout.LayoutParams parm = (RelativeLayout.LayoutParams) params;
		switch(abe.getAlign())
		{
		case center:
			parm.addRule(RelativeLayout.CENTER_IN_PARENT);
			break;
		case left:
			parm.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			break;
		case right:
			parm.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			break;
		}
		super.addView(child, index, params);
	}

}

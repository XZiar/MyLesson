package xziar.mylesson.view.actionbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SimpleActionBar extends RelativeLayout
{
	private LinearLayout BarLeft, BarMid, BarRight;

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
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		BarLeft = new LinearLayout(context, null);
		BarLeft.setLayoutParams(lp);
		BarLeft.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams parmL = new RelativeLayout.LayoutParams(lp);
		parmL.addRule(RelativeLayout.ALIGN_PARENT_START);
		super.addView(BarLeft, -1, parmL);
		
		BarRight = new LinearLayout(context, null);
		BarRight.setLayoutParams(lp);
		BarRight.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams parmR = new RelativeLayout.LayoutParams(lp);
		parmR.addRule(RelativeLayout.ALIGN_PARENT_END);
		super.addView(BarRight, -1, parmR);
		
		BarMid = new LinearLayout(context, null);
		BarMid.setLayoutParams(lp);
		BarMid.setOrientation(LinearLayout.HORIZONTAL);
		RelativeLayout.LayoutParams parmM = new RelativeLayout.LayoutParams(lp);
		parmM.addRule(RelativeLayout.CENTER_IN_PARENT);
		super.addView(BarMid, -1, parmM);
		
		Log.v("tester", "ActBar from ctx:" + context.getClass().getName());
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params)
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
		ActionBarElement abe = (ActionBarElement) child;
		switch (abe.getAlign())
		{
		case center:
			BarMid.addView(child, -1, params);
			break;
		case left:
			BarLeft.addView(child, -1, params);
			break;
		case right:
			BarRight.addView(child, 0, params);
			break;
		}
	}

}

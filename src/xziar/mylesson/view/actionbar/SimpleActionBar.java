package xziar.mylesson.view.actionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import xziar.mylesson.util.SizeUtil;

public class SimpleActionBar extends RelativeLayout
{
	private LinearLayout BarLeft, BarMid, BarRight;
	private int pad = SizeUtil.dp2px(5);

	public SimpleActionBar(Context context)
	{
		super(context);
		init(context);
	}

	public SimpleActionBar(Context context, AttributeSet attrs,
			int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs,
				new int[] { android.R.attr.layout_margin });
		pad = ta.getDimensionPixelSize(0, 1);
		ta.recycle();
		init(context);
	}

	public SimpleActionBar(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	private void init(Context context)
	{
		BarLeft = new LinearLayout(context, null);
		RelativeLayout.LayoutParams parmL = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		parmL.addRule(RelativeLayout.ALIGN_PARENT_START);
		super.addView(BarLeft, -1, parmL);

		BarRight = new LinearLayout(context, null);
		RelativeLayout.LayoutParams parmR = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		parmR.addRule(RelativeLayout.ALIGN_PARENT_END);
		super.addView(BarRight, -1, parmR);

		BarMid = new LinearLayout(context, null);
		RelativeLayout.LayoutParams parmM = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		parmM.addRule(RelativeLayout.CENTER_IN_PARENT);
		super.addView(BarMid, -1, parmM);

		Log.v("tester", "ActBar from ctx:" + context.getClass().getName());
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params)
	{
		Log.v("tester", "addView,params=" + params.getClass());
		Class<?>[] viewifs = child.getClass().getInterfaces();
		boolean isAdd = false;
		for (Class<?> viewif : viewifs)
		{
			if (viewif == ActionBarElement.class)
			{
				isAdd = true;// can add
				break;
			}
		}
		if (!isAdd)
			return;
		
		ActionBarElement abe = (ActionBarElement) child;
		child.setPaddingRelative(pad, 0, pad, 0);
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
